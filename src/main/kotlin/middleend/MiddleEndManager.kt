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
    Eliminator.visit(module)
    SSAConstructor.visit(module)
    Checker.visit(module)
    if (buildOptions.contains("--svn")) {
      SuperValueNumbering.visit(module)
    }
    if (buildOptions.contains("--inline")) {
      Inliner.visit(module)
    }
    Checker.visit(module)

    if (testing) {
      IREmit.visit(module)
    }

    SSADestructor.visit(module)

    return module
  }
}