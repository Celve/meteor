package middleend.helper

import middleend.basic.Value

class PatternTable(val parent: PatternTable?) {
  private val table = hashMapOf<Triple<Value, String, Value>, Value>()

  fun add(lhs: Value, op: String, rhs: Value, result: Value) {
    table[Triple(lhs, op, rhs)] = result
  }

  fun get(lhs: Value, op: String, rhs: Value): Value? {
    return table[Triple(lhs, op, rhs)] ?: parent?.get(lhs, op, rhs)
  }
}

class NumTable(val parent: NumTable?) {
  private val table = hashMapOf<String, Value>()

  fun add(num: String, result: Value) {
    table[num] = result
  }

  fun get(num: String): Value? {
    return table[num] ?: parent?.get(num)
  }
}