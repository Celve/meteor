package backend.pass

import backend.basic.*
import java.util.*

// This optimization might lead to irreversible effects.
// It might be hard to determine which basic block is the return block.
object GlobalCodePlacement : ASMVisitor() {
  override fun visit(module: ASMModule) {
    module.funcList.forEach { it.accept(this) }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {}

  override fun visit(func: ASMFunc) {
    val blockList = mutableListOf<ASMBlock>()
    val workList = PriorityQueue<ASMBlock> { a, b -> b.execFreq - a.execFreq }
    val visited = hashSetOf<ASMBlock>()
    workList.add(func.blockList.first())
    while (workList.isNotEmpty()) {
      var curr = workList.poll()
      if (visited.contains(curr)) {
        continue
      }
      do {
        blockList.add(curr)
        visited.add(curr)
        val next = curr.succList.filter { !visited.contains(it) }.maxWithOrNull { a, b -> a.execFreq - b.execFreq }
        curr.succList.filter { it != next && !visited.contains(it) }.forEach { workList.add(it) }
        curr = next
      } while (curr != null)
    }

    for (index in 1 until blockList.size) {
      val last = blockList[index - 1]
      val curr = blockList[index]
      if (last.instList.last() is ASMJInst && (last.instList.last() as ASMJInst).getLabel() == curr) {
        last.instList.removeLast()
      }
    }

    func.blockList = blockList
  }

  override fun visit(block: ASMBlock) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMStoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMBrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMBzInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMJInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMRetInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMArithInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMArithiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMMvInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpzInst) {
    TODO("Not yet implemented")
  }
}