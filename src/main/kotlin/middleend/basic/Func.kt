package middleend.basic

import middleend.helper.SSATable
import middleend.helper.ValueTable
import middleend.pass.IRVisitor
import middleend.struct.*

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
  val eqSet = EqSet(this)
  val indVar = IndVar(this)

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

  fun getIndexOfBlock(block: BasicBlock): Int {
    return blockList.indexOf(block)
  }

  fun addBasicBlockAtIndex(index: Int, basicBlock: BasicBlock) {
    basicBlock.parent = this
    blockList.add(index, basicBlock)
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}
