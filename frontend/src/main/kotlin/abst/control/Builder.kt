package abst.control

import abst.nodes.*
import abst.utils.Position
import abst.utils.Scope
import parser.MeteorBaseVisitor
import parser.MeteorParser
import java.util.*


class Builder : MeteorBaseVisitor<BaseNode>() {
  override fun visitProg(ctx: MeteorParser.ProgContext?): BaseNode {
    return ProgNode(Position(ctx!!), visit(ctx.suite()))
  }

  override fun visitSuite(ctx: MeteorParser.SuiteContext?): BaseNode {
    return SuiteNode(Position(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitFuncSuite(ctx: MeteorParser.FuncSuiteContext?): BaseNode {
    return FuncSuiteNode(Position(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitClassSuite(ctx: MeteorParser.ClassSuiteContext?): BaseNode {
    return ClassSuiteNode(Position(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitSimpleSuite(ctx: MeteorParser.SimpleSuiteContext?): BaseNode {
    // TODO: double check this, I don't sure the getChild() work
    return SimpleSuiteNode(Position(ctx!!), visit(ctx.getChild(0)))
  }

  override fun visitClassDef(ctx: MeteorParser.ClassDefContext?): BaseNode {
    return ClassDefNode(Position(ctx!!), ctx.className.text, visit(ctx.classSuite()))
  }

  override fun visitFuncDef(ctx: MeteorParser.FuncDefContext?): BaseNode {
    return FuncDefNode(
      Position(ctx!!),
      ctx.funcName.text,
      ctx.paramDefList().varType().map { it.text },
      ctx.paramDefList().Id().map { it.text },
      ctx.returnType().text,
      visit(ctx.funcSuite())
    )
  }

  override fun visitVarDecl(ctx: MeteorParser.VarDeclContext?): BaseNode {
    val assigns: Vector<Pair<String, BaseNode?>> = Vector()
    for (it in ctx!!.assignUnit()) {
      val expr = if (it.expr() == null) null else visit(it.expr())
      assigns.addElement(Pair(it.Id().text, expr))
    }
    return VarDeclNode(Position(ctx), ctx.varType().text, assigns.elements().toList())
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

    return ForNode(Position(ctx), Scope(null), init, cond, step, visit(ctx.extendedBlock()))
  }

  override fun visitWhile(ctx: MeteorParser.WhileContext?): BaseNode {
    return WhileNode(Position(ctx!!), visit(ctx.expr()) as ExprNode, visit(ctx.extendedBlock()))
  }

  override fun visitJump(ctx: MeteorParser.JumpContext?): BaseNode {
    val expr = if (ctx!!.expr() == null) null else visit(ctx.expr()) as ExprNode // cannot pass null to visit

    return JumpNode(
      Position(ctx),
      ctx.op.text,
      expr
    )
  }

  override fun visitCond(ctx: MeteorParser.CondContext?): BaseNode {
    val thenDo = if (ctx!!.extendedBlock(0) == null) null else visit(ctx.extendedBlock(0))
    val elseDo = if (ctx.extendedBlock(1) == null) null else visit(ctx.extendedBlock(1))

    return CondNode(
      Position(ctx),
      visit(ctx.expr()) as ExprNode,
      thenDo,
      elseDo,
    )
  }

  override fun visitAtom(ctx: MeteorParser.AtomContext?): BaseNode {
    return AtomNode(Position(ctx!!), ctx.text)
  }

  override fun visitFuncCall(ctx: MeteorParser.FuncCallContext?): BaseNode {
    return FuncCallNode(
      Position(ctx!!),
      ctx.funcName.text,
      ctx.paramInputList().expr().map { visit(it) as ExprNode }.toTypedArray()
    )
  }

  override fun visitMemberAccess(ctx: MeteorParser.MemberAccessContext?): BaseNode {
    return MemberAccessNode(Position(ctx!!), visit(ctx.expr()) as ExprNode, ctx.classMember.text)
  }

  override fun visitArrayAccess(ctx: MeteorParser.ArrayAccessContext?): BaseNode {
    return ArrayAccessNode(Position(ctx!!), visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }

  override fun visitSuffixExpr(ctx: MeteorParser.SuffixExprContext?): BaseNode {
    return SuffixExprNode(Position(ctx!!), visit(ctx.expr()) as ExprNode, ctx.op.text)
  }

  override fun visitPrefixExpr(ctx: MeteorParser.PrefixExprContext?): BaseNode {
    return PrefixExprNode(Position(ctx!!), ctx.prefixOps().text, visit(ctx.expr()) as ExprNode)
  }

  override fun visitBinaryExpr(ctx: MeteorParser.BinaryExprContext?): BaseNode {
    return BinaryExprNode(Position(ctx!!), ctx.op.text, visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }

  override fun visitAssignExpr(ctx: MeteorParser.AssignExprContext?): BaseNode {
    return AssignExprNode(Position(ctx!!), visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }
}
