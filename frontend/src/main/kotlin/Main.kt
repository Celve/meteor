import abst.control.Builder
import abst.nodes.ProgNode
import abst.utils.GlobalScope
import meta.ClassMeta
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parser.MeteorLexer
import parser.MeteorParser
import semantic.SymbolCollector
import java.io.FileInputStream

fun setPrimitives(scope: GlobalScope) {
  scope.setClass("void", ClassMeta("void", listOf(), listOf()))
  scope.setClass("int", ClassMeta("int", listOf(), listOf()))
  scope.setClass("bool", ClassMeta("bool", listOf(), listOf()))
}

fun main(args: Array<String>) {
  val input = if (args.size > 1) FileInputStream(args[1]) else System.`in`
  val lexer = MeteorLexer(CharStreams.fromStream(input))
  val parser = MeteorParser(CommonTokenStream(lexer))
  val parserRoot = parser.prog()
  val builder = Builder()
  val builderRoot = builder.visitProg(parserRoot) as ProgNode
  setPrimitives(builderRoot.scope)
  val symbolCollector = SymbolCollector()
  symbolCollector.visit(builderRoot)
  builderRoot.scope.debug()
//  val semanticChecker = SemanticChecker()
//  semanticChecker.visit(builderRoot as ProgNode)
}