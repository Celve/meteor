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
  private var func: Func? = null
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
    // because all variables are suffixed with ".ptr", then there is no need to distinguish them
    // this naming custom might be changed in the future
    val initFuncName = "global_variable_init"
    initFunc = Func(initFuncName, FuncType(initFuncName, listOf(), TypeFactory.getVoidType()), listOf())
    val initBlock = BasicBlock("entry")
    initBlock.insertAtTheTailOf(initFunc!!) // it supposes that the initFunc contains only one block

    curr.children.forEach { it.accept(this) }

    // to see if there is a need to init global variable in a particular function
    if (initBlock.instList.size > 0) {
      IRBuilder.setInsertPoint(initBlock)
      IRBuilder.createRetVoid()
      topModule.setFunc(initFuncName, initFunc!!)

      val mainFunc = topModule.getFunc("main")
      val mainEntryBlock = mainFunc.blockList.first()
      IRBuilder.setInsertPoint(mainEntryBlock)
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
    func = topModule.getFunc(funcName)
    func!!.vst.addAll(topModule) // avoid conflicts with global value
    IRBuilder.setValueSymbolTable(func!!.vst)
    val funcType = func!!.type as FuncType

    val entryBlock = BasicBlock(func!!.vst.defineName("entry"))
    entryBlock.insertAtTheTailOf(func!!)
    IRBuilder.setInsertPoint(entryBlock)

    // setup for parameters
    if (func!!.args.isNotEmpty()) {
      for (arg in func!!.args) {
        // "this" should be inserted into method's value symbol table and scope, in order to retrieve
        func!!.vst.reinsertValue(arg)
        if (arg.name == "this") {
          innerScope.setValue(arg.name!!, arg)
          continue // TODO: decide whether to jump around the store and load instruction of variable "this"
        }
        val ptrSuffixed = "${arg.name}.ptr"
        val allocaInst = IRBuilder.createAlloca(ptrSuffixed, arg.type, arg.type.getAlign())
        innerScope.setValue(arg.name!!, arg)
        innerScope.setValue(ptrSuffixed, allocaInst)
        IRBuilder.createStore(arg.type, arg, allocaInst)
      }
    }

    // setup for the return value
    if (funcType.result != TypeFactory.getVoidType()) {
      val ptrSuffixed = "retptr"
      val allocaInst = IRBuilder.createAlloca(ptrSuffixed, funcType.result, funcType.result.getAlign())
      innerScope.setValue(ptrSuffixed, allocaInst)
      if (funcName == "main") {
        IRBuilder.createStore(funcType.result, ConstantInt(32, 0), allocaInst)
      }
    }

    curr.funcBlock?.accept(this)

    if (IRBuilder.getInsertPoint() !== entryBlock) {
      val exitBlock = BasicBlock(func!!.vst.defineName("exit"))
      val branchInst = IRBuilder.createBr(exitBlock)

      for (block in func!!.blockList) {
        if (!block.hasTerminator()) {
          branchInst.insertAtTheTailOf(block)
        }
      }

      exitBlock.insertAtTheTailOf(func!!)
      IRBuilder.setInsertPoint(exitBlock)
    }

    if (funcType.result != TypeFactory.getVoidType()) {
      val ptrSuffixed = "retptr"
      val valSuffixed = "retval"
      val loadInst = IRBuilder.createLoad(valSuffixed, funcType.result, innerScope.getValue(ptrSuffixed)!!)
      innerScope.setValue(valSuffixed, loadInst)
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
        val ptrSuffixed = "${assign.first}.ptr"
        val ptr = IRBuilder.createAlloca(ptrSuffixed, type, type.getAlign())
        innerScope.setValue(ptrSuffixed, ptr)
        if (assign.second != null) {
          assign.second!!.accept(this)
          // TODO: I don't know what to do next, but there must be a step to update namedValues
          IRBuilder.createStore(type, assign.second!!.value!!, ptr)
        } else if (type is PointerType) {
          IRBuilder.createStore(type, ConstantNull(), ptr)
        }
      }
    } else { // if it is a global variable
      IRBuilder.setInsertPoint(initFunc!!.blockList.last())
      for (assign in curr.assigns) {
        val ptrSuffixed = "${assign.first}.ptr"
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
    TODO("Not yet implemented")
  }

  override fun visit(curr: WhileSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: CondSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FieldSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: JumpNode) {
    when (curr.type) {
      "return" -> {
        if (curr.expr != null) {
          curr.expr.accept(this)
          IRBuilder.createStore(
            (func!!.type as FuncType).result,
            curr.expr.value!!,
            scopeManger.last().getValue("retptr")!!
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
        val id = curr.literal
        val ptrSuffixed = "$id.ptr"
        val ptr = innerScope.getValue(ptrSuffixed)
        if (ptr == null) {
          val recentClass = scopeManger.getRecentClass()
          if (recentClass != null && recentClass.memberToIndex.containsKey(id)) {
            // now we could know that this is a class member
            val memberName = "${recentClass.className}.$id"
            val structType = topModule.getStruct("class.${recentClass.className}")
            IRBuilder.createGEP(memberName, structType, innerScope.getValue("this")!!, structType.getIndex(id))
          } else {
            val globalPtr = topModule.getGlobalVar(id)
            val loadInst = IRBuilder.createLoad(curr.literal, globalPtr.type, globalPtr)
            innerScope.setValue(curr.literal, loadInst)
            loadInst
          }
        } else {
          val loadInst = IRBuilder.createLoad(curr.literal, ptr.type, ptr)
          innerScope.setValue(curr.literal, loadInst)
          loadInst
        }
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
    TODO("Not yet implemented")
  }

  override fun visit(curr: SuffixExprNode) {
    curr.expr.accept(this)
    val op = Utils.unaryOpToStr(curr.op)
    curr.value = curr.expr.value
    IRBuilder.createBinary(op, op, TypeFactory.getType(curr.type!!), curr.expr.value!!, ConstantInt(32, 1))
  }

  override fun visit(curr: PrefixExprNode) {
    curr.expr.accept(this)
    val op = Utils.unaryOpToStr(curr.op)
    curr.value = when (curr.op) {
      "++", "--" -> {
        IRBuilder.createBinary(
          op,
          op,
          TypeFactory.getType(curr.type!!),
          curr.expr.value!!,
          ConstantInt(32, 1)
        )
      }

      "+" -> {
        curr.expr.value
      }

      "-" -> {
        IRBuilder.createBinary(
          op,
          "sub",
          TypeFactory.getType(curr.type!!),
          ConstantInt(8, 0),
          curr.expr.value!!
        )
      }

      "~" -> {
        IRBuilder.createBinary(
          op,
          "xor",
          TypeFactory.getType(curr.type!!),
          curr.expr.value!!,
          ConstantInt(8, -1)
        )
      }

      else -> { // !
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
    curr.lhs.accept(this)
    curr.rhs.accept(this)

    val op = Utils.binaryOpToStr(curr.op)

    curr.value = when (curr.op) {
      "*", "/", "%", "+", "-", "<<", ">>", "&", "^", "|" -> {
        IRBuilder.createBinary(
          op,
          op,
          TypeFactory.getType(curr.type!!),
          curr.lhs.value!!,
          curr.rhs.value!!
        )
      }

      else -> { // namely <, <=, >, >=, ==, !=
        IRBuilder.createCmp(
          op,
          op,
          TypeFactory.getType(curr.lhs.type!!),
          curr.lhs.value!!,
          curr.rhs.value!!
        )
      }
    }
  }

  override fun visit(curr: AssignExprNode) {
    val ptr = when {
      curr.lhs is AtomNode -> {
        val suffixed = "${curr.lhs.literal}.ptr"
        scopeManger.last().getValue(suffixed)!!
      }

      else -> TODO() // I don't know that exact implementation detail of struct
    }
    curr.rhs.accept(this)
    IRBuilder.createStore(curr.rhs.value!!.type, curr.rhs.value!!, ptr)
  }
}

