package frontend.semantic

import exceptions.SemanticException
import frontend.ast.controller.AstVisitor
import frontend.ast.nodes.*
import frontend.metadata.FuncMetadata
import frontend.metadata.TypeMetadata
import frontend.utils.ClassScope
import frontend.utils.ScopeManager
import java.util.*

class SemanticChecker : AstVisitor() {
  private val scopeManager = ScopeManager()
  override fun visit(curr: ProgNode) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
  }

  override fun visit(curr: ProgBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: FuncBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: ClassBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: SimpleBlockNode) {
    curr.child.accept(this)
  }

  // the definition of class should be in global scope
  // the antlr guarantees it, so here is not check
  // the member and method should be put only inside the local space
  override fun visit(curr: ClassDefNode) {
    val scope = scopeManager.first()
    scope.setClass(curr.className, curr.classMetadata)
    scopeManager.addLast(curr.classMetadata)
    curr.classSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    scopeManager.addLast(curr.funcMetadata)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  // definition of function should be in global scope
  // antlr guarantees it, so here is no check
  override fun visit(curr: FuncDefNode) {
    scopeManager.addLast(curr.funcMetadata)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: LambdaDefNode) {
    println("get in lambda def in ${curr.pos}")
    // omit this duplication for the time being
    val innerScope = curr.funcMetadata.funcScope
    val globalScope = scopeManager.first()
    val paramInput: Vector<TypeMetadata> = Vector()

    // init params and add them into local scope
    for (it in curr.params) {
      val varType = globalScope.getVarType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.addElement(varType)
      innerScope.setVar(it.second, varType)
    }
    curr.funcMetadata.paramInput = paramInput.elements().toList()

    scopeManager.addLast(curr.funcMetadata)
    curr.funcSuite?.accept(this)
    scopeManager.removeLast()

    if (curr.funcMetadata.returnType == null) { // this part is a double check, another check is in "return;" situation
      curr.funcMetadata.returnType = globalScope.getFuncType("void")
    }
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
        println(curr.pos)
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
        val recentFunc = scopeManager.getRecentFunc() ?: throw SemanticException(
          curr.pos,
          "Return can only be used inside a function"
        )
        val returnType = recentFunc.returnType

        if (returnType == null) { // check for lambda expr only
          println("get in for lambda only")
          recentFunc.returnType = if (curr.expr == null) { // this allows the origin value in scopeManager to be changed
            scopeManager.first().getFuncType("void")
          } else {
            curr.expr.accept(this)
            curr.expr.type
          }
        } else if (curr.expr == null && !returnType.isVoid()) {
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

  override fun visit(curr: ShortNode) {
    curr.expr?.accept(this)
  }

  override fun visit(curr: PriorExprNode) {
    curr.expr.accept(this)
    curr.type = curr.expr.type
    curr.assignable = curr.expr.assignable
  }

  override fun visit(curr: AtomNode) {
    val scope = scopeManager.last()
    when (curr.id) {
      0 -> curr.type = scope.getVarType("int")!!
      1 -> curr.type = scope.getVarType("string")!!
      2 -> curr.type = scope.getVar(curr.literal) ?: throw SemanticException(curr.pos, "${curr.literal} is undeclared")
      3 -> {
        scopeManager.getRecentFunc() ?: throw SemanticException(
          curr.pos,
          "This could only be used inside a class method"
        )
        val cl =
          scopeManager.getRecentClass() ?: throw SemanticException(
            curr.pos,
            "This could only be used inside a class method"
          )
        curr.type = TypeMetadata(cl, 0)
      }

      4 -> curr.type = scope.getVarType("bool")!!
      5 -> curr.type = scope.getVarType("bool")!!
      6 -> curr.type = scope.getVarType("null")!!
    }
  }

  private fun checkParamsForFunc(curr: ExprNode, func: FuncMetadata, usrInput: List<ExprNode>) {
    val stdInput = func.paramInput
    if (stdInput.size != usrInput.size) {
      throw SemanticException(curr.pos, "Unequal parameters for ${func.funcName}")
    }
    for (pair in stdInput.zip(usrInput)) {
      if (!pair.first.matchesWith(pair.second.type!!)) {
        throw SemanticException(pair.second.pos, "Expect ${pair.first}, find ${pair.second.type}")
      }
    }
    curr.type = func.returnType
  }

  override fun visit(curr: InitExprNode) {
    val globalScope = scopeManager.first()
    val type =
      globalScope.getClass(curr.typeDef) ?: throw SemanticException(curr.pos, "Type ${curr.typeDef} not found")
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
    curr.type = TypeMetadata(type, curr.dim)
  }

  override fun visit(curr: LambdaCallNode) {
    curr.params.forEach { it.accept(this) }
    curr.lambdaDef.accept(this)
    val func = curr.lambdaDef.funcMetadata
    checkParamsForFunc(curr, func, curr.params)
    if (curr.type == null) {
      println("lambda's result is null in ${curr.pos}")
    }
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
      FuncMetadata("size", listOf(), scopeManager.last().getVarType("int")!!)
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
    curr.type = TypeMetadata(arrayType.cl, arrayType.dim - 1)
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
        curr.type = TypeMetadata(scopeManager.first().getClass("bool")!!, 0)
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