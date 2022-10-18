package backend.controller

import backend.basic.*
import backend.helper.RegFactory
import backend.helper.Utils
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
  val regFactory = RegFactory()
  private var value2Reg = hashMapOf<Value, Register>() // this mapping is only for virtual register
  private var reg2Value = hashMapOf<Register, Value>() // this mapping is only for virtual register

  fun linkValAndReg(value: Value, reg: Register) {
    println("linkValAndReg: $value -> $reg")
    value2Reg[value] = reg
    reg2Value[reg] = value
  }

  fun getRegOfValue(value: Value): Register {
    println("getRegOfValue: $value -> ${value2Reg[value]}")
    return if (value is ConstantInt) {
      val anotherVirReg = regFactory.newVirReg()
      ASMBuilder.createLiInst(anotherVirReg, Imm(value.value))
      anotherVirReg
    } else {
      value2Reg[value]!!
    }
  }

  override fun visit(topModule: TopModule) {
    module = ASMModule()

    // FIXME: The whole process doen't take care of the global variable
    topModule.globalVar.forEach { it.value.accept(this) }
    topModule.funcList.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    val newGlobalVar = ASMGlobalPointer(globalVar.name!!)
    val type = globalVar.type as PointerType
    module!!.addGlobalVar(newGlobalVar)

    newGlobalVar.addDef(Directive(".type", listOf(newGlobalVar.name, "@object")))
    newGlobalVar.addDef(Directive(".data", listOf()))
    newGlobalVar.addDef(Directive(".global", listOf(newGlobalVar.name)))

    if (type.getAlign() > 1) {
      newGlobalVar.addDef(Directive(".align", listOf("2")))
    }
    val alignment = globalVar.type.getAlign()
    newGlobalVar.addEmit(Directive(".${Utils.align2Identifier(alignment)}", listOf("0")))
    newGlobalVar.addEmit(Directive(".size", listOf(newGlobalVar.name, alignment.toString())))
  }

  override fun visit(func: Func) {
    val asmFunc = ASMFunc(func.name!!)
    ASMBuilder.setCurrentFunc(asmFunc)
    module!!.funcList.add(asmFunc)
    regFactory.position = asmFunc

    func.blockList.forEach { asmFunc.addBlock(ASMBlock(it.name!!, asmFunc)) }
    if (func.returnBlock != null) {
      asmFunc.addBlock(ASMBlock(func.returnBlock!!.name!!, asmFunc))
    }

    ASMBuilder.setInsertBlock(asmFunc.blockList.first())
    for ((index, arg) in func.argList.withIndex()) {
      val virReg = regFactory.newVirReg()
      linkValAndReg(arg, virReg)
      ASMBuilder.createMvInst(regFactory.getPhyReg(index), virReg)
    }

    func.blockList.forEach { it.accept(this) }
    ASMBuilder.setInsertPointBefore(asmFunc.blockList.first().instList.first())
    val spReg = regFactory.getPhyReg("sp")
    ASMBuilder.createArithiInst("addi", spReg, spReg, Imm(-asmFunc.stackAlloca))
    ASMBuilder.setInsertBlock(asmFunc.blockList.last())
    ASMBuilder.createArithiInst("addi", spReg, spReg, Imm(asmFunc.stackAlloca))
    ASMBuilder.createRet()
  }

  override fun visit(block: BasicBlock) {
    val func = ASMBuilder.getCurrentFunc()
    ASMBuilder.setInsertBlock(func.getBlockByPureName(block.name!!)!!)
    block.instList.forEach { it.accept(this) }
  }

  override fun visit(inst: AllocaInst) {
    val virReg = regFactory.newVirReg()
    val func = ASMBuilder.getCurrentFunc()
    linkValAndReg(inst, virReg)
    ASMBuilder.createArithiInst("addi", virReg, PhyReg("sp"), Imm(func.stackAlloca))
    func.stackAlloca += inst.getAllocatedSize()
  }

  override fun visit(inst: CallInst) {
    // there is not a concept of caller save in virtual register
    for ((index, arg) in inst.args.withIndex()) { // TODO: I don't know what to do when too many args
      ASMBuilder.createMvInst(value2Reg[arg]!!, regFactory.getPhyReg(10 + index))
    }
    // TODO: this implementation is buggy, because the builtin func is generated every single time
    ASMBuilder.createCallInst(module!!.getFunc(inst.funcType.funcName) ?: ASMFunc(inst.funcType.funcName))
    if (inst.funcType.result != TypeFactory.getVoidType()) {
      val virReg = regFactory.newVirReg()
      linkValAndReg(inst, virReg)
      ASMBuilder.createMvInst(regFactory.getPhyReg("a0"), virReg)
    }
  }

  override fun visit(inst: LoadInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    ASMBuilder.createLoadInst(inst.type.getAlign(), virReg, Imm(0), value2Reg[inst.addr]!!)
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    val func = ASMBuilder.getCurrentFunc()
    val block = ASMBuilder.getInsertBlock()
    val reg = regFactory.newVirReg()
    linkValAndReg(inst, reg)
    for (pred in inst.preds) {
      val predBlock = func.getBlockByPureName(pred.second.name!!)!!
      ASMBuilder.setInsertBlock(predBlock)
      if (pred.first is ConstantInt) {
        ASMBuilder.createLiInst(reg, Imm((pred.first as ConstantInt).value))
      } else {
        ASMBuilder.createMvInst(value2Reg[pred.first]!!, reg)
      }
    }
    ASMBuilder.setInsertBlock(block)
  }

  override fun visit(inst: BinaryInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    ASMBuilder.createArithInst(
      inst.op,
      virReg,
      getRegOfValue(inst.lhs),
      getRegOfValue(inst.rhs)
    )
  }

  override fun visit(inst: BranchInst) {
    val func = ASMBuilder.getCurrentFunc()
    if (inst.cond == null) { // unconditional br
      ASMBuilder.createJInst(func.getBlockByPureName(inst.trueBlock.name!!)!!)
    } else { // conditional br
      val condReg = value2Reg[inst.cond]!!
      val trueBlock = func.getBlockByPureName(inst.trueBlock.name!!)!!
      val falseBlock = func.getBlockByPureName(inst.falseBlock!!.name!!)!!
      ASMBuilder.createBzInst("bnez", condReg, trueBlock)
      ASMBuilder.createBzInst("beqz", condReg, falseBlock)
    }
  }

  override fun visit(inst: GetElementPtrInst) {
    val index = inst.index
    val elemSize = (inst.ptr.type as PointerType).pointeeTy!!.getAlign()
    val elemSizeReg = regFactory.newVirReg()
    ASMBuilder.createLiInst(elemSizeReg, Imm(elemSize))
    val indexReg = if (index is ConstantInt) {
      val anotherVirReg = regFactory.newVirReg()
      ASMBuilder.createLiInst(anotherVirReg, Imm(index.value))
      anotherVirReg
    } else {
      value2Reg[index]!!
    }
    ASMBuilder.createArithInst("mul", elemSizeReg, elemSizeReg, indexReg)
    ASMBuilder.createArithInst("add", elemSizeReg, value2Reg[inst.ptr]!!, elemSizeReg)
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    ASMBuilder.createLoadInst(elemSize, virReg, Imm(0), elemSizeReg)
  }

  override fun visit(inst: ZExtInst) {
//    ASMBuilder.zext2NewReg()
  }

  override fun visit(inst: TruncInst) {
    // TODO: I need a empty slot on the stack to store the truncated value
  }

  override fun visit(inst: StoreInst) {
    ASMBuilder.createStoreInst(inst.type.getAlign(), getRegOfValue(inst.value), Imm(0), getRegOfValue(inst.addr))
  }

  override fun visit(inst: CmpInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)

    val lhsReg = getRegOfValue(inst.lhs)
    val rhsReg = getRegOfValue(inst.rhs)
    ASMBuilder.createCmpInst(inst.cond.toString(), virReg, lhsReg, rhsReg)
  }

  override fun visit(inst: ReturnInst) {
    if (inst.retVal != null) {
      val retValReg = getRegOfValue(inst.retVal)
      ASMBuilder.createMvInst(retValReg, regFactory.getPhyReg("a0"))
    }
    val stackAlloca = ASMBuilder.getCurrentFunc().stackAlloca
    ASMBuilder.createArithiInst("addi", PhyReg("sp"), PhyReg("sp"), Imm(stackAlloca))
    ASMBuilder.createRet()
  }
}