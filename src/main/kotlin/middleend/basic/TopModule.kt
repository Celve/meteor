package middleend.basic

class TopModule {
  val constStrMap: LinkedHashMap<String, ConstantStr> = linkedMapOf()
  val structTypeMap: LinkedHashMap<String, StructType> = linkedMapOf()
  val funcMap: LinkedHashMap<String, Func> = linkedMapOf()
  val builtinFuncMap: LinkedHashMap<String, Func> = linkedMapOf()
  val globalVarMap: LinkedHashMap<String, GlobalVariable> = linkedMapOf()

  fun setConst(name: String, value: ConstantStr) {
    constStrMap[name] = value
  }

  fun getConst(name: String): ConstantStr {
    return constStrMap[name]!!
  }

  fun setStruct(name: String, value: StructType) {
    structTypeMap[name] = value
  }

  fun getStruct(name: String): StructType {
    return structTypeMap[name]!!
  }

  fun setFunc(name: String, value: Func) {
    funcMap[name] = value
  }

  fun getFunc(name: String): Func? {
    return funcMap[name]
  }

  fun setBuiltinFunc(name: String, value: Func) {
    builtinFuncMap[name] = value
  }

  fun getBuiltinFunc(name: String): Func? {
    return builtinFuncMap[name]
  }

  fun setGlobalAddr(name: String, value: GlobalVariable) {
    globalVarMap[name] = value
  }

  fun getGlobalAddr(name: String): GlobalVariable {
    return globalVarMap[name]!!
  }
}