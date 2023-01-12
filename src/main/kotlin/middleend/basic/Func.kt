package middleend.basic

import middleend.helper.SSATable
import middleend.helper.ValueTable
import middleend.pass.IRVisitor
import middleend.struct.DomTree
import middleend.struct.LivenessAnalyzer
import middleend.struct.LoopNestTree
import middleend.struct.ValNum

class Func(name: String, val funcType: FuncType, val argList: List<Value>) : GlobalValue(name, funcType) {
  // ensure that entry block is always the first block and the return block is always the last block
  var blockList: MutableList<BasicBlock> = mutableListOf()
  val mulTable = ValueTable()
  val ssaTable = SSATable()
  val domTree = DomTree(this, false)
  val revDomTree = DomTree(this, true)
  val loopNestTree = LoopNestTree(this)
  val livenessAnalyzer = LivenessAnalyzer(this)
  val valNum = ValNum(this)

  override fun replicate(): Value {
    TODO("Not yet implemented")
  }

  fun getEntryBlock(): BasicBlock {
    return blockList.first()
  }

  fun getReturnBlock(): BasicBlock {
    assert(blockList.last().instList.any { it is ReturnInst })
    return blockList.last()
  }

  fun addBasicBlockAtIndex(index: Int, basicBlock: BasicBlock) {
    blockList.add(index, basicBlock)
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}
