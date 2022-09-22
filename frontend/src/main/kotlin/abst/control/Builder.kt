package abst.control

import abst.nodes.*
import abst.utils.Position
import parser.MeteorParserBaseVisitor
import parser.MeteorParserParser

class Builder : MeteorParserBaseVisitor<Base>() {
  override fun visitProg(ctx: MeteorParserParser.ProgContext?): Base {
    return Prog(Position(ctx!!), ctx.stmt().map { it -> visit(it) }.toTypedArray())
  }

  override fun visitJumpStmt(ctx: MeteorParserParser.JumpStmtContext?): Base {
    val keyword = when {
      ctx!!.text.startsWith("return") -> 1
      ctx.text.startsWith("break") -> 2
      ctx.text.startsWith("continue") -> 3
      else -> throw Exception("wrong jump statement")
    }
    val expr = if (ctx.expr() == null) null else visit(ctx.expr()) as Expr // cannot pass null to visit

    return JumpStmt(
      Position(ctx),
      keyword,
      expr
    )
  }

  override fun visitCondStmt(ctx: MeteorParserParser.CondStmtContext?): Base {
    return CondStmt(
      Position(ctx!!),
      visit(ctx.expr()) as Expr,
      visit(ctx.blockSuite(0)) as BlockSuite,
      visit(ctx.blockSuite(1)) as BlockSuite
    )
  }

  override fun visitForStmt(ctx: MeteorParserParser.ForStmtContext?): Base {
    val init = if (ctx!!.forInitUnit().varDecl().isEmpty) {
      null
    } else {
      // TODO: deal with expression situation
      visit(ctx.forInitUnit().varDecl()) as? VarDecl
    }
    val cond = if (ctx.forCondUnit().expr().isEmpty) null else visit(ctx.forCondUnit().expr()) as Expr
    val step = if (ctx.forStepUnit().expr().isEmpty) null else visit(ctx.forStepUnit().expr()) as Expr

    return ForStmt(Position(ctx), init, cond, step)
  }

  override fun visitWhileStmt(ctx: MeteorParserParser.WhileStmtContext?): Base {
    return WhileStmt(Position(ctx!!), visit(ctx.expr()) as Expr)
  }
}
