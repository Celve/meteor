package middleend.builder

import frontend.ast.controller.AstVisitor
import frontend.ast.node.*
import frontend.utils.FuncScope
import frontend.utils.GlobalScope
import frontend.utils.ScopeManager
import middleend.basic.*
import middleend.helper.Utils

class IRVisitor : AstVisitor() {
  private val scopeManger = ScopeManager()
  val topModule = TopModule()
  private var initFunc: Func? = null

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

    // set up init func
    // because all variables are suffixed with ".addr", then there is no need to distinguish them
    // this naming custom might be changed in the future
    val initFuncName = "global_variable_init"
    initFunc = Func(initFuncName, FuncType(initFuncName, listOf(), TypeFactory.getVoidType()), listOf())
    val initBlock = BasicBlock("entry")
    initBlock.insertAtTheTailOf(initFunc!!) // it supposes that the initFunc contains only one block

    curr.children.forEach { it.accept(this) }

    // to see if there is a need to init global variable in a particular function
    if (initBlock.instList.size > 0) {
      IRBuilder.setInsertBlock(initBlock)
      IRBuilder.createRetVoid()
      topModule.setFunc(initFuncName, initFunc!!)

      val mainFunc = topModule.getFunc("main")
      val mainEntryBlock = mainFunc.blockList.first()
      IRBuilder.setInsertBlock(mainEntryBlock)
      IRBuilder.createCall(null, initFunc!!.funcType, listOf(), true)
    }

  }

  override fun visit(curr: FuncBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: ClassBlockNode) {
    curr.children.forEach { if (it !is VarDeclNode) it.accept(this) }
  }

  override fun visit(curr: SimpleBlockNode) {
    TODO("Not yet implemented")
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
    val func = topModule.getFunc(funcName)
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

    curr.funcBlock?.accept(this)

    if (IRBuilder.getInsertBlock() !== entryBlock) {
//      val exitBlock = BasicBlock(func.vst.defineName("exit"))
//      val branchInst = IRBuilder.createBr(exitBlock)
//
//      for (block in func.blockList) {
//        if (!block.hasTerminator()) {
//          branchInst.insertAtTheTailOf(block)
//        }
//      }
//
//      IRBuilder.setInsertBlock(exitBlock)
    }

    if (funcType.result != TypeFactory.getVoidType()) {
      val addrSuffixed = "retaddr"
      val valSuffixed = "retval"
      val loadInst = IRBuilder.createLoad(valSuffixed, innerScope.getValue(addrSuffixed)!!)
      IRBuilder.createRet(funcType.result, loadInst)
    } else {
      IRBuilder.createRetVoid()
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
      IRBuilder.setInsertBlock(initFunc!!.blockList.last())
      for (assign in curr.assigns) {
        val ptrSuffixed = "${assign.first}.addr"
        val gloVarPtr = GlobalVariable(ptrSuffixed, type)
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
  }

  override fun visit(curr: WhileSuiteNode) {
    val condBlock = BasicBlock("while.cond")
    val bodyBlock = BasicBlock("while.body")
    val endBlock = BasicBlock("while.end")

    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(condBlock)
    curr.cond.accept(this)
    IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)

    IRBuilder.setInsertBlock(bodyBlock)
    curr.body.accept(this)
    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(endBlock)
  }

  override fun visit(curr: CondSuiteNode) {
    curr.cond.accept(this)

    val endBlock = BasicBlock("if.end")

    val thenBlock = BasicBlock("if.then")
    IRBuilder.setInsertBlock(thenBlock)
    curr.thenDo.accept(this)
    IRBuilder.createBr(endBlock)

    if (curr.elseDo != null) {
      val elseBlock = BasicBlock("if.else")
      IRBuilder.setInsertBlock(elseBlock)
      curr.elseDo.accept(this)
      IRBuilder.createBr(endBlock)
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
          IRBuilder.createStore(
            IRBuilder.getCurrentFuncReturnType(),
            curr.expr.value!!,
            scopeManger.last().getValue("retaddr")!!
          )
        }
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
      1 -> ConstantStr(curr.literal) // const str
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
    TODO("Not yet implemented")
  }

  override fun visit(curr: LambdaCallNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncCallNode) {
    val callee = topModule.getFunc(curr.funcName)
    curr.params.forEach { it.accept(this) }
    curr.value = IRBuilder.createCall(
      "retval",
      callee.funcType,
      curr.params.map { it.value!! }) // TODO: determine its type
  }

  override fun visit(curr: MethodAccessNode) {
    val callee = topModule.getFunc("${curr.expr.type!!.cl.className}.${curr.method}")
    curr.params.forEach { it.accept(this) }
    curr.value = IRBuilder.createCall(
      "retval",
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
    val pointerType = curr.array.value!!.type as PointerType
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
        Pair(id, topModule.getGlobalVar(id))
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

//  private fun getVarPtrAndLoad(curr: ExprNode): Value {
//    val varPtr = getVarPtr(curr)
//    IRBuilder.createLoad()
//  }

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
        IRBuilder.createPhi("phi", TypeFactory.getIntType(8), candidates)
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

