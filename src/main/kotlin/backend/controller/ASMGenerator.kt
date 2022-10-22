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
  private var value2Offset = hashMapOf<Value, Int>()
  private var block2ExtraPhiInstList =
    hashMapOf<ASMBlock, MutableList<Pair<Register, Value>>>() // this mapping is only for phi inst

  fun linkValAndReg(value: Value, reg: Register) {
    value2Reg[value] = reg
    reg2Value[reg] = value
  }

  fun linkValAndOffset(value: Value, offset: Int) {
    value2Offset[value] = offset
  }

  fun getRegOfValue(value: Value): Register? {
    return if (value is ConstantInt) {
      val virReg = regFactory.newVirReg()
      ASMBuilder.createLiInst(virReg, Literal(value.value))
      virReg
    } else if (value is ConstantNull) {
      val virReg = regFactory.newVirReg()
      ASMBuilder.createLiInst(
        virReg,
        Literal(0)
      ) // FIXME: I don't know whether it is correct, for convenience, I use 0 to represent null
      virReg
    } else if (value is GlobalVariable || value is ConstantStr) {
      val virReg = regFactory.newVirReg()
      ASMBuilder.createLaInst(virReg, module!!.getGlobalPtr(value.name!!))
      virReg
    } else {
      value2Reg[value]
    }
  }

  fun getOffsetOfValue(value: Value): Int? {
    return value2Offset[value]
  }

  override fun visit(topModule: TopModule) {
    module = ASMModule()

    // FIXME: The whole process doesn't take care of the global variable
    topModule.globalVar.forEach { it.value.accept(this) }
    topModule.constStr.forEach { it.value.accept(this) }
    topModule.funcList.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    val nameWithAddr = globalVar.name!!
    val newGlobalVar = ASMGlobalPointer(nameWithAddr.split('.').first())
    module!!.addGlobalPtr(nameWithAddr, newGlobalVar)

    newGlobalVar.addDef(Directive(".type", listOf(newGlobalVar.name, "@object")))
    newGlobalVar.addDef(Directive(".data", listOf()))
    newGlobalVar.addDef(Directive(".global", listOf(newGlobalVar.name)))

    val alignment = globalVar.type.getAlign()
    newGlobalVar.addEmit(Directive(".${Utils.align2Identifier(alignment)}", listOf("0")))
    newGlobalVar.addEmit(Directive(".size", listOf(newGlobalVar.name, alignment.toString())))
  }

  override fun visit(constStr: ConstantStr) {
    val newGlobalVar = ASMGlobalPointer(constStr.name!!)
    module!!.addGlobalPtr(constStr.name!!, newGlobalVar)

    newGlobalVar.addDef(Directive(".type", listOf(newGlobalVar.name, "@object")))
    newGlobalVar.addDef(Directive(".rodata", listOf()))

    val alignment = constStr.type.getAlign()
    newGlobalVar.addEmit(Directive(".asciz", listOf("\"${constStr.str}\"")))
    newGlobalVar.addEmit(Directive(".size", listOf(newGlobalVar.name, constStr.str.length.toString())))
  }


  override fun visit(func: Func) {
    val asmFunc = ASMFunc(func.name!!)
    ASMBuilder.setCurrentFunc(asmFunc)
    module!!.funcList.add(asmFunc)
    regFactory.position = asmFunc
    value2Offset = hashMapOf()

    if (func.returnBlock != null) {
      func.blockList.add(func.returnBlock!!)
//      asmFunc.addBlock(ASMBlock(func.returnBlock!!.name!!, asmFunc))
    }
    func.blockList.forEach { asmFunc.addBlock(ASMBlock(it.name!!, asmFunc)) }

    ASMBuilder.setInsertBlock(asmFunc.blockList.first())
    for ((index, arg) in func.argList.withIndex()) {
      val virReg = regFactory.newVirReg()
      linkValAndReg(arg, virReg)
      ASMBuilder.createMvInst(virReg, regFactory.getPhyReg(index + 10))
    }

    func.blockList.forEach { it.accept(this) }
    ASMBuilder.setInsertPointBefore(asmFunc.blockList.first().instList.first())
    val spReg = regFactory.getPhyReg("sp")
    ASMBuilder.createArithiInst("addi", spReg, spReg, Literal(-asmFunc.stackAlloca))
    ASMBuilder.setInsertBlock(asmFunc.blockList.last())
//    ASMBuilder.createArithiInst("addi", spReg, spReg, Literal(asmFunc.stackAlloca))
//    ASMBuilder.createRet()
  }

  override fun visit(block: BasicBlock) {
    val func = ASMBuilder.getCurrentFunc()
    val asmBlock = func.getBlockByPureName(block.name!!)!!
    ASMBuilder.setInsertBlock(asmBlock)
    block.instList.forEach { it.accept(this) }
    if (block2ExtraPhiInstList.containsKey(asmBlock)) {
      ASMBuilder.setInsertPointBefore(asmBlock.firstBrInstOrNull()!!)
      block2ExtraPhiInstList[asmBlock]!!.forEach {
        if (it.second is ConstantInt) {
          ASMBuilder.createLiInst(it.first, Literal((it.second as ConstantInt).value))
        } else {
          ASMBuilder.createMvInst(it.first, value2Reg[it.second]!!)
        }
      }
      block2ExtraPhiInstList.remove(asmBlock)
    }
  }

  override fun visit(inst: AllocaInst) {
//    val virReg = regFactory.newVirReg()
    val func = ASMBuilder.getCurrentFunc()
//    linkValAndReg(inst, virReg)
//    ASMBuilder.createArithiInst("addi", virReg, PhyReg("sp"), Literal(func.stackAlloca))
    linkValAndOffset(inst, func.stackAlloca)
    func.stackAlloca += inst.getAllocatedSize()
  }

  override fun visit(inst: CallInst) {
    // there is not a concept of caller save in virtual register
    for ((index, arg) in inst.args.withIndex()) { // TODO: I don't know what to do when too many args
      ASMBuilder.createMvInst(regFactory.getPhyReg(10 + index), getRegOfValue(arg)!!, "mv args")
    }

    // however, we should preserve ra in this stage
    val raVirReg = regFactory.newVirReg()
    ASMBuilder.createMvInst(raVirReg, PhyReg("ra"))

    // TODO: this implementation is buggy, because the builtin func is generated every single time
    ASMBuilder.createCallInst(module!!.getFunc(inst.funcType.funcName) ?: ASMFunc(inst.funcType.funcName))
    if (inst.funcType.result != TypeFactory.getVoidType()) {
      val virReg = regFactory.newVirReg()
      linkValAndReg(inst, virReg)
      ASMBuilder.createMvInst(virReg, regFactory.getPhyReg("a0"), "mv result")
    }

    // move ra back
    ASMBuilder.createMvInst(PhyReg("ra"), raVirReg)
  }

  override fun visit(inst: LoadInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    if (getOffsetOfValue(inst.addr) != null) {
      ASMBuilder.createLoadInst(
        inst.type.getAlign(),
        virReg,
        Literal(getOffsetOfValue(inst.addr)!!),
        regFactory.getPhyReg("sp"),
        "load"
      )
    } else {
      ASMBuilder.createLoadInst(
        inst.type.getAlign(),
        virReg,
        Literal(0),
        getRegOfValue(inst.addr)!!,
        "load"
      )
    }
  }

  override fun visit(inst: BitCastInst) {
    // I think it's useless
    val virReg = regFactory.newVirReg()
    ASMBuilder.createMvInst(virReg, getRegOfValue(inst.castee)!!)
    linkValAndReg(inst, virReg)
//    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    val func = ASMBuilder.getCurrentFunc()
    val block = ASMBuilder.getInsertBlock()
    val reg = regFactory.newVirReg()
    linkValAndReg(inst, reg)
    for (pred in inst.preds) {
      val predBlock = func.getBlockByPureName(pred.second.name!!)!!
      val insertPoint = predBlock.firstBrInstOrNull()
      if (insertPoint != null) {
        ASMBuilder.setInsertPointBefore(insertPoint)
        if (pred.first is ConstantInt) {
          ASMBuilder.createLiInst(reg, Literal((pred.first as ConstantInt).value))
        } else {
          ASMBuilder.createMvInst(reg, value2Reg[pred.first]!!)
        }
      } else {
        // due to the insert order, the pred block may not have been visited
        // therefore it's necessary to maintain a list to solve it
        val extraPhiInstList = block2ExtraPhiInstList.getOrDefault(predBlock, mutableListOf())
        extraPhiInstList.add(Pair(reg, pred.first))
        block2ExtraPhiInstList[predBlock] = extraPhiInstList
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
      getRegOfValue(inst.lhs)!!,
      getRegOfValue(inst.rhs)!!
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
    val shiftReg = regFactory.newVirReg()
    linkValAndReg(inst, shiftReg)
    if (inst.op == "ptr") {
      val elemSize = (inst.ptr.type as PointerType).pointeeTy!!.getAlign() // TODO: I don't know if it's right
      val elemSizeReg = regFactory.newVirReg()
      val indexReg = getRegOfValue(inst.index)!!
      ASMBuilder.createLiInst(elemSizeReg, Literal(elemSize))
      ASMBuilder.createArithInst("mul", shiftReg, elemSizeReg, indexReg)
      ASMBuilder.createArithInst("add", shiftReg, getRegOfValue(inst.ptr)!!, shiftReg)
    } else if (inst.op == "array") {
      val elemSize = ((inst.ptr.type as PointerType).pointeeTy as ArrayType).containedType.getAlign()
      val elemSizeReg = regFactory.newVirReg()
      val offsetReg = getRegOfValue(inst.offset!!)!!
      ASMBuilder.createLiInst(elemSizeReg, Literal(elemSize))
      ASMBuilder.createArithInst("mul", shiftReg, elemSizeReg, offsetReg)
      ASMBuilder.createArithInst("add", shiftReg, getRegOfValue(inst.ptr)!!, shiftReg)
    } else { // it must be struct
      val offset = inst.offset!!.value
      val structType = (inst.ptr.type as PointerType).pointeeTy as StructType
      val elemSizeSum = structType.symbolList.take(offset).sumOf { it.second.getAlign() }
      val elemSizeSumReg = regFactory.newVirReg()
      ASMBuilder.createLiInst(elemSizeSumReg, Literal(elemSizeSum))
      ASMBuilder.createArithInst("add", shiftReg, getRegOfValue(inst.ptr)!!, elemSizeSumReg)
    }
  }

  override fun visit(inst: ZExtInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    ASMBuilder.createMvInst(virReg, getRegOfValue(inst.originalVal)!!)
//    ASMBuilder.zext2NewReg()
  }

  override fun visit(inst: TruncInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    ASMBuilder.createMvInst(virReg, getRegOfValue(inst.originalVal)!!)
//     TODO: I need a empty slot on the stack to store the truncated value
  }

  override fun visit(inst: StoreInst) {
    if (getOffsetOfValue(inst.addr) != null) {
      ASMBuilder.createStoreInst(
        inst.value.type.getAlign(),
        getRegOfValue(inst.value)!!,
        Literal(getOffsetOfValue(inst.addr)!!),
        regFactory.getPhyReg("sp"),
      )
    } else {
      ASMBuilder.createStoreInst(
        inst.value.type.getAlign(),
        getRegOfValue(inst.value)!!,
        Literal(0),
        getRegOfValue(inst.addr)!!,
      )
    }
  }

  override fun visit(inst: CmpInst) {
    val virReg = regFactory.newVirReg()
    linkValAndReg(inst, virReg)
    val lhsReg = getRegOfValue(inst.lhs)!!
    val rhsReg = getRegOfValue(inst.rhs)!!
    when (inst.cond.name) {
      "slt" -> {
        ASMBuilder.createCmpInst("slt", virReg, lhsReg, rhsReg)
      }

      "sle" -> {
        val relayVirReg = regFactory.newVirReg()
        ASMBuilder.createCmpInst("slt", relayVirReg, rhsReg, lhsReg)
        ASMBuilder.createArithiInst("xori", virReg, relayVirReg, Literal(1))
      }

      "sgt" -> {
        ASMBuilder.createCmpInst("slt", virReg, rhsReg, lhsReg)
      }

      "sge" -> {
        val relayVirReg = regFactory.newVirReg()
        ASMBuilder.createCmpInst("slt", relayVirReg, lhsReg, rhsReg)
        ASMBuilder.createArithiInst("xori", virReg, relayVirReg, Literal(1))
      }

      "eq" -> {
        val relayVirReg = regFactory.newVirReg()
        ASMBuilder.createArithInst("xor", relayVirReg, lhsReg, rhsReg)
        ASMBuilder.createCmpzInst("seqz", virReg, relayVirReg)
      }

      "ne" -> {
        val relayVirReg = regFactory.newVirReg()
        ASMBuilder.createArithInst("xor", relayVirReg, lhsReg, rhsReg)
        ASMBuilder.createCmpzInst("snez", virReg, relayVirReg)
      }
    }
  }

  override fun visit(inst: ReturnInst) {
    if (inst.retVal != null) {
      ASMBuilder.createMvInst(regFactory.getPhyReg("a0"), getRegOfValue(inst.retVal)!!)
    }
    val stackAlloca = ASMBuilder.getCurrentFunc().stackAlloca
    ASMBuilder.createArithiInst("addi", PhyReg("sp"), PhyReg("sp"), Literal(stackAlloca))
    ASMBuilder.createRet()
  }
}
