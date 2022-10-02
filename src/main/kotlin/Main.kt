import frontend.ast.controller.AntlrErrorListener
import frontend.ast.controller.AstBuilder
import frontend.ast.node.ProgNode
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import middleend.builder.IRBuilder
import middleend.builder.IRVisitor
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.FileInputStream

fun main(args: Array<String>) {
  val input = if (args.size > 1) FileInputStream(args[1]) else System.`in`

  val lexer = MeteorLexer(CharStreams.fromStream(input))
  lexer.removeErrorListeners()
  lexer.addErrorListener(AntlrErrorListener())

  val parser = MeteorParser(CommonTokenStream(lexer))
  parser.removeErrorListeners()
  parser.addErrorListener(AntlrErrorListener())
  val parserRoot = parser.prog()

  val astBuilder = AstBuilder()
  val builderRoot = astBuilder.visitProg(parserRoot) as ProgNode

  val symbolCollector = SymbolCollector()
  symbolCollector.visit(builderRoot)

  val semanticChecker = SemanticChecker()
  semanticChecker.visit(builderRoot)

  val middleend = IRVisitor()
  middleend.visit(builderRoot)
  IRBuilder.debug()

}