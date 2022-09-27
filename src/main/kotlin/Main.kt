import frontend.abst.control.AntlrErrorListener
import frontend.abst.control.Builder
import frontend.abst.meta.ClassMeta
import frontend.abst.meta.FuncMeta
import frontend.abst.nodes.ProgNode
import frontend.abst.utils.GlobalScope
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.FileInputStream

fun setPrimitives(scope: GlobalScope) {
  scope.setClass("void", ClassMeta("void"))
  scope.setClass("int", ClassMeta("int"))
  scope.setClass("bool", ClassMeta("bool"))
  scope.setClass("null", ClassMeta("null"))
  scope.setClass("string", ClassMeta("string"))
}

fun setBuiltinFuncs(scope: GlobalScope) {
  val voidType = scope.getFuncType("void")!!
  val intType = scope.getVarType("int")!!
  val stringType = scope.getVarType("string")!!

  scope.setFunc("print", FuncMeta("print", listOf(stringType), voidType))
  scope.setFunc("println", FuncMeta("println", listOf(stringType), voidType))
  scope.setFunc("printInt", FuncMeta("printInt", listOf(intType), voidType))
  scope.setFunc("printlnInt", FuncMeta("printlnInt", listOf(intType), voidType))
  scope.setFunc("getString", FuncMeta("getString", listOf(), stringType))
  scope.setFunc("getInt", FuncMeta("getInt", listOf(), intType))
  scope.setFunc("toString", FuncMeta("toString", listOf(intType), stringType))

  val classMeta = scope.getClass("string")!!
  classMeta.classScope.setFunc("length", FuncMeta("length", listOf(), intType))
  classMeta.classScope.setFunc("substring", FuncMeta("substring", listOf(intType, intType), stringType))
  classMeta.classScope.setFunc("parseInt", FuncMeta("parseInt", listOf(), intType))
  classMeta.classScope.setFunc("ord", FuncMeta("ord", listOf(intType), intType))
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

  val builder = Builder()
  val builderRoot = builder.visitProg(parserRoot) as ProgNode

  setPrimitives(builderRoot.scope)
  setBuiltinFuncs(builderRoot.scope)

  val symbolCollector = SymbolCollector()
  symbolCollector.visit(builderRoot)

  val semanticChecker = SemanticChecker()
  semanticChecker.visit(builderRoot)
}