package middleend.basic

class TopModule {
  val constStr: LinkedHashMap<String, ConstantStr> = linkedMapOf()
  val structType: LinkedHashMap<String, StructType> = linkedMapOf()
  val funcList: LinkedHashMap<String, Func> = linkedMapOf()
  val builtinFunc: LinkedHashMap<String, Func> = linkedMapOf()
  val name2Addr: LinkedHashMap<String, GlobalVariable> = linkedMapOf()

  fun setConst(name: String, value: ConstantStr) {
    constStr[name] = value
  }

  fun getConst(name: String): ConstantStr {
    return constStr[name]!!
  }

  fun setStruct(name: String, value: StructType) {
    structType[name] = value
  }

  fun getStruct(name: String): StructType {
    return structType[name]!!
  }

  fun setFunc(name: String, value: Func) {
    funcList[name] = value
  }

  fun getFunc(name: String): Func? {
    return funcList[name]
  }

  fun setBuiltinFunc(name: String, value: Func) {
    builtinFunc[name] = value
  }

  fun getBuiltinFunc(name: String): Func? {
    return builtinFunc[name]
  }

  fun setGlobalAddr(name: String, value: GlobalVariable) {
    name2Addr[name] = value
  }

  fun getGlobalAddr(name: String): GlobalVariable {
    return name2Addr[name]!!
  }
}