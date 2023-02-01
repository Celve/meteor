package middleend.struct

import middleend.basic.*

class ValNum(val func: Func) {
  private val table = hashMapOf<Value, String>()
  private var cnt = 0
  private val totalPatternTable = hashMapOf<Pair<String, List<String>>, Value>()

  fun toValSetTable(): HashMap<Value, Set<String>> {
    return table.map { it.key to setOf(it.value) }.toMap() as HashMap
  }

  fun get(value: Value): String {
    return when (value) {
      is BasicBlock -> "b${value.name}"
      is GlobalVariable -> "g${value.name}"
      is ConstantStr -> "c${value.name}"
      is ConstantInt -> "i${value.value}"
      is ConstantNull -> "null"
      else -> table[value]!!
    }
  }

  private fun alias(new: Value, old: Value) {
    table[new] = get(old)
  }

  private fun new(value: Value) {
    table[value] = "v${cnt++}"
  }

  private fun isTargetInst(inst: Instruction): Boolean {
    return inst is BinaryInst || inst is CmpInst || inst is GetElementPtrInst || inst is PhiInst || inst is MvInst
  }

  private fun getToken(inst: Instruction): String {
    return when (inst) {
      is BinaryInst -> inst.op
      is CmpInst -> inst.cond
      is GetElementPtrInst -> "gep"
      is PhiInst -> "phi"
      else -> throw Exception("$inst is not allowed")
    }
  }

  private fun equal(value: Value, integer: Int): Boolean {
    return value is ConstantInt && value.value == integer
  }

  private fun isInvariant(inst: BinaryInst): Boolean {
    return when (inst.op) {
      "add" -> equal(inst.getLhs(), 0) || equal(inst.getRhs(), 0)
      "sub" -> equal(inst.getRhs(), 0)
      "mul" -> equal(inst.getLhs(), 1) || equal(inst.getRhs(), 1)
      "sdiv" -> equal(inst.getRhs(), 1)
      else -> false
    }
  }

  fun build() {
    table.clear()
    cnt = 0
    totalPatternTable.clear()
    val workList = func.blockList.toMutableList() // duplicate it
    func.argList.forEach { new(it) }
    func.blockList.flatMap { it.instList.filter { it.isDef() } }.forEach { new(it) }
    while (workList.isNotEmpty()) {
      val block = workList.removeFirst()
      val affected = hashSetOf<BasicBlock>()
      block.instList.filter { it.isDef() && isTargetInst(it) }.forEach { inst ->
        if (inst is BinaryInst && isInvariant(inst)) {
          val invariant = if (inst.getLhs() !is ConstantInt) inst.getLhs() else inst.getRhs()
          if (get(inst) != get(invariant)) {
            alias(inst, invariant)
            affected.addAll(inst.userList.filterIsInstance<Instruction>().map { it.parent })
          }
        } else if (inst is MvInst) {
          if (get(inst) != get(inst.getSrc())) {
            alias(inst, inst.getSrc())
            affected.addAll(inst.userList.filterIsInstance<Instruction>().map { it.parent })
          }
        } else {
          val result = totalPatternTable[Pair(getToken(inst), inst.useeList.map { get(it) })]
          if (result != null && get(result) != get(inst)) {
            alias(inst, result)
            affected.addAll(inst.userList.filterIsInstance<Instruction>().map { it.parent })
          } else if (result == null) {
            totalPatternTable[Pair(getToken(inst), inst.useeList.map { get(it) })] = inst
          }
        }
      }
      workList.addAll(affected)
    }
//    table.forEach { (key, value) -> println("$key -> $value") }
  }
}