package middleend.builder

import frontend.ast.controller.AstVisitor
import frontend.ast.node.*
import frontend.utils.FuncScope
import frontend.utils.GlobalScope
import frontend.utils.ScopeManager
import middleend.basic.*
import middleend.helper.RenameManager
import middleend.helper.Suffixed
import middleend.helper.Utils

class IRVisitor : AstVisitor() {
  private val scopeManger = ScopeManager()
  private val module = Module()
  private val namedValues: HashMap<Suffixed, Value> = hashMapOf()
  private var func: Func? = null

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
      classMd.structIr = TypeFactory.createStruct(classMd) // create the concrete struct, instead of reference
      println("${className} -> ${globalScope.getUniqueName(className)}")
      module.setStruct(globalScope.getUniqueName(className), classMd.structIr!!)
    }

    // function definitions
    for ((funcName, funcMd) in globalScope.funcs) {
      funcMd.funcIr = TypeFactory.createFunc(funcMd)
      println("${funcName} -> ${globalScope.getUniqueName(funcName)}")
      module.setFunc(globalScope.getUniqueName(funcName), Func(funcMd.funcIr!!))
    }

    // visit the remainder
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: FuncBlockNode) {
    val innerScope = scopeManger.last() as FuncScope
    val globalScope = scopeManger.first()
    val funcName = globalScope.getUniqueName(innerScope.funcName)
    func = module.getFunc(funcName)
    val entryBlock = BasicBlock(func!!.vst.defineTwine(Suffixed(funcName, "entry")))
    entryBlock.insertAtTail(func!!)
    IRBuilder.setInsertPoint(entryBlock)

    curr.children.forEach { it.accept(this) }

    if (IRBuilder.getInsertPoint() !== entryBlock) {
      val exitBlock = BasicBlock(func!!.vst.defineTwine(Suffixed(funcName, "exit")))
      exitBlock.insertAtTail(func!!)
      IRBuilder.setInsertPoint(exitBlock)
//      IRBuilder.createRetVoid() TODO: something deal with return value
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

    // if it is a local variable
    if (innerScope !is GlobalScope) {
      for (assign in curr.assigns) {
        val type = TypeFactory.createType(curr.varTypeMd!!)
        val name = innerScope.getUniqueName(assign.first)
        val suffixed = Suffixed(name, "addr")
        val addr = IRBuilder.createAlloca(suffixed, type, type.getNumBits() / 8)
        namedValues[suffixed] = addr
        if (assign.second != null) {
          assign.second!!.accept(this)
          // TODO: I don't know what to do next, but there must be a step to update namedValues
          IRBuilder.createStore(type, assign.second!!.value!!, addr)
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
    TODO("Not yet implemented")
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
        val addrSuffixed = Suffixed(name, "addr")
        val realSuffixed = Suffixed(name, "real")
        val addr = namedValues[addrSuffixed]!!
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
    TODO("Not yet implemented")
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
    val suffixed = Suffixed(RenameManager.rename(op), "temp")
    curr.value = curr.expr.value
    IRBuilder.createBinary(suffixed, op, TypeFactory.createType(curr.type!!), curr.expr.value!!, ConstantInt(32, 1))
  }

  override fun visit(curr: PrefixExprNode) {
    curr.expr.accept(this)
    val op = Utils.unaryOpToStr(curr.op)
    val suffixed = Suffixed(RenameManager.rename(op), "temp")
    curr.value = when (curr.op) {
      "++", "--" -> {
        IRBuilder.createBinary(
          suffixed,
          op,
          TypeFactory.createType(curr.type!!),
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
          TypeFactory.createType(curr.type!!),
          ConstantInt(8, 0),
          curr.expr.value!!
        )
      }

      "~" -> {
        IRBuilder.createBinary(
          suffixed,
          "xor",
          TypeFactory.createType(curr.type!!),
          curr.expr.value!!,
          ConstantInt(8, -1)
        )
      }

      else -> {
        val truncInst = IRBuilder.createTrunc(
          suffixed,
          curr.expr.value!!.type,
          curr.expr.value!!,
          TypeFactory.createInt(1)
        )
        IRBuilder.createBinary(
          Suffixed(RenameManager.rename(op), "temp"),
          "xor",
          TypeFactory.createType(curr.type!!),
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
    val suffixed = Suffixed(RenameManager.rename(op), "temp")

    curr.value = when (curr.op) {
      "*", "/", "%", "+", "-", "<<", ">>", "&", "^", "|" -> {
        IRBuilder.createBinary(
          suffixed,
          op,
          TypeFactory.createType(curr.type!!),
          curr.lhs.value!!,
          curr.rhs.value!!
        )
      }

      else -> { // namely <, <=, >, >=, ==, !=
        IRBuilder.createCmp(
          suffixed,
          op,
          TypeFactory.createType(curr.lhs.type!!),
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
        val suffixed = Suffixed(name, "addr")
        namedValues[suffixed]
      }

      else -> TODO() // I don't know that exact implementation detail of struct
    }
    curr.rhs.accept(this)
    IRBuilder.createStore(curr.rhs.value!!.type, curr.rhs.value!!, addr!!)
  }
}