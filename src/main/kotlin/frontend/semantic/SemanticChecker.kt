package frontend.semantic

import exceptions.SemanticException
import frontend.abst.control.Visitor
import frontend.abst.meta.FuncMeta
import frontend.abst.meta.TypeMeta
import frontend.abst.nodes.*
import frontend.abst.utils.ClassScope
import frontend.abst.utils.GlobalScope
import frontend.abst.utils.ScopeManager

class SemanticChecker : Visitor() {
  private val scopeManager = ScopeManager()
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
    scopeManager.addLast(curr.funcMeta.funcScope)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  // the definition of function should be in global scope
  // the antlr guarantees it, so here is no check
  override fun visit(curr: FuncDefNode) {
    scopeManager.addLast(curr.funcMeta.funcScope, curr.funcMeta.returnType)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDeclNode) {
    val globalScope = scopeManager.first()
    val innerScope = scopeManager.last()
    if (innerScope is ClassScope || innerScope is GlobalScope) { // the declaration is implemented in symbol collector
      return
    }

    // only judge for some exceptions
    val type = globalScope.getType(curr.type) ?: throw SemanticException(curr.pos, "No type called ${curr.type}")
    for (it in curr.assigns) {
      if (innerScope.testVar(it.first)) {
        throw SemanticException(curr.pos, "Redeclare ${it.first}")
      } else if (it.second != null) {
        it.second!!.accept(this)
        if ((it.second as ExprNode).type!!.cl.className != curr.type) {
          throw SemanticException(curr.pos, "Wrong type matching for ${it.second}")
        }
      }
      innerScope.setVar(it.first, type)
    }
  }

  override fun visit(curr: ForNode) {
    scopeManager.addLast(curr.scope)
    curr.init?.accept(this)
    if (curr.cond != null) {
      curr.cond.accept(this)
      if (!curr.cond.type!!.isBool()) {
        throw SemanticException(curr.pos, "Conditional expression should be bool")
      }
    }
    curr.step?.accept(this)
    curr.suite.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: WhileNode) {
    // omit this duplication
    curr.cond.accept(this)
    if (!curr.cond.type!!.isBool()) {
      throw SemanticException(curr.cond.pos, "Conditional expression should be bool")
    }
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: CondNode) {
    // omit this duplication
    curr.cond.accept(this)
    if (!curr.cond.type!!.isBool()) {
      throw SemanticException(curr.cond.pos, "Conditional expression is not a bool")
    }
    scopeManager.addLast(curr.thenScope)
    curr.thenDo.accept(this)
    scopeManager.removeLast()
    if (curr.elseDo != null) {
      scopeManager.addLast(curr.elseScope)
      curr.elseDo.accept(this)
      scopeManager.removeLast()
    }
  }

  override fun visit(curr: FieldNode) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: JumpNode) {
    val globalScope = scopeManager.first()
    when (curr.type) {
      "return" -> {
        val returnType = scopeManager.isFunc()
        if (curr.expr == null && !returnType.isVoid()) {
          throw SemanticException(curr.pos, "Return type doesn't match")
        } else if (curr.expr != null) {
          curr.expr.accept(this)
          if (curr.expr.type != returnType) {
            throw SemanticException(curr.pos, "Return type doesn't match")
          }
        }
      }

      "break", "continue" -> if (!scopeManager.isInLoop()) throw SemanticException(
        curr.pos,
        "Break can only be used in loop"
      )
    }
  }

  override fun visit(curr: PriorExprNode) {
    curr.expr.accept(this)
    curr.type = curr.expr.type
  }

  override fun visit(curr: AtomNode) {
    val scope = scopeManager.last()
    when (curr.id) {
      0 -> curr.type = scope.getType("int")!!
      1 -> curr.type = scope.getType("string")!!
      2 -> curr.type = scope.getVar(curr.literal)!!
      // TODO: how to deal with this pointer?
//      3 -> curr.type = scope.getVar("int")!!
      4 -> curr.type = scope.getType("bool")!!
      5 -> curr.type = scope.getType("bool")!!
      6 -> curr.type = scope.getType("null")!!
    }
  }

  override fun visit(curr: InitExprNode) {
    val type =
      scopeManager.first().getClass(curr.typeDef) ?: throw SemanticException(curr.pos, "Type ${curr.typeDef} not found")
    for (it in curr.arraySize) {
      it.accept(this)
      if (it.type!!.cl.className != "int") {
        throw SemanticException(curr.pos, "Invalid expression for array declaration")
      }
    }
    curr.type = TypeMeta(type, curr.dim)
  }

  private fun checkParamsForFunc(curr: ExprNode, method: FuncMeta, usrInput: List<ExprNode>) {
    val stdInput = method.paramInput
    if (stdInput.size != usrInput.size) {
      throw SemanticException(curr.pos, "Not enough parameters for ${method.funcName}")
    }
    for (pair in stdInput.zip(usrInput)) {
      if (pair.first != pair.second.type) {
        throw SemanticException(pair.second.pos, "Expect ${pair.first}, find ${pair.second.type}")
      }
    }
    curr.type = method.returnType
  }

  override fun visit(curr: FuncCallNode) {
    curr.params.forEach { it.accept(this) }
    val method = scopeManager.last().getFunc(curr.func) ?: throw SemanticException(curr.pos, "No such function")
    checkParamsForFunc(curr, method, curr.params)
  }

  override fun visit(curr: MethodAccessNode) {
    curr.expr.accept(this)
    curr.params.forEach { it.accept(this) }

    val method = curr.expr.type!!.cl.classScope.getFunc(curr.method) ?: throw SemanticException(
      curr.pos,
      "No correspond method for ${curr.expr.type!!.cl.className}.${curr.method}"
    )

    checkParamsForFunc(curr, method, curr.params)
  }

  override fun visit(curr: MemberAccessNode) {
    curr.expr.accept(this)
    curr.type = curr.expr.type!!.cl.classScope.getVar(curr.member) ?: throw SemanticException(
      curr.pos,
      "No correspond member for ${curr.expr.type!!.cl.className}"
    )
  }

  override fun visit(curr: ArrayAccessNode) {
    curr.array.accept(this)
    curr.index.accept(this)
    val arrayType = curr.array.type!!
    val indexType = curr.index.type!!
    if (arrayType.dim == 0 || (indexType.cl.className != "int" && indexType.dim != 0)) {
      throw SemanticException(curr.pos, "Invalid array access")
    }
    curr.type = TypeMeta(arrayType.cl, arrayType.dim - 1)
  }

  override fun visit(curr: SuffixExprNode) {
    curr.expr.accept(this)
    val src = curr.expr.type!!
    if (src.cl.className != "int" || src.dim != 0) {
      throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
    }
    curr.type = src
  }

  override fun visit(curr: PrefixExprNode) {
    curr.expr.accept(this)
    val src = curr.expr.type!!
    when (curr.op) {
      "++", "--", "+", "-", "~" -> {
        if (src.cl.className != "int" || src.dim != 0) {
          throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
        }
        curr.type = src
      }

      "!" -> {
        if (src.cl.className != "bool" || src.dim != 0) {
          throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
        }
        curr.type = src
      }
    }
  }

  override fun visit(curr: BinaryExprNode) {
    curr.lhs.accept(this)
    curr.rhs.accept(this)
    val lhs = curr.lhs.type!!
    val rhs = curr.rhs.type!!

    when (curr.op) {
      "+", "<", "<=", ">", ">=" -> {
        // all binary operators require the opposite to be the same for int and bool
        if (lhs.cl.className != rhs.cl.className) {
          throw SemanticException(curr.pos, "Type don't match")
        }
        if ((lhs.cl.className != "int" && lhs.cl.className != "string") || lhs.dim != 0 || rhs.dim != 0) {
          throw SemanticException(curr.pos, "Invalid binary operator ${curr.op} for ${lhs.cl}")
        }
        // TODO: determine whether it should use copy or reference to TypeMeta
        if (curr.op == "+") {
          curr.type = lhs
        } else {
          curr.type = scopeManager.first().getType("bool")
        }
      }

      "*", "/", "%", "-", "<<", ">>", "&", "^", "|" -> {
        if (lhs.cl.className != rhs.cl.className) {
          throw SemanticException(curr.pos, "Type don't match")
        }
        if (lhs.cl.className != "int" || lhs.dim != 0 || rhs.dim != 0) { // those operators are only for int
          throw SemanticException(curr.pos, "Invalid binary operator ${curr.op} for ${lhs.cl}")
        }
        curr.type = lhs
      }

      "==", "!=" -> {
        if (lhs.dim != 0 || rhs.dim != 0) { // for arrays
          if (lhs.cl.className != "null" && rhs.cl.className != "null") {
            throw SemanticException(
              curr.pos,
              "Invalid binary operator ${curr.op} for ${lhs.cl} and ${rhs.cl}"
            )
          }
        }
        // all binary operators require the opposite to be the same for int and bool
        if (lhs != rhs) {
          throw SemanticException(curr.pos, "Type don't match")
        }
        curr.type = TypeMeta(scopeManager.first().getClass("bool")!!, 0)
      }

      "&&", "||" -> {
        if (lhs.cl.className != rhs.cl.className) {
          throw SemanticException(curr.pos, "Type don't match")
        }
        if (lhs.cl.className != "bool" || lhs.dim != 0 || rhs.dim != 0) {
          throw SemanticException(curr.pos, "Invalid binary operator ${curr.op} for ${lhs.cl}")
        }
        curr.type = lhs
      }
    }
  }

  override fun visit(curr: AssignExprNode) {
    curr.lhs.accept(this)
    curr.rhs.accept(this)
    val lhs = curr.lhs
    if ((lhs is AtomNode && lhs.id == 2) || (lhs is MemberAccessNode) || (lhs is ArrayAccessNode)) {
      curr.type = curr.rhs.type
    } else {
      throw SemanticException(lhs.pos, "Invalid left value")
    }
  }
}