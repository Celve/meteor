package middleend.pass

import middleend.basic.*
import middleend.struct.EqSet

object Peephole : IRVisitor() {
  private lateinit var currModule: TopModule
  private lateinit var currFunc: Func
  private lateinit var eqSet: EqSet

  override fun visit(topModule: TopModule) {
    currModule = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    TODO("Not yet implemented")
  }

  override fun visit(constStr: ConstantStr) {
    TODO("Not yet implemented")
  }

  override fun visit(func: Func) {
    currFunc = func
    eqSet = func.eqSet
    eqSet.build()
    func.blockList.forEach { it.accept(this) }
  }

  override fun visit(block: BasicBlock) {
    block.instList.toList().forEach { it.accept(this) }
  }

  override fun visit(inst: AllocaInst) {}

  override fun visit(inst: CallInst) {
    if (inst.getCallee().name!! == "toString") {
      if (inst.userList.all { it is CallInst && (it.getCallee().name == "print" || it.getCallee().name == "println") }) {
        inst.userList.filterIsInstance<CallInst>()
          .forEach { it.setCallee(currModule.getBuiltinFunc(it.getCallee().name!! + "Int")!!) }
        inst.substitutedBy(inst.getArgList().first()) // it should have only one argument
        inst.parent.removeInst(inst, inst.getArgList().first())
      }
    }
  }

  private fun isViolateLAS(instList: List<Instruction>, addr: Value): Boolean {
    return instList.any { (it is StoreInst && eqSet.get(it.getAddr()) == eqSet.get(addr)) || it is CallInst }
  }

  private fun isViolateSAS(instList: List<Instruction>, addr: Value): Boolean {
    return instList.any { (it is LoadInst && eqSet.get(it.getAddr()) == eqSet.get(addr)) || it is CallInst }
  }

  override fun visit(inst: LoadInst) {
    // load after store, generalized
    val addr = inst.getAddr()
    val block = inst.parent
    val storeInst = addr.userList.asSequence()
      .filterIsInstance<StoreInst>()
      .filter { it.parent == block }
      .filter { block.getIndexOfInst(it) < block.getIndexOfInst(inst) }
      .lastOrNull()
    storeInst?.let {
      val from = block.getIndexOfInst(it)
      val to = block.getIndexOfInst(inst)
      if (!isViolateLAS(block.instList.subList(from + 1, to), addr)) {
        block.removeInst(inst, it.getValue())
      }
    }
  }

  override fun visit(inst: BitCastInst) {}

  override fun visit(inst: PhiInst) {}

  private fun isPowerOf2(value: Int): Pair<Boolean, Int> {
    var mutableVal = value
    var cnt = 0
    while (mutableVal > 0) {
      if (mutableVal and 1 == 1) {
        return Pair(mutableVal == 1, cnt)
      }
      mutableVal = mutableVal shr 1
      cnt++
    }
    return Pair(false, 0)
  }

  override fun visit(inst: BinaryInst) {
    val block = inst.parent
    val func = inst.parent.parent
    when (inst.op) {
      "mul" -> {
        if (inst.getLhs() is ConstantInt) {
          val value = (inst.getLhs() as ConstantInt).value
          val (isPowerOf2, cnt) = isPowerOf2(value)
          if (value == 1) {
            block.removeInst(inst, inst.getRhs())
          } else if (isPowerOf2) {
            inst.parent.replaceInst(
              inst,
              BinaryInst(func.mulTable.rename(".shl"), "shl", inst.getRhs(), ConstantInt(32, cnt))
            )
          }
        } else if (inst.getRhs() is ConstantInt) {
          val value = (inst.getRhs() as ConstantInt).value
          val (isPowerOf2, cnt) = isPowerOf2(value)
          if (value == 1) {
            block.removeInst(inst, inst.getLhs())
          } else if (isPowerOf2) {
            inst.parent.replaceInst(
              inst,
              BinaryInst(func.mulTable.rename(".shl"), "shl", inst.getLhs(), ConstantInt(32, cnt))
            )
          }
        }
      }

      "sdiv" -> {
        val rhs = inst.getRhs()
        if (rhs is ConstantInt) {
          val value = (inst.getRhs() as ConstantInt).value
          val (isPowerOf2, cnt) = isPowerOf2(value)
          if (value == 1) {
            block.removeInst(inst, inst.getLhs())
          } else if (isPowerOf2) {
            inst.parent.replaceInst(
              inst,
              BinaryInst(func.mulTable.rename(".ashr"), "ashr", inst.getLhs(), ConstantInt(32, cnt))
            )
          }
        }
      }


      "srem" -> {
        val rhs = inst.getRhs()
        if (rhs is ConstantInt) {
          val value = (inst.getRhs() as ConstantInt).value
          val (isPowerOf2, cnt) = isPowerOf2(value)
          if (value == 1) {
            block.removeInst(inst, ConstantInt(32, 0))
          } else if (isPowerOf2) {
            inst.parent.replaceInst(
              inst,
              BinaryInst(func.mulTable.rename(".and"), "and", inst.getLhs(), ConstantInt(32, value - 1))
            )
          }
        }
      }
    }
  }

  override fun visit(inst: BranchInst) {}

  override fun visit(inst: GetElementPtrInst) {}

  override fun visit(inst: ZExtInst) {}

  override fun visit(inst: TruncInst) {}

  override fun visit(inst: StoreInst) {
    // store after load, generalized
    val addr = inst.getAddr()
    val loadInst = inst.getValue()
    if (loadInst is LoadInst && loadInst.getAddr() == addr) {
      inst.parent.removeInst(inst)
      if (loadInst.userList.size == 0) {
        inst.parent.removeInst(loadInst)
      }
    }

    // store after store, generalized
    return
    val block = inst.parent
    val targetStoreInst =
      addr.userList.asSequence()
        .filterIsInstance<StoreInst>()
        .minus(inst)
        .filter { it.parent == block }
        .filter { block.getIndexOfInst(it) < block.getIndexOfInst(inst) }
        .maxByOrNull { it.parent.getIndexOfInst(it) }
    targetStoreInst?.let {
      val from = inst.parent.getIndexOfInst(targetStoreInst)
      val to = inst.parent.getIndexOfInst(inst)
      if (!isViolateSAS(block.instList.subList(from + 1, to), addr)) {
        inst.parent.removeInst(targetStoreInst)
      }
    }
  }

  override fun visit(inst: CmpInst) {}

  override fun visit(inst: ReturnInst) {}

  override fun visit(inst: PCopyInst) {}

  override fun visit(inst: MvInst) {
    if (inst.userList.size == 1) {
      inst.parent.removeInst(inst, inst.getSrc())
    }
  }
}