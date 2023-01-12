package middleend.pass

import middleend.basic.*
import middleend.helper.NumTable
import middleend.struct.ValNum

object DomValueNumbering : IRVisitor() {
  private lateinit var currFunc: Func
  private lateinit var valNum: ValNum

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun valueNumbering(block: BasicBlock, parentTable: NumTable?) {
    val numTable = NumTable(parentTable)

    for (phiInst in block.instList.filterIsInstance<PhiInst>()) {
      if (phiInst.getPredList().map { it.first }.distinct().size == 1) {
        block.removeInst(phiInst, phiInst.useeList.first())
      } else {
        val result = numTable.get(valNum.get(phiInst))
        if (result != null) { // redundant
          block.removeInst(phiInst, result)
        } else {
          numTable.add(valNum.get(phiInst), phiInst)
        }
      }
    }

    block.instList.filterIsInstance<MvInst>().forEach { block.removeInst(it, it.getSrc()) }

    for (inst in block.instList.filter { it is BinaryInst || it is CmpInst || it is GetElementPtrInst }) {
      val result = numTable.get(valNum.get(inst))
      if (result != null) { // redundant
        block.removeInst(inst, result)
      } else {
        numTable.add(valNum.get(inst), inst)
      }
    }

    for (succ in currFunc.domTree.successors.getValue(block)) {
      valueNumbering(succ, numTable)
    }
  }

  override fun visit(func: Func) {
    currFunc = func
    valNum = func.valNum
    valNum.build()
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