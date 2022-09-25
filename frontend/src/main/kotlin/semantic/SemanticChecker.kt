package semantic

import abst.control.Visitor
import abst.nodes.*
import abst.utils.ScopeManager

class SemanticChecker : Visitor() {
  val scopeManager = ScopeManager()
  override fun visit(curr: ProgNode) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
  }

  override fun visit(curr: SuiteNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: FuncSuiteNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: ClassSuiteNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: SimpleSuiteNode) {
    curr.child.accept(this)
  }

  // the definition of class should be in global scope
  // the antlr guarantees it, so here is no check
  // the member and method should be put in both global scope and local scope
  override fun visit(curr: ClassDefNode) {
    val scope = scopeManager.first()
    scope.setClass(curr.className, curr.classMeta)
    scopeManager.addLast(curr.classMeta.classScope)
    curr.classSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    TODO("Not yet implemented")
  }

  // the definition of function should be in global scope
  // the antlr guarantees it, so here is no check
  override fun visit(curr: FuncDefNode) {
    val outerScope = scopeManager.first()
    outerScope.setFunc(curr.funcName, curr.funcMeta)
    scopeManager.addLast(curr.funcMeta.funcScope)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDeclNode) {
    val scope = scopeManager.last()

    // only judge for some exceptions
    if (scope.getClass(curr.type) == null) {
      throw Exception("there is no type for variable declaration")
    }
    for (it in curr.assigns) {
      if (!scope.testVar(it.first)) {
        throw Exception("redeclaration of variable ${it.first}")
      } else if (it.second != null) {
        // TODO: maybe one day I would move them into the same module
        it.second!!.accept(this)
        if ((it.second as ExprNode).type!!.className != curr.type) {
          throw Exception("wrong type matching for ${it.second}")
        }
      }
    }

    curr.assigns.forEach { scope.setVar(it.first, scope.getClass(curr.type)!!) }
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