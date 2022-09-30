import frontend.ast.controller.AntlrErrorListener
import frontend.ast.controller.AstBuilder
import frontend.ast.node.ProgNode
import frontend.metadata.ClassMetadata
import frontend.metadata.FuncMetadata
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import frontend.utils.GlobalScope
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.FileInputStream

fun setPrimitives(scope: GlobalScope) {
  scope.setClass("void", ClassMetadata("void"))
  scope.setClass("int", ClassMetadata("int"))
  scope.setClass("bool", ClassMetadata("bool"))
  scope.setClass("null", ClassMetadata("null"))
  scope.setClass("string", ClassMetadata("string"))
}

fun setBuiltinFuncs(scope: GlobalScope) {
  val voidType = scope.getFuncType("void")!!
  val intType = scope.getVarType("int")!!
  val stringType = scope.getVarType("string")!!

  scope.setFunc("print", FuncMetadata("print", listOf(stringType), voidType))
  scope.setFunc("println", FuncMetadata("println", listOf(stringType), voidType))
  scope.setFunc("printInt", FuncMetadata("printInt", listOf(intType), voidType))
  scope.setFunc("printlnInt", FuncMetadata("printlnInt", listOf(intType), voidType))
  scope.setFunc("getString", FuncMetadata("getString", listOf(), stringType))
  scope.setFunc("getInt", FuncMetadata("getInt", listOf(), intType))
  scope.setFunc("toString", FuncMetadata("toString", listOf(intType), stringType))

  val classMeta = scope.getClass("string")!!
  classMeta.classScope.setFunc("length", FuncMetadata("length", listOf(), intType))
  classMeta.classScope.setFunc("substring", FuncMetadata("substring", listOf(intType, intType), stringType))
  classMeta.classScope.setFunc("parseInt", FuncMetadata("parseInt", listOf(), intType))
  classMeta.classScope.setFunc("ord", FuncMetadata("ord", listOf(intType), intType))
}

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

  setPrimitives(builderRoot.scope)
  setBuiltinFuncs(builderRoot.scope)

  val symbolCollector = SymbolCollector()
  symbolCollector.visit(builderRoot)

  val semanticChecker = SemanticChecker()
  semanticChecker.visit(builderRoot)
}