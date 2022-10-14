package backend.controller

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

class ASMGenerator(topModule: TopModule) : IRVisitor(topModule) {


  override fun visit(func: Func) {
    ASMBuilder.setCurrentFunc(func)
    func.blockList.forEach { it.accept(this) }
  }

  override fun visit(block: BasicBlock) {
    ASMBuilder.setCurrentBlock(block)
    block.instList.forEach { it.accept(this) }
  }

  override fun visit(inst: AllocaInst) {
    ASMBuilder.alloca(inst.getAllocatedSize())
  }

  override fun visit(inst: CallInst) {
    // there is not a concept of caller save in virtual register
    for ((index, arg) in inst.args.withIndex()) { // TODO: I don't know what to do when too many args
      ASMBuilder.mvVal2Reg(arg, PhyReg(index))
    }
    ASMBuilder.call(inst.funcType.funcName)
  }

  override fun visit(inst: LoadInst) {
    ASMBuilder.load2NewReg(inst.addr, inst)
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BinaryInst) {
    ASMBuilder.arith2NewReg(inst.op, inst.lhs, inst.rhs, inst)
  }

  override fun visit(inst: BranchInst) {
    if (inst.cond == null) { // unconditional br
//      ASMBuilder.j2Label(inst.trueBlock) TODO: determine the argument
    } else { // conditional br
      if (inst.cond is Instruction) {
        inst.accept(this)
      }

//      ASMBuilder.bnez2Label(inst.cond, inst.trueBlock) TODO: determine the second argument
//      ASMBuilder.beqz2Label(inst.cond, inst.falseBlock) TODO: determine the second argument
    }
  }

  override fun visit(inst: GetElementPtrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ZExtInst) {
//    ASMBuilder.zext2NewReg()
  }

  override fun visit(inst: TruncInst) {
    // TODO: I need a empty slot on the stack to store the truncated value
  }

  override fun visit(inst: StoreInst) {
    ASMBuilder.store(inst.value, inst.addr)
  }

  override fun visit(inst: CmpInst) {
    if (inst.lhs is Instruction) {
      inst.lhs.accept(this)
    }
    if (inst.rhs is Instruction) {
      inst.rhs.accept(this)
    }
//    ASMBuilder.cmp2NewReg(inst.op, inst.lhs, inst.rhs, inst)
  }

  override fun visit(inst: ReturnInst) {
    if (inst.retVal != null) {
      ASMBuilder.mvVal2Reg(inst.retVal, PhyReg(10))
    }
    ASMBuilder.ret()
  }
}