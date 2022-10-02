package middleend.helper

import middleend.basic.Value

class ValueSymbolTable {
  private val symbolToVersion: HashMap<Suffixed, Int> = hashMapOf()
  private val symbolToValue: HashMap<Suffixed, Value> = hashMapOf()

  fun referValue(symbol: Suffixed): Value {
    return symbolToValue[symbol]!!
  }

  fun defineValue(value: Value) {
    symbolToValue[value.name!!.symbol] = value
  }

  fun referTwine(symbol: Suffixed): Twine {
    return if (symbolToVersion.containsKey(symbol)) {
      Twine(symbol, symbolToVersion[symbol]!!)
    } else {
      // this situation is almost impossible
      symbolToVersion[symbol] = 0
      Twine(symbol, 0)
    }
  }

  fun defineTwine(symbol: Suffixed): Twine {
    if (symbolToVersion.containsKey(symbol)) {
      symbolToVersion[symbol] = symbolToVersion[symbol]!! + 1
    } else {
      symbolToVersion[symbol] = 0
    }
    return Twine(symbol, symbolToVersion[symbol]!!)
  }
}