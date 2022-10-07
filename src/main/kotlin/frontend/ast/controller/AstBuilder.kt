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
      ctx!!.IntegerLiteral() != null -> 0
      ctx.StringLiteral() != null -> 1
      ctx.Id() != null -> 2
      ctx.This() != null -> 3
      ctx.True() != null -> 4
      ctx.False() != null -> 5
      ctx.Null() != null -> 6
      else -> throw Exception("invalid atom expression")
    }
    return AtomNode(SrcPos(ctx), id, ctx.text)
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

  override fun visitMethodCall(ctx: MeteorParser.MethodCallContext?): BaseNode {
    return MethodAccessNode(
      SrcPos(ctx!!),
      visit(ctx.suffixExpr()) as ExprNode,
      ctx.methodName.text,
      ctx.paramInputList().expr().map { visit(it) as ExprNode })
  }


  override fun visitMemberAccess(ctx: MeteorParser.MemberAccessContext?): BaseNode {
    return MemberAccessNode(SrcPos(ctx!!), visit(ctx.suffixExpr()) as ExprNode, ctx.memberName.text)
  }

  override fun visitArrayAccess(ctx: MeteorParser.ArrayAccessContext?): BaseNode {
    return ArrayAccessNode(SrcPos(ctx!!), visit(ctx.suffixExpr()) as ExprNode, visit(ctx.expr()) as ExprNode)
  }

  override fun visitSuffixIncrement(ctx: MeteorParser.SuffixIncrementContext?): BaseNode {
    return SuffixExprNode(SrcPos(ctx!!), visit(ctx.suffixExpr()) as ExprNode, ctx.op.text)
  }

  override fun visitPrefixIncrement(ctx: MeteorParser.PrefixIncrementContext?): BaseNode {
    return PrefixExprNode(SrcPos(ctx!!), ctx.op.text, visit(ctx.prefixExpr()) as ExprNode)
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

  override fun visitMulExpr(ctx: MeteorParser.MulExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(
        SrcPos(ctx),
        ctx.op.text,
        ctx.prefixExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.prefixExpr(0))
    }
  }

  override fun visitAddExpr(ctx: MeteorParser.AddExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(
        SrcPos(ctx),
        ctx.op.text,
        ctx.mulExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.mulExpr(0))
    }
  }

  override fun visitShiftExpr(ctx: MeteorParser.ShiftExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(
        SrcPos(ctx),
        ctx.op.text,
        ctx.addExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.addExpr(0))
    }
  }

  override fun visitCmpExpr(ctx: MeteorParser.CmpExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(
        SrcPos(ctx),
        ctx.op.text,
        ctx.shiftExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.shiftExpr(0))
    }
  }

  override fun visitEqualExpr(ctx: MeteorParser.EqualExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(SrcPos(ctx), ctx.op.text, ctx.cmpExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.cmpExpr(0))
    }
  }

  override fun visitBitwiseAndExpr(ctx: MeteorParser.BitwiseAndExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(SrcPos(ctx), "&", ctx.equalExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.equalExpr(0))
    }
  }

  override fun visitBitwiseXorExpr(ctx: MeteorParser.BitwiseXorExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(SrcPos(ctx), "^", ctx.bitwiseAndExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.bitwiseAndExpr(0))
    }
  }

  override fun visitBitwiseOrExpr(ctx: MeteorParser.BitwiseOrExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(SrcPos(ctx), "|", ctx.bitwiseXorExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.bitwiseXorExpr(0))
    }
  }

  override fun visitLogicalAndExpr(ctx: MeteorParser.LogicalAndExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(SrcPos(ctx), "&&", ctx.bitwiseOrExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.bitwiseOrExpr(0))
    }
  }

  override fun visitLogicalOrExpr(ctx: MeteorParser.LogicalOrExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      BinaryExprNode(SrcPos(ctx), "||", ctx.logicalAndExpr().map { visit(it) as ExprNode })
    } else {
      visit(ctx.logicalAndExpr(0))
    }
  }

  override fun visitAssignExpr(ctx: MeteorParser.AssignExprContext?): BaseNode {
    return if (ctx!!.op != null) {
      AssignExprNode(SrcPos(ctx), visit(ctx.logicalOrExpr()) as ExprNode, visit(ctx.assignExpr()) as ExprNode)
    } else {
      visit(ctx.logicalOrExpr())
    }
  }
}
