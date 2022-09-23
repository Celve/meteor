package abst.control

import abst.nodes.*
import abst.utils.Position
import abst.utils.Scope
import parser.MeteorBaseVisitor
import parser.MeteorParser


class Builder : MeteorBaseVisitor<Base>() {
  override fun visitProg(ctx: MeteorParser.ProgContext?): Base {
    return Prog(Position(ctx!!), Scope(null), ctx.children.map { visit(it) }.toTypedArray())
  }

  override fun visitFor(ctx: MeteorParser.ForContext?): Base {
    val init = if (ctx!!.forInitUnit().varDecl() == null) {
      null
    } else {
      // TODO: deal with expression situation
      visit(ctx.forInitUnit().varDecl()) as? VarDecl
    }
    val cond = if (ctx.forCondUnit().expr() == null) null else visit(ctx.forCondUnit().expr()) as Expr
    val step = if (ctx.forStepUnit().expr() == null) null else visit(ctx.forStepUnit().expr()) as Expr

    return For(Position(ctx), Scope(null), init, cond, step, visit(ctx.extendedBlock()))
  }

  override fun visitWhile(ctx: MeteorParser.WhileContext?): Base {
    return While(Position(ctx!!), visit(ctx.expr()) as Expr, visit(ctx.extendedBlock()))
  }

  override fun visitJump(ctx: MeteorParser.JumpContext?): Base {
    val expr = if (ctx!!.expr() == null) null else visit(ctx.expr()) as Expr // cannot pass null to visit

    return Jump(
      Position(ctx),
      ctx.op.text,
      expr
    )
  }

  override fun visitCond(ctx: MeteorParser.CondContext?): Base {
    val thenDo = if (ctx!!.extendedBlock(0) == null) null else visit(ctx.extendedBlock(0))
    val elseDo = if (ctx.extendedBlock(1) == null) null else visit(ctx.extendedBlock(1))

    return Cond(
      Position(ctx),
      visit(ctx.expr()) as Expr,
      thenDo,
      elseDo,
    )
  }

  // TODO: solve that the extended block should have a local scope
  override fun visitFuncSuite(ctx: MeteorParser.FuncSuiteContext?): Base {
    return FuncSuite(Position(ctx!!), ctx.stmt().map { visit(it) }.toTypedArray())
  }

  override fun visitAtom(ctx: MeteorParser.AtomContext?): Base {
    return Atom(Position(ctx!!), ctx.text)
  }

  override fun visitFuncCall(ctx: MeteorParser.FuncCallContext?): Base {
    return FuncCall(
      Position(ctx!!),
      ctx.funcName.text,
      ctx.paramInputList().expr().map { visit(it) as Expr }.toTypedArray()
    )
  }

  override fun visitMemberAccess(ctx: MeteorParser.MemberAccessContext?): Base {
    return MemberAccess(Position(ctx!!), visit(ctx.expr()) as Expr, ctx.classMember.text)
  }

  override fun visitArrayAccess(ctx: MeteorParser.ArrayAccessContext?): Base {
    return ArrayAccess(Position(ctx!!), visit(ctx.expr(0)) as Expr, visit(ctx.expr(1)) as Expr)
  }

  override fun visitSuffixExpr(ctx: MeteorParser.SuffixExprContext?): Base {
    return SuffixExpr(Position(ctx!!), visit(ctx.expr()) as Expr, ctx.op.text)
  }

  override fun visitPrefixExpr(ctx: MeteorParser.PrefixExprContext?): Base {
    return PrefixExpr(Position(ctx!!), ctx.prefixOps().text, visit(ctx.expr()) as Expr)
  }

  override fun visitBinaryExpr(ctx: MeteorParser.BinaryExprContext?): Base {
    return BinaryExpr(Position(ctx!!), ctx.op.text, visit(ctx.expr(0)) as Expr, visit(ctx.expr(1)) as Expr)
  }

  override fun visitAssignExpr(ctx: MeteorParser.AssignExprContext?): Base {
    return AssignExpr(Position(ctx!!), visit(ctx.expr(0)) as Expr, visit(ctx.expr(1)) as Expr)
  }
}
