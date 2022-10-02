package middleend.basic

class Module {
  private val constData: LinkedHashMap<String, ConstantData> = linkedMapOf()
  private val structType: LinkedHashMap<String, StructType> = linkedMapOf()
  private val func: LinkedHashMap<String, Func> = linkedMapOf()
  private val globalVar: LinkedHashMap<String, GlobalVariable> = linkedMapOf()

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

  fun geGlobalVar(name: String): GlobalVariable {
    return globalVar[name]!!
  }

}