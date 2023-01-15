package middleend.struct

class DirectedGraph<T> {
  private val edges = mutableMapOf<T, HashSet<T>>().withDefault { hashSetOf() }
  private val dfn = mutableMapOf<T, Int>().withDefault { 0 }
  private val low = mutableMapOf<T, Int>().withDefault { 0 }
  val sccId = mutableMapOf<T, Int>().withDefault { 0 } // 0 means that it's not in any scc
  val sccSet = hashMapOf<Int, HashSet<T>>()
  private val stack = mutableListOf<T>()
  private val inStack = mutableMapOf<T, Boolean>().withDefault { false }
  private var dfnCnt = 0
  private var sccCnt = 0
  val sccEdges = mutableMapOf<Int, HashSet<Int>>().withDefault { hashSetOf() }
  val topOrder = mutableListOf<Int>()

  fun addEdge(from: T, to: T) {
    edges.getOrPut(from) { hashSetOf() }.add(to)
  }

  fun addBidEdge(from: T, to: T) {
    addEdge(from, to)
    addEdge(to, from)
  }

  fun tarjan(u: T) {
    dfn[u] = ++dfnCnt
    low[u] = dfnCnt
    stack.add(u)
    inStack[u] = true
    for (v in edges.getValue(u)) {
      if (dfn.getValue(v) == 0) {
        tarjan(v)
        low[u] = minOf(low[u]!!, low[v]!!)
      } else if (inStack.getValue(v)) {
        low[u] = minOf(low[u]!!, dfn[v]!!)
      }
    }
    if (low[u] == dfn[u]) {
      var topNode: T
      sccSet[++sccCnt] = hashSetOf()
      do {
        topNode = stack.removeLast()
        inStack[topNode] = false
        sccId[topNode] = sccCnt
        sccSet[sccCnt]!!.add(topNode)
      } while (topNode != u)
    }
  }

  fun tarjan() {
    dfn.clear()
    low.clear()
    sccId.clear()
    sccSet.clear()
    stack.clear()
    inStack.clear()
    dfnCnt = 0
    sccCnt = 0

    for (u in edges.keys) {
      if (dfn.getValue(u) == 0) {
        tarjan(u)
      }
    }
  }

  fun rebuild() {
    sccEdges.clear()

    for (u in edges.keys) {
      for (v in edges.getValue(u)) {
        if (sccId.getValue(u) != sccId.getValue(v)) {
          sccEdges.getOrPut(sccId.getValue(u)) { hashSetOf() }.add(sccId.getValue(v))
        }
      }
    }
  }

  fun topSort() {
    val degrees = mutableMapOf<Int, Int>()
    (1..sccCnt).forEach { degrees[it] = 0 }
    sccEdges.forEach { u -> u.value.forEach { degrees[it] = degrees.getValue(it) + 1 } }
    val queue = (1..sccCnt).filter { degrees[it] == 0 }.toMutableList()
    while (queue.isNotEmpty()) {
      val u = queue.removeFirst()
      topOrder.add(u)
      sccEdges.getValue(u).forEach {
        degrees[it] = degrees.getValue(it) - 1
        if (degrees.getValue(it) == 0) {
          queue.add(it)
        }
      }
    }
  }
}