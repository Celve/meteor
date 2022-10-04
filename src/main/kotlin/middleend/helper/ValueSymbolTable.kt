package middleend.helper

import middleend.basic.Value

class ValueSymbolTable {
  private val symbolToVersion: HashMap<String, Int> = hashMapOf()
  private val symbolToValue: HashMap<String, Value> = hashMapOf()

  fun referValue(symbol: String): Value {
    return symbolToValue[symbol]!!
  }

  fun defineValue(value: Value) {
    symbolToValue[value.name!!.split('.').last()] = value
  }

  fun referTwine(symbol: String): String {
    return if (symbolToVersion.containsKey(symbol)) {
      "$symbol.${symbolToVersion[symbol]!!}"
    } else {
      // this situation is almost impossible
      symbolToVersion[symbol] = 0
      "$symbol.0"
    }
  }

  fun defineTwine(symbol: String): String {
    return if (symbolToVersion.containsKey(symbol)) {
      symbolToVersion[symbol] = symbolToVersion[symbol]!! + 1
      "$symbol.${symbolToVersion[symbol]!!}"
    } else {
      symbolToVersion[symbol] = 0
      symbol
    }
  }
}