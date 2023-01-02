package middleend.pass

import middleend.basic.*
import middleend.helper.HashPatternTable

object DomValueNumbering : IRVisitor() {
  private lateinit var currFunc: Func

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun valueNumbering(block: BasicBlock, parentTable: HashPatternTable?) {
    val hashTable = HashPatternTable(parentTable)

    for (phiInst in block.instList.filterIsInstance<PhiInst>()) {
      val operands = phiInst.useeList
      if (phiInst.useeList.distinct().size == 1) {
        block.removeInst(phiInst, operands.first())
      }

      val result = hashTable.get("phi", operands)
      if (result != null) { // redundant
        block.removeInst(phiInst, result)
      } else {
        hashTable.add("phi", operands, phiInst)
      }
    }

    for (inst in block.instList.filterIsInstance<MvInst>()) {
      if (inst.getSrc().isDef()) {
        block.removeInst(inst, inst.getSrc())
      }
    }

    for (inst in block.instList.filter { it is BinaryInst || it is CmpInst || it is GetElementPtrInst }) {
      val operands = inst.useeList
      val op = when (inst) {
        is BinaryInst -> inst.op
        is CmpInst -> inst.cond
        is GetElementPtrInst -> "getelementptr"
        is MvInst -> "mv"
        else -> throw Exception("unreachable")
      }
      val result = hashTable.get(op, operands)
      if (result != null) { // redundant
        block.removeInst(inst, result)
      } else {
        hashTable.add(op, operands, inst)
      }
    }

    for (succ in currFunc.domTree.successors.getValue(block)) {
      valueNumbering(succ, hashTable)
    }
  }

  override fun visit(func: Func) {
    currFunc = func
    func.domTree.build()
    valueNumbering(func.getEntryBlock(), null)
  }

  override fun visit(block: BasicBlock) {
    TODO("Not yet implemented")
  }

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