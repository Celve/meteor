package middleend.pass

import middleend.basic.*
import middleend.helper.HashPatternTable
import middleend.struct.ValNum

object DomValueNumbering : IRVisitor() {
  private lateinit var currFunc: Func
  private lateinit var valNum: ValNum
  private var changed = false

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun valueNumbering(block: BasicBlock, parentTable: HashPatternTable?) {
    val hashTable = HashPatternTable(parentTable)

    for (phiInst in block.instList.filterIsInstance<PhiInst>()) {
      val operands = phiInst.useeList
      if (phiInst.getPredList().map { it.first }.distinct().size == 1) {
        changed = true
//        valNum.alias(phiInst, operands.first())
//        block.replaceInst(phiInst, MvInst(phiInst.name!!, operands.first()))
        block.removeInst(phiInst, operands.first())
      } else {
        val nums = operands.map { valNum.get(it)!! }
        val result = hashTable.get("phi", nums)
        if (result != null) { // redundant
          changed = true
//          val mvInst = MvInst(phiInst.name!!, result)
//          valNum.alias(mvInst, result)
//          block.replaceInst(phiInst, mvInst)
//          hashTable.add("phi", nums, mvInst)
          block.removeInst(phiInst, result)
        } else {
          hashTable.add("phi", nums, phiInst)
        }
      }
    }

    block.instList.filterIsInstance<MvInst>().forEach {
      block.removeInst(it, it.getSrc())
//      valNum.alias(it, it.getSrc())
    }

    for (inst in block.instList.filter { it is BinaryInst || it is CmpInst || it is GetElementPtrInst }) {
      val operands = inst.useeList.map { valNum.get(it)!! }
      val op = when (inst) {
        is BinaryInst -> inst.op
        is CmpInst -> inst.cond
        is GetElementPtrInst -> "getelementptr"
        else -> throw Exception("unreachable")
      }
      val result = hashTable.get(op, operands)
      if (result != null) { // redundant
        changed = true
//        val mvInst = MvInst(inst.name!!, result)
//        valNum.alias(mvInst, result)
//        block.replaceInst(inst, mvInst)
//        hashTable.add(op, operands, mvInst)
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
    valNum = func.valNum
    func.domTree.build()
    func.argList.forEach { valNum.new(it) }
    func.blockList.forEach { it.instList.forEach { inst -> valNum.new(inst) } }
    do {
      changed = false
      valueNumbering(func.getEntryBlock(), null)
    } while (changed)
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