import frontend.ast.controller.AntlrErrorListener
import frontend.ast.controller.AstBuilder
import frontend.ast.node.ProgNode
import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import frontend.utils.GlobalScope
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.FileInputStream

fun setPrimitives(scope: GlobalScope) {
  scope.setClass("void", ClassMd("void"))
  scope.setClass("int", ClassMd("int"))
  scope.setClass("bool", ClassMd("bool"))
  scope.setClass("null", ClassMd("null"))
  scope.setClass("string", ClassMd("string"))
}

fun setBuiltinFuncs(scope: GlobalScope) {
  val voidType = scope.getFuncType("void")!!
  val intType = scope.getVarType("int")!!
  val stringType = scope.getVarType("string")!!

  scope.setFunc("print", FuncMd("print", listOf(stringType), voidType))
  scope.setFunc("println", FuncMd("println", listOf(stringType), voidType))
  scope.setFunc("printInt", FuncMd("printInt", listOf(intType), voidType))
  scope.setFunc("printlnInt", FuncMd("printlnInt", listOf(intType), voidType))
  scope.setFunc("getString", FuncMd("getString", listOf(), stringType))
  scope.setFunc("getInt", FuncMd("getInt", listOf(), intType))
  scope.setFunc("toString", FuncMd("toString", listOf(intType), stringType))

  val classMeta = scope.getClass("string")!!
  classMeta.classScope.setFunc("length", FuncMd("length", listOf(), intType))
  classMeta.classScope.setFunc("substring", FuncMd("substring", listOf(intType, intType), stringType))
  classMeta.classScope.setFunc("parseInt", FuncMd("parseInt", listOf(), intType))
  classMeta.classScope.setFunc("ord", FuncMd("ord", listOf(intType), intType))
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