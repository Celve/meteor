package middleend.struct

import middleend.basic.BasicBlock
import middleend.basic.Func

class DomTree(val func: Func, val reversed: Boolean) {
  val domeds = hashMapOf<BasicBlock, HashSet<BasicBlock>>().withDefault { hashSetOf() }
  val doms = hashMapOf<BasicBlock, HashSet<BasicBlock>>()
  val idoms = hashMapOf<BasicBlock, BasicBlock>()
  val domFrontiers = hashMapOf<BasicBlock, MutableList<BasicBlock>>().withDefault { mutableListOf() }
  val successors = hashMapOf<BasicBlock, MutableList<BasicBlock>>().withDefault { mutableListOf() }

  // used to calculate the postorder of the spanning DFS tree
  val blockListInPostorder = mutableListOf<BasicBlock>()
  val block2Postorder = hashMapOf<BasicBlock, Int>()
  private val visitedSet = hashSetOf<BasicBlock>()
  private lateinit var startNode: BasicBlock

  private fun getNextBlockSet(block: BasicBlock): Set<BasicBlock> {
    return if (reversed) {
      block.prevBlockSet
    } else {
      block.nextBlockSet
    }
  }

  private fun getPrevBlockSet(block: BasicBlock): Set<BasicBlock> {
    return if (reversed) {
      block.nextBlockSet
    } else {
      block.prevBlockSet
    }
  }

  private fun calcPostorder() {
    blockListInPostorder.clear()
    block2Postorder.clear()
    visitedSet.clear()
    calcPostorderOf(startNode)
    for ((index, block) in blockListInPostorder.withIndex()) {
      block2Postorder[block] = index
    }
  }

  private fun calcPostorderOf(block: BasicBlock) {
    visitedSet.add(block)
    for (nextBlock in getNextBlockSet(block)) {
      if (!visitedSet.contains(nextBlock)) {
        calcPostorderOf(nextBlock)
      }
    }
    blockListInPostorder.add(block)
  }

  private fun intersect(lhsBlock: BasicBlock, rhsBlock: BasicBlock): BasicBlock {
    var lhsFinger = lhsBlock
    var rhsFinger = rhsBlock
    while (lhsFinger != rhsFinger) {
      while (block2Postorder[lhsFinger]!! < block2Postorder[rhsFinger]!!) {
        lhsFinger = idoms[lhsFinger]!!
      }

      while (block2Postorder[lhsFinger]!! > block2Postorder[rhsFinger]!!) {
        rhsFinger = idoms[rhsFinger]!!
      }
    }
    return lhsFinger
  }

  fun build() {
    idoms.clear() // initialize
    successors.clear()
    doms.clear()
    domeds.clear()
    domFrontiers.clear()

    startNode = if (reversed) {
      func.blockList.last()
    } else {
      func.getEntryBlock()
    }
    idoms[startNode] = startNode

    calcPostorder()

    var changed = true
    while (changed) {
      changed = false
      for (block in blockListInPostorder.asReversed()) {
        if (block == startNode) { // except the startNode
          continue
        }

        var newIdom: BasicBlock? = null
        for (prevBlock in getPrevBlockSet(block)) {
          if (idoms[prevBlock] != null) {
            newIdom = if (newIdom == null) {
              prevBlock
            } else {
              intersect(newIdom, prevBlock)
            }
          }
        }

        if (newIdom != null && newIdom != idoms[block]) {
          idoms[block] = newIdom
          changed = true
        }
      }
    }

    // construct tree
    for (block in blockListInPostorder) {
      if (block != idoms[block]!!) {
        successors.getOrPut(idoms[block]!!) { mutableListOf() }.add(block)
      }
    }

    // remove unused blocks
    val removingBlocks = func.blockList.subtract(visitedSet)
    func.blockList.removeAll(removingBlocks)
    for (block in removingBlocks) {
      for (prevBlock in getPrevBlockSet(block)) {
        prevBlock.removeNextBlock(block)
        block.removePrevBlock(block)
      }
      for (nextBlock in getNextBlockSet(block)) {
        nextBlock.removePrevBlock(block)
        block.removeNextBlock(block)
      }
    }

    // calculate dom frontiers
    for (block in blockListInPostorder) {
      if (getPrevBlockSet(block).size >= 2) {
        for (prevBlock in getPrevBlockSet(block)) {
          var runner = prevBlock
          while (runner != idoms[block]) {
            domFrontiers.getOrPut(runner) { mutableListOf() }.add(block)
            runner = idoms[runner]!!
          }
        }
      }
    }

    // calculate doms
    for (block in blockListInPostorder) {
      doms[block] = hashSetOf(startNode)
      var runner = block
      while (runner != startNode) {
        doms[block]!!.add(runner)
        runner = idoms[runner]!!
      }
    }

    for (block in blockListInPostorder) {
      doms[block]!!.forEach { domeds.getOrPut(it) { hashSetOf() }.add(block) }
    }
  }
}
