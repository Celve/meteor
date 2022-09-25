package abst.utils

import org.antlr.v4.runtime.ParserRuleContext


class Position(ctx: ParserRuleContext) {
  val row = ctx.getStart().line
  val column = ctx.getStart().charPositionInLine
}