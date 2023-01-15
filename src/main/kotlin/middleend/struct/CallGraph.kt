package middleend.struct

import middleend.basic.CallInst
import middleend.basic.Func
import middleend.basic.TopModule

class CallGraph(val module: TopModule) {
  val func2CalleeSet = mutableMapOf<Func, HashSet<Func>>().withDefault { hashSetOf() }
  val func2CallerSet = mutableMapOf<Func, HashSet<Func>>().withDefault { hashSetOf() }

  val scc2CalleeSet = mutableMapOf<Int, HashSet<Int>>().withDefault { hashSetOf() }
  val scc2CallerSet = mutableMapOf<Int, HashSet<Int>>().withDefault { hashSetOf() }

  val dfn = mutableMapOf<Func, Int>().withDefault { 0 }
  val low = mutableMapOf<Func, Int>().withDefault { 0 }
  val sccId = mutableMapOf<Func, Int>().withDefault { 0 } // 0 means that it's not in any scc
  val sccSet = hashMapOf<Int, HashSet<Func>>()
  val stack = mutableListOf<Func>()
  val inStack = mutableMapOf<Func, Boolean>().withDefault { false }
  var dfnCnt = 0
  var sccCnt = 0

  private fun tarjan(func: Func) {
    dfn[func] = ++dfnCnt
    low[func] = dfnCnt
    stack.add(func)
    inStack[func] = true
    for (callee in func2CalleeSet.getValue(func)) {
      if (dfn.getValue(callee) == 0) {
        tarjan(callee)
        low[func] = minOf(low[func]!!, low[callee]!!)
      } else if (inStack.getValue(callee)) {
        low[func] = minOf(low[func]!!, dfn[callee]!!)
      }
    }
    if (low[func] == dfn[func]) {
      var topFunc: Func
      sccSet[++sccCnt] = hashSetOf()
      do {
        topFunc = stack.removeLast()
        inStack[topFunc] = false
        sccId[topFunc] = sccCnt
        sccSet[sccCnt]!!.add(topFunc)
      } while (topFunc != func)
    }
  }

  private fun init() {
    func2CalleeSet.clear()
    func2CallerSet.clear()

    dfnCnt = 0
    sccCnt = 0
    dfn.clear()
    low.clear()
    stack.clear()
    inStack.clear()
    sccId.clear()
    sccSet.clear()
  }

  fun build() {
    init()
    for (func in module.funcMap.values) {
      for (block in func.blockList) {
        block.instList.filterIsInstance<CallInst>().forEach {
          val callee = it.getCallee()
          func2CalleeSet.getOrPut(func) { hashSetOf() }.add(callee)
          func2CallerSet.getOrPut(callee) { hashSetOf() }.add(func)
        }
      }
    }

    tarjan(module.getFunc("main")!!)

    for (func in module.funcMap.values.filter { sccId[it] != null }) {
      for (callee in func2CalleeSet.getValue(func).filter { sccId[it] != null }) {
        scc2CalleeSet.getOrPut(sccId.getValue(func)) { hashSetOf() }.add(sccId.getValue(callee))
        scc2CallerSet.getOrPut(sccId.getValue(callee)) { hashSetOf() }.add(sccId.getValue(func))
      }
    }
  }
}
