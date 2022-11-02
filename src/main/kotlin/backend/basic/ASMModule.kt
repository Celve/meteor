package backend.basic

import backend.helper.RegFactory

class ASMModule {
  var funcList = mutableListOf<ASMFunc>()
  var globalPtr = hashMapOf<String, ASMGlobalPointer>()
  val regFactory = RegFactory()

  fun addFunc(func: ASMFunc) {
    funcList.add(func)
  }

  fun getFunc(funcName: String): ASMFunc? {
    return funcList.find { it.name == funcName }
  }

  fun addGlobalPtr(name: String, globalVar: ASMGlobalPointer) {
    globalPtr[name] = globalVar
  }

  fun getGlobalPtr(name: String): ASMGlobalPointer {
    return globalPtr[name]!!
  }
}