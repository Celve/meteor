package backend.basic

import middleend.basic.TopModule

class ASMModule(topModule: TopModule) {
  val funcList = topModule.func.map { ASMFunc(it.value) }

  fun getFunc(funcName: String): ASMFunc? {
    for (func in funcList) {
      if (func.name == funcName) {
        return func
      }
    }
    return null
  }
}