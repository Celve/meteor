package frontend.utils

import org.antlr.v4.runtime.ParserRuleContext


// CodePos is used to record and identify code location
// it would be used in error messages
class CodePos(val row: Int, val column: Int) {
  constructor(ctx: ParserRuleContext) : this(ctx.getStart().line, ctx.getStart().charPositionInLine)

  override fun toString(): String {
    return "${row}:${column}"
  }
}