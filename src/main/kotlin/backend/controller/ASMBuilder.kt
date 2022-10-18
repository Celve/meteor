package backend.controller

import backend.basic.*
import middleend.basic.Value

object ASMBuilder {
  var func: ASMFunc? = null
  var block: ASMBlock? = null
  var point: ASMInst? = null
  private var value2Reg = hashMapOf<Value, Register>() // this mapping is only for virtual register
  private var reg2Value = hashMapOf<Register, Value>() // this mapping is only for virtual register

  fun setCurrentFunc(newFunc: ASMFunc) {
    func = newFunc
    value2Reg = func!!.value2Reg
    reg2Value = func!!.reg2Value
    newFunc.stackAlloca = 0
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

  fun createCallInst(func: ASMFunc) {
    val callInst = ASMCallInst(func)
    insertInstBeforeInsertPoint(callInst)
  }

  fun createArithInst(op: String, rd: Register, rs1: Register, rs2: Register) {
    val arithInst = ASMArithInst(op, rs1, rs2, rd)
    insertInstBeforeInsertPoint(arithInst)
  }

  fun createArithiInst(op: String, rd: Register, rs1: Register, imm: Imm) {
    val arithiInst = ASMArithiInst(op, rs1, imm, rd)
    insertInstBeforeInsertPoint(arithiInst)
  }

  fun createLiInst(rd: Register, imm: Imm) {
    val liInst = ASMLiInst(imm, rd)
    insertInstBeforeInsertPoint(liInst)
  }

  fun createMvInst(rs: Register, rd: Register) {
    val mvInst = ASMMvInst(rs, rd)
    insertInstBeforeInsertPoint(mvInst)
  }

  fun createLoadInst(byteNum: Int, rd: Register, offset: Imm, rs: Register) {
    val loadInst = ASMLoadInst(byteNum, rs, rd, offset)
    insertInstBeforeInsertPoint(loadInst)
  }

  fun createStoreInst(byteNum: Int, rs: Register, offset: Imm, rd: Register) {
    val storeInst = ASMStoreInst(byteNum, rs, offset, rd)
    insertInstBeforeInsertPoint(storeInst)
  }

  fun createJInst(target: ASMBlock) {
    val jInst = ASMJInst(target)
    insertInstBeforeInsertPoint(jInst)
  }

  fun createBzInst(op: String, rs: Register, target: ASMBlock) {
    val bzInst = ASMBzInst(op, rs, target)
    insertInstBeforeInsertPoint(bzInst)
  }

  fun createCmpInst(op: String, rd: Register, rs1: Register, rs2: Register) {
    val cmpInst = ASMCmpInst(op, rs1, rs2, rd)
    insertInstBeforeInsertPoint(cmpInst)
  }

  fun createRet() {
    val retInst = ASMRetInst()
    insertInstBeforeInsertPoint(retInst)
  }
}