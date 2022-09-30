package frontend.ast.controller

import exceptions.SemanticException
import frontend.utils.SrcPos
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

class AntlrErrorListener : BaseErrorListener() {
  override fun syntaxError(
    recognizer: Recognizer<*, *>?,
    offendingSymbol: Any?,
    line: Int,
    charPositionInLine: Int,
    msg: String?,
    e: RecognitionException?
  ) {
    throw SemanticException(SrcPos(line, charPositionInLine), "Error occurs when parsing")
  }
}