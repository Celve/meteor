package backend.controller

import backend.basic.*
import middleend.basic.*

object ASMBuilder {
  var func: ASMFunc? = null
  var block: ASMBlock? = null
  var point: ASMInst? = null
  var stackAlloca = 0
  private var value2Reg = hashMapOf<Value, Register>() // this mapping is only for virtual register
  private var reg2Value = hashMapOf<Register, Value>() // this mapping is only for virtual register
  var virRegId = 0

  fun newVirReg(value: Value? = null): VirReg {
    val virReg = VirReg(virRegId++)
    func!!.usedVirRegNum++
    if (value != null) {
      println("newVirReg: $value -> $virReg")
      linkValAndReg(value, virReg)
    }
    return virReg
  }

  fun linkValAndReg(value: Value, reg: Register) {
    value2Reg[value] = reg
    reg2Value[reg] = value
  }

  fun setCurrentFunc(newFunc: ASMFunc) {
    func = newFunc
    value2Reg = func!!.value2Reg
    reg2Value = func!!.reg2Value
    stackAlloca = 0
  }

  fun getCurrentFunc(): ASMFunc {
    return func!!
  }

  fun setInsertBlock(newBlock: ASMBlock) {
    block = newBlock
    point = null
  }

  fun getInsertBlock(): ASMBlock {
    return block!!
  }

  fun setInsertPointBefore(newInst: ASMInst) {
    point = newInst
    block = newInst.parent
    func = block!!.parent
  }

  fun insertInstBeforeInsertPoint(inst: ASMInst) {
    if (point == null) {
      inst.insertAtTheTailOf(block!!)
    } else {
      val index = point!!.getIndexAtBlock()
      inst.insertAtIndexOf(block!!, index)
    }
  }

  fun getInsertPoint(): ASMInst? {
    return point
  }

  fun call(func: ASMFunc) {
    val callInst = ASMCallInst(func)
    callInst.insertAtTheTailOf(block!!)
  }

  fun alloca(size: Int, dst: Value) {
    val virReg = newVirReg(dst)
    val tempReg = newVirReg()
    val arithiInst = ASMArithiInst("addi", PhyReg("sp"), Imm(stackAlloca), tempReg)
    insertInstBeforeInsertPoint(arithiInst)
    val mvInst = ASMMvInst(tempReg, virReg)
    insertInstBeforeInsertPoint(mvInst)

    stackAlloca += size
  }

  fun mvVal2NewReg(src: Value) {
    newVirReg(src)
  }

  fun mvReg2NewReg(src: Register) {
    newVirReg(reg2Value[src]!!)
  }

  fun mvVal2OldReg(src: Value, dst: Register) {
    value2Reg.remove(reg2Value[dst]!!)
    linkValAndReg(src, dst)
  }

  fun load2NewReg(src: Value, dst: Value) {
    // TODO: create load inst with both src and dst
    val virReg = newVirReg(dst)
    val loadInst = ASMLoadInst(dst.type.getAlign(), value2Reg[src]!!, virReg, Imm(0))
    insertInstBeforeInsertPoint(loadInst)
  }

  fun arith2NewReg(op: String, lhs: Value, rhs: Value, dst: Value) {
    val virReg = newVirReg(dst)
    val lhsReg = value2Reg[lhs]!!
    val rhsReg = value2Reg[rhs]!!
    val arithInst = ASMArithInst(op, lhsReg, rhsReg, virReg)
    insertInstBeforeInsertPoint(arithInst)
  }

  fun arith2OldReg(op: String, lhs: PhyReg, rhs: Imm, dst: PhyReg) {
    val arithInst = ASMArithiInst(op, lhs, rhs, dst)
    insertInstBeforeInsertPoint(arithInst)
  }

  fun ret() {
    val retInst = ASMRetInst()
    insertInstBeforeInsertPoint(retInst)
  }

  fun store(src: Value, dst: Value, offset: Int) {
    if (src is ConstantInt) {
      val virReg = newVirReg()
      val liInst = ASMLiInst(virReg, Imm(src.value))
      insertInstBeforeInsertPoint(liInst)
      val storeInst = ASMStoreInst(src.type.getAlign(), virReg, value2Reg[dst]!!, Imm(offset))
      insertInstBeforeInsertPoint(storeInst)
    } else if (src is ConstantNull) {
      // TODO: There is nothing I can do to store null?
    } else {
      val storeInst = ASMStoreInst(src.type.getAlign(), value2Reg[src]!!, value2Reg[dst]!!, Imm(offset))
      insertInstBeforeInsertPoint(storeInst)
    }
  }

  fun cmp2NewReg(op: String, lhs: Value, rhs: Value, dst: Value) {
    val virReg = newVirReg(dst)
    println("cmp2NewReg: $lhs $op $rhs -> $virReg")
    val lhsReg = if (lhs is ConstantInt) {
      val anotherVirReg = newVirReg()
      val liInst = ASMLiInst(anotherVirReg, Imm(lhs.value))
      insertInstBeforeInsertPoint(liInst)
      anotherVirReg
    } else {
      value2Reg[lhs]!!
    }
    val rhsReg = if (rhs is ConstantInt) {
      val anotherVirReg = newVirReg()
      val liInst = ASMLiInst(anotherVirReg, Imm(rhs.value))
      insertInstBeforeInsertPoint(liInst)
      anotherVirReg
    } else {
      value2Reg[rhs]!!
    }
    val cmpInst = ASMCmpInst(op, lhsReg, rhsReg, virReg)
    insertInstBeforeInsertPoint(cmpInst)
  }

  fun brz2Label(op: String, src: Value, basicBlock: BasicBlock) {
    val brInst = ASMBrzInst(op, value2Reg[src]!!, func!!.getBlock(basicBlock.name!!)!!)
    insertInstBeforeInsertPoint(brInst)
  }

  fun j2Label(basicBlock: BasicBlock) {
    val jInst = ASMJInst(func!!.getBlock(basicBlock.name!!)!!)
    insertInstBeforeInsertPoint(jInst)
  }

  fun pushSp() {
    val arithiInst = ASMArithiInst("addi", PhyReg("sp"), Imm(-stackAlloca), PhyReg("sp"))
    insertInstBeforeInsertPoint(arithiInst)
  }

  fun popSp() {
    val arithiInst = ASMArithiInst("addi", PhyReg("sp"), Imm(stackAlloca), PhyReg("sp"))
    insertInstBeforeInsertPoint(arithiInst)
  }

  fun li2NewReg(src: ConstantInt) {
    val virReg = newVirReg(src)
    val liInst = ASMLiInst(virReg, Imm(src.value))
    insertInstBeforeInsertPoint(liInst)
  }

  fun fetch2NewReg(inst: GetElementPtrInst) {
    val index = inst.index
    val elemSize = (inst.ptr.type as PointerType).pointeeTy!!.getAlign()
    val elemSizeReg = newVirReg()
    val elemSizeLiInst = ASMLiInst(elemSizeReg, Imm(elemSize))
    insertInstBeforeInsertPoint(elemSizeLiInst)
    val indexReg = if (index is ConstantInt) {
      val anotherVirReg = newVirReg()
      val indexLiInst = ASMLiInst(anotherVirReg, Imm(index.value))
      insertInstBeforeInsertPoint(indexLiInst)
      anotherVirReg
    } else {
      value2Reg[index]!!
    }
    val mulInst = ASMArithInst("mul", elemSizeReg, indexReg, elemSizeReg)
    insertInstBeforeInsertPoint(mulInst)
    val addInst = ASMArithInst("add", value2Reg[inst.ptr]!!, elemSizeReg, elemSizeReg)
    insertInstBeforeInsertPoint(addInst)
    val virReg = newVirReg(inst)
    val loadInst = ASMLoadInst(elemSize, elemSizeReg, virReg, Imm(0))
    insertInstBeforeInsertPoint(loadInst)
  }
}