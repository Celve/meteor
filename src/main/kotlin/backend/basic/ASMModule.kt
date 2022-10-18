package backend.basic

class ASMModule {
  val funcList = mutableListOf<ASMFunc>()
  var globalVarList = mutableListOf<ASMGlobalPointer>()
  var constStrList = mutableListOf<ASMGlobalPointer>()

  fun addFunc(func: ASMFunc) {
    funcList.add(func)
  }

  fun getFunc(funcName: String): ASMFunc? {
    return funcList.find { it.name == funcName }
  }

  fun addGlobalVar(globalVar: ASMGlobalPointer) {
    globalVarList.add(globalVar)
  }
}