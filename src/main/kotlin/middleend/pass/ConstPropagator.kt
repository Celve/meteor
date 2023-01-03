package middleend.pass

import middleend.basic.*
import middleend.helper.Utils

object ConstPropagator : IRVisitor() {
  sealed class VarState(val stage: Int) {
    object NoEvidence : VarState(0) {
      override fun toString(): String {
        return "NoEvidence"
      }
    }

    class Determined(val value: Int?) : VarState(1) {
      override fun toString(): String {
        return "Determined($value)"
      }
    }

    object Undetermined : VarState(2) {
      override fun toString(): String {
        return "Undetermined"
      }
    }
  }

  val varWorkList = mutableListOf<Value>()
  val blockWorkList = mutableListOf<BasicBlock>()
  val varStateMap = mutableMapOf<Value, VarState>().withDefault { VarState.NoEvidence }
  val blockStateMap = mutableMapOf<BasicBlock, Boolean>().withDefault { false }

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun isDetermined(value: Value): Boolean {
    return value is Constant || varStateMap.getValue(value) is VarState.Determined
  }

  private fun isUndetermined(value: Value): Boolean {
    return value !is Constant && varStateMap.getValue(value) is VarState.Undetermined
  }

  private fun isAccessible(block: BasicBlock, prevBlock: BasicBlock): Boolean {
    val brInst = prevBlock.getTerminator() as BranchInst
    val cond = brInst.getCond()
    return cond == null
        || !isDetermined(cond)
        || (getDetermined(cond) == 0 && block == brInst.getFalseBlock()!!)
        || (getDetermined(cond) == 1 && block == brInst.getTrueBlock())
  }

  private fun getDetermined(value: Value): Int? {
    return if (value is ConstantInt) {
      value.value
    } else {
      (varStateMap.getValue(value) as VarState.Determined).value
    }
  }

  private fun addBlock2WorkList(block: BasicBlock) {
    if (!blockStateMap.getValue(block)) {
      blockWorkList.add(block)
      blockWorkList.addAll(block.nextBlockSet.filter { blockStateMap.getValue(it) })
      blockStateMap[block] = true
    } else {
      // this is to solve the accessibility problem for phi inst
      varWorkList.addAll(block.instList.filterIsInstance<PhiInst>())
    }
  }

  private fun addVar2WorkList(value: Value, newState: VarState) {
    if (value !is Constant && newState.stage > varStateMap.getValue(value).stage) {
      varWorkList.add(value)
      varStateMap[value] = newState
    }
  }

  override fun visit(func: Func) {
    varWorkList.clear()
    blockWorkList.clear()
    varStateMap.clear()
    blockStateMap.clear()
    addBlock2WorkList(func.getEntryBlock())

    func.argList.forEach { varStateMap[it] = VarState.Undetermined }

    while (varWorkList.isNotEmpty() || blockWorkList.isNotEmpty()) {
      if (varWorkList.isNotEmpty()) {
        val variable = varWorkList.removeFirst()
        variable.userList.filterIsInstance<Instruction>()
          .filter { blockStateMap.getValue(it.parent) || it is PhiInst }.forEach { it.accept(this) }
      }
      if (blockWorkList.isNotEmpty()) {
        val block = blockWorkList.first()
        blockWorkList.removeFirst()
        block.accept(this)
      }
    }

    varStateMap.forEach {
      if (it.value is VarState.Determined) {
        val value = (it.value as VarState.Determined).value
        if (value == null) {
          it.key.substituteAll(ConstantNull())
        } else {
          it.key.substituteAll(ConstantInt(it.key.type.getNumBits(), value))
        }
        if (it.key is Instruction) {
          val block = (it.key as Instruction).parent
          block.instList.remove(it.key)
        }
        (it.key as User).eliminate()
      }
    }

    // remove useless branch inst
    for (block in func.blockList) {
      for (inst in block.instList.filterIsInstance<BranchInst>()) {
        val cond = inst.getCond()
        val trueBlock = inst.getTrueBlock()
        val falseBlock = inst.getFalseBlock()
        if (cond is ConstantInt) {
          if (cond.value == 0) {
            block.replaceInst(inst, BranchInst(falseBlock!!, null, null))
            block.removeNextBlock(trueBlock)
            trueBlock.removePrevBlock(block)
            trueBlock.instList.filterIsInstance<PhiInst>().forEach { it.removePred(block) }
          } else {
            block.replaceInst(inst, BranchInst(trueBlock, null, null))
            block.removeNextBlock(falseBlock!!)
            falseBlock.removePrevBlock(block)
            falseBlock.instList.filterIsInstance<PhiInst>().forEach { it.removePred(block) }
          }
        }
      }
    }

    val removedBlockSet = func.blockList.filter { !blockStateMap.getValue(it) }
    removedBlockSet.forEach { block ->
      block.userList.toList().filterIsInstance<PhiInst>().forEach { it.removePred(block) }
      block.prevBlockSet.forEach { it.removeNextBlock(block) }
      block.nextBlockSet.forEach { it.removePrevBlock(block) }
      block.instList.forEach { it.eliminate() }
      block.instList.clear()
    }
    func.blockList.removeAll(removedBlockSet)


    // remove useless phi inst
    for (block in func.blockList) {
      for (inst in block.instList.filterIsInstance<PhiInst>()) {
        if (inst.getSize() == 1) {
          block.replaceInst(inst, MvInst(inst.name!!, inst.getPred(0).first))
        }
      }
    }
  }

  override fun visit(block: BasicBlock) {
    block.instList.forEach { it.accept(this) }
  }

  override fun visit(inst: AllocaInst) {
    addVar2WorkList(inst, VarState.Undetermined)
  }

  override fun visit(inst: CallInst) {
    addVar2WorkList(inst, VarState.Undetermined)
  }

  override fun visit(inst: LoadInst) {
    addVar2WorkList(inst, VarState.Undetermined)
  }

  override fun visit(inst: BitCastInst) {
    if (isDetermined(inst.getCastee())) {
      addVar2WorkList(inst, VarState.Determined(getDetermined(inst.getCastee())))
    } else if (isUndetermined(inst.getCastee())) {
      addVar2WorkList(inst, VarState.Undetermined)
    }
  }

  override fun visit(inst: PhiInst) {
    val determinedSet = hashSetOf<Int?>()
    var undeterminedSum = 0
    inst.getPredList().forEach {
      // use isAccessible() to eliminate some impossible branch instruction
      // however, bugs are introduced consequently
      // there would be some cases that an initially not accessible branch becomes accessible,
      // and it only affects the effectiveness of phi inst
      // therefore when a branch inst becomes undetermined, all phi insts in its successor blocks should be added into work list
      if (blockStateMap.getValue(it.second) && isAccessible(inst.parent, it.second)) {
        if (isDetermined(it.first)) {
          determinedSet.add(getDetermined(it.first))
        } else if (isUndetermined(it.first)) {
          undeterminedSum++
        }
      }
    }
    if (blockStateMap.getValue(inst.parent)) {
      if (undeterminedSum > 0) {
        addVar2WorkList(inst, VarState.Undetermined)
      } else if (determinedSet.size > 1) {
        addVar2WorkList(inst, VarState.Undetermined)
      }
    }
    if (undeterminedSum == 0 && determinedSet.size == 1) {
      addVar2WorkList(inst, VarState.Determined(determinedSet.first()))
    }
  }

  override fun visit(inst: BinaryInst) {
    val lhs = inst.getLhs()
    val rhs = inst.getRhs()
    if (isDetermined(lhs) && isDetermined(rhs)) {
      val result = Utils.calculate(inst.op, getDetermined(lhs), getDetermined(rhs))
      addVar2WorkList(inst, VarState.Determined(result))
    } else if (isUndetermined(lhs) || isUndetermined(rhs)) {
      addVar2WorkList(inst, VarState.Undetermined)
    }
  }

  override fun visit(inst: BranchInst) {
    val cond = inst.getCond()
    val trueBlock = inst.getTrueBlock()
    val falseBlock = inst.getFalseBlock()
    if (cond != null) {
      val condState = varStateMap.getValue(cond)
      if (condState is VarState.Determined) {
        if (condState.value == 1) {
//          inst.parent!!.replaceInst(inst, BranchInst(trueBlock, null, null))
          addBlock2WorkList(trueBlock)
        } else {
//          inst.parent!!.replaceInst(inst, BranchInst(falseBlock!!, null, null))
        }
      } else if (condState is VarState.Undetermined) {
        addBlock2WorkList(trueBlock)
        addBlock2WorkList(falseBlock!!)
      }
    } else {
      addBlock2WorkList(trueBlock)
    }
  }

  override fun visit(inst: GetElementPtrInst) {
    addVar2WorkList(inst, VarState.Undetermined)
  }

  override fun visit(inst: ZExtInst) {
    if (isDetermined(inst.getOriginalVal())) {
      addVar2WorkList(inst, VarState.Determined(getDetermined(inst.getOriginalVal())))
    } else if (isUndetermined(inst.getOriginalVal())) {
      addVar2WorkList(inst, VarState.Undetermined)
    }
  }

  override fun visit(inst: TruncInst) {
    if (isDetermined(inst.getOriginalVal())) {
      addVar2WorkList(inst, VarState.Determined(getDetermined(inst.getOriginalVal())))
    } else if (isUndetermined(inst.getOriginalVal())) {
      addVar2WorkList(inst, VarState.Undetermined)
    }
  }

  override fun visit(inst: StoreInst) {}

  override fun visit(inst: CmpInst) {
    val lhs = inst.getLhs()
    val rhs = inst.getRhs()
    if (isDetermined(lhs) && isDetermined(rhs)) {
      val result = Utils.calculate(inst.cond, getDetermined(lhs), getDetermined(rhs))
      addVar2WorkList(inst, VarState.Determined(result))
    } else if (isUndetermined(lhs) || isUndetermined(rhs)) {
      addVar2WorkList(inst, VarState.Undetermined)
    }
  }

  override fun visit(inst: ReturnInst) {}

  override fun visit(inst: PCopyInst) {}

  override fun visit(inst: MvInst) {
    val src = inst.getSrc()
    if (src is ConstantNull) {
      addVar2WorkList(inst, VarState.Determined(null))
    } else if (isDetermined(src)) {
      addVar2WorkList(inst, VarState.Determined(getDetermined(src)))
    } else if (isUndetermined(src)) {
      addVar2WorkList(inst, VarState.Undetermined)
    }
  }
}