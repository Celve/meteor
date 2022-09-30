package frontend.ast.controller

import frontend.ast.node.*
import frontend.parser.MeteorBaseVisitor
import frontend.parser.MeteorParser
import frontend.utils.SrcPos

class AstBuilder : MeteorBaseVisitor<BaseNode>() {
  override fun visitProg(ctx: MeteorParser.ProgContext?): BaseNode {
    return ProgNode(SrcPos(ctx!!), visit(ctx.progBlock()))
  }

  override fun visitProgBlock(ctx: MeteorParser.ProgBlockContext?): BaseNode {
    return ProgBlockNode(SrcPos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitFuncBlock(ctx: MeteorParser.FuncBlockContext?): BaseNode {
    return FuncBlockNode(SrcPos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitClassBlock(ctx: MeteorParser.ClassBlockContext?): BaseNode {
    return ClassBlockNode(SrcPos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitSimpleBlock(ctx: MeteorParser.SimpleBlockContext?): BaseNode {
    // TODO: double check this, I don't sure the getChild() work
    return SimpleBlockNode(SrcPos(ctx!!), visit(ctx.getChild(0)))
  }

  override fun visitClassDef(ctx: MeteorParser.ClassDefContext?): BaseNode {
    return ClassDefNode(SrcPos(ctx!!), ctx.className.text, visit(ctx.classBlock()))
  }

  override fun visitClassCtor(ctx: MeteorParser.ClassCtorContext?): BaseNode {
    return ClassCtorNode(
      SrcPos(ctx!!),
      ctx.classId.text,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      visit(ctx.funcBlock())
    )
  }

  override fun visitFuncDef(ctx: MeteorParser.FuncDefContext?): BaseNode {
    return FuncDefNode(
      SrcPos(ctx!!),
      ctx.funcName.text,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      ctx.returnType().text,
      visit(ctx.funcBlock())
    )
  }

  override fun visitLambdaDef(ctx: MeteorParser.LambdaDefContext?): BaseNode {
    return LambdaDefNode(
      SrcPos(ctx!!),
      ctx.BitwiseAnd() != null,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      visit(ctx.funcBlock())
    )
  }

  override fun visitVarDecl(ctx: MeteorParser.VarDeclContext?): BaseNode {
    val assigns: MutableList<Pair<String, ExprNode?>> = mutableListOf()
    for (it in ctx!!.assignUnit()) {
      val expr = if (it.expr() == null) null else visit(it.expr())
      assigns.add(Pair(it.Id().text, expr as ExprNode?))
    }
    return VarDeclNode(SrcPos(ctx), ctx.varType().text, assigns)
  }

  override fun visitForSuite(ctx: MeteorParser.ForSuiteContext?): BaseNode {
    val init = if (ctx!!.forInitUnit().varDecl() == null) {
      null
    } else {
      // TODO: deal with expression situation
      visit(ctx.forInitUnit().varDecl()) as? VarDeclNode
    }
    val cond = if (ctx.forCondUnit().expr() == null) null else visit(ctx.forCondUnit().expr()) as ExprNode
    val step = if (ctx.forStepUnit().expr() == null) null else visit(ctx.forStepUnit().expr()) as ExprNode

    return ForSuiteNode(SrcPos(ctx), init, cond, step, visit(ctx.extendedSuite()))
  }

  override fun visitWhileSuite(ctx: MeteorParser.WhileSuiteContext?): BaseNode {
    return WhileSuiteNode(SrcPos(ctx!!), visit(ctx.expr()) as ExprNode, visit(ctx.extendedSuite()))
  }

  override fun visitJump(ctx: MeteorParser.JumpContext?): BaseNode {
    val expr = if (ctx!!.expr() == null) null else visit(ctx.expr()) as ExprNode // cannot pass null to visit
    return JumpNode(
      SrcPos(ctx),
      ctx.op.text,
      expr
    )
  }

  override fun visitCondSuite(ctx: MeteorParser.CondSuiteContext?): BaseNode {
    val thenDo = visit(ctx!!.extendedSuite(0))
    val elseDo = if (ctx.Else() == null) null else visit(ctx.extendedSuite(1))

    return CondSuiteNode(
      SrcPos(ctx),
      visit(ctx.expr()) as ExprNode,
      thenDo,
      elseDo,
    )
  }

  override fun visitFieldSuite(ctx: MeteorParser.FieldSuiteContext?): BaseNode {
    return FieldSuiteNode(SrcPos(ctx!!), visit(ctx.funcBlock()))
  }

  // when let antlr automatically iterate the tree
  // it only returns the last result of its children
  override fun visitShort(ctx: MeteorParser.ShortContext?): BaseNode {
    return ShortNode(SrcPos(ctx!!), if (ctx.expr() == null) null else visit(ctx.expr()) as ExprNode)
  }

  override fun visitPriorExpr(ctx: MeteorParser.PriorExprContext?): BaseNode {
    return PriorExprNode(SrcPos(ctx!!), visit(ctx.expr()) as ExprNode)
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
    return AtomNode(SrcPos(ctx), id, ctx.basicExpr().text)
  }

  override fun visitInitExpr(ctx: MeteorParser.InitExprContext?): BaseNode {
    val exprs: MutableList<ExprNode?> = mutableListOf()
    for (it in ctx!!.bracketedExpr()) {
      exprs.add(if (it.expr() != null) visit(it.expr()) as ExprNode else null)
    }
    return InitExprNode(
      SrcPos(ctx),
      ctx.classType().text,
      ctx.bracketedExpr().size,
      exprs
    )
  }

  override fun visitLambdaCall(ctx: MeteorParser.LambdaCallContext?): BaseNode {
    return LambdaCallNode(
      SrcPos(ctx!!),
      visit(ctx.lambdaDef()) as LambdaDefNode,
      ctx.paramInputList().expr().map { visit(it) as ExprNode }
    )
  }

  override fun visitFuncCall(ctx: MeteorParser.FuncCallContext?): BaseNode {
    return FuncCallNode(
      SrcPos(ctx!!),
      ctx.funcName.text,
      ctx.paramInputList().expr().map { visit(it) as ExprNode }
    )
  }

  override fun visitMethodAccess(ctx: MeteorParser.MethodAccessContext?): BaseNode {
    return MethodAccessNode(
      SrcPos(ctx!!),
      visit(ctx.expr()) as ExprNode,
      ctx.methodName.text,
      ctx.paramInputList().expr().map { visit(it) as ExprNode })
  }

  override fun visitMemberAccess(ctx: MeteorParser.MemberAccessContext?): BaseNode {
    return MemberAccessNode(SrcPos(ctx!!), visit(ctx.expr()) as ExprNode, ctx.classMember.text)
  }

  override fun visitArrayAccess(ctx: MeteorParser.ArrayAccessContext?): BaseNode {
    return ArrayAccessNode(SrcPos(ctx!!), visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }

  override fun visitSuffixExpr(ctx: MeteorParser.SuffixExprContext?): BaseNode {
    return SuffixExprNode(SrcPos(ctx!!), visit(ctx.expr()) as ExprNode, ctx.op.text)
  }

  override fun visitPrefixExpr(ctx: MeteorParser.PrefixExprContext?): BaseNode {
    return PrefixExprNode(SrcPos(ctx!!), ctx.prefixOps().text, visit(ctx.expr()) as ExprNode)
  }

  override fun visitBinaryExpr(ctx: MeteorParser.BinaryExprContext?): BaseNode {
    return BinaryExprNode(SrcPos(ctx!!), ctx.op.text, visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }

  override fun visitAssignExpr(ctx: MeteorParser.AssignExprContext?): BaseNode {
    return AssignExprNode(SrcPos(ctx!!), visit(ctx.expr(0)) as ExprNode, visit(ctx.expr(1)) as ExprNode)
  }
}
