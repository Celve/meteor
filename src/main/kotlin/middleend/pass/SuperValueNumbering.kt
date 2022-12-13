package middleend.pass

import middleend.basic.*
import middleend.helper.PatternTable
import middleend.helper.Utils

object SuperValueNumbering : IRVisitor() {
  private val processedSet = hashSetOf<BasicBlock>()
  private val workList = mutableListOf<BasicBlock>()

  fun init() {
    processedSet.clear()
    workList.clear()
  }

  override fun visit(topModule: TopModule) {
    init()
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    workList.add(func.getEntryBlock())
    val emptyTable = PatternTable(null)
    while (workList.isNotEmpty()) {
      val block = workList.first()
      workList.removeFirst()
      superValueNumbering(block, emptyTable)
    }
  }

  private fun localValueNumbering(block: BasicBlock, patternTable: PatternTable) {
    for (inst in block.instList.filterIsInstance<BinaryInst>()) {
      val lhs = inst.getLhs()
      val rhs = inst.getRhs()
      val op = inst.op
      if (lhs is ConstantInt && rhs is ConstantInt) {
        val newInst =
          MvInst(inst.name!!, ConstantInt(inst.type.getNumBits(), Utils.calculate(inst.op, lhs.value, rhs.value)))
        block.replaceInst(inst, newInst)
      } else {
        val result = patternTable.get(lhs, op, rhs)
        if (result != null) { // FIXME: temporarily ignore commute operations
          val newInst = MvInst(inst.name!!, result)
          block.replaceInst(inst, newInst)
        }
        patternTable.add(lhs, op, rhs, inst)
      }
    }
  }

  private fun superValueNumbering(block: BasicBlock, parentTable: PatternTable) {
    if (processedSet.contains(block)) {
      return
    }
    processedSet.add(block)
    val patternTable = PatternTable(parentTable)
    localValueNumbering(block, patternTable)
    block.nextBlockList.forEach {
      if (it.prevBlockList.size == 1) {
        superValueNumbering(it, patternTable)
      } else if (!processedSet.contains(it)) {
        workList.add(it)
      }
    }
  }

  override fun visit(block: BasicBlock) {}

  override fun visit(inst: AllocaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: LoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BinaryInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BranchInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: GetElementPtrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ZExtInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: TruncInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: StoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ReturnInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PCopyInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: MvInst) {
    TODO("Not yet implemented")
  }
}
