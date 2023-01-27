package middleend

import frontend.ast.node.ProgNode
import middleend.basic.TopModule
import middleend.controller.IRGenerator
import middleend.pass.*

object MiddleEndManager {
  fun visit(progNode: ProgNode, testing: Boolean, buildOptions: HashSet<String>): TopModule {
    val irGenerator = IRGenerator()
    irGenerator.visit(progNode)

    val module = irGenerator.topModule

    // optimization
    Transformer.visit(module)
    if (buildOptions.contains("--localize")) {
      Localizer.visit(module)
      Checker.visit(module)
    }
    SSAConstructor.visit(module)
    Checker.visit(module)
    if (buildOptions.contains("--inline")) {
      Inliner.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--sccp")) {
      ConstPropagator.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--svn")) {
      SuperValueNumbering.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--dvnt")) {
      DomValueNumbering.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--licm") && buildOptions.contains("--dvnt")) {
      LoopInvarCodeMotion.visit(module) // must be placed after dvnt
      Checker.visit(module)
    }
    if (buildOptions.contains("--dmnt")) {
      DomMemNumbering.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--sr")) {
      StrengthReduction.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--dvnt")) {
      DomValueNumbering.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--adce")) {
      Eliminator.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--peephole")) {
      Peephole.visit(module)
      Checker.visit(module)
    }

    if (testing) {
      IREmit.visit(module)
    }

    SSADestructor.visit(module)

    return module
  }
}