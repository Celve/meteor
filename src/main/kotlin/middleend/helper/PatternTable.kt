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

class HashTable(val parent: HashTable?) {
  private val table = hashMapOf<Pair<String, List<Value>>, Value>()

  fun add(op: String, operands: List<Value>, result: Value) {
    table[op to operands] = result
  }

  fun get(op: String, operands: List<Value>): Value? {
    return table[op to operands] ?: parent?.get(op, operands)
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

class MemTable(var parent: MemTable?, val invalid: HashSet<String>) {
  private val table = hashMapOf<String, Value>()

  fun add(addr: String, value: Value) {
//    println("add $addr with $value")
    invalid.remove(addr)
    table[addr] = value
  }

  fun get(addr: String): Value? {
    return if (invalid.contains(addr)) {
      null
    } else {
      table[addr] ?: parent?.get(addr)
    }
  }

  fun remove(addr: String) {
    invalid.add(addr)
    table.remove(addr)
  }

  fun removeAll() {
    table.clear()
    invalid.clear()
    parent = null
  }
}