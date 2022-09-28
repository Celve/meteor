package frontend.abst.controller

import frontend.abst.nodes.*
import frontend.parser.MeteorBaseVisitor
import frontend.parser.MeteorParser
import frontend.utils.CodePos
import java.util.*

class AbstBuilder : MeteorBaseVisitor<BaseNode>() {
  override fun visitProg(ctx: MeteorParser.ProgContext?): BaseNode {
    return ProgNode(CodePos(ctx!!), visit(ctx.suite()))
  }

  override fun visitSuite(ctx: MeteorParser.SuiteContext?): BaseNode {
    return SuiteNode(CodePos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitFuncSuite(ctx: MeteorParser.FuncSuiteContext?): BaseNode {
    return FuncSuiteNode(CodePos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitClassSuite(ctx: MeteorParser.ClassSuiteContext?): BaseNode {
    return ClassSuiteNode(CodePos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitSimpleSuite(ctx: MeteorParser.SimpleSuiteContext?): BaseNode {
    // TODO: double check this, I don't sure the getChild() work
    return SimpleSuiteNode(CodePos(ctx!!), visit(ctx.getChild(0)))
  }

  override fun visitClassDef(ctx: MeteorParser.ClassDefContext?): BaseNode {
    return ClassDefNode(CodePos(ctx!!), ctx.className.text, visit(ctx.classSuite()))
  }

  override fun visitClassCtor(ctx: MeteorParser.ClassCtorContext?): BaseNode {
    return ClassCtorNode(
      CodePos(ctx!!),
      ctx.classId.text,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      visit(ctx.funcSuite())
    )
  }

  override fun visitFuncDef(ctx: MeteorParser.FuncDefContext?): BaseNode {
    return FuncDefNode(
      CodePos(ctx!!),
      ctx.funcName.text,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      ctx.returnType().text,
      visit(ctx.funcSuite())
    )
  }

  override fun visitLambdaDef(ctx: MeteorParser.LambdaDefContext?): BaseNode {
    return LambdaDefNode(
      CodePos(ctx!!),
      ctx.BitwiseAnd() != null,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      visit(ctx.funcSuite())
    )
  }

  override fun visitVarDecl(ctx: MeteorParser.VarDeclContext?): BaseNode {
    val assigns: Vector<Pair<String, BaseNode?>> = Vector()
    for (it in ctx!!.assignUnit()) {
      val expr = if (it.expr() == null) null else visit(it.expr())
      assigns.addElement(Pair(it.Id().text, expr))
    }
    return VarDeclNode(CodePos(ctx), ctx.varType().text, assigns.map { Pair(it.first, it.second as ExprNode?) })
  }

  override fun visitFor(ctx: MeteorParser.ForContext?): BaseNode {
    val init = if (ctx!!.forInitUnit().varDecl() == null) {
      null
    } else {
      // TODO: deal with expression situation
      visit(ctx.forInitUnit().varDecl()) as? VarDeclNode
    }
    val cond = if (ctx.forCondUnit().expr() == null) null else visit(ctx.forCondUnit().expr()) as ExprNode
    val step = if (ctx.forStepUnit().expr() == null) null else visit(ctx.forStepUnit().expr()) as ExprNode

    if (ctx.extendedBlock() == null) {
      println("this is impossible")
    }
    return ForNode(CodePos(ctx), init, cond, step, visit(ctx.extendedBlock()))
  }

  override fun visitWhile(ctx: MeteorParser.WhileContext?): BaseNode {
    return WhileNode(CodePos(ctx!!), visit(ctx.expr()) as ExprNode, visit(ctx.extendedBlock()))
  }

  override fun visitJump(ctx: MeteorParser.JumpContext?): BaseNode {
    val expr = if (ctx!!.expr() == null) null else visit(ctx.expr()) as ExprNode // cannot pass null to visit

    return JumpNode(
      CodePos(ctx),
      ctx.op.text,
      expr
    )
  }

  override fun visitCond(ctx: MeteorParser.CondContext?): BaseNode {
    val thenDo = visit(ctx!!.extendedBlock(0))
    val elseDo = if (ctx.Else() == null) null else visit(ctx.extendedBlock(1))

    return CondNode(
      CodePos(ctx),
      visit(ctx.expr()) as ExprNode,
      thenDo,
      elseDo,
    )
  }

  override fun visitField(ctx: MeteorParser.FieldContext?): BaseNode {
    return FieldNode(CodePos(ctx!!), visit(ctx.funcSuite()))
  }

  // when let antlr automatically iterate the tree
  // it only returns the last result of its children
  override fun visitStmt(ctx: MeteorParser.StmtContext?): BaseNode {
    return StmtNode(CodePos(ctx!!), if (ctx.expr() == null) null else visit(ctx.expr()) as ExprNode)
  }

  override fun visitPriorExpr(ctx: MeteorParser.PriorExprContext?): BaseNode {
    return PriorExprNode(CodePos(ctx!!), visit(ctx.expr()) as ExprNode)
  }

  override fun visitAtom(ctx: MeteorParser.AtomContext?): BaseNode {
    val id = when {
      ctx!!.basicExpr().IntegerLiteral() != null -> 0
      ctx.basicExpr().StringLiteral() != null -> 1
      ctx.basicExpr().Id() != null -> 2
      ctx.basicExpr().This() != null -> 3
      ctx.basicExpr().True() != null -> 4
      ctx.basicExpr().False() != null -> 5
      ctx.basicExpr().Null() != null -> 6
      else -> throw Exception("invalid atom expression")
    }
    return AtomNode(CodePos(ctx), id, ctx.basicExpr().text)
  }

  override fun visitInitExpr(ctx: MeteorParser.InitExprContext?): BaseNode {
    val exprs: Vector<ExprNode?> = Vector()
    for (it in ctx!!.bracketedExpr()) {
      if (it.expr() != null) {
        exprs.addElement(visit(it.expr()) as ExprNode)
      } else {
        exprs.addElement(null)
      }
    }
    return InitExprNode(
      CodePos(ctx),
      ctx.classType().text,
      ctx.bracketedExpr().size,
      exprs.elements().toList()
    )
  }

  override fun visitLambdaCall(ctx: MeteorParser.LambdaCallContext?): BaseNode {
    return LambdaCallNode(
      CodePos(ctx!!),
      visit(ctx.lambdaDef()) as LambdaDefNode,
      ctx.paramInputList().expr().map { visit(it) as ExprNode }
    )
  }

  override fun visitFuncCall(ctx: MeteorParser.FuncCallContext?): BaseNode {
    return FuncCallNode(
      CodePos(ctx!!),
      ctx.funcName.text,
      ctx.paramInputList().expr().map { visit(it) as ExprNode }
    )
  }

  override fun visitMethodAccess(ctx: MeteorParser.MethodAccessContext?): BaseNode {
    return MethodAccessNode(
      CodePos(ctx!!),
      visit(ctx.expr()) as ExprNode,
      ctx.methodName.text,
      ctx.paramInputList().expr().map { visit(it) as ExprNode })
  }

  override fun visitMemberAccess(ctx: MeteorParser.MemberAccessContext?): BaseNode {
    return MemberAccessNode(CodePos(ctx!!), visit(ctx.expr()) as ExprNode, ctx.classMember.text)
  }

  override fun visitArrayAccess(ctx: MeteorParser.ArrayAccessContext?): BaseNode {
    return ArrayAccessNode(CodePos(ctx!!), visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }

  override fun visitSuffixExpr(ctx: MeteorParser.SuffixExprContext?): BaseNode {
    return SuffixExprNode(CodePos(ctx!!), visit(ctx.expr()) as ExprNode, ctx.op.text)
  }

  override fun visitPrefixExpr(ctx: MeteorParser.PrefixExprContext?): BaseNode {
    return PrefixExprNode(CodePos(ctx!!), ctx.prefixOps().text, visit(ctx.expr()) as ExprNode)
  }

  override fun visitBinaryExpr(ctx: MeteorParser.BinaryExprContext?): BaseNode {
    return BinaryExprNode(CodePos(ctx!!), ctx.op.text, visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }

  override fun visitAssignExpr(ctx: MeteorParser.AssignExprContext?): BaseNode {
    return AssignExprNode(CodePos(ctx!!), visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }
}
