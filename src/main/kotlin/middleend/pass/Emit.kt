package middleend.pass

import middleend.basic.TopModule

class Emit(val topModule: TopModule) {
  fun main() {
    for ((_, structType) in topModule.structType) {
      println(structType)
    }

    for ((_, globalVar) in topModule.globalVar) {
      println(globalVar)
    }

    for ((funcName, func) in topModule.func) {
      println(func)
    }
  }
}