package middleend.pass

import middleend.basic.*

object Peephole : IRVisitor() {
  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    TODO("Not yet implemented")
  }

  override fun visit(constStr: ConstantStr) {
    TODO("Not yet implemented")
  }

  override fun visit(func: Func) {
    func.blockList.forEach { it.accept(this) }
  }

  override fun visit(block: BasicBlock) {
    block.instList.toList().forEach { it.accept(this) }
  }

  override fun visit(inst: AllocaInst) {}

  override fun visit(inst: CallInst) {}

  override fun visit(inst: LoadInst) {}

  override fun visit(inst: BitCastInst) {}

  override fun visit(inst: PhiInst) {}

  private fun isPowerOf2(value: Int): Pair<Boolean, Int> {
    var value = value
    var cnt = 0
    while (value > 0) {
      if (value and 1 == 1) {
        return Pair(value == 1, cnt)
      }
      value = value shr 1
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
    val addr = inst.getAddr()
    val loadInst = inst.getValue()
    if (loadInst is LoadInst && loadInst.getAddr() == addr) {
      inst.parent.removeInst(inst)
      if (loadInst.userList.size == 0) {
        inst.parent.removeInst(loadInst)
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