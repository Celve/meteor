package backend.controller

import backend.basic.*
import backend.helper.RegFactory
import backend.helper.Utils
import middleend.basic.*
import middleend.pass.IRVisitor

/**
 * ASMGenerator generate assembly code from IR.
 * In this stage, there is no an idea called callee-save or caller-save, because all registers in used are virtual registers.
 * This convention only takes effect when we actually use all kinds of the physical register.
 */
class ASMGenerator : IRVisitor() {
  lateinit var module: ASMModule

  // map llvm value to virtual register, or in some cases, physical register
  private val value2Reg = hashMapOf<String, Register>() // this mapping is only for virtual register

  // indicate whether it's on stack
  private val onStack = hashSetOf<Value>()

  // connect an offset to a value, including offset to sp
  private val value2Offset = mutableMapOf<Value, Immediate>().withDefault { DeterminedImmediate(0) }

  private val block2ExtraPhiInstList =
    hashMapOf<ASMBlock, MutableList<Pair<Register, Value>>>() // this mapping is only for phi inst
  private var allocaInstNum = 0

  private val args2Gep = hashMapOf<Pair<Value, Value>, Value>()

  /**
   * @param reg should be a virtual register
   */
  private fun linkValAndReg(value: Value, reg: Register) {
    value2Reg[value.name!!] = reg
  }

  /**
   * @param offset is the offset from the stack pointer, representing a allocated space on stack
   */
  private fun linkValAndOffset(value: Value, offset: Immediate) {
    value2Offset[value] = offset
  }

  /**
   * This functions take all possible situations with value into consideration
   * It could deal with ConstantInt, ConstantNull, GlobalVariable, ConstantStr, and Instruction
   * For Instruction, it has to be maintained in definition beforehand.
   */
  private fun getRegOfValue(value: Value): Register? {
    return if (value is ConstantInt) {
      if (value.value == 0) {
        RegFactory.getPhyReg(0)
      } else {
        val virReg = RegFactory.newVirReg()
        ASMBuilder.createLiInst(virReg, DeterminedImmediate(value.value))
        virReg
      }
    } else if (value is ConstantNull) {
      val virReg = RegFactory.newVirReg()
      ASMBuilder.createLiInst(
        virReg,
        DeterminedImmediate(0)
      ) // FIXME: I don't know whether it is correct, for convenience, I use 0 to represent null
      virReg
    } else if (value is GlobalVariable || value is ConstantStr) {
      val virReg = RegFactory.newVirReg()
      ASMBuilder.createLaInst(virReg, module.getGlobalPtr(value.name!!))
      virReg
    } else {
      value2Reg[value.name!!]
    }
  }

  fun getRegOfInst(inst: Instruction): Register {
    return if (value2Reg.contains(inst.name!!)) {
      value2Reg[inst.name!!]!!
    } else {
      val virReg = RegFactory.newVirReg()
      linkValAndReg(inst, virReg)
      virReg
    }
  }

  private fun getOffsetOfValue(value: Value): Immediate {
    return value2Offset.getValue(value)
  }

  override fun visit(topModule: TopModule) {
    module = ASMModule()

    // FIXME: The whole process doesn't take care of the global variable
    topModule.globalVarMap.forEach { it.value.accept(this) }
    topModule.constStrMap.forEach { it.value.accept(this) }
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    val nameWithAddr = globalVar.name!!
    val newGlobalVar = ASMGlobalPointer(nameWithAddr.split('.').first())
    module.addGlobalPtr(nameWithAddr, newGlobalVar)

    newGlobalVar.addDef(Directive(".type", listOf(newGlobalVar.name!!, "@object")))
    newGlobalVar.addDef(Directive(".data", listOf()))
    newGlobalVar.addDef(Directive(".global", listOf(newGlobalVar.name)))

    val alignment = globalVar.type.getAlign()
    newGlobalVar.addEmit(Directive(".${Utils.align2Identifier(alignment)}", listOf("0")))
    newGlobalVar.addEmit(Directive(".size", listOf(newGlobalVar.name, alignment.toString())))
  }

  override fun visit(constStr: ConstantStr) {
    val newGlobalVar = ASMGlobalPointer(constStr.name!!)
    module.addGlobalPtr(constStr.name!!, newGlobalVar)

    newGlobalVar.addDef(Directive(".type", listOf(newGlobalVar.name!!, "@object")))
    newGlobalVar.addDef(Directive(".rodata", listOf()))

    newGlobalVar.addEmit(Directive(".asciz", listOf("\"${constStr.str}\"")))
    newGlobalVar.addEmit(Directive(".size", listOf(newGlobalVar.name, constStr.str.length.toString())))
  }

  override fun visit(func: Func) {
    val asmFunc = ASMFunc(func.name!!)
    asmFunc.argsNum = func.argList.size
    ASMBuilder.setCurrentFunc(asmFunc)
    module.funcList.add(asmFunc)
    RegFactory.position = asmFunc
    value2Offset.clear()

    func.blockList.forEach { asmFunc.addBlock(ASMBlock(it.name!!, asmFunc, it.execFreq)) }

    // move args to a0, a1, ...
    ASMBuilder.setInsertBlock(asmFunc.blockList.first())
    for ((index, arg) in func.argList.withIndex()) {
      val virReg = RegFactory.newVirReg()
      linkValAndReg(arg, virReg)
      if (index <= 7) { // only first 8 args are passed by registers
        ASMBuilder.createMvInst(virReg, RegFactory.getPhyReg(index + 10))
      } else { // the others are passed by stack
        val offset = asmFunc.stackFrame.newAllocaSpace() + (index - 8) * 4
        ASMBuilder.createLoadInst(arg.type.getAlign(), virReg, offset, RegFactory.getPhyReg("sp"))

      }
    }

    func.blockList.forEach { it.accept(this) }

    // preserve the stack pointer
    ASMBuilder.setInsertPointBefore(asmFunc.blockList.first().instList.first())
    val spReg = RegFactory.getPhyReg("sp")
    ASMBuilder.createArithiInst("add", spReg, spReg, -asmFunc.stackFrame.newAllocaSpace())
    ASMBuilder.setInsertBlock(asmFunc.blockList.last())
  }

  override fun visit(block: BasicBlock) {
    args2Gep.clear()
    val func = ASMBuilder.getCurrentFunc()
    val asmBlock = func.getBlockByPureName(block.name!!)!!
    ASMBuilder.setInsertBlock(asmBlock)
    block.instList.forEach { it.accept(this) }

    // these are for blocks that are defined after the presence of phi inst
    // when the block has finished, the extra work for phi inst should be done before branch inst
    if (block2ExtraPhiInstList.containsKey(asmBlock)) {
      ASMBuilder.setInsertPointBefore(asmBlock.firstBrInstOrNull()!!)
      block2ExtraPhiInstList[asmBlock]!!.forEach {
        if (it.second is ConstantInt) {
          ASMBuilder.createLiInst(it.first, DeterminedImmediate((it.second as ConstantInt).value))
        } else {
          ASMBuilder.createMvInst(it.first, value2Reg[it.second.name!!]!!)
        }
      }
      block2ExtraPhiInstList.remove(asmBlock)
    }
  }

  override fun visit(inst: AllocaInst) {
    val func = ASMBuilder.getCurrentFunc()
    onStack.add(inst)
    if (++allocaInstNum <= func.argsNum) {
      if (allocaInstNum <= 8) {
        linkValAndOffset(inst, func.stackFrame.newCallerArgument())
      } else {
        // this might lead to an assignment to address in another function's stack frame
        linkValAndOffset(inst, func.stackFrame.newAllocaSpace() + (allocaInstNum - 9) * 4)
      }
    } else {
      linkValAndOffset(inst, func.stackFrame.newLocalVariable())
    }
//    func.stackAlloca += inst.getAllocatedSize()
//    func.stackAlloca += 4
  }

  override fun visit(inst: CallInst) {
    // there is not a concept of caller save in virtual register
    val func = ASMBuilder.getCurrentFunc()
    for ((index, arg) in inst.getArgList().withIndex()) { // TODO: I don't know what to do when too many args
      if (index <= 7) {
        ASMBuilder.createMvInst(RegFactory.getPhyReg(10 + index), getRegOfValue(arg)!!)
      } else {
        ASMBuilder.createStoreInst(
          arg.type.getAlign(),
          getRegOfValue(arg)!!,
          func.stackFrame.getCalleeArgument(index - 8),
          RegFactory.getPhyReg("sp")
        )
      }
    }

    // however, we should preserve ra in this stage
//    val raVirReg = RegFactory.newVirReg()
//    ASMBuilder.createMvInst(raVirReg, PhyReg("ra"))

    // TODO: this implementation is buggy or costy, because the builtin func is generated every single time
    ASMBuilder.createCallInst(
      module.getFunc(inst.getCallee().funcType.funcName) ?: ASMFunc(inst.getCallee().funcType.funcName)
    )
    if (inst.getCallee().funcType.resultType != TypeFactory.getVoidType()) {
      val virReg = RegFactory.newVirReg()
      linkValAndReg(inst, virReg)
      ASMBuilder.createMvInst(virReg, RegFactory.getPhyReg("a0"))
    }

    // move ra back
//    ASMBuilder.createMvInst(PhyReg("ra"), raVirReg)
  }

  override fun visit(inst: LoadInst) {
    val addr = inst.getAddr()
    val virReg = getRegOfInst(inst)
    if (onStack.contains(addr)) {
      // for values that are allocated on stack
      ASMBuilder.createLoadInst(
        inst.type.getAlign(),
        virReg,
        getOffsetOfValue(addr),
        RegFactory.getPhyReg("sp")
      )
    } else if (true) { // addr !is GlobalVariable && addr !is ConstantStr
      // for values whose location is unknown
      ASMBuilder.createLoadInst(
        inst.type.getAlign(),
        virReg,
        getOffsetOfValue(addr), // gep inst might depend on this to add some offset
        getRegOfValue(addr)!!
      )
    } else {
      val gloPtr = module.getGlobalPtr(addr.name!!)
      val newVirReg = RegFactory.newVirReg()
      ASMBuilder.createLuiInst(newVirReg, RelocationHi(gloPtr))
      ASMBuilder.createLoadInst(inst.type.getAlign(), virReg, RelocationLo(gloPtr), newVirReg)
    }
  }

  override fun visit(inst: BitCastInst) {
    val virReg = getRegOfInst(inst)
    ASMBuilder.createMvInst(virReg, getRegOfValue(inst.getCastee())!!)
  }

  override fun visit(inst: PhiInst) {
    throw Exception("Phi-instruction should not occur")
  }

  override fun visit(inst: BinaryInst) {
    val virReg = getRegOfInst(inst)
    val lhs = inst.getLhs()
    val rhs = inst.getRhs()

    // rs, rt = reg, imm or rs, rt = reg, reg or rs, rt = imm, imm
    if (lhs is ConstantInt && rhs is ConstantInt) {
      ASMBuilder.createLiInst(virReg, DeterminedImmediate(Utils.calculate(inst.op, lhs.value, rhs.value)))
    } else if (rhs is ConstantInt) {
      ASMBuilder.createArithiInst(
        Utils.convertArith(inst.op), virReg, getRegOfValue(lhs)!!, DeterminedImmediate(rhs.value)
      )
    } else if (lhs is ConstantInt && (inst.op == "add" || inst.op == "mul")) {
      when (inst.op) {
        "add", "mul" -> ASMBuilder.createArithiInst(
          Utils.convertArith(inst.op), virReg, getRegOfValue(rhs)!!, DeterminedImmediate(lhs.value)
        )
      }
    } else {
      ASMBuilder.createArithInst(
        Utils.convertArith(inst.op),
        virReg,
        getRegOfValue(lhs)!!,
        getRegOfValue(rhs)!!
      )
    }
  }

  override fun visit(inst: BranchInst) {
    val func = ASMBuilder.getCurrentFunc()
    val cond = inst.getCond()
    if (cond == null) { // unconditional br
      ASMBuilder.createJInst(func.getBlockByPureName(inst.getTrueBlock().name!!)!!)
    } else if (cond.userList.size == 1 && cond is CmpInst) {
      ASMBuilder.createBrInst(
        Utils.convertBr(cond.cond),
        getRegOfValue(cond.getLhs())!!,
        getRegOfValue(cond.getRhs())!!,
        func.getBlockByPureName(inst.getTrueBlock().name!!)!!,
      )
      ASMBuilder.createJInst(func.getBlockByPureName(inst.getFalseBlock()!!.name!!)!!)
    } else { // conditional br
      val condReg = getRegOfValue(inst.getCond()!!)!!
      val trueBlock = func.getBlockByPureName(inst.getTrueBlock().name!!)!!
      val falseBlock = func.getBlockByPureName(inst.getFalseBlock()!!.name!!)!!
      ASMBuilder.createBzInst("bnez", condReg, trueBlock)
      ASMBuilder.createBzInst("beqz", condReg, falseBlock)
    }
  }

  private fun getIncrement(inst: Value): Pair<Value, ConstantInt>? {
    if (inst is BinaryInst && inst.useeList.any { it is ConstantInt } && inst.useeList.any { it !is ConstantInt } && (inst.op == "add" || inst.op == "sub")) {
      val base = inst.useeList.first { it !is ConstantInt }
      val const = inst.useeList.first { it is ConstantInt } as ConstantInt
      val sign = if (inst.op == "add") 1 else -1
      return base to const * sign
    }
    return null
  }

  private fun isAllAddr(inst: GetElementPtrInst): Boolean {
    return inst.userList.all {
      if (it is LoadInst) it.getAddr() == inst else if (it is StoreInst) it.getAddr() == inst else false
    }
  }

  override fun visit(inst: GetElementPtrInst) {
    val shiftReg = getRegOfInst(inst)
    when (inst.op) {
      "ptr" -> { // it's a pointer to an array
        val elemSize = (inst.ptrType as PointerType).pointeeTy!!.getAlign() // TODO: I don't know if it's right
        val indexReg = getRegOfValue(inst.getOffset())!!

        val inc = getIncrement(inst.getOffset())
        if (isAllAddr(inst) && inc != null && args2Gep.containsKey(inst.getPtr() to inc.first)) {
          val lastGep = args2Gep[inst.getPtr() to inc.first]!!
          ASMBuilder.createMvInst(shiftReg, getRegOfValue(lastGep)!!)
          val originalOffset = getOffsetOfValue(lastGep) as DeterminedImmediate
          linkValAndOffset(inst, DeterminedImmediate(elemSize * inc.second.value) + originalOffset)
        } else {
          ASMBuilder.createArithiInst("mul", shiftReg, indexReg, DeterminedImmediate(elemSize))
          ASMBuilder.createArithInst("add", shiftReg, getRegOfValue(inst.getPtr())!!, shiftReg)
        }
        args2Gep[inst.getPtr() to inst.getOffset()] = inst
      }

      "array" -> {
        val elemSize = ((inst.ptrType as PointerType).pointeeTy as ArrayType).containedType.getAlign()
        val offsetReg = getRegOfValue(inst.getIndex()!!)!!
        ASMBuilder.createArithiInst("mul", shiftReg, offsetReg, DeterminedImmediate(elemSize))
        ASMBuilder.createArithInst("add", shiftReg, getRegOfValue(inst.getPtr())!!, shiftReg)
      }

      else -> { // it must be struct
        // the calculation of offset of struct is to add them up
        val offset = inst.getIndex()!!.value
        val structType = (inst.ptrType as PointerType).pointeeTy as StructType
        val elemSizeSum = structType.symbolList.take(offset).sumOf { it.second.getAlign() }
//        ASMBuilder.createArithiInst("add", shiftReg, getRegOfValue(inst.getPtr())!!, DeterminedImmediate(elemSizeSum))
        ASMBuilder.createMvInst(shiftReg, getRegOfValue(inst.getPtr())!!)
        linkValAndOffset(inst, DeterminedImmediate(elemSizeSum))
      }
    }
  }

  override fun visit(inst: ZExtInst) {
    // it's useless too, at least in Mx*
    val virReg = getRegOfInst(inst)
    ASMBuilder.createMvInst(virReg, getRegOfValue(inst.getOriginalVal())!!)
  }

  override fun visit(inst: TruncInst) {
    // it's useless too, at least in Mx*
    val virReg = getRegOfInst(inst)
    ASMBuilder.createMvInst(virReg, getRegOfValue(inst.getOriginalVal())!!)
  }

  override fun visit(inst: StoreInst) {
    val addr = inst.getAddr()
    if (onStack.contains(inst)) {
      // for values that are allocated on stack
      ASMBuilder.createStoreInst(
        inst.getValue().type.getAlign(),
        getRegOfValue(inst.getValue())!!,
        getOffsetOfValue(addr),
        RegFactory.getPhyReg("sp"),
      )
    } else if (true) { // addr !is GlobalVariable && addr !is Constant
      // for values whose location is unknown
      ASMBuilder.createStoreInst(
        inst.getValue().type.getAlign(),
        getRegOfValue(inst.getValue())!!,
        getOffsetOfValue(addr),
        getRegOfValue(addr)!!,
      )
    } else {
      val gloPtr = module.getGlobalPtr(addr.name!!)
      val virReg = RegFactory.newVirReg()
      ASMBuilder.createLuiInst(virReg, RelocationHi(gloPtr))
      ASMBuilder.createStoreInst(
        inst.getValue().type.getAlign(),
        getRegOfValue(inst.getValue())!!,
        RelocationLo(gloPtr),
        virReg
      )
    }
  }

  override fun visit(inst: CmpInst) {
    if (inst.userList.size == 1 && inst.userList.first() is BranchInst) {
      // it's a cmp in branch
      return
    }
    val virReg = getRegOfInst(inst)
    val (cond, lhs, rhs) = if (inst.getLhs() is ConstantInt) {
      Triple(Utils.revCond(inst.cond), inst.getRhs(), inst.getLhs())
    } else {
      Triple(inst.cond, inst.getLhs(), inst.getRhs())
    }

    // due to the limitation of RISC-V, implementations of some comparisons should be done with help of others
    if (lhs is ConstantInt && rhs is ConstantInt) {
      throw Exception("Cmp instruction with two constants")
    } else if (rhs is ConstantInt) {
      val lhsReg = getRegOfValue(lhs)!!
      when (cond) {
        "slt" -> {
          ASMBuilder.createCmpiInst("slt", virReg, lhsReg, DeterminedImmediate(rhs.value))
        }

        "sle" -> {
          ASMBuilder.createCmpiInst("slt", virReg, lhsReg, DeterminedImmediate(rhs.value + 1))
        }

        "sgt" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createCmpiInst("slt", relayVirReg, lhsReg, DeterminedImmediate(rhs.value + 1))
          ASMBuilder.createArithiInst("xor", virReg, relayVirReg, DeterminedImmediate(1))
        }

        "sge" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createCmpiInst("slt", relayVirReg, lhsReg, DeterminedImmediate(rhs.value))
          ASMBuilder.createArithiInst("xor", virReg, relayVirReg, DeterminedImmediate(1))
        }

        "eq" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createArithiInst("add", relayVirReg, lhsReg, DeterminedImmediate(-rhs.value))
          ASMBuilder.createCmpzInst("seqz", virReg, relayVirReg)
        }


        "ne" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createArithiInst("add", relayVirReg, lhsReg, DeterminedImmediate(-rhs.value))
          ASMBuilder.createCmpzInst("snez", virReg, relayVirReg)
        }
      }
    } else {
      val lhsReg = getRegOfValue(lhs)!!
      val rhsReg = getRegOfValue(rhs)!!
      when (cond) {
        "slt" -> {
          ASMBuilder.createCmpInst("slt", virReg, lhsReg, rhsReg)
        }

        "sle" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createCmpInst("slt", relayVirReg, rhsReg, lhsReg)
          ASMBuilder.createArithiInst("xor", virReg, relayVirReg, DeterminedImmediate(1))
        }

        "sgt" -> {
          ASMBuilder.createCmpInst("slt", virReg, rhsReg, lhsReg)
        }

        "sge" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createCmpInst("slt", relayVirReg, lhsReg, rhsReg)
          ASMBuilder.createArithiInst("xor", virReg, relayVirReg, DeterminedImmediate(1))
        }

        "eq" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createArithInst("xor", relayVirReg, lhsReg, rhsReg)
          ASMBuilder.createCmpzInst("seqz", virReg, relayVirReg)
        }

        "ne" -> {
          val relayVirReg = RegFactory.newVirReg()
          ASMBuilder.createArithInst("xor", relayVirReg, lhsReg, rhsReg)
          ASMBuilder.createCmpzInst("snez", virReg, relayVirReg)
        }
      }
    }
  }

  override fun visit(inst: ReturnInst) {
    if (inst.getRetVal() != null) {
      ASMBuilder.createMvInst(RegFactory.getPhyReg("a0"), getRegOfValue(inst.getRetVal()!!)!!)
    }
    val func = ASMBuilder.getCurrentFunc()
    ASMBuilder.createArithiInst("add", PhyReg("sp"), PhyReg("sp"), func.stackFrame.newAllocaSpace())
    ASMBuilder.createRet()
  }

  override fun visit(inst: PCopyInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: MvInst) {
    val virReg = getRegOfInst(inst)
    if (inst.getSrc() is ConstantInt) {
      ASMBuilder.createLiInst(virReg, DeterminedImmediate((inst.getSrc() as ConstantInt).value))
    } else {
      ASMBuilder.createMvInst(virReg, getRegOfValue(inst.getSrc())!!)
      linkValAndOffset(inst, getOffsetOfValue(inst.getSrc()))
    }
  }
}
