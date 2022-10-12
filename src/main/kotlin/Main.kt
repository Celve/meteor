import frontend.ast.controller.ASTBuilder
import frontend.ast.controller.AntlrErrorListener
import frontend.ast.node.ProgNode
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import middleend.builder.IRVisitor
import middleend.pass.Emit
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

  val astBuilder = ASTBuilder()
  val builderRoot = astBuilder.visitProg(parserRoot) as ProgNode

  val symbolCollector = SymbolCollector()
  symbolCollector.visitProg(builderRoot)

  val semanticChecker = SemanticChecker()
  semanticChecker.visitProg(builderRoot)

  val middleend = IRVisitor()
  middleend.visitProg(builderRoot)

  val emit = Emit(middleend.topModule)
  emit.main()
}