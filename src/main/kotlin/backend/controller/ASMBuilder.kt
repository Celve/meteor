package backend.controller

import backend.basic.*
import backend.helper.RegFactory
import backend.helper.Utils

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

  fun createCallInst(func: ASMFunc) {
    val callInst = ASMCallInst(func)
    insertInst(callInst)
  }

  /**
   * This function is used to build almost every binary arithmetic instructions.
   */
  fun createArithInst(op: String, rd: Register, rs1: Register, rs2: Register) {
    val arithInst = ASMArithInst(op, rd, rs1, rs2)
    insertInst(arithInst)
  }

  fun createArithiInst(op: String, rd: Register, rs: Register, imm: Immediate) {
    when (op) {
      "addi", "slli", "srai" -> {
        if (imm !is DeterminedImmediate || imm.value != 0) {
          insertInst(ASMArithiInst(op, rd, rs, imm))
        } else {
          insertInst(ASMMvInst(rd, rs))
        }
      }

      "subi" -> {
        if (imm !is DeterminedImmediate || imm.value != 0) {
          val sub = when (imm) {
            is DeterminedImmediate -> DeterminedImmediate(-imm.value)
            is UndeterminedImmediate -> -imm
            else -> throw Exception("Unknown immediate type")
          }
          insertInst(ASMArithiInst("addi", rd, rs, sub))
        } else {
          insertInst(ASMMvInst(rd, rs))
        }
      }

      "andi", "ori", "xori" -> insertInst(ASMArithiInst(op, rd, rs, imm))

      "muli", "divi" -> {
        val dim = imm as DeterminedImmediate
        if (imm.value != 1) {
          val (isPowerOf2, power) = Utils.isPowerOf2(imm.value)
          if (isPowerOf2) {
            when (op) {
              "muli" -> insertInst(ASMArithiInst("slli", rd, rs, DeterminedImmediate(power)))
              "divi" -> insertInst(ASMArithiInst("srai", rd, rs, DeterminedImmediate(power)))
            }
          } else {
            val virReg = RegFactory.newVirReg()
            insertInst(ASMLiInst(virReg, dim))
            insertInst(ASMArithInst(op.dropLast(1), rd, rs, virReg))
          }
        } else {
          insertInst(ASMMvInst(rd, rs))
        }
      }

      "remi" -> {
        val virReg = RegFactory.newVirReg()
        insertInst(ASMLiInst(virReg, imm as DeterminedImmediate))
        insertInst(ASMArithInst(op.dropLast(1), rd, rs, virReg))
      }

      else -> throw Exception("Unknown op: $op")
    }
  }

  fun createLiInst(rd: Register, imm: DeterminedImmediate) {
    val liInst = ASMLiInst(rd, imm)
    insertInst(liInst)
  }

  fun createMvInst(rd: Register, rs: Register) {
    val mvInst = ASMMvInst(rd, rs)
    insertInst(mvInst)
  }

  fun createLoadInst(byteNum: Int, rd: Register, offset: Immediate, rs: Register) {
    val loadInst = ASMLoadInst(byteNum, rd, offset, rs)
    insertInst(loadInst)
  }

  fun createStoreInst(byteNum: Int, rs2: Register, offset: Immediate, rs1: Register) {
    val storeInst = ASMStoreInst(byteNum, rs2, offset, rs1)
    insertInst(storeInst)
  }

  /**
   * This function is used for building instruction j.
   */
  fun createJInst(target: ASMBlock) {
    val jInst = ASMJInst(target)
    insertInst(jInst)
    target.addPredBlock(block)
  }

  fun createBrInst(op: String, rs1: Register, rs2: Register, target: ASMBlock) {
    val brInst = ASMBrInst(op, rs1, rs2, target)
    insertInst(brInst)
    target.addPredBlock(block)
  }

  /**
   * This function is used to build instructions like beqz, bnez, bltz, bgez, blez, bgtz.
   */
  fun createBzInst(op: String, rs: Register, target: ASMBlock) {
    val bzInst = ASMBzInst(op, rs, target)
    insertInst(bzInst)
    target.addPredBlock(block)
  }

  /**
   * This function is used to build instruction slt.
   */
  fun createCmpInst(op: String, rd: Register, rs1: Register, rs2: Register) {
    val cmpInst = ASMCmpInst(op, rd, rs1, rs2)
    insertInst(cmpInst)
  }

  fun createCmpiInst(op: String, rd: Register, rs1: Register, imm: DeterminedImmediate) {
    val cmpiInst = ASMCmpiInst(op, rd, rs1, imm)
    insertInst(cmpiInst)
  }

  /**
   * This function is used to build instruction like seqz, snez.
   */
  fun createCmpzInst(op: String, rd: Register, rs1: Register) {
    val cmpzInst = ASMCmpzInst(op, rd, rs1)
    insertInst(cmpzInst)
  }

  fun createRet() {
    val retInst = ASMRetInst()
    insertInst(retInst)
  }

  fun createLaInst(rd: Register, symbol: ASMGlobalPointer) {
    val laInst = ASMLaInst(rd, symbol)
    insertInst(laInst)
  }

  fun createLuiInst(rd: Register, imm: Immediate) {
    insertInst(ASMLuiInst(rd, imm))
  }
}