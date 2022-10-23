package middleend.pass

import middleend.basic.TopModule

class IREmit(val topModule: TopModule) {
  fun main() {
    println(
      "; ModuleID = 'test'\n" +
          "source_filename = \"test\"\n" +
          "target datalayout = \"e-m:o-i64:64-i128:128-n32:64-S128\"\n" +
          "target triple = \"riscv32\"\n"
    )

    for ((_, func) in topModule.builtinFunc) {
      print(func.toDeclaration())
    }
    println()

    for ((_, structType) in topModule.structType) {
      println(structType.toDeclaration())
    }

    for ((_, globalVar) in topModule.globalVar) {
      print(globalVar)
    }
    if (topModule.globalVar.size >= 1) {
      println()
    }

    for ((_, const) in topModule.constStr) {
      print(const)
    }
    if (topModule.constStr.size >= 1) {
      println()
    }

    for ((_, func) in topModule.funcList) {
      println(func)
    }
  }
}