package middleend.helper

import middleend.basic.BasicBlock
import middleend.basic.Func

class DomTree(val func: Func?) {
  private var doms = hashMapOf<BasicBlock, BasicBlock>()
  var domFrontiers = hashMapOf<BasicBlock, MutableList<BasicBlock>>().withDefault { mutableListOf() }
  var successors = hashMapOf<BasicBlock, MutableList<BasicBlock>>().withDefault { mutableListOf() }

  // used to calculate the postorder of the spanning DFS tree
  private var blockListInPostorder = mutableListOf<BasicBlock>()
  private var block2Postorder = hashMapOf<BasicBlock, Int>()
  private var visitedSet = hashSetOf<BasicBlock>()
  private var startNode = BasicBlock("init", 0)

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
    for (nextBlock in block.nextBlockList) {
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
    startNode = func!!.getEntryBlock()
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
        for (prevBlock in block.prevBlockList) {
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

    for (block in blockListInPostorder) {
      if (block.prevBlockList.size >= 2) {
        for (prevBlock in block.prevBlockList) {
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