package backend.pass

import backend.basic.*
import backend.controller.ASMBuilder
import backend.controller.ASMVisitor
import backend.helper.RegFactory
import middleend.basic.Value

class ASMAllocator : ASMVisitor() {
  var vir2Offset = hashMapOf<VirReg, Int>()
  val regFactory = RegFactory()
  private var value2Reg = hashMapOf<Value, Register>() // this mapping is only for virtual register
  private var reg2Value = hashMapOf<Register, Value>() // this mapping is only for virtual register
  var stackAlloca = 0
  var limitedStackAlloca = 0
  val module = ASMModule()

  override fun visit(module: ASMModule) {
    module.globalPtr.forEach { it.value.accept(this) }
    module.funcList.forEach { it.accept(this) }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {
    module.addGlobalPtr(globalPtr.name, globalPtr)
  }

  override fun visit(func: ASMFunc) {
    val newFunc = ASMFunc(func.name)
    module.addFunc(newFunc)
    ASMBuilder.setCurrentFunc(newFunc)
    regFactory.position = func

    func.blockList.forEach { newFunc.addBlock(ASMBlock(it.pureName, newFunc)) }

    vir2Offset = hashMapOf()
    stackAlloca = func.stackAlloca + func.usedVirRegNum * 4
    limitedStackAlloca = -stackAlloca
//    val stackPushInst = func.blockList.first().instList.first() as ASMArithiInst
//    val stackPopInst = func.blockList.last().instList[func.blockList.last().instList.size - 2] as ASMArithiInst
//    stackPushInst.imm = Imm(stackAlloca)
//    stackPopInst.imm = Imm(-stackAlloca)
    func.blockList.forEach { it.accept(this) }
  }

  override fun visit(block: ASMBlock) {
    ASMBuilder.setInsertBlock(ASMBuilder.getCurrentFunc().getBlockByPureName(block.pureName)!!)
    block.instList.forEach { it.accept(this) }
  }

  private fun getOffset(reg: VirReg): Int {
    val temp = if (vir2Offset.containsKey(reg)) {
      vir2Offset[reg]!!
    } else {
      stackAlloca -= 4
      vir2Offset[reg] = stackAlloca
      stackAlloca
    }
    return temp
  }

  private fun replaceWithAsRs(pendingReg: Register, phyReg: PhyReg): PhyReg {
    return if (pendingReg is PhyReg) {
      pendingReg
    } else {
      ASMBuilder.createLoadInst(4, phyReg, Literal(getOffset(pendingReg as VirReg)), regFactory.getPhyReg("sp"))
      phyReg
    }
  }

  private fun replaceWithAsRd(pendingReg: Register, phyReg: PhyReg): PhyReg {
    return if (pendingReg is PhyReg) {
      pendingReg
    } else {
      phyReg
    }
  }

  private fun supplementAsRd(pendingReg: Register, phyReg: PhyReg) {
    if (pendingReg is VirReg) {
      ASMBuilder.createStoreInst(4, phyReg, Literal(getOffset(pendingReg)), regFactory.getPhyReg("sp"))
    }
  }

  override fun visit(inst: ASMStoreInst) {
    val rs1Reg = replaceWithAsRs(inst.rs1, regFactory.getPhyReg("t0"))
    val rs2Reg = replaceWithAsRs(inst.rs2, regFactory.getPhyReg("t1"))
    ASMBuilder.createStoreInst(inst.byteNum, rs2Reg, inst.imm, rs1Reg)
  }

  override fun visit(inst: ASMLoadInst) {
    val rsReg = replaceWithAsRs(inst.rs, regFactory.getPhyReg("t0"))
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t1"))
    ASMBuilder.createLoadInst(inst.byteNum, rdReg, inst.imm, rsReg)
    supplementAsRd(inst.rd, rdReg)
  }

  override fun visit(inst: ASMBzInst) {
    val func = ASMBuilder.getCurrentFunc()
    val rsReg = replaceWithAsRs(inst.rs, regFactory.getPhyReg("t0"))
    ASMBuilder.createBzInst(inst.op, rsReg, func.getBlockByCompositeName(inst.label.name)!!)
  }

  override fun visit(inst: ASMJInst) {
    val func = ASMBuilder.getCurrentFunc()
    ASMBuilder.createJInst(func.getBlockByCompositeName(inst.label.name)!!)
  }

  override fun visit(inst: ASMRetInst) {
    ASMBuilder.createRet()
  }

  override fun visit(inst: ASMArithInst) {
    val rs1Reg = replaceWithAsRs(inst.rs1, regFactory.getPhyReg("t0"))
    val rs2Reg = replaceWithAsRs(inst.rs2, regFactory.getPhyReg("t1"))
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t2"))
    ASMBuilder.createArithInst(inst.op, rdReg, rs1Reg, rs2Reg)
    supplementAsRd(inst.rd, rdReg)
  }

  override fun visit(inst: ASMArithiInst) {
    if (inst.rs == PhyReg("sp") && inst.rd == PhyReg("sp")) {
      val spReg = regFactory.getPhyReg("sp")
      ASMBuilder.createArithiInst("addi", spReg, spReg, Literal(limitedStackAlloca))
      limitedStackAlloca = -limitedStackAlloca
    } else if (inst.rs == PhyReg("sp")) {
      // this case would never occur
//      getOffset(inst.rd as VirReg)
    } else {
      val rsReg = replaceWithAsRs(inst.rs, regFactory.getPhyReg("t0"))
      val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t1"))
      ASMBuilder.createArithiInst(inst.op, rdReg, rsReg, inst.imm)
      supplementAsRd(inst.rd, rdReg)
    }
  }

  override fun visit(inst: ASMCmpInst) {
    val rs1Reg = replaceWithAsRs(inst.rs1, regFactory.getPhyReg("t0"))
    val rs2Reg = replaceWithAsRs(inst.rs2, regFactory.getPhyReg("t1"))
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t2"))
    ASMBuilder.createCmpInst(inst.op, rdReg, rs1Reg, rs2Reg)
    supplementAsRd(inst.rd, rdReg)
  }

  override fun visit(inst: ASMCmpiInst) {
  }

  override fun visit(inst: ASMCallInst) {
    // FIXME: this is buggy because the builtin func is generated every single time
    ASMBuilder.createCallInst(module.getFunc(inst.label.name) ?: ASMFunc(inst.label.name))
  }

  override fun visit(inst: ASMLiInst) {
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t0"))
    ASMBuilder.createLiInst(rdReg, inst.imm)
    supplementAsRd(inst.rd, rdReg)
  }

  override fun visit(inst: ASMMvInst) {
    val rsReg = replaceWithAsRs(inst.rs, regFactory.getPhyReg("t0"))
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t1"))
    if (rsReg != rdReg) {
      ASMBuilder.createMvInst(rdReg, rsReg)
      supplementAsRd(inst.rd, rdReg)
    }
  }

  override fun visit(inst: ASMLaInst) {
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t0"))
    ASMBuilder.createLaInst(rdReg, inst.symbol)
    supplementAsRd(inst.rd, rdReg)
  }

  override fun visit(inst: ASMCmpzInst) {
    val rsReg = replaceWithAsRs(inst.rs1, regFactory.getPhyReg("t0"))
    val rdReg = replaceWithAsRd(inst.rd, regFactory.getPhyReg("t1"))
    ASMBuilder.createCmpzInst(inst.op, rdReg, rsReg)
    supplementAsRd(inst.rd, rdReg)
  }
}