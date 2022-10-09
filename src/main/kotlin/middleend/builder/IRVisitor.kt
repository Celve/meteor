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
      IRBuilder.setInsertBlock(initBlock)
      IRBuilder.createRetVoid()
      topModule.setFunc(initFuncName, initFunc!!)

      val mainFunc = topModule.getFunc("main")!!
      val mainEntryBlock = mainFunc.blockList.first()
      IRBuilder.setInsertBlock(mainEntryBlock)
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
        func.vst.reinsertValue(arg)
        if (arg.name == "this") {
          innerScope.setValue("this", arg)
          continue
        }
        val ptrSuffixed = "${arg.name}.addr"
        val allocaInst = IRBuilder.createAlloca(ptrSuffixed, arg.type)
        innerScope.setValue(ptrSuffixed, allocaInst)
        IRBuilder.createStore(arg.type, arg, allocaInst)
      }
    }

    // setup for the return value
    if (funcType.result != TypeFactory.getVoidType()) {
      val addrSuffixed = "retaddr"
      val allocaInst = IRBuilder.createAlloca(addrSuffixed, funcType.result)
      innerScope.setValue(addrSuffixed, allocaInst)
      if (funcName == "main") {
        IRBuilder.createStore(funcType.result, ConstantInt(32, 0), allocaInst)
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
          IRBuilder.createStore(type, assign.second!!.value!!, ptr)
        } else if (type is PointerType) {
          IRBuilder.createStore(type, ConstantNull(), ptr)
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
          IRBuilder.createStore(type, assign.second!!.value!!, gloVarPtr)
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
          IRBuilder.createStore(curr.expr.value!!.type, curr.expr.value!!, scopeManger.last().getValue("retaddr")!!)
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
        IRBuilder.createGEP("str", const.type as ArrayType, const, ConstantInt(32, 0))
      } // const str
      2 -> {
        val (varName, varAddr) = getIdsNameAndItsPtr(curr)
        IRBuilder.createLoad(varName, varAddr)
      }

      3 -> innerScope.getValue("this")
      4 -> ConstantInt(1, 1) // const bool for true
      5 -> ConstantInt(1, 0) // const bool for false
      else -> ConstantNull() // const null
    }
  }

  override fun visit(curr: InitExprNode) {
    var currType = TypeFactory.getType(curr.type!!) as PointerType
    val loopStack = ArrayDeque<Triple<Value, PhiInst, BasicBlock>>()
    for ((index, arraySizeExpr) in curr.arraySizeExprList.withIndex()) {
      if (arraySizeExpr == null) {
        break
      }
      arraySizeExpr.accept(this)
      val arrayByteNum =
        IRBuilder.createBinary(
          "mul",
          "mul",
          TypeFactory.getIntType(32),
          arraySizeExpr.value!!,
          ConstantInt(32, currType.getAlign())
        )
      val extendedArrayByteNum =
        IRBuilder.createBinary("add", "add", TypeFactory.getIntType(32), arrayByteNum, ConstantInt(32, 4))
      val callee = topModule.getBuiltinFunc("_malloc")!!
      val createdSpace = IRBuilder.createCall(callee.funcType, listOf(extendedArrayByteNum))
      val extendedArray = IRBuilder.createBitCast("bitcast", createdSpace, currType)
      IRBuilder.createStore(arraySizeExpr.value!!.type, arraySizeExpr.value!!, extendedArray)
      val pureArray = IRBuilder.createGEP("arrayidx", currType, extendedArray, ConstantInt(32, 1))

      if (index == 0) {
        curr.value = pureArray
      }

      if (curr.arraySizeExprList.size - 1 == index) {
        var lastEndBlock: BasicBlock? = null
        for (index in loopStack.size - 1 downTo 0) {
          val (arrayHead, phiInst, bodyBlock) = loopStack[index]
          val condBlock = phiInst.parent!!
          val endBlock = BasicBlock("while.end")
          IRBuilder.resetInsertBlock(condBlock)
          val cond = IRBuilder.createCmp("cmp", "ne", phiInst.type, phiInst, arrayHead)
          IRBuilder.createBr(cond, bodyBlock, endBlock)

          IRBuilder.setInsertBlock(endBlock)
          if (lastEndBlock == null) {
            IRBuilder.resetInsertBlock(bodyBlock)
          } else {
            IRBuilder.resetInsertBlock(lastEndBlock)
          }
          val nextElement = IRBuilder.createGEP(
            "arrayidx",
            currType,
            phiInst,
            ConstantInt(32, -1)
          ) // the value is comes from the
          phiInst.candidates.add(Pair(nextElement, bodyBlock))
          IRBuilder.createBr(condBlock)
          lastEndBlock = endBlock
        }
        if (lastEndBlock != null) {
          IRBuilder.resetInsertBlock(lastEndBlock)
        }
        break
      }

      val startPoint = IRBuilder.createGEP("arrayidx", currType, pureArray, arraySizeExpr.value!!)
      val previousBlock = IRBuilder.getInsertBlock()!!
      val condBlock = BasicBlock("while.cond")
      val bodyBlock = BasicBlock("while.body")

      IRBuilder.createBr(condBlock)
      IRBuilder.setInsertBlock(condBlock)
      val phiInst = IRBuilder.createPhi(
        "phi",
        currType,
        mutableListOf(Pair(startPoint, previousBlock))
      ) // the body block would be split, so no one knows which block will it comes from, maybe the next layer's while end?
      loopStack.addLast(Triple(pureArray, phiInst, bodyBlock))

      // some preparation for following recursive building
      IRBuilder.setInsertBlock(bodyBlock)
      currType = currType.pointeeTy!! as PointerType
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

  override fun visit(curr: MethodAccessNode) {
    val callee = topModule.getFunc("${curr.expr.type!!.cl.className}.${curr.method}")!!
    curr.params.forEach { it.accept(this) }
    curr.value = IRBuilder.createCall(
      callee.funcType,
      curr.params.map { it.value!! }) // TODO: determine its type
  }

  override fun visit(curr: MemberAccessNode) {
    curr.expr.accept(this)
    val memberName = "${curr.expr.type!!.cl.className}.${curr.member}"
    val pointerType = curr.expr.value!!.type as PointerType
    val structType = pointerType.pointeeTy as StructType
    curr.value = IRBuilder.createGEP(memberName, structType, curr.expr.value!!, structType.getIndex(curr.member))
  }

  override fun visit(curr: ArrayAccessNode) {
    curr.array.accept(this)
    curr.index.accept(this)
    val pointerType = TypeFactory.getPtrType(curr.array.value!!.type)
    val ptr = IRBuilder.createGEP("elem.addr", pointerType, curr.array.value!!, curr.index.value!!)
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
          IRBuilder.createGEP("${memberName}.addr", structType, innerScope.getValue("this")!!, structType.getIndex(id))
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
        Pair(
          "elem", IRBuilder.createGEP(
            "elem.addr",
            curr.array.value!!.type as PointerType,
            curr.array.value!!,
            curr.index.value!!
          )
        )
      }

      is MemberAccessNode -> {
        curr.expr.accept(this)
        val className = curr.expr.type!!.cl.className
        val memberName = "$className.${curr.member}"
        val structType = topModule.getStruct("class.$className")
        Pair(
          memberName,
          IRBuilder.createGEP(memberName, structType, curr.expr.value!!, structType.getIndex(curr.member))
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
    IRBuilder.createStore(varType, binaryInst, varPtr)
    curr.value = curr.expr.value
  }

  override fun visit(curr: PrefixExprNode) {
    val op = Utils.unaryOpToStr(curr.op)
    curr.value = when (curr.op) {
      "++", "--" -> {
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
        IRBuilder.createStore(varType, binaryInst, varPtr)
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
          TypeFactory.getType(curr.type!!),
          ConstantInt(8, 0),
          curr.expr.value!!
        )
      }

      "~" -> {
        curr.expr.accept(this)
        IRBuilder.createBinary(
          op,
          "xor",
          TypeFactory.getType(curr.type!!),
          curr.expr.value!!,
          ConstantInt(8, -1)
        )
      }

      else -> { // !
        curr.expr.accept(this)
        val truncInst = IRBuilder.createTrunc(
          op,
          curr.expr.value!!.type,
          curr.expr.value!!,
          TypeFactory.getIntType(1)
        )
        IRBuilder.createBinary(
          op,
          "xor",
          TypeFactory.getType(curr.type!!),
          truncInst,
          ConstantInt(1, 1)
        )
      }
    }
  }

  // it gives out a value containing a temporary result of given binary operator
  override fun visit(curr: BinaryExprNode) {
    val op = Utils.binaryOpToStr(curr.op)

    curr.value = when (curr.op) {
      "*", "/", "%", "+", "-", "<<", ">>", "&", "^", "|" -> {
        var lhs: Value? = null
        for (expr in curr.exprs) {
          expr.accept(this)
          val rhs = expr.value!!
          if (lhs == null) {
            lhs = rhs
            continue
          }
          lhs = IRBuilder.createBinary(op, op, TypeFactory.getType(curr.type!!), lhs, rhs)
        }
        lhs
      }

      "&&", "||" -> {
        val endBlock = BasicBlock("$op.end")
        val candidates: MutableList<Pair<Value, BasicBlock>> = mutableListOf()
        val size = curr.exprs.size
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          candidates.add(
            Pair(
              if (index == size - 1) expr.value!! else ConstantInt(8, if (op == "lor") 1 else 0),
              IRBuilder.getInsertBlock()!!
            )
          )
          if (index == size - 1) {
            IRBuilder.createBr(endBlock)
          } else {
            val rhsBlock = BasicBlock(
              if (op == "lor") {
                if (index != size - 2) {
                  "lor.lhs.false"
                } else {
                  "lor.rhs"
                }
              } else {
                if (index != size - 2) {
                  "land.lhs.true"
                } else {
                  "land.rhs"
                }
              }
            )
            if (op == "lor") {
              IRBuilder.createBr(expr.value!!, endBlock, rhsBlock)
            } else {
              IRBuilder.createBr(expr.value!!, rhsBlock, endBlock)
            }
            // TODO: create something called type?
            IRBuilder.setInsertBlock(rhsBlock)
          }
        }
        IRBuilder.setInsertBlock(endBlock)
        IRBuilder.createPhi("phi", TypeFactory.getIntType(1), candidates)
      }

      else -> { // namely <, <=, >, >=, ==, !=
        var lhs: Value? = null
        for (expr in curr.exprs) {
          expr.accept(this)
          val rhs = expr.value
          if (lhs == null) {
            lhs = rhs
            continue
          }
          lhs = IRBuilder.createCmp("cmp", op, lhs.type, lhs, rhs!!)
        }
        lhs
      }
    }
  }

  override fun visit(curr: AssignExprNode) {
    val (_, ptr) = getVarsNameAndItsPtr(curr.lhs)
    curr.rhs.accept(this)
    IRBuilder.createStore(curr.rhs.value!!.type, curr.rhs.value!!, ptr)
  }
}
