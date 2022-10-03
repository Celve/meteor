package middleend.basic

class TopModule {
  val constData: LinkedHashMap<String, ConstantData> = linkedMapOf()
  val structType: LinkedHashMap<String, StructType> = linkedMapOf()
  val func: LinkedHashMap<String, Func> = linkedMapOf()
  val globalVar: LinkedHashMap<String, GlobalVariable> = linkedMapOf()

  fun setConst(name: String, value: ConstantData) {
    constData[name] = value
  }

  fun getConst(name: String): ConstantData {
    return constData[name]!!
  }

  fun setStruct(name: String, value: StructType) {
    structType[name] = value
  }

  fun getStruct(name: String): StructType {
    return structType[name]!!
  }

  fun setFunc(name: String, value: Func) {
    func[name] = value
  }

  fun getFunc(name: String): Func {
    return func[name]!!
  }

  fun setGlobalVar(name: String, value: GlobalVariable) {
    globalVar[name] = value
  }

  fun getGlobalVar(name: String): GlobalVariable {
    return globalVar[name]!!
  }

}