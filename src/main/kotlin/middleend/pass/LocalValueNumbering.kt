package middleend.pass

import middleend.basic.*
import middleend.helper.Utils

object LocalValueNumbering : IRVisitor() {
  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    func.blockList.forEach { it.accept(this) }
  }

  override fun visit(block: BasicBlock) {
    val patternMap = hashMapOf<Triple<Value, String, Value>, Instruction>()
    for (inst in block.instList.filterIsInstance<BinaryInst>()) {
      val lhs = inst.getLhs()
      val rhs = inst.getRhs()
      val op = inst.op
      if (lhs is ConstantInt && rhs is ConstantInt) {
        val newInst =
          MvInst(inst.name!!, ConstantInt(inst.type.getNumBits(), Utils.calculate(inst.op, lhs.value, rhs.value)))
        block.replaceInst(inst, newInst)
      } else if (patternMap.contains(Triple(lhs, op, rhs))) { // FIXME: temporarily ignore commute operations
        val newInst = MvInst(inst.name!!, patternMap[Triple(lhs, op, rhs)]!!)
        block.replaceInst(inst, newInst)
      }
      patternMap[Triple(lhs, op, rhs)] = inst
    }
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
