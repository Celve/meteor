package middleend.struct

import middleend.basic.*

class EqSet(val func: Func) {
  private val valNum = func.valNum
  private val eqSets = mutableListOf<HashSet<String>>()
  private val num2Set = hashMapOf<String, HashSet<String>>()
  private val graph = DirectedGraph<String>()
  private val totalAliasTable = hashMapOf<Pair<String, String>, HashSet<String>>()
  private val totalPtrTable = hashMapOf<String, HashSet<String>>()
  private var changed = false

  private fun addAlias(ptr: String, operand: String, value: String) {
    totalAliasTable.getOrPut(ptr to operand) { hashSetOf() }.add(value)
    totalPtrTable.getOrPut(ptr) { hashSetOf() }.add(value)
  }

  private fun getAlias(ptr: String, operand: String): Set<String> {
    return totalAliasTable[ptr to operand] ?: setOf()
  }

  private fun getPtr(ptr: String): Set<String> {
    return totalPtrTable[ptr] ?: setOf()
  }

  fun get(value: Value): Set<String> {
    return when (value) {
      is BasicBlock -> setOf("b${value.name}")
      is GlobalVariable -> setOf("g${value.name}")
      is ConstantStr -> setOf("c${value.name}")
      is ConstantInt -> setOf("i${value.value}")
      is ConstantNull -> setOf("null")
      else -> num2Set[valNum.get(value)] ?: setOf(valNum.get(value))
    }
  }

  private fun fixInvariants() {
    with(func) {
      func.argList.union(func.blockList.flatMap { it.instList.filter { it.isDef() } }).forEach {
        val num = valNum.get(it)
        val set = hashSetOf(valNum.get(it))
        eqSets.add(set)
        num2Set[num] = set
      }

      blockList.flatMap { it.instList.filterIsInstance<PhiInst>() }.forEach { inst ->
        inst.getPredList().map { valNum.get(it.first) }.forEach { graph.addEdge(it, valNum.get(inst)) }
      }

      blockList.flatMap { it.instList.filterIsInstance<MvInst>() }.forEach {
        graph.addBidEdge(valNum.get(it.getSrc()), valNum.get(it))
      }

      graph.tarjan()
      graph.rebuild()
      graph.topSort()

      val id2EqSet = hashMapOf<Int, HashSet<String>>()

      for (sccId in graph.topOrder) {
        val scc = graph.sccSet.getValue(sccId)
        val eqSet = id2EqSet.getOrDefault(sccId, hashSetOf()).union(scc).toHashSet()
        eqSet.forEach { num2Set[it] = eqSet }
        eqSets.add(eqSet)
        for (nextSccId in graph.sccEdges.getValue(sccId)) {
          id2EqSet.getOrPut(nextSccId) { hashSetOf() }.addAll(eqSet)
        }
      }
    }
  }

  private fun isInTheSameEqSet(lhs: Value, rhs: Value): Boolean {
    return num2Set[valNum.get(lhs)] == num2Set[valNum.get(rhs)] && num2Set[valNum.get(lhs)] != null
  }

  private fun isGepEqual(inst1: GetElementPtrInst, inst2: GetElementPtrInst): Boolean {
    return isInTheSameEqSet(inst1.getPtr(), inst2.getPtr()) && !isInTheSameEqSet(inst1, inst2) && when (inst1.op) {
      "struct" -> inst1.getIndex()!! == inst2.getIndex()!!
      "ptr" -> inst1.getOffset() !is Constant || inst2.getOffset() !is Constant || inst1.getOffset() == inst2.getOffset()
      else -> throw Exception("Unknown gep op: ${inst1.op}")
    }
  }

  private fun merge(value1: Value, value2: Value) {
    val num1 = valNum.get(value1)
    val num2 = valNum.get(value2)
    when {
      num2Set[num1] == null && num2Set[num2] == null -> {
        val eqSet = hashSetOf(num1, num2)
        num2Set[num1] = eqSet
        num2Set[num2] = eqSet
        eqSets.add(eqSet)
      }

      num2Set[num1] == null && num2Set[num2] != null -> {
        num2Set[num1] = num2Set[num2]!!
        num2Set[num2]!!.add(num1)
      }

      num2Set[num1] != null && num2Set[num2] == null -> {
        num2Set[num2] = num2Set[num1]!!
        num2Set[num1]!!.add(num2)
      }

      num2Set[num1] != null && num2Set[num2] != null -> {
        val removed = num2Set[num2]!!
        num2Set[num1]!!.addAll(num2Set[num2]!!)
        num2Set[num2]!!.forEach { num2Set[it] = num2Set[num1]!! }
        eqSets.remove(removed)
      }
    }
  }

  private fun fixGep() {
    with(func) {
      val instList = blockList.flatMap { it.instList.filterIsInstance<GetElementPtrInst>() }
      for (i in instList.indices) {
        for (j in 0 until i) {
          if (isGepEqual(instList[i], instList[j])) {
            merge(instList[i], instList[j])
            changed = true
          }
        }
      }
    }
  }

  private fun fixLoad() {
    with(func) {
      val instList = blockList.flatMap { it.instList.filterIsInstance<LoadInst>() }
      for (i in instList.indices) {
        for (j in 0 until i) {
          val inst1 = instList[i]
          val inst2 = instList[j]
          if (!isInTheSameEqSet(inst1, inst2) && isInTheSameEqSet(inst1.getAddr(), inst2.getAddr())) {
            merge(inst1, inst2)
            changed = true
          }
        }
      }
    }
  }

  private fun fixStore() {
    with(func) {
      val instList = blockList.flatMap { it.instList.filterIsInstance<StoreInst>() }
      instList.forEach { inst ->
        inst.getAddr().userList.filterIsInstance<LoadInst>().filter { it.parent.parent == func }.forEach {
          if (!isInTheSameEqSet(inst.getValue(), it)) {
            merge(inst.getValue(), it)
            changed = true
          }
        }
      }
    }
  }

  fun build() {
    valNum.build()
    fixInvariants()
    do {
      changed = false
      fixGep()
      fixLoad()
      fixStore()
    } while (changed)
//    println(eqSets.filter { it.size > 1 })
  }
}