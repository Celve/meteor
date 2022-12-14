package middleend.struct

import middleend.basic.BasicBlock
import middleend.basic.Func

class DomTree(val func: Func, val reversed: Boolean) {
  var doms = hashMapOf<BasicBlock, BasicBlock>()
  var domFrontiers = hashMapOf<BasicBlock, MutableList<BasicBlock>>().withDefault { mutableListOf() }
  var successors = hashMapOf<BasicBlock, MutableList<BasicBlock>>().withDefault { mutableListOf() }

  // used to calculate the postorder of the spanning DFS tree
  var blockListInPostorder = mutableListOf<BasicBlock>()
  private var block2Postorder = hashMapOf<BasicBlock, Int>()
  private var visitedSet = hashSetOf<BasicBlock>()
  private var startNode = BasicBlock("init", 0)

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
    blockListInPostorder = mutableListOf()
    block2Postorder = hashMapOf()
    visitedSet = hashSetOf()
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
        lhsFinger = doms[lhsFinger]!!
      }

      while (block2Postorder[lhsFinger]!! > block2Postorder[rhsFinger]!!) {
        rhsFinger = doms[rhsFinger]!!
      }
    }
    return lhsFinger
  }

  fun build() {
    doms.clear() // initialize
    startNode = if (reversed) {
      func.blockList.last()
    } else {
      func.getEntryBlock()
    }
    doms[startNode] = startNode

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
          if (doms[prevBlock] != null) {
            newIdom = if (newIdom == null) {
              prevBlock
            } else {
              intersect(newIdom, prevBlock)
            }
          }
        }

        if (newIdom != null && newIdom != doms[block]) {
          doms[block] = newIdom
          changed = true
        }
      }
    }

    // construct tree
    for (block in blockListInPostorder) {
      if (block != doms[block]!!) {
        successors.getOrPut(doms[block]!!) { mutableListOf() }.add(block)
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

    for (block in blockListInPostorder) {
      if (getPrevBlockSet(block).size >= 2) {
        for (prevBlock in getPrevBlockSet(block)) {
          var runner = prevBlock
          while (runner != doms[block]) {
            domFrontiers.getOrPut(runner) { mutableListOf() }.add(block)
            runner = doms[runner]!!
          }
        }
      }
    }
  }
}
