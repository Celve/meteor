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

class HashPatternTable(val parent: HashPatternTable?) {
  private val table = hashMapOf<Pair<String, List<String>>, Value>()

  fun add(operator: String, operands: List<String>, result: Value) {
    table[Pair(operator, operands)] = result
  }

  fun get(operator: String, operands: List<String>): Value? {
    return table[Pair(operator, operands)] ?: parent?.get(operator, operands)
  }
}