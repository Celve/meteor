package middleend.struct

import middleend.basic.BasicBlock
import middleend.basic.Func
import middleend.basic.Value

class LivenessAnalyzer(val func: Func) {
  val defs = hashMapOf<BasicBlock, HashSet<Value>>()
  val uses = hashMapOf<BasicBlock, HashSet<Value>>()
  val liveIns = hashMapOf<BasicBlock, HashSet<Value>>()
  val liveOuts = hashMapOf<BasicBlock, HashSet<Value>>()

  fun analyze() {
    // collect defs and uses for all blocks
    func.blockList.forEach {
      liveIns[it] = hashSetOf()
      liveOuts[it] = hashSetOf()
      defs[it] = it.instList.toHashSet()
      uses[it] = it.instList.flatMap { it.useeList }.toHashSet()
    }

    // in = use + (out - def)
    // out = union of all in of successors
    // repeat until in and out are fixed
    var changed: Boolean
    do {
      changed = false
      for (block in func.blockList) {
        val originLiveInSet = liveIns[block]!!
        val originLiveOutSet = liveOuts[block]!!
        liveIns[block] = uses[block]!!.union(liveOuts[block]!!.minus(defs[block]!!)).toHashSet()
        liveOuts[block] =
          block.nextBlockSet.fold(setOf<Value>()) { acc, succ -> acc.union(liveIns.getValue(succ)) }.toHashSet()

        // FIXME: This is time-wasting, maybe I should maintain something like finish set?
        if (originLiveInSet != liveIns[block] || originLiveOutSet != liveOuts[block]) {
          changed = true
        }
      }
    } while (changed)
  }
}