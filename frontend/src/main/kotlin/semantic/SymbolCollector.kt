package semantic

import abst.control.Visitor
import abst.nodes.*
import abst.utils.ScopeManager
import exceptions.SemanticException

/**
 * SymbolCollector is used to collect classes and funcs which support forward reference
 */
class SymbolCollector : Visitor() {
  private val scopeManager = ScopeManager()

  override fun visit(curr: ProgNode) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: SuiteNode) {
    // check for redefinition and register classes and funcs
    val globalScope = scopeManager.first()
    for (it in curr.children) {
      if (it is ClassDefNode) {
        if (globalScope.getClass(it.className) != null) {
          throw SemanticException(curr.pos, "Redeclare class ${it.className}")
        }
        globalScope.setClass(it.className, it.classMeta)
      } else if (it is FuncDefNode) {
        if (globalScope.getFunc(it.funcName) != null) {
          throw SemanticException(curr.pos, "Redeclare function ${it.funcName}")
        }
        globalScope.setFunc(it.funcName, it.funcMeta)
      }
    }

    // register classes' members and methods
    // separate from above to support forward reference
    for (it in curr.children) {
      if (it is ClassDefNode) {
        it.accept(this)
      }
    }
  }

  override fun visit(curr: FuncSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ClassSuiteNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: SimpleSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ClassDefNode) {
    scopeManager.addLast(curr.classMeta.classScope)
    curr.classSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    val globalScope = scopeManager.first()
    curr.funcMeta.returnType = globalScope.getClass("void")
    // TODO: determine whether to add constructor to class scope
    globalScope.setFunc(curr.className, curr.funcMeta)
  }

  override fun visit(curr: FuncDefNode) {
    val scope = scopeManager.last()
    curr.funcMeta.returnType =
      scope.getClass(curr.returnType) ?: throw Exception("${curr.returnType} is not defined")
    scope.setFunc(curr.funcName, curr.funcMeta)
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  // this node would only be included in classDef
  override fun visit(curr: VarDeclNode) {
    val globalScope = scopeManager.first()
    val classScope = scopeManager.last()
    val type =
      globalScope.getClass(curr.type) ?: throw Exception("${curr.type} is not defined")

    for (it in curr.assigns) {
      if (classScope.getVar(it.first) != null) {
        throw Exception("Redeclare class member ${it.first}")
      }
      // TODO: can the member's type to be the class itself?
      classScope.setVar(it.first, type)
    }
  }

  override fun visit(curr: ForNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: WhileNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: CondNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FieldNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: JumpNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: PriorExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: AtomNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: InitExprNode) {
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
    TODO("Not yet implemented")
  }

  override fun visit(curr: PrefixExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: BinaryExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: AssignExprNode) {
    TODO("Not yet implemented")
  }
}