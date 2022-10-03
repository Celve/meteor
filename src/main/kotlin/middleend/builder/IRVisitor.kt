package middleend.builder

import frontend.ast.controller.AstVisitor
import frontend.ast.node.*
import frontend.utils.FuncScope
import frontend.utils.GlobalScope
import frontend.utils.ScopeManager
import middleend.basic.*
import middleend.helper.RenameManager
import middleend.helper.Utils

class IRVisitor : AstVisitor() {
  private val scopeManger = ScopeManager()
  val topModule = TopModule()
  private val namedValues: HashMap<String, Value> = hashMapOf()
  private var func: Func? = null
  private var initFunc: Func? = null

  override fun visit(curr: ProgNode) {
    scopeManger.addLast(curr.scope)
    curr.block.accept(this)
    scopeManger.removeLast()
  }

  override fun visit(curr: ProgBlockNode) {
    /// In order to support forward reference,
    /// class definitions, member declarations, method declarations, function declarations,
    /// all have to be iterated first to build the type system.
    val globalScope = scopeManger.first() as GlobalScope

    // class definitions
    for ((className, classMd) in globalScope.classes) {
      classMd.structIr = TypeFactory.getStructType(classMd) // create the concrete struct, instead of reference
      println("${className} -> ${globalScope.getUniqueName(className)}")
      topModule.setStruct(globalScope.getUniqueName(className), classMd.structIr!!)
    }

    // function definitions
    for ((funcName, funcMd) in globalScope.funcs) {
      funcMd.funcIr = TypeFactory.getFuncType(funcMd)
      println("${funcName} -> ${globalScope.getUniqueName(funcName)}")
      topModule.setFunc(
        globalScope.getUniqueName(funcName),
        Func(
          funcName,
          funcMd.funcIr!!,
          funcMd.paramInput.map { Value(TypeFactory.getType(it.second), funcMd.funcScope.getUniqueName(it.first)) }
        )
      )
    }

    // set up init func
    val initFuncName = "global_variable_init"
    initFunc = Func(initFuncName, FuncType(initFuncName, listOf(), TypeFactory.getVoidType()), listOf())
    val initBlock = BasicBlock("entry")
    initBlock.insertAtTheTailOf(initFunc!!) // it supposes that the initFunc contains only one block

    // visit the remainder
    curr.children.forEach { it.accept(this) }

    if (initBlock.instList.size > 0) {
      IRBuilder.setInsertPoint(initBlock)
      IRBuilder.createRetVoid()
      topModule.setFunc(initFuncName, initFunc!!)

      // setup main' init
      val mainFunc = topModule.getFunc("main")
      val mainEntryBlock = mainFunc.blockList.first()
      IRBuilder.setInsertPoint(mainEntryBlock)
      IRBuilder.createCallInst(null, initFunc!!.funcType, listOf(), true)
    }

  }

  override fun visit(curr: FuncBlockNode) {
    val innerScope = scopeManger.last() as FuncScope
    val globalScope = scopeManger.first()
    val funcName = globalScope.getUniqueName(innerScope.funcName)
    func = topModule.getFunc(funcName)
    val funcType = func!!.type as FuncType

    val entryBlock = BasicBlock(func!!.vst.defineTwine("$funcName.entry"))
    entryBlock.insertAtTheTailOf(func!!)
    IRBuilder.setInsertPoint(entryBlock)

    // setup for parameters
    if (func!!.args.isNotEmpty()) {
      for (arg in func!!.args) {
        // TODO: it should be the same with passing parameter's name
        val addrSuffixed = "${arg.name}.addr"
        val allocaInst = IRBuilder.createAlloca(addrSuffixed, arg.type, arg.type.getAlign())
        namedValues[arg.name!!] = arg
        namedValues[addrSuffixed] = allocaInst
        IRBuilder.createStore(arg.type, namedValues[arg.name]!!, namedValues[addrSuffixed]!!)
      }
    }

    // setup for the return value
    if (funcType.result != TypeFactory.getVoidType()) {
      val addrSuffixed = "$funcName.retaddr"
      val allocaInst = IRBuilder.createAlloca(addrSuffixed, funcType.result, funcType.result.getAlign())
      namedValues[addrSuffixed] = allocaInst
      if (funcName == "main") {
        IRBuilder.createStore(funcType.result, ConstantInt(32, 0), namedValues[addrSuffixed]!!)
      }
    }

    curr.children.forEach { it.accept(this) }

    if (IRBuilder.getInsertPoint() !== entryBlock) {
      val exitBlock = BasicBlock(func!!.vst.defineTwine("$funcName.exit"))
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
      val addrSuffixed = "$funcName.retaddr"
      val valSuffixed = "$funcName.retval"
      val loadInst = IRBuilder.createLoad(valSuffixed, funcType.result, namedValues[addrSuffixed]!!)
      namedValues[valSuffixed] = loadInst
      IRBuilder.createRet(funcType.result, loadInst)
    } else {
      IRBuilder.createRetVoid()
    }
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
    curr.funcBlock?.accept(this)
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
        val name = innerScope.getUniqueName(assign.first)
        val suffixed = "$name.addr"
        val addr = IRBuilder.createAlloca(suffixed, type, type.getAlign())
        namedValues[suffixed] = addr
        if (assign.second != null) {
          assign.second!!.accept(this)
          // TODO: I don't know what to do next, but there must be a step to update namedValues
          IRBuilder.createStore(type, assign.second!!.value!!, addr)
        }
      }
    } else { // if it is a global variable
      IRBuilder.setInsertPoint(initFunc!!.blockList.last())
      for (assign in curr.assigns) {
        val name = innerScope.getUniqueName(assign.first)
        val suffixed = "$name.addr"
        val gloVarAddr = GlobalVariable(suffixed, type)
        topModule.setGlobalVar(suffixed, gloVarAddr)
        if (assign.second != null) {
          assign.second!!.accept(this)
          IRBuilder.createStore(type, assign.second!!.value!!, gloVarAddr)
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
            namedValues["${func!!.name}.retaddr"]!!
          )
        }
      }
    }
  }

  override fun visit(curr: ShortNode) {
    curr.expr?.accept(this)
  }

  override fun visit(curr: PriorExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: AtomNode) {
    curr.value = when (curr.id) {
      0 -> ConstantInt(32, curr.literal.toInt()) // const int
      1 -> ConstantStr(curr.literal) // const str
      2 -> {
        val name = scopeManger.last().getUniqueName(curr.literal)
        val addrSuffixed = "$name.addr"
        val realSuffixed = "$name.real"
        val addr = namedValues[addrSuffixed] ?: topModule.getGlobalVar(addrSuffixed)
        val loadInst = IRBuilder.createLoad(realSuffixed, addr.type, addr)
        namedValues[realSuffixed] = loadInst
        loadInst
      }

      3 -> TODO("How to declare a class method?")
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
    curr.value = IRBuilder.createCallInst(
      "${RenameManager.rename(callee.name!!)}.ret",
      callee.funcType,
      curr.params.map { Argument(it.value!!, callee) }) // TODO: determine its type
  }

  override fun visit(curr: MethodAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: MemberAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ArrayAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: SuffixExprNode) {
    curr.expr.accept(this)
    val op = Utils.unaryOpToStr(curr.op)
    val suffixed = "${RenameManager.rename(op)}.temp"
    curr.value = curr.expr.value
    IRBuilder.createBinary(suffixed, op, TypeFactory.getType(curr.type!!), curr.expr.value!!, ConstantInt(32, 1))
  }

  override fun visit(curr: PrefixExprNode) {
    curr.expr.accept(this)
    val op = Utils.unaryOpToStr(curr.op)
    val suffixed = "${RenameManager.rename(op)}.temp"
    curr.value = when (curr.op) {
      "++", "--" -> {
        IRBuilder.createBinary(
          suffixed,
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
          suffixed,
          "sub",
          TypeFactory.getType(curr.type!!),
          ConstantInt(8, 0),
          curr.expr.value!!
        )
      }

      "~" -> {
        IRBuilder.createBinary(
          suffixed,
          "xor",
          TypeFactory.getType(curr.type!!),
          curr.expr.value!!,
          ConstantInt(8, -1)
        )
      }

      else -> {
        val truncInst = IRBuilder.createTrunc(
          suffixed,
          curr.expr.value!!.type,
          curr.expr.value!!,
          TypeFactory.getIntType(1)
        )
        IRBuilder.createBinary(
          "${RenameManager.rename(op)}.temp",
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
    val suffixed = "${RenameManager.rename(op)}.temp"

    curr.value = when (curr.op) {
      "*", "/", "%", "+", "-", "<<", ">>", "&", "^", "|" -> {
        IRBuilder.createBinary(
          suffixed,
          op,
          TypeFactory.getType(curr.type!!),
          curr.lhs.value!!,
          curr.rhs.value!!
        )
      }

      else -> { // namely <, <=, >, >=, ==, !=
        IRBuilder.createCmp(
          suffixed,
          op,
          TypeFactory.getType(curr.lhs.type!!),
          curr.lhs.value!!,
          curr.rhs.value!!
        )
      }
    }
  }

  override fun visit(curr: AssignExprNode) {
    val addr = when {
      curr.lhs is AtomNode -> {
        val name = scopeManger.last().getUniqueName(curr.lhs.literal)
        val suffixed = "$name.addr"
        namedValues[suffixed]
      }

      else -> TODO() // I don't know that exact implementation detail of struct
    }
    curr.rhs.accept(this)
    IRBuilder.createStore(curr.rhs.value!!.type, curr.rhs.value!!, addr!!)
  }
}