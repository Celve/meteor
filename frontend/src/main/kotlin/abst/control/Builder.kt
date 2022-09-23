package abst.control

import abst.nodes.*
import abst.utils.Position
import abst.utils.Scope
import parser.MeteorBaseVisitor
import parser.MeteorParser


class Builder : MeteorBaseVisitor<Base>() {
  override fun visitProg(ctx: MeteorParser.ProgContext?): Base {
    return Prog(Position(ctx!!), Scope(null), visit(ctx.suite()))
  }

  override fun visitSuite(ctx: MeteorParser.SuiteContext?): Base {
    return Suite(Position(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitFuncSuite(ctx: MeteorParser.FuncSuiteContext?): Base {
    return FuncSuite(Position(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitClassSuite(ctx: MeteorParser.ClassSuiteContext?): Base {
    return ClassSuite(Position(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitClassDef(ctx: MeteorParser.ClassDefContext?): Base {
    return ClassDef(Position(ctx!!), ctx.className.text, visit(ctx.classSuite()))
  }

  override fun visitFuncDef(ctx: MeteorParser.FuncDefContext?): Base {
    return FuncDef(
      Position(ctx!!),
      Scope(null),
      ctx.returnType().text,
      ctx.funcName.text,
      ctx.paramDefList().varType().map { it.text },
      ctx.paramDefList().Id().map { it.text },
      visit(ctx.funcSuite())
    )
  }

  override fun visitSimpleSuite(ctx: MeteorParser.SimpleSuiteContext?): Base {
    // TODO: double check this, I don't sure the getChild() work
    return SimpleSuite(Position(ctx!!), visit(ctx.getChild(0)))
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
