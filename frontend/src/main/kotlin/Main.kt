import abst.control.Builder
import abst.nodes.ProgNode
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parser.MeteorLexer
import parser.MeteorParser
import semantic.Checker
import java.io.FileInputStream

fun main(args: Array<String>) {
  val input = if (args.size > 1) FileInputStream(args[1]) else System.`in`
  val lexer = MeteorLexer(CharStreams.fromStream(input))
  val parser = MeteorParser(CommonTokenStream(lexer))
  val parserRoot = parser.prog()
  val builder = Builder()
  val builderRoot = builder.visitProg(parserRoot)
  val checker = Checker()
  checker.visit(builderRoot as ProgNode)
}