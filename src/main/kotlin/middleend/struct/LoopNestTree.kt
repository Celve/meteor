package middleend.struct

import middleend.basic.BasicBlock
import middleend.basic.Func

class Loop(val headerBlock: BasicBlock, unorderedBody: Set<BasicBlock>) {
  lateinit var preHeaderBlock: BasicBlock // preHeaderBlock is not inside the loop
  var allBlocks = unorderedBody.sortedBy { headerBlock.parent.blockList.indexOf(it) }
  var exitingBlocks = allBlocks.filter { it.nextBlockSet.any { next -> !allBlocks.contains(next) } }.toSet() // exisitn
  val succLoops = hashSetOf<Loop>()
  val predLoops = hashSetOf<Loop>()

  fun union(other: Set<BasicBlock>) {
    allBlocks = (allBlocks + other).sortedBy { headerBlock.parent.blockList.indexOf(it) }
    exitingBlocks = allBlocks.filter { it.nextBlockSet.any { next -> !allBlocks.contains(next) } }.toSet()
  }
}

class LoopNestTree(val func: Func) {
  private val backEdges = hashSetOf<Pair<BasicBlock, BasicBlock>>()
  private val naturalLoops = hashSetOf<List<BasicBlock>>()
  private val header2Loop = hashMapOf<BasicBlock, Loop>()
  val roots = hashSetOf<Loop>()

  private fun collectBackEdges() {
    backEdges.clear()
    val doms = func.domTree.doms
    for (block in func.blockList) {
      for (nextBlock in block.nextBlockSet) {
        if (doms[block]!!.contains(nextBlock)) {
          backEdges.add(Pair(block, nextBlock))
        }
      }
    }
  }

  private fun overflow(start: BasicBlock, limit: BasicBlock): List<BasicBlock> {
    val queue = mutableListOf(start)
    val visited = hashSetOf(start)
    while (queue.isNotEmpty()) {
      val block = queue.first()
      queue.removeFirst()

      val pending = block.prevBlockSet.filter { !visited.contains(it) && it != limit }
      pending.forEach { visited.add(it) }
      queue.addAll(pending)
    }
    return visited.filter { func.domTree.doms[it]!!.contains(limit) }
  }

  private fun collectNaturalLoops() {
    naturalLoops.clear()
    for (backEdge in backEdges) {
      val (from, to) = backEdge
      naturalLoops.add(listOf(to) + overflow(from, to))
    }
  }

  private fun combineLoops() {
    for (naturalLoop in naturalLoops) {
      val header = naturalLoop.first()
      val body = naturalLoop.toHashSet()
      if (header2Loop.contains(header)) {
        header2Loop[header]!!.union(body)
      } else {
        header2Loop[header] = Loop(header, body)
      }
    }
  }

  private fun constructTree() {
    val loopsBySize = header2Loop.values.sortedBy { it.allBlocks.size }
    for (succ in loopsBySize) {
      val pred = loopsBySize.firstOrNull { it != succ && it.allBlocks.contains(succ.headerBlock) }
      if (pred != null) {
        pred.succLoops.add(succ)
        succ.predLoops.add(pred)
      }
    }
    loopsBySize.filter { it.predLoops.isEmpty() }.forEach { roots.add(it) }
  }

  fun build() {
    func.domTree.build()
    collectBackEdges()
    collectNaturalLoops()
    combineLoops()
    constructTree()
  }
}