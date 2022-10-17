package backend.controller

import backend.basic.ASMModule
import backend.basic.Imm
import backend.basic.PhyReg
import middleend.basic.*
import middleend.pass.IRVisitor

/**
 * Mapping rules for llvm inst:
 *  alloca
 *    addi sp, sp, -size
 *    addi sp, sp, size
 *  call
 *    call func
 *  load
 *    lw reg, offset(sp) TODO: how to determine the offset?
 *  bitcast
 *    no need to implement TODO: determine it
 *  phi
 *    for every block that could be a predecessor of phi inst
 *    store the value to stack
 *  br
 *    conditional jump
 *      TODO: I think it should use the beq, however, I don't know how to determine the offset of the label.
 *    unconditional jump
 *      jal
 */

class ASMGenerator : IRVisitor() {
  var module: ASMModule? = null
  override fun visit(topModule: TopModule) {
    module = ASMModule(topModule)
    topModule.func.forEach { it.value.accept(this) }
  }

  override fun visit(func: Func) {
    val asmFunc = module!!.getFunc(func.name!!)!!
    ASMBuilder.setCurrentFunc(asmFunc)
    func.blockList.forEach { it.accept(this) }
    ASMBuilder.setInsertPointBefore(asmFunc.blockList.first().instList.first())
    ASMBuilder.pushSp()
    ASMBuilder.setInsertBlock(asmFunc.blockList.last())
    ASMBuilder.popSp()
    ASMBuilder.ret()
  }

  override fun visit(block: BasicBlock) {
    val func = ASMBuilder.getCurrentFunc()
    ASMBuilder.setInsertBlock(func.getBlock(block.name!!)!!)
    block.instList.forEach { it.accept(this) }
  }

  override fun visit(inst: AllocaInst) {
    ASMBuilder.alloca(inst.getAllocatedSize(), inst)
  }

  override fun visit(inst: CallInst) {
    // there is not a concept of caller save in virtual register
    for ((index, arg) in inst.args.withIndex()) { // TODO: I don't know what to do when too many args
      ASMBuilder.mvVal2OldReg(arg, PhyReg(index))
    }
    ASMBuilder.call(module!!.getFunc(inst.funcType.funcName)!!)
  }

  override fun visit(inst: LoadInst) {
    ASMBuilder.load2NewReg(inst.addr, inst)
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    val func = ASMBuilder.getCurrentFunc()
    val block = ASMBuilder.getInsertBlock()
    val reg = ASMBuilder.newVirReg(inst)
    for (pred in inst.preds) {
      val predBlock = func.getBlock(pred.second.name!!)!!
      ASMBuilder.setInsertBlock(predBlock)
      ASMBuilder.mvVal2OldReg(pred.first, reg)
    }
    ASMBuilder.linkValAndReg(inst, reg)
    ASMBuilder.setInsertBlock(block)
    ASMBuilder.mvReg2NewReg(reg)
  }

  override fun visit(inst: BinaryInst) {
    ASMBuilder.arith2NewReg(inst.op, inst.lhs, inst.rhs, inst)
  }

  override fun visit(inst: BranchInst) {
    if (inst.cond == null) { // unconditional br
      ASMBuilder.j2Label(inst.trueBlock) // TODO: determine the argument
    } else { // conditional br
      val func = ASMBuilder.getCurrentFunc()
      ASMBuilder.brz2Label("bnez", inst.cond, inst.trueBlock) // TODO: determine the second argument
      ASMBuilder.brz2Label("beqz", inst.cond, inst.falseBlock!!) // TODO: determine the second argument
    }
  }

  override fun visit(inst: GetElementPtrInst) {
    ASMBuilder.fetch2NewReg(inst)
  }

  override fun visit(inst: ZExtInst) {
//    ASMBuilder.zext2NewReg()
  }

  override fun visit(inst: TruncInst) {
    // TODO: I need a empty slot on the stack to store the truncated value
  }

  override fun visit(inst: StoreInst) {
    ASMBuilder.store(inst.value, inst.addr, 0)
  }

  override fun visit(inst: CmpInst) {
    if (inst.lhs is Instruction) {
      inst.lhs.accept(this)
    }
    if (inst.rhs is Instruction) {
      inst.rhs.accept(this)
    }
    ASMBuilder.cmp2NewReg(inst.cond.toString(), inst.lhs, inst.rhs, inst)
  }

  override fun visit(inst: ReturnInst) {
    if (inst.retVal != null) {
      ASMBuilder.mvVal2OldReg(inst.retVal, PhyReg(10))
    }
    val stackAlloca = ASMBuilder.stackAlloca
    ASMBuilder.arith2OldReg("addi", PhyReg("sp"), Imm(stackAlloca), PhyReg("sp"))
    ASMBuilder.ret()
  }
}