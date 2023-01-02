package backend

import backend.controller.ASMGenerator
import backend.pass.ASMEmit
import backend.pass.ASMRegisterAllocator
import middleend.basic.TopModule

object BackEndManager {
  fun visit(topModule: TopModule, testing: Boolean) {
    val backend = ASMGenerator()
    backend.visit(topModule)

    val module = backend.module

    ASMRegisterAllocator.visit(module)

    if (testing) {
      ASMEmit.visit(module)
    }
  }
}