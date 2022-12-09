import backend.controller.ASMGenerator
import backend.pass.ASMEmit
import backend.pass.ASMRegisterAllocator
import backend.pass.ASMStackAllocator
import frontend.ast.controller.ASTBuilder
import frontend.ast.controller.AntlrErrorListener
import frontend.ast.node.ProgNode
import frontend.parser.MeteorLexer
import frontend.parser.MeteorParser
import frontend.semantic.SemanticChecker
import frontend.semantic.SymbolCollector
import middleend.controller.IRGenerator
import middleend.pass.*
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
  symbolCollector.visit(builderRoot)

  val semanticChecker = SemanticChecker()
  semanticChecker.visit(builderRoot)

  val middleend = IRGenerator()
  middleend.visit(builderRoot)

//  IREmit.visit(middleend.topModule)

  val eliminator = Eliminator()
  eliminator.visit(middleend.topModule)

//  IREmit.visit(middleend.topModule)

  val ssaConstructor = SSAConstructor()
  ssaConstructor.visit(middleend.topModule)

  SuperValueNumbering.visit(middleend.topModule)

  IREmit.visit(middleend.topModule)

  val ssaDestructor = SSADestructor()
  ssaDestructor.visit(middleend.topModule)

//  IREmit.visit(middleend.topModule)

  val backend = ASMGenerator()
  backend.visit(middleend.topModule)

  val asmEmit = ASMEmit()
//  asmEmit.visit(backend.module!!)

  val asmAllocator = ASMStackAllocator()
//  asmAllocator.visit(backend.module!!)

//  asmEmit.visit(backend.module!!)

  val registerAllocator = ASMRegisterAllocator()
  registerAllocator.visit(backend.module!!)
//  asmEmit.visit(backend.module!!)
}

