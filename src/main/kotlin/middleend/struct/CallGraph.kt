package middleend.struct

import middleend.basic.CallInst
import middleend.basic.Func
import middleend.basic.TopModule

class CallGraph(val module: TopModule) {
  val func2CalleeSet = mutableMapOf<Func, HashSet<Func>>().withDefault { hashSetOf() }
  val func2CallerSet = mutableMapOf<Func, HashSet<Func>>().withDefault { hashSetOf() }

  fun build() {
    func2CalleeSet.clear()
    func2CallerSet.clear()
    for (func in module.funcMap.values) {
      for (block in func.blockList) {
        block.instList.filterIsInstance<CallInst>().forEach {
          val callee = it.getCallee()
          func2CalleeSet.getOrPut(func) { hashSetOf() }.add(callee)
          func2CallerSet.getOrPut(callee) { hashSetOf() }.add(func)
        }
      }
    }
  }
}
