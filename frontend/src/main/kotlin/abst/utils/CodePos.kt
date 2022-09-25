package abst.utils

import org.antlr.v4.runtime.ParserRuleContext


class CodePos(ctx: ParserRuleContext) {
  val row = ctx.getStart().line
  val column = ctx.getStart().charPositionInLine
}