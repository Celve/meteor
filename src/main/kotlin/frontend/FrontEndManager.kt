package frontend

import frontend.ast.controller.ASTBuilder
import frontend.ast.controller.AntlrErrorListener
import frontend.ast.node.ProgNode
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream

object FrontEndManager {
  fun visit(inputStream: InputStream): ProgNode {
    val lexer = MeteorLexer(CharStreams.fromStream(inputStream))
    lexer.removeErrorListeners()
    lexer.addErrorListener(AntlrErrorListener())

    val parser = MeteorParser(CommonTokenStream(lexer))
    parser.removeErrorListeners()
    parser.addErrorListener(AntlrErrorListener())

    val astBuilder = ASTBuilder()
    val builderRoot = astBuilder.visitProg(parser.prog()) as ProgNode

    SymbolCollector.visit(builderRoot)
    SemanticChecker.visit(builderRoot)

    return builderRoot
  }
}