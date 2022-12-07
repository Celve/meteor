package middleend.pass

import middleend.basic.*

/**
 * remove useless loads and stores from program
 */
class Eliminator : IRVisitor() {
  var module: TopModule? = null

  private fun eliminateInst(inst: Instruction) {
    inst.collectUses().forEach { it.removeUser(inst) }
  }

  /**
   * add user-usee relationship
   */
  private fun buildInst(inst: Instruction, block: BasicBlock): Instruction {
    inst.collectUses().forEach { it.addUser(inst) }
    inst.parent = block
    return inst
  }

  override fun visit(topModule: TopModule) {
    module = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    // collect alloca
    // eliminate instruction with load and store to alloca
    val addr2Value = hashMapOf<Instruction, BinaryInst>()
    val entryBlock = func.blockList.first()

    val allocaInstList =
      entryBlock.instList
        .filterIsInstance<AllocaInst>()
        .filter { it.varType is IntType }
        .toSet()

    allocaInstList.forEach { eliminateInst(it) }

    for (inst in allocaInstList) {
      val original = inst.name!!.substringBeforeLast(".")
      addr2Value[inst] =
        BinaryInst(
          inst.name!!.substringBeforeLast("."),
          "add",
          if (!func.argList.any { it.name!! == original }) {
            ConstantInt(inst.varType.getNumBits(), 0)
          } else {
            func.argList.find { it.name!! == original }!!
          },
          ConstantInt(inst.varType.getNumBits(), 0)
        )
    }

    entryBlock.instList = entryBlock.instList.map { addr2Value[it] ?: it }.toMutableList()

    for (block in func.blockList) {
      // remove load & convert store into assignment
      val eliminated =
        block.instList.filter { it is LoadInst && it.collectUses().intersect(allocaInstList).isNotEmpty() }
      eliminated.forEach { eliminateInst(it) }

      block.instList = block.instList
        .filter { it !is LoadInst || it.collectUses().intersect(allocaInstList).isEmpty() }
        .map {
          if (it is StoreInst && it.collectUses().intersect(allocaInstList).isNotEmpty()) {
            eliminateInst(it)
            val binaryInst = buildInst(
              BinaryInst(addr2Value[it.addr as AllocaInst]!!.name!!, "add", it.value, ConstantInt(32, 0)),
              block
            ) as BinaryInst
            addr2Value[it.addr as AllocaInst] = binaryInst

            binaryInst
          } else {
            it
          }
        }.toMutableList()
    }
  }

  override fun visit(block: BasicBlock) {

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