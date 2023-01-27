package backend.pass

import backend.basic.*

object Peephole : ASMVisitor() {
  private var lastAddr: Pair<Register, DeterminedImmediate>? = null
  private var lastValue: Register? = null

  override fun visit(module: ASMModule) {
    module.funcList.forEach { it.accept(this) }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {}

  override fun visit(func: ASMFunc) {
    func.blockList.forEach { it.accept(this) }
  }

  private fun init() {
    lastAddr = null
  }

  private fun modify(inst: ASMInst) {
    val dest = inst.getRd()
    dest?.let {
      if (lastAddr?.first == dest || lastValue == dest) {
        lastAddr = null
      }
    }
  }

  override fun visit(block: ASMBlock) {
    val instList = block.instList
    val divisions = listOf(-1) + instList
      .filter { it is ASMBrInst || it is ASMBzInst || it is ASMJInst || it is ASMRetInst }
    for (i in 0 until divisions.size - 1) {
      val left = instList.indexOf(divisions[i]) + 1
      val right = instList.indexOf(divisions[i + 1]) - 1
      if (left > right) continue
      init()
      instList.subList(left, right + 1).toList().forEach { it.accept(this) }
    }
  }

  override fun visit(inst: ASMStoreInst) {
    val offset = inst.getImm()
    if (offset is DeterminedImmediate) {
      val addr = inst.getRs1()
      val value = inst.getRs2()
      lastAddr = addr to offset
      lastValue = value
    }
  }

  override fun visit(inst: ASMLoadInst) {
    val addr = inst.getRs()
    val offset = inst.getImm()
    if (offset is DeterminedImmediate && lastAddr == addr to offset) {
      inst.eliminate()
      if (inst.getRd()!! != lastValue) {
        val mvInst = ASMMvInst(inst.getRd()!!, lastValue!!)
        inst.parent.replaceInst(inst, mvInst)
        modify(mvInst)
      } else {
        inst.parent.removeInst(inst)
      }
    } else {
      modify(inst)
    }
  }

  override fun visit(inst: ASMBrInst) {
    modify(inst)
  }

  override fun visit(inst: ASMBzInst) {
    modify(inst)
  }

  override fun visit(inst: ASMJInst) {
    modify(inst)
  }

  override fun visit(inst: ASMRetInst) {
    modify(inst)
  }

  override fun visit(inst: ASMArithInst) {
    modify(inst)
  }

  override fun visit(inst: ASMArithiInst) {
    val imm = inst.getImm()
    if (imm !is DeterminedImmediate) return

    val const = imm.value
    if ((inst.op == "addi" || inst.op == "slli" || inst.op == "srai" || inst.op == "ori") && const == 0) {
      inst.parent.removeInst(inst)
    }

    modify(inst)
  }

  override fun visit(inst: ASMCmpInst) {
    modify(inst)
  }

  override fun visit(inst: ASMCmpiInst) {
    modify(inst)
  }

  override fun visit(inst: ASMCallInst) {
    lastAddr = null
  }

  override fun visit(inst: ASMLiInst) {
    modify(inst)
  }

  override fun visit(inst: ASMMvInst) {
    if (inst.getRd()!! == inst.getRs()) {
      inst.parent.removeInst(inst)
    } else {
      modify(inst)
    }
  }

  override fun visit(inst: ASMLaInst) {
    modify(inst)
  }

  override fun visit(inst: ASMCmpzInst) {
    modify(inst)
  }

  override fun visit(inst: ASMLuiInst) {
    modify(inst)
  }
}