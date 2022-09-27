package frontend.semantic

import exceptions.SemanticException
import frontend.abst.control.Visitor
import frontend.abst.meta.FuncMeta
import frontend.abst.meta.TypeMeta
import frontend.abst.nodes.*
import frontend.abst.utils.ClassScope
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
  // the antlr guarantees it, so here is not check
  // the member and method should be put in both global scope and local scope
  override fun visit(curr: ClassDefNode) {
    val scope = scopeManager.first()
    scope.setClass(curr.className, curr.classMeta)
    scopeManager.addLast(curr.classMeta.classScope, curr.classMeta)
    curr.classSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    scopeManager.addLast(curr.funcMeta.funcScope, null, curr.funcMeta.returnType)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  // the definition of function should be 'in global scope
  // the antlr guarantees it, so here is no check
  override fun visit(curr: FuncDefNode) {
    scopeManager.addLast(curr.funcMeta.funcScope, null, curr.funcMeta.returnType)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDeclNode) {
    val globalScope = scopeManager.first()
    val innerScope = scopeManager.last()

    // only judge for some exceptions
    val varType =
      globalScope.getVarType(curr.varType) ?: throw SemanticException(curr.pos, "No type called ${curr.varType}")
    for (it in curr.assigns) {
      if (innerScope !is ClassScope && innerScope.testVar(it.first)) {
        throw SemanticException(curr.pos, "Redeclare ${it.first}")
      } else if (it.second != null) {
        it.second!!.accept(this)
        if (!varType.matchesWith(it.second!!.type!!)) {
          throw SemanticException(
            curr.pos,
            "Wrong type, expected ${varType}, found ${it.second!!.type}"
          )
        }
      }
      if (innerScope !is ClassScope) {
        innerScope.setVar(it.first, varType)
      }
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
    when (curr.type) {
      "return" -> {
        val returnType =
          scopeManager.getFunc() ?: throw SemanticException(curr.pos, "Return can only be used inside a function")
        if (curr.expr == null && !returnType.isVoid()) {
          throw SemanticException(curr.pos, "Return type doesn't match")
        } else if (curr.expr != null) {
          curr.expr.accept(this)
          if (!returnType.matchesWith(curr.expr.type!!)) {
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

  override fun visit(curr: StmtNode) {
    curr.expr?.accept(this)
  }

  override fun visit(curr: PriorExprNode) {
    curr.expr.accept(this)
    curr.type = curr.expr.type
    curr.assignable = curr.expr.assignable
    println("${curr.pos} is ${curr.assignable}")
  }

  override fun visit(curr: AtomNode) {
    val scope = scopeManager.last()
    when (curr.id) {
      0 -> curr.type = scope.getVarType("int")!!
      1 -> curr.type = scope.getVarType("string")!!
      2 -> curr.type = scope.getVar(curr.literal) ?: throw SemanticException(curr.pos, "${curr.literal} is undeclared")
      // TODO: how to deal with this pointer?
      3 -> {
        scopeManager.getFunc() ?: throw SemanticException(curr.pos, "This could only be used inside a class method")
        val cl =
          scopeManager.getClass() ?: throw SemanticException(curr.pos, "This could only be used inside a class method")
        curr.type = TypeMeta(cl, 0)
      }

      4 -> curr.type = scope.getVarType("bool")!!
      5 -> curr.type = scope.getVarType("bool")!!
      6 -> curr.type = scope.getVarType("null")!!
    }
  }

  override fun visit(curr: InitExprNode) {
    val type =
      scopeManager.first().getClass(curr.typeDef) ?: throw SemanticException(curr.pos, "Type ${curr.typeDef} not found")
    var isNull = false
    for (it in curr.arraySizeList) {
      if (it == null) {
        isNull = true
        continue
      } else if (isNull) {
        throw SemanticException(it.pos, "Such new is not allowed")
      }
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
      if (!pair.first.matchesWith(pair.second.type!!)) {
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

    val varType = curr.expr.type!!
    val method = if (!curr.expr.type!!.isArray()) {
      varType.cl.classScope.getFunc(curr.method) ?: throw SemanticException(
        curr.pos,
        "No correspond method for ${varType.cl.className}.${curr.method}"
      )
    } else if (curr.method == "size") {
      FuncMeta("size", listOf(), scopeManager.last().getVarType("int")!!)
    } else {
      throw SemanticException(curr.pos, "No correspond method for ${varType.cl.className}.${curr.method}")
    }

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
    if (!arrayType.isArray() || !indexType.isInt()) {
      throw SemanticException(curr.pos, "Invalid array access")
    }
    curr.type = TypeMeta(arrayType.cl, arrayType.dim - 1)
  }

  override fun visit(curr: SuffixExprNode) {
    curr.expr.accept(this)
    if (!curr.expr.assignable) {
      throw SemanticException(curr.pos, "Expression is not assignable")
    }
    val src = curr.expr.type!!
    if (!src.isInt()) {
      throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
    }
    curr.type = src
  }

  override fun visit(curr: PrefixExprNode) {
    curr.expr.accept(this)
    val src = curr.expr.type!!
    when (curr.op) {
      "++", "--" -> {
        if (!curr.expr.assignable) {
          throw SemanticException(curr.pos, "Expression is not assignable")
        }
        if (!src.isInt()) {
          throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
        }
      }

      "+", "-", "~" -> {
        if (!src.isInt()) {
          throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
        }
      }

      "!" -> {
        if (!src.isBool()) {
          throw SemanticException(curr.pos, "Invalid unary operator ${curr.op} for ${src.cl}")
        }
      }
    }

    curr.type = src
  }

  override fun visit(curr: BinaryExprNode) {
    curr.lhs.accept(this)
    curr.rhs.accept(this)
    val lhs = curr.lhs.type!!
    val rhs = curr.rhs.type!!

    when (curr.op) {
      "+", "<", "<=", ">", ">=" -> {
        // all binary operators require the opposite to be the same for int and bool
        if (!lhs.matchesWith(rhs)) {
          throw SemanticException(curr.pos, "Type don't match")
        }
        if (!lhs.isInt() && !lhs.isString()) {
          throw SemanticException(curr.pos, "Invalid binary operator ${curr.op} for ${lhs.cl}")
        }
        // TODO: determine whether it should use copy or reference to TypeMeta
        if (curr.op == "+") {
          curr.type = lhs
        } else {
          curr.type = scopeManager.first().getVarType("bool")
        }
      }

      "*", "/", "%", "-", "<<", ">>", "&", "^", "|" -> {
        if (!lhs.isInt() || !rhs.isInt()) { // those operators are only for int
          throw SemanticException(curr.pos, "Invalid binary operator ${curr.op} for ${lhs.cl}")
        }
        curr.type = lhs
      }

      "==", "!=" -> {
        if (lhs.isArray() || rhs.isArray()) { // for arrays
          if (!lhs.isNull() && !rhs.isNull()) {
            throw SemanticException(
              curr.pos,
              "Invalid binary operator ${curr.op} for ${lhs.cl} and ${rhs.cl}"
            )
          }
        }
        // all binary operators require the opposite to be the same for int and bool
        if (!lhs.matchesWith(rhs)) {
          throw SemanticException(curr.pos, "Type don't match")
        }
        curr.type = TypeMeta(scopeManager.first().getClass("bool")!!, 0)
      }

      "&&", "||" -> {
        if (!lhs.isBool() || !rhs.isBool()) {
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
    if (lhs.assignable) {
      if (!curr.lhs.type!!.matchesWith(curr.rhs.type!!)) {
        throw SemanticException(curr.pos, "Type mismatching in assignment")
      }
      curr.type = curr.rhs.type
    } else {
      throw SemanticException(lhs.pos, "Invalid left value")
    }
  }
}