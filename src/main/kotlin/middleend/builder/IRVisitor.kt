package middleend.builder

import frontend.ast.controller.AstVisitor
import frontend.ast.node.*
import frontend.utils.BuiltinScope
import frontend.utils.FuncScope
import frontend.utils.GlobalScope
import frontend.utils.ScopeManager
import middleend.basic.*
import middleend.helper.LoopManager
import middleend.helper.Utils
import middleend.helper.ValueSymbolTable

class IRVisitor : AstVisitor() {
  private val scopeManger = ScopeManager()
  private val loopManager = LoopManager()
  val topModule = TopModule()
  private var initFunc: Func? = null
  private val globalVst = ValueSymbolTable()

  override fun visit(curr: ProgNode) {
    scopeManger.addLast(curr.scope)
    curr.block.accept(this)
    scopeManger.removeLast()
  }

  override fun visit(curr: ProgBlockNode) {
    /// In order to support forward reference,
    /// class definitions, method declarations, function declarations,
    /// all have to be iterated first to build the type system.
    /// Member declarations are omitted, because there are simply retrieve by index.
    val globalScope = scopeManger.first() as GlobalScope

    // class definitions
    for ((className, classMd) in globalScope.classes) {
      val localScope = classMd.classScope
      val structType = TypeFactory.getStructType(classMd) // create the concrete struct, instead of reference
      topModule.setStruct("class.$className", structType)

      for ((funcName, funcMd) in localScope.methods) {
        // value's name should be reinserted into value symbol table again, because it didn't be renamed.
        val args = funcMd.paramInput.map { Value(TypeFactory.getType(it.second), it.first) }.toMutableList()
        args.add(0, Value(TypeFactory.getPtrToStructType(classMd), "this"))

        topModule.setFunc(
          "$className.$funcName",
          Func(
            "$className.$funcName",
            TypeFactory.getFuncType(funcMd),
            args
          )
        )
      }
    }

    // function definitions
    for ((funcName, funcMd) in globalScope.funcs) {
      val funcType = TypeFactory.getFuncType(funcMd)
      topModule.setFunc(
        funcName,
        Func(
          funcName,
          funcType,
          funcMd.paramInput.map { Value(TypeFactory.getType(it.second), it.first) }
        )
      )
    }

    // builtin function declarations
    for ((funcName, funcMd) in BuiltinScope.funcs) {
      val funcType = TypeFactory.getFuncType(funcMd)
      topModule.setBuiltinFunc(
        funcName,
        Func(
          funcName,
          funcType,
          funcMd.paramInput.map { Value(TypeFactory.getType(it.second)) }
        )
      )
    }

    // set up init func
    // because all variables are suffixed with ".addr", then there is no need to distinguish them
    // this naming custom might be changed in the future
    val initFuncName = "_global_variable_init"
    initFunc = Func(initFuncName, FuncType(initFuncName, listOf(), TypeFactory.getVoidType()), listOf())
    val initBlock = BasicBlock("init")
    initBlock.insertAtTheTailOf(initFunc!!) // it supposes that the initFunc contains only one block

    curr.children.forEach { it.accept(this) }

    // to see if there is a need to init global variable in a particular function
    if (initBlock.instList.size > 0) {
      IRBuilder.resetInsertBlock(initFunc!!.blockList.last())
      IRBuilder.createRetVoid()
      topModule.setFunc(initFuncName, initFunc!!)

      val mainFunc = topModule.getFunc("main")!!
      val mainEntryBlock = mainFunc.blockList.first()
      IRBuilder.resetInsertBlock(mainEntryBlock)
      IRBuilder.createCall(initFunc!!.funcType, listOf(), true)
    }

  }

  override fun visit(curr: FuncBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: ClassBlockNode) {
    curr.children.forEach { if (it !is VarDeclNode) it.accept(this) }
  }

  override fun visit(curr: SimpleBlockNode) {
    curr.child.accept(this)
  }

  override fun visit(curr: ClassDefNode) {
    scopeManger.addLast(curr.classMd)
    curr.classBlock?.accept(this)
    scopeManger.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncDefNode) {
    scopeManger.addLast(curr.funcMd)
    val innerScope = scopeManger.last() as FuncScope
    val recentClass = scopeManger.getRecentClass()

    val funcName = if (recentClass != null) "${recentClass.className}.${innerScope.funcName}" else innerScope.funcName
    val func = topModule.getFunc(funcName)!!
    IRBuilder.setCurrentFunc(func)
    func.vst.addAll(topModule) // avoid conflicts with global value
    IRBuilder.setValueSymbolTable(func.vst)
    val funcType = func.type as FuncType

    val entryBlock = BasicBlock(func.vst.defineName("entry"))
    IRBuilder.setInsertBlock(entryBlock)

    // setup for parameters
    if (func.args.isNotEmpty()) {
      for (arg in func.args) {
        // "this" should be inserted into method's value symbol table and scope, in order to retrieve
        func.vst.insertValue(arg)
        if (arg.name == "this") {
          innerScope.setValue("this", arg)
          continue
        }
        val ptrSuffixed = "${arg.name}.addr"
        val allocaInst = IRBuilder.createAlloca(ptrSuffixed, arg.type)
        innerScope.setValue(ptrSuffixed, allocaInst)
        IRBuilder.createStore(arg, allocaInst)
      }
    }

    // setup for the return value
    if (funcType.result != TypeFactory.getVoidType()) {
      val addrSuffixed = "retaddr"
      val allocaInst = IRBuilder.createAlloca(addrSuffixed, funcType.result)
      innerScope.setValue(addrSuffixed, allocaInst)
      if (funcName == "main") {
        IRBuilder.createStore(ConstantInt(32, 0), allocaInst)
      }
    }

    val returnBlock = BasicBlock("return")
    IRBuilder.setReturnBlock(returnBlock)
    if (funcType.result != TypeFactory.getVoidType()) {
      val addrSuffixed = "retaddr"
      val valSuffixed = "retval"
      val loadInst = IRBuilder.createLoad(valSuffixed, innerScope.getValue(addrSuffixed)!!)
      IRBuilder.createRet(loadInst)
    } else {
      IRBuilder.createRetVoid()
    }

    IRBuilder.resetInsertBlock(entryBlock)
    curr.funcBlock?.accept(this)

    if (!IRBuilder.getInsertBlock()!!.hasTerminator()) {
      IRBuilder.createBr(returnBlock)
    }

    scopeManger.removeLast()
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDeclNode) {
    val innerScope = scopeManger.last()
    val type = TypeFactory.getType(curr.varTypeMd!!)

    if (innerScope !is GlobalScope) { // if it is a local variable
      for (assign in curr.assigns) {
        val ptrSuffixed = "${assign.first}.addr"
        val ptr = IRBuilder.createAlloca(ptrSuffixed, type)
        innerScope.setValue(ptrSuffixed, ptr)
        if (assign.second != null) {
          assign.second!!.accept(this)
          IRBuilder.createStore(assign.second!!.value!!, ptr)
        } else if (type is PointerType) {
          IRBuilder.createStore(ConstantNull(), ptr)
        }
      }
    } else { // if it is a global variable
      IRBuilder.resetInsertBlock(initFunc!!.blockList.last())
      for (assign in curr.assigns) {
        val ptrSuffixed = "${assign.first}.addr"
        val gloVarPtr = GlobalVariable(ptrSuffixed, TypeFactory.getPtrType(type))
        topModule.setGlobalVar(ptrSuffixed, gloVarPtr)
        if (assign.second != null) {
          assign.second!!.accept(this)
          IRBuilder.createStore(assign.second!!.value!!, gloVarPtr)
        }
      }
    }
  }

  override fun visit(curr: ForSuiteNode) {
    val condBlock = BasicBlock("for.cond")
    val incBlock = BasicBlock("for.inc")
    val bodyBlock = BasicBlock("for.body")
    val endBlock = BasicBlock("for.end")

    scopeManger.addLast(curr.scope)
    loopManager.addLast(incBlock, endBlock)

    curr.init?.accept(this)
    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(condBlock)
    if (curr.cond == null) {
      IRBuilder.createBr(bodyBlock)
    } else {
      curr.cond.accept(this)
      IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)
    }

    IRBuilder.setInsertBlock(bodyBlock)
    curr.body.accept(this)
    IRBuilder.createBr(incBlock)

    IRBuilder.setInsertBlock(incBlock)
    curr.inc?.accept(this)
    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(endBlock)

    scopeManger.removeLast()
    loopManager.removeLast()
  }

  override fun visit(curr: WhileSuiteNode) {
    val condBlock = BasicBlock("while.cond")
    val bodyBlock = BasicBlock("while.body")
    val endBlock = BasicBlock("while.end")

    scopeManger.addLast(curr.scope)
    loopManager.addLast(condBlock, endBlock)

    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(condBlock)
    curr.cond.accept(this)
    IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)

    IRBuilder.setInsertBlock(bodyBlock)
    curr.body.accept(this)
    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(endBlock)

    scopeManger.removeLast()
    loopManager.removeLast()
  }

  override fun visit(curr: CondSuiteNode) {
    curr.cond.accept(this)

    val endBlock = BasicBlock("if.end")
    val thenBlock = BasicBlock("if.then")
    val elseBlock: BasicBlock? = if (curr.elseDo != null) BasicBlock("if.else") else null
    if (curr.elseDo != null) {
      IRBuilder.createBr(curr.cond.value!!, thenBlock, elseBlock!!)
    } else {
      IRBuilder.createBr(curr.cond.value!!, thenBlock, endBlock)
    }

    scopeManger.addLast(curr.thenScope)
    IRBuilder.setInsertBlock(thenBlock)
    curr.thenDo.accept(this)
    IRBuilder.createBr(endBlock)
    scopeManger.removeLast()

    if (curr.elseDo != null) {
      scopeManger.addLast(curr.elseScope)
      IRBuilder.setInsertBlock(elseBlock!!)
      curr.elseDo.accept(this)
      IRBuilder.createBr(endBlock)
      scopeManger.removeLast()
    }

    IRBuilder.setInsertBlock(endBlock)
  }

  override fun visit(curr: FieldSuiteNode) {
    curr.block.accept(this)
  }

  override fun visit(curr: JumpNode) {
    when (curr.type) {
      "return" -> {
        if (curr.expr != null) {
          curr.expr.accept(this)
          IRBuilder.createStore(curr.expr.value!!, scopeManger.last().getValue("retaddr")!!)
        }
        IRBuilder.createBr(IRBuilder.getReturnBlock()!!)
      }

      "break" -> {
        IRBuilder.createBr(loopManager.getLastBreakBlock()!!)
      }

      "continue" -> {
        IRBuilder.createBr(loopManager.getLastContinueBlock()!!)
      }
    }
  }

  override fun visit(curr: ShortNode) {
    curr.expr?.accept(this)
  }

  override fun visit(curr: PriorExprNode) {
    curr.expr.accept(this)
    curr.value = curr.expr.value
  }

  override fun visit(curr: AtomNode) {
    val innerScope = scopeManger.last()
    curr.value = when (curr.id) {
      0 -> ConstantInt(32, curr.literal.toInt()) // const int
      1 -> {
        val const = ConstantStr(curr.literal.substring(1, curr.literal.length - 1), globalVst.defineName(".str"))
        topModule.setConst(const.name!!, const)
        IRBuilder.createGEP("array", "str", const, ConstantInt(32, 0))
      } // const str
      2 -> {
        val (varName, varAddr) = getIdsNameAndItsPtr(curr)
        IRBuilder.createLoad(varName, varAddr)
      }

      3 -> innerScope.getValue("this")
      4 -> ConstantInt(8, 1) // const bool for true
      5 -> ConstantInt(8, 0) // const bool for false
      else -> ConstantNull() // const null
    }
  }

  private fun mallocElem(currType: Type): Value {
    val elemByteNum = ConstantInt(32, currType.getAlign())
    val malloc = topModule.getBuiltinFunc("_malloc")!!
    val call = IRBuilder.createCall(malloc.funcType, listOf(elemByteNum))
    return IRBuilder.createBitCast("bitcast", call, TypeFactory.getPtrType(currType))
  }

  private fun mallocArray(currType: PointerType, arraySizeList: List<Value>): Value {
    val arraySize = arraySizeList.first()
    val pureArrayByteNum =
      IRBuilder.createBinary(
        "mul",
        "mul",
        TypeFactory.getIntType(32),
        arraySize,
        ConstantInt(32, currType.getAlign())
      )
    val extendedArrayByteNum =
      IRBuilder.createBinary("add", "add", TypeFactory.getIntType(32), pureArrayByteNum, ConstantInt(32, 4))
    val malloc = topModule.getBuiltinFunc("_malloc")!!
    val createdSpace = IRBuilder.createCall(malloc.funcType, listOf(extendedArrayByteNum))
    val extendedArray = IRBuilder.createBitCast("bitcast", createdSpace, currType)
    val arraySizeAddr =
      IRBuilder.createBitCast("bitcast", extendedArray, TypeFactory.getPtrType(TypeFactory.getIntType(32)))
    IRBuilder.createStore(arraySize, arraySizeAddr)
    val pureArray = IRBuilder.createGEP("ptr", "arrayidx", extendedArray, ConstantInt(32, 1))

    if (arraySizeList.size == 1) {
      return pureArray
    }

    val startPoint = IRBuilder.createGEP("ptr", "arrayidx", pureArray, arraySize)
    val previousBlock = IRBuilder.getInsertBlock()!!
    val condBlock = BasicBlock("while.cond")
    val bodyBlock = BasicBlock("while.body")
    val endBlock = BasicBlock("while.end")

    IRBuilder.createBr(condBlock)
    IRBuilder.setInsertBlock(condBlock)
    val phiInst = IRBuilder.createPhi(
      "phi",
      currType,
      mutableListOf(Pair(startPoint, previousBlock))
    ) // the body block would be split, so no one knows which block will it comes from, maybe the next layer's while end?

    val cond = IRBuilder.createCmp("cmp", "ne", phiInst.type, phiInst, extendedArray)
    IRBuilder.createBr(cond, bodyBlock, endBlock)

    // some preparation for following recursive building
    IRBuilder.setInsertBlock(bodyBlock)
    mallocArray(currType.pointeeTy!! as PointerType, arraySizeList.subList(1, arraySizeList.size))

    val nextElement = IRBuilder.createGEP(
      "ptr",
      "arrayidx",
      phiInst,
      ConstantInt(32, -1)
    )
    phiInst.candidates.add(Pair(nextElement, bodyBlock))
    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(endBlock)
    return pureArray
  }

  override fun visit(curr: InitExprNode) {
    curr.arraySizeExprList.forEach { it?.accept(this) }
    curr.value = if (curr.arraySizeExprList.isNotEmpty()) {
      val currType = TypeFactory.getType(curr.type!!) as PointerType
      val arraySizeList = curr.arraySizeExprList.map { it!!.value!! }
      mallocArray(currType, arraySizeList)
    } else {
      mallocElem(TypeFactory.getBasicType(curr.type!!))
    }
  }


  override fun visit(curr: LambdaCallNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncCallNode) {
    val callee = topModule.getFunc(curr.funcName) ?: topModule.getBuiltinFunc(curr.funcName)!!
    curr.params.forEach { it.accept(this) }
    curr.value = IRBuilder.createCall(
      callee.funcType,
      curr.params.map { it.value!! }) // TODO: determine its type
  }

  override fun visit(curr: MethodCallNode) {
    curr.value = if (curr.method == "size") {
      curr.expr.accept(this)
      val arraySizeAddr = IRBuilder.createGEP("ptr", "arraysize.addr", curr.expr.value!!, ConstantInt(32, -1))
      val finalInst = if (arraySizeAddr.type != TypeFactory.getPtrType(TypeFactory.getIntType(32))) {
        IRBuilder.createBitCast("bitcast", arraySizeAddr, TypeFactory.getPtrType(TypeFactory.getIntType(32)))
      } else {
        arraySizeAddr
      }
      IRBuilder.createLoad("arraysize", finalInst)
    } else {
      val callee = topModule.getFunc("${curr.expr.type!!.cl.className}.${curr.method}")
        ?: topModule.getBuiltinFunc("_${curr.expr.type!!.cl.className}_${curr.method}")!!
      // TODO: size()
      curr.expr.accept(this)
      curr.params.forEach { it.accept(this) }
      IRBuilder.createCall(
        callee.funcType,
        listOf(curr.expr.value!!) + curr.params.map { it.value!! }) // TODO: determine its type
    }
  }

  override fun visit(curr: MemberAccessNode) {
    curr.expr.accept(this)
    val memberName = "${curr.expr.type!!.cl.className}.${curr.member}"
    val pointerType = curr.expr.value!!.type as PointerType
    val structType = pointerType.pointeeTy as StructType
    curr.value =
      IRBuilder.createGEP("struct", memberName, curr.expr.value!!, ConstantInt(32, structType.getIndex(curr.member)))
  }

  override fun visit(curr: ArrayAccessNode) {
    curr.array.accept(this)
    curr.index.accept(this)
    val ptr = IRBuilder.createGEP("ptr", "elem.addr", curr.array.value!!, curr.index.value!!)
    curr.value = IRBuilder.createLoad("elem", ptr)
  }

  private fun getIdsNameAndItsPtr(curr: AtomNode): Pair<String, Value> {
    val innerScope = scopeManger.last()
    val id = curr.literal
    val ptrSuffixed = "$id.addr"
    val ptr = innerScope.getValue(ptrSuffixed)
    return if (ptr == null) {
      val recentClass = scopeManger.getRecentClass()
      if (recentClass != null && recentClass.memberToIndex.containsKey(id)) {
        // now we could know that this is a class member
        val memberName = "${recentClass.className}.$id"
        val structType = topModule.getStruct("class.${recentClass.className}")
        Pair(
          memberName,
          IRBuilder.createGEP(
            "struct",
            "${memberName}.addr",
            innerScope.getValue("this")!!,
            ConstantInt(32, structType.getIndex(id))
          )
        )
      } else {
        Pair(id, topModule.getGlobalVar(ptrSuffixed))
      }
    } else {
      Pair(id, ptr)
    }
  }

  private fun getVarsNameAndItsPtr(curr: ExprNode): Pair<String, Value> {
    return when (curr) {
      is AtomNode -> getIdsNameAndItsPtr(curr)

      is ArrayAccessNode -> {
        curr.array.accept(this)
        curr.index.accept(this)
        Pair("elem", IRBuilder.createGEP("ptr", "elem.addr", curr.array.value!!, curr.index.value!!))
      }

      is MemberAccessNode -> {
        curr.expr.accept(this)
        val className = curr.expr.type!!.cl.className
        val memberName = "$className.${curr.member}"
        val structType = topModule.getStruct("class.$className")
        Pair(
          memberName,
          IRBuilder.createGEP(
            "struct",
            memberName,
            curr.expr.value!!,
            ConstantInt(32, structType.getIndex(curr.member))
          )
        )
      }

      else -> TODO() // I don't know that exact implementation detail of struct
    }
  }

  override fun visit(curr: SuffixExprNode) {
    val op = Utils.unaryOpToStr(curr.op)
    val (varName, varPtr) = getVarsNameAndItsPtr(curr.expr)
    curr.expr.value = IRBuilder.createLoad(varName, varPtr)
    val varType = TypeFactory.getType(curr.type!!)
    val binaryInst = IRBuilder.createBinary(
      op,
      if (op == "inc") "add" else "sub",
      varType,
      curr.expr.value!!,
      ConstantInt(32, 1)
    )
    IRBuilder.createStore(binaryInst, varPtr)
    curr.value = curr.expr.value
  }

  override fun visit(curr: PrefixExprNode) {
    val op = Utils.unaryOpToStr(curr.op)
    curr.value = when (curr.op) {
      "++", "--" -> {
        val (varName, varPtr) = getVarsNameAndItsPtr(curr.expr)
        curr.expr.value = IRBuilder.createLoad(varName, varPtr)
        val varType = TypeFactory.getIntType(32)
        val binaryInst = IRBuilder.createBinary(
          op,
          if (op == "inc") "add" else "sub",
          varType,
          curr.expr.value!!,
          ConstantInt(32, 1)
        )
        IRBuilder.createStore(binaryInst, varPtr)
        binaryInst
      }

      "+" -> {
        curr.expr.accept(this)
        curr.expr.value
      }

      "-" -> {
        curr.expr.accept(this)
        IRBuilder.createBinary(
          op,
          "sub",
          TypeFactory.getIntType(32),
          ConstantInt(32, 0),
          curr.expr.value!!
        )
      }

      "~" -> {
        curr.expr.accept(this)
        IRBuilder.createBinary(
          op,
          "xor",
          TypeFactory.getIntType(32),
          curr.expr.value!!,
          ConstantInt(32, -1)
        )
      }

      else -> { // !
        curr.expr.accept(this)
        val truncInst = IRBuilder.createTrunc(
          op,
          curr.expr.value!!,
          TypeFactory.getIntType(1)
        )
        IRBuilder.createBinary(
          op,
          "xor",
          truncInst.type,
          truncInst,
          ConstantInt(1, 1)
        )
      }
    }
  }

  // it gives out a value containing a temporary result of given binary operator
  override fun visit(curr: BinaryExprNode) {
    curr.value = when (curr.ops.first()) {
      "+" -> {
        var lhs: Value? = null
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          val rhs = expr.value!!
          if (lhs == null) {
            lhs = rhs
            continue
          }
          val op = Utils.binaryOpToStr(curr.ops[index - 1])
          lhs = if (lhs.type is PointerType && rhs.type is PointerType) {
            val func = topModule.getBuiltinFunc("_string_concat")!!
            IRBuilder.createCall(func.funcType, listOf(lhs, rhs))
          } else {
            IRBuilder.createBinary(op, op, TypeFactory.getType(curr.type!!), lhs, rhs)
          }
        }
        lhs
      }

      "*", "/", "%", "-", "<<", ">>", "&", "^", "|" -> {
        var lhs: Value? = null
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          val rhs = expr.value!!
          if (lhs == null) {
            lhs = rhs
            continue
          }
          val op = Utils.binaryOpToStr(curr.ops[index - 1])
          lhs = IRBuilder.createBinary(op, op, TypeFactory.getType(curr.type!!), lhs, rhs)
        }
        lhs
      }

      else -> { // namely <, <=, >, >=, ==, !=
        var lhs: Value? = null
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          val rhs = expr.value
          if (lhs == null) {
            lhs = rhs
            continue
          }
          val op = Utils.binaryOpToStr(curr.ops[index - 1])
          lhs = IRBuilder.createCmp("cmp", op, lhs.type, lhs, rhs!!)
        }
        lhs
      }
    }
  }

  override fun visit(curr: LogicalOrExprNode) {
    val endBlock = BasicBlock("lor.end")
    val candidates: MutableList<Pair<Value, BasicBlock>> = mutableListOf()
    val size = curr.exprs.size
    for ((index, expr) in curr.exprs.withIndex()) {
      expr.accept(this)
      candidates.add(
        Pair(if (index == size - 1) expr.value!! else ConstantInt(8, 1), IRBuilder.getInsertBlock()!!)
      )
      if (index == size - 1) {
        IRBuilder.createBr(endBlock)
      } else {
        val rhsBlock = BasicBlock(if (index != size - 2) "lor.lhs.false" else "lor.rhs")
        IRBuilder.createBr(expr.value!!, endBlock, rhsBlock)
        // TODO: create something called type?
        IRBuilder.setInsertBlock(rhsBlock)
      }
    }
    IRBuilder.setInsertBlock(endBlock)
    curr.value = IRBuilder.createPhi("phi", TypeFactory.getIntType(1), candidates)
  }

  override fun visit(curr: LogicalAndExprNode) {
    val endBlock = BasicBlock("land.end")
    val candidates: MutableList<Pair<Value, BasicBlock>> = mutableListOf()
    val size = curr.exprs.size
    for ((index, expr) in curr.exprs.withIndex()) {
      expr.accept(this)
      val value = if (index == size - 1) {
        if (expr.value!!.type == TypeFactory.getIntType(1)) {
          expr.value!!
        } else {
          IRBuilder.createTrunc("trunc", expr.value!!, TypeFactory.getIntType(1))
        }
      } else {
        ConstantInt(8, 0)
      }
      candidates.add(Pair(value, IRBuilder.getInsertBlock()!!))
      if (index == size - 1) {
        IRBuilder.createBr(endBlock)
      } else {
        val rhsBlock = BasicBlock(if (index != size - 2) "land.lhs.true" else "land.rhs")
        IRBuilder.createBr(expr.value!!, rhsBlock, endBlock)
        // TODO: create something called type?
        IRBuilder.setInsertBlock(rhsBlock)
      }
    }
    IRBuilder.setInsertBlock(endBlock)
    curr.value = IRBuilder.createPhi("phi", TypeFactory.getIntType(1), candidates)
  }

  override fun visit(curr: AssignExprNode) {
    val (_, ptr) = getVarsNameAndItsPtr(curr.lhs)
    curr.rhs.accept(this)
    IRBuilder.createStore(curr.rhs.value!!, ptr)
  }
}
