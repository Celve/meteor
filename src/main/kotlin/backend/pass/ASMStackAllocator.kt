package backend.pass

import backend.basic.*
import backend.controller.ASMBuilder
import backend.controller.ASMVisitor
import backend.helper.RegFactory

/**
 * This is a brute-force register allocator.
 * For every virtual register, we allocate a space for it on stack.
 * When we need to use it, we load it from stack to a physical register.
 * After we have use it, we store it back to stack.
 * Besides, in this stage, the only register caller has to preserve is ra, in order to jump back.
 * Its functionality is broken due to the introduction of new stack frame memory system, please fix it.
 */
class ASMStackAllocator : ASMVisitor() {
  private var vir2Offset = hashMapOf<VirReg, Int>()
  private var regFactory = RegFactory()
  private var stackAlloca = 0
  private var limitedStackAlloca = 0
  private val newModule = ASMModule()

  override fun visit(module: ASMModule) {
    regFactory = module.regFactory
    module.globalPtr.forEach { it.value.accept(this) }
    module.funcList.forEach { it.accept(this) }
    module.funcList = newModule.funcList
    module.globalPtr = newModule.globalPtr
  }

  override fun visit(globalPtr: ASMGlobalPointer) {
    newModule.addGlobalPtr(globalPtr.name!!, globalPtr)
  }

  override fun visit(func: ASMFunc) {
    // because ra's consistency is necessary in function call
    // therefore even if we allocate variable on stack, we have to maintain it
    val raVirReg = regFactory.newVirReg()
    ASMBuilder.setInsertPointBefore(func.blockList.first().instList.first())
    ASMBuilder.createMvInst(raVirReg, regFactory.getPhyReg("ra"))
    ASMBuilder.setInsertPointBefore(func.blockList.first().instList.last())
    ASMBuilder.createMvInst(regFactory.getPhyReg("ra"), raVirReg)

    val newFunc = ASMFunc(func.name!!)
    newModule.addFunc(newFunc)
    ASMBuilder.setCurrentFunc(newFunc)
    regFactory.position = func

    func.blockList.forEach { newFunc.addBlock(ASMBlock(it.pureName, newFunc, it.executionFrequency)) }

    vir2Offset = hashMapOf()
    stackAlloca = func.stackAlloca + func.usedVirRegNum * 4
    limitedStackAlloca = -stackAlloca
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
      ASMBuilder.createLoadInst(
        4,
        phyReg,
        DeterminedImmediate(getOffset(pendingReg as VirReg)),
        regFactory.getPhyReg("sp")
      )
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
      ASMBuilder.createStoreInst(4, phyReg, DeterminedImmediate(getOffset(pendingReg)), regFactory.getPhyReg("sp"))
    }
  }

  override fun visit(inst: ASMStoreInst) {
    val rs1Reg = replaceWithAsRs(inst.getRs1(), regFactory.getPhyReg("t0"))
    val rs2Reg = replaceWithAsRs(inst.getRs2(), regFactory.getPhyReg("t1"))
    ASMBuilder.createStoreInst(inst.byteNum, rs2Reg, inst.getImm(), rs1Reg)
  }

  override fun visit(inst: ASMLoadInst) {
    val rsReg = replaceWithAsRs(inst.getRs(), regFactory.getPhyReg("t0"))
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t1"))
    ASMBuilder.createLoadInst(inst.byteNum, rdReg, inst.getImm(), rsReg)
    supplementAsRd(inst.getRd()!!, rdReg)
  }

  override fun visit(inst: ASMBrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMBzInst) {
    val func = ASMBuilder.getCurrentFunc()
    val rsReg = replaceWithAsRs(inst.getRs(), regFactory.getPhyReg("t0"))
    ASMBuilder.createBzInst(inst.op, rsReg, func.getBlockByCompositeName(inst.getLabel().name!!)!!)
  }

  override fun visit(inst: ASMJInst) {
    val func = ASMBuilder.getCurrentFunc()
    ASMBuilder.createJInst(func.getBlockByCompositeName(inst.getLabel().name!!)!!)
  }

  override fun visit(inst: ASMRetInst) {
    ASMBuilder.createRet()
  }

  override fun visit(inst: ASMArithInst) {
    val rs1Reg = replaceWithAsRs(inst.getRs1(), regFactory.getPhyReg("t0"))
    val rs2Reg = replaceWithAsRs(inst.getRs2(), regFactory.getPhyReg("t1"))
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t2"))
    ASMBuilder.createArithInst(inst.op, rdReg, rs1Reg, rs2Reg)
    supplementAsRd(inst.getRd()!!, rdReg)
  }

  override fun visit(inst: ASMArithiInst) {
    if (inst.getRs() == PhyReg("sp") && inst.getRd() == PhyReg("sp")) {
      val spReg = regFactory.getPhyReg("sp")
      ASMBuilder.createArithiInst("addi", spReg, spReg, DeterminedImmediate(limitedStackAlloca))
      limitedStackAlloca = -limitedStackAlloca
    } else if (inst.getRs() == PhyReg("sp")) {
      // this case would never occur, due to the optimization of the generator
    } else {
      val rsReg = replaceWithAsRs(inst.getRs(), regFactory.getPhyReg("t0"))
      val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t1"))
      ASMBuilder.createArithiInst(inst.op, rdReg, rsReg, inst.getImm())
      supplementAsRd(inst.getRd()!!, rdReg)
    }
  }

  override fun visit(inst: ASMCmpInst) {
    val rs1Reg = replaceWithAsRs(inst.getRs1(), regFactory.getPhyReg("t0"))
    val rs2Reg = replaceWithAsRs(inst.getRs2(), regFactory.getPhyReg("t1"))
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t2"))
    ASMBuilder.createCmpInst(inst.op, rdReg, rs1Reg, rs2Reg)
    supplementAsRd(inst.getRd()!!, rdReg)
  }

  override fun visit(inst: ASMCmpiInst) {
  }

  override fun visit(inst: ASMCallInst) {
    // FIXME: this is buggy because the builtin func is generated every single time
    ASMBuilder.createCallInst(newModule.getFunc(inst.getLabel().name!!) ?: ASMFunc(inst.getLabel().name!!))
  }

  override fun visit(inst: ASMLiInst) {
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t0"))
    ASMBuilder.createLiInst(rdReg, inst.getImm())
    supplementAsRd(inst.getRd()!!, rdReg)
  }

  override fun visit(inst: ASMMvInst) {
    val rsReg = replaceWithAsRs(inst.getRs(), regFactory.getPhyReg("t0"))
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t1"))
    if (rsReg != rdReg) {
      ASMBuilder.createMvInst(rdReg, rsReg)
      supplementAsRd(inst.getRd()!!, rdReg)
    }
  }

  override fun visit(inst: ASMLaInst) {
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t0"))
    ASMBuilder.createLaInst(rdReg, inst.getSymbol())
    supplementAsRd(inst.getRd()!!, rdReg)
  }

  override fun visit(inst: ASMCmpzInst) {
    val rsReg = replaceWithAsRs(inst.getRs(), regFactory.getPhyReg("t0"))
    val rdReg = replaceWithAsRd(inst.getRd()!!, regFactory.getPhyReg("t1"))
    ASMBuilder.createCmpzInst(inst.op, rdReg, rsReg)
    supplementAsRd(inst.getRd()!!, rdReg)
  }
}