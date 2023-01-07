package backend

import backend.controller.ASMGenerator
import backend.pass.*
import middleend.basic.TopModule

object BackEndManager {
  fun visit(topModule: TopModule, testing: Boolean, buildOptions: HashSet<String>) {
    val backend = ASMGenerator()
    backend.visit(topModule)

    val module = backend.module
    Checker.visit(module)

    ASMRegisterAllocator.visit(module)
    Checker.visit(module)
    if (buildOptions.contains("--adce")) {
      Eliminator.visit(module)
      Checker.visit(module)
    }
    if (buildOptions.contains("--gcp")) {
      GlobalCodePlacement.visit(module)
      Checker.visit(module)
    }

    if (testing) {
      ASMEmit.visit(module)
    }
  }
}