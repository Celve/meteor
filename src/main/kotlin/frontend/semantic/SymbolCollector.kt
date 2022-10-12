package frontend.semantic

import exceptions.SemanticException
import frontend.ast.controller.ASTVisitor
import frontend.ast.node.*
import frontend.metadata.FuncMd
import frontend.metadata.TypeMd
import frontend.utils.ScopeManager

/**
 * SymbolCollector is used to collect classes and funcs which support forward reference
 */
class SymbolCollector : ASTVisitor() {
  private val scopeManager = ScopeManager()

  override fun visitProg(curr: ProgNode) {
    scopeManager.addLast(curr.scope)
    curr.block.accept(this)
    scopeManager.removeLast()

    // and the main has to be int
    curr.scope.getFunc("main") ?: throw SemanticException(curr.pos, "No main function")
  }

  override fun visitProgBlock(curr: ProgBlockNode) {
    // check for redefinition and register classes and funcs
    val globalScope = scopeManager.first()
    for (it in curr.children) {
      if (it is ClassDefNode) {
        if (globalScope.testClass(it.className)) {
          throw SemanticException(curr.pos, "Redeclare class ${it.className}")
        } else if (globalScope.testFunc(it.className)) {
          throw SemanticException(curr.pos, "Class ${it.className} has the same name with another function")
        }
        globalScope.setClass(it.className, it.classMd)
      }
    }

    // register classes' members and methods
    // separate from above to support forward reference
    for (it in curr.children) {
      if (it is ClassDefNode) {
        it.accept(this)

        // in the case that it doesn't have a initial constructor
        val scope = it.classMd.classScope
        if (!scope.testFunc("new")) {
          scope.setFunc("new", FuncMd("new", listOf(), globalScope.getFuncType("void")))
        }
      }
    }
    for (it in curr.children) {
      if (it is FuncDefNode) {
        it.accept(this)
      }
    }
  }

  override fun visitFuncBlock(curr: FuncBlockNode) {
    TODO("Not yet implemented")
  }

  override fun visitClassBlock(curr: ClassBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visitSimpleBlock(curr: SimpleBlockNode) {
    TODO("Not yet implemented")
  }

  override fun visitClassDef(curr: ClassDefNode) {
    scopeManager.addLast(curr.classMd)
    curr.classBlock?.accept(this)
    scopeManager.removeLast()
  }

  override fun visitClassCtor(curr: ClassCtorNode) {
    // omit this duplication for the time being
    val globalScope = scopeManager.first()
    val outerScope = scopeManager.last()
    val innerScope = curr.funcMd.funcScope
    val paramInput: MutableList<Pair<String, TypeMd>> = mutableListOf()
    val recentClass = scopeManager.getRecentClass()!!

    if (curr.className != recentClass.className) {
      throw SemanticException(curr.pos, "Class can't have this constructor")
    }

    val previous = outerScope.getFunc("new")
    if (previous != null) {
      throw SemanticException(curr.pos, "Redeclare class constructor ${curr.className}")
    }

    // init params and add them into local scope
    for (it in curr.params) {
      val varType = outerScope.getVarType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.add(Pair(it.second, varType))
      innerScope.setVar(it.second, varType)
    }
    curr.funcMd.argList = paramInput

//    curr.funcMd.returnType = globalScope.getFuncType(curr.className)
    curr.funcMd.returnType = globalScope.getFuncType("void") // forbid ctor's parameters
    outerScope.setFunc("new", curr.funcMd) // use new to infer ctor
    recentClass.hasCustomCtor = true
  }

  override fun visitFuncDef(curr: FuncDefNode) {
    // omit this duplication for the time being
    val outerScope = scopeManager.last()
    val innerScope = curr.funcMd.funcScope
    val paramInput: MutableList<Pair<String, TypeMd>> = mutableListOf()

    // need to check here, there is no check above
    if (outerScope.testFunc(curr.funcName)) {
      throw SemanticException(curr.pos, "Redeclare ${curr.funcName}")
    }

    // init params and add them into local scope
    for (it in curr.params) {
      val varType = outerScope.getVarType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.add(Pair(it.second, varType))
      innerScope.setVar(it.second, varType)
    }
    curr.funcMd.argList = paramInput

    // check for its return type
    curr.funcMd.returnType =
      outerScope.getFuncType(curr.returnType) ?: throw SemanticException(curr.pos, "${curr.returnType} is not defined")

    // for main only
    if (curr.funcName == "main") {
      if (!curr.funcMd.returnType!!.isInt()) {
        throw SemanticException(curr.pos, "Function main has to have a int return ")
      }
      if (curr.params.size != 0) {
        throw SemanticException(curr.pos, "Function main should not have parameters")
      }
    }

    outerScope.setFunc(curr.funcName, curr.funcMd)
  }

  override fun visitLambdaDef(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  // this node would only be included in classDef
  override fun visitVarDecl(curr: VarDeclNode) {
    val globalScope = scopeManager.first()
    val classScope = scopeManager.last()
    val classMd = scopeManager.getRecentClass()!!
    val varType =
      globalScope.getVarType(curr.varTypeStr) ?: throw SemanticException(curr.pos, "${curr.varTypeStr} is not defined")

    for (it in curr.assigns) {
      if (classScope.getVar(it.first) != null) {
        throw Exception("Redeclare class member ${it.first}")
      }
      // TODO: can the member's type to be the class itself?
      classScope.setVar(it.first, varType)
      classMd.memberToIndex[it.first] = classMd.memberToIndex.size
    }
  }

  override fun visitForSuite(curr: ForSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visitWhileSuite(curr: WhileSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visitCondSuite(curr: CondSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visitFieldSuite(curr: FieldSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visitJump(curr: JumpNode) {
    TODO("Not yet implemented")
  }

  override fun visitShort(curr: ShortNode) {
    TODO("Not yet implemented")
  }

  override fun visitPriorExpr(curr: PriorExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitAtom(curr: AtomNode) {
    TODO("Not yet implemented")
  }

  override fun visitInitExpr(curr: NewExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitLambdaCall(curr: LambdaCallNode) {
    TODO("Not yet implemented")
  }

  override fun visitFuncCall(curr: FuncCallNode) {
    TODO("Not yet implemented")
  }

  override fun visitMethodCall(curr: MethodCallNode) {
    TODO("Not yet implemented")
  }

  override fun visitMemberAccess(curr: MemberAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visitArrayAccess(curr: ArrayAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visitSuffixExpr(curr: SuffixExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitPrefixExpr(curr: PrefixExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitBinaryExpr(curr: BinaryExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitLogicalAndExpr(curr: LogicalAndExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitLogicalOrExpr(curr: LogicalOrExprNode) {
    TODO("Not yet implemented")
  }

  override fun visitAssignExpr(curr: AssignExprNode) {
    TODO("Not yet implemented")
  }
}