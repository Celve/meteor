package backend.controller

import backend.basic.*

/**
 * ASMBuilder is a really general singleton class that is used to build assembly code.
 * When constructing the assembly code, we need to set up the ASMFunc, ASMBlock, or ASMInst properly, indicating the insert point.
 */
object ASMBuilder {
  private lateinit var func: ASMFunc
  private lateinit var block: ASMBlock
  var point: ASMInst? = null
  var isBefore = false

  fun setCurrentFunc(newFunc: ASMFunc) {
    func = newFunc
    newFunc.stackAlloca = 0
  }

  fun getCurrentFunc(): ASMFunc {
    return func
  }

  fun setInsertBlock(newBlock: ASMBlock) {
    block = newBlock
    point = null
  }

  fun getInsertBlock(): ASMBlock {
    return block
  }

  fun setInsertPointBefore(newInst: ASMInst) {
    point = newInst
    block = newInst.parent
    func = block.parent
    isBefore = true
  }

  /**
   * @param newInst cannot be the last inst in the inst list
   */
  fun setInsertPointAfter(inst: ASMInst) {
    block = inst.parent
    func = block.parent
//    point = block!!.instList[inst.getIndexAtBlock() + 1]
    point = inst
    isBefore = false
  }

  private fun insertInst(inst: ASMInst) {
    if (point == null) {
      inst.insertAtTheTailOf(block)
    } else {
      val index = point!!.getIndexAtBlock()
      if (isBefore) {
        inst.insertAtIndexOf(block, index)
      } else {
        inst.insertAtIndexOf(block, index + 1)
      }
    }
  }

  fun getInsertPoint(): ASMInst? {
    return point
  }

  fun createCallInst(func: ASMFunc, comment: String = "") {
    val callInst = ASMCallInst(func, comment)
    insertInst(callInst)
  }

  /**
   * This function is used to build almost every binary arithmetic instructions.
   */
  fun createArithInst(op: String, rd: Register, rs1: Register, rs2: Register, comment: String = "") {
    val newOp = when (op) {
      "sdiv" -> "div"
      "srem" -> "rem"
      "shl" -> "sll"
      "ashr" -> "sra"
      else -> op
    }
    val arithInst = ASMArithInst(newOp, rd, rs1, rs2, comment)
    insertInst(arithInst)
  }

  fun createArithiInst(op: String, rd: Register, rs1: Register, imm: Immediate, comment: String = "") {
    val newOp = when (op) {
      "shli" -> "slli"
      "ashri" -> "srai"
      else -> op
    }
    val arithiInst = ASMArithiInst(newOp, rd, imm, rs1, comment)
    insertInst(arithiInst)
  }

  fun createLiInst(rd: Register, imm: DeterminedImmediate, comment: String = "") {
    val liInst = ASMLiInst(rd, imm, comment)
    insertInst(liInst)
  }

  fun createMvInst(rd: Register, rs: Register, comment: String = "") {
    val mvInst = ASMMvInst(rd, rs, comment)
    insertInst(mvInst)
  }

  fun createLoadInst(byteNum: Int, rd: Register, offset: Immediate, rs: Register, comment: String = "") {
    val loadInst = ASMLoadInst(byteNum, rd, offset, rs, comment)
    insertInst(loadInst)
  }

  fun createStoreInst(byteNum: Int, rs2: Register, offset: Immediate, rs1: Register, comment: String = "") {
    val storeInst = ASMStoreInst(byteNum, rs2, offset, rs1, comment)
    insertInst(storeInst)
  }

  /**
   * This function is used for building instruction j.
   */
  fun createJInst(target: ASMBlock, comment: String = "") {
    val jInst = ASMJInst(target, comment)
    insertInst(jInst)
    target.addPredBlock(block)
  }

  /**
   * This function is used to build instructions like beqz, bnez, bltz, bgez, blez, bgtz.
   */
  fun createBzInst(op: String, rs: Register, target: ASMBlock, comment: String = "") {
    val bzInst = ASMBzInst(op, rs, target, comment)
    insertInst(bzInst)
    target.addPredBlock(block)
  }

  /**
   * This function is used to build instruction slt.
   */
  fun createCmpInst(op: String, rd: Register, rs1: Register, rs2: Register, comment: String = "") {
    val cmpInst = ASMCmpInst(op, rd, rs1, rs2, comment)
    insertInst(cmpInst)
  }

  fun createCmpiInst(op: String, rd: Register, rs1: Register, imm: DeterminedImmediate, comment: String = "") {
    val cmpiInst = ASMCmpiInst(op, rd, rs1, imm, comment)
    insertInst(cmpiInst)
  }

  /**
   * This function is used to build instruction like seqz, snez.
   */
  fun createCmpzInst(op: String, rd: Register, rs1: Register, comment: String = "") {
    val cmpzInst = ASMCmpzInst(op, rd, rs1, comment)
    insertInst(cmpzInst)
  }

  fun createRet(comment: String = "") {
    val retInst = ASMRetInst(comment)
    insertInst(retInst)
  }

  fun createLaInst(rd: Register, symbol: ASMGlobalPointer, comment: String = "") {
    val laInst = ASMLaInst(rd, symbol, comment)
    insertInst(laInst)
  }
}