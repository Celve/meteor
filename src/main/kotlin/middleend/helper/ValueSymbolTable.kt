package middleend.helper

import middleend.basic.TopModule
import middleend.basic.Value

// deprecated
class ValueSymbolTable {
  private val nameToVersion: HashMap<String, Int> = hashMapOf()
  private val nameToValue: HashMap<String, Value> = hashMapOf()

  fun addAll(topModule: TopModule) {
    for ((funcName, _) in topModule.funcList) {
      defineName(funcName)
    }
  }

  fun getValue(symbol: String): Value {
    return nameToValue[symbol]!!
  }

  // when the value's name is not set up, which means that it might be duplicate, use this function
  fun insertValue(value: Value) {
    value.name = defineName(value.name!!)
    nameToValue[value.name!!] = value
  }

  // when the value's name is set up, use this function
  // make sure that its name hasn't been defined by value table
  fun reinsertValue(value: Value) {
    nameToValue[value.name!!] = value
  }

  // this function helps to set up the value name
  fun defineValue(value: Value, name: String) {
    val newName = defineName(name)
    nameToValue[newName] = value
    value.name = newName
  }

  fun defineName(name: String): String {
    return if (nameToVersion.containsKey(name)) {
      nameToVersion[name] = nameToVersion[name]!! + 1
      "$name.${nameToVersion[name]!!}"
    } else {
      nameToVersion[name] = 0
      name
    }
  }
}
