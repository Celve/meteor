package frontend.ast.controller

import frontend.ast.nodes.*
import frontend.parser.MeteorBaseVisitor
import frontend.parser.MeteorParser
import frontend.utils.CodePos
import java.util.*

class AstBuilder : MeteorBaseVisitor<BaseNode>() {
  override fun visitProg(ctx: MeteorParser.ProgContext?): BaseNode {
    return ProgNode(CodePos(ctx!!), visit(ctx.progBlock()))
  }

  override fun visitProgBlock(ctx: MeteorParser.ProgBlockContext?): BaseNode {
    return ProgBlockNode(CodePos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitFuncBlock(ctx: MeteorParser.FuncBlockContext?): BaseNode {
    return FuncBlockNode(CodePos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitClassBlock(ctx: MeteorParser.ClassBlockContext?): BaseNode {
    return ClassBlockNode(CodePos(ctx!!), ctx.children?.map { visit(it) } ?: listOf())
  }

  override fun visitSimpleBlock(ctx: MeteorParser.SimpleBlockContext?): BaseNode {
    // TODO: double check this, I don't sure the getChild() work
    return SimpleBlockNode(CodePos(ctx!!), visit(ctx.getChild(0)))
  }

  override fun visitClassDef(ctx: MeteorParser.ClassDefContext?): BaseNode {
    return ClassDefNode(CodePos(ctx!!), ctx.className.text, visit(ctx.classBlock()))
  }

  override fun visitClassCtor(ctx: MeteorParser.ClassCtorContext?): BaseNode {
    return ClassCtorNode(
      CodePos(ctx!!),
      ctx.classId.text,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      visit(ctx.funcBlock())
    )
  }

  override fun visitFuncDef(ctx: MeteorParser.FuncDefContext?): BaseNode {
    return FuncDefNode(
      CodePos(ctx!!),
      ctx.funcName.text,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      ctx.returnType().text,
      visit(ctx.funcBlock())
    )
  }

  override fun visitLambdaDef(ctx: MeteorParser.LambdaDefContext?): BaseNode {
    return LambdaDefNode(
      CodePos(ctx!!),
      ctx.BitwiseAnd() != null,
      ctx.paramDeclList().paramDecl().map { Pair(it.varType().text, it.Id().text) },
      visit(ctx.funcBlock())
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

  override fun visitForSuite(ctx: MeteorParser.ForSuiteContext?): BaseNode {
    val init = if (ctx!!.forInitUnit().varDecl() == null) {
      null
    } else {
      // TODO: deal with expression situation
      visit(ctx.forInitUnit().varDecl()) as? VarDeclNode
    }
    val cond = if (ctx.forCondUnit().expr() == null) null else visit(ctx.forCondUnit().expr()) as ExprNode
    val step = if (ctx.forStepUnit().expr() == null) null else visit(ctx.forStepUnit().expr()) as ExprNode

    if (ctx.extendedSuite() == null) {
      println("this is impossible")
    }
    return ForSuiteNode(CodePos(ctx), init, cond, step, visit(ctx.extendedSuite()))
  }

  override fun visitWhileSuite(ctx: MeteorParser.WhileSuiteContext?): BaseNode {
    return WhileSuiteNode(CodePos(ctx!!), visit(ctx.expr()) as ExprNode, visit(ctx.extendedSuite()))
  }

  override fun visitJump(ctx: MeteorParser.JumpContext?): BaseNode {
    val expr = if (ctx!!.expr() == null) null else visit(ctx.expr()) as ExprNode // cannot pass null to visit

    return JumpNode(
      CodePos(ctx),
      ctx.op.text,
      expr
    )
  }

  override fun visitCondSuite(ctx: MeteorParser.CondSuiteContext?): BaseNode {
    val thenDo = visit(ctx!!.extendedSuite(0))
    val elseDo = if (ctx.Else() == null) null else visit(ctx.extendedSuite(1))

    return CondSuiteNode(
      CodePos(ctx),
      visit(ctx.expr()) as ExprNode,
      thenDo,
      elseDo,
    )
  }

  override fun visitFieldSuite(ctx: MeteorParser.FieldSuiteContext?): BaseNode {
    return FieldSuiteNode(CodePos(ctx!!), visit(ctx.funcBlock()))
  }

  // when let antlr automatically iterate the tree
  // it only returns the last result of its children
  override fun visitShort(ctx: MeteorParser.ShortContext?): BaseNode {
    return ShortNode(CodePos(ctx!!), if (ctx.expr() == null) null else visit(ctx.expr()) as ExprNode)
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
