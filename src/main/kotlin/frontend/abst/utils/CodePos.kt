package frontend.abst.utils

import org.antlr.v4.runtime.ParserRuleContext


class CodePos(val row: Int, val column: Int) {
  constructor(ctx: ParserRuleContext) : this(ctx.getStart().line, ctx.getStart().charPositionInLine)

  override fun toString(): String {
    return "${row}:${column}"
  }
}