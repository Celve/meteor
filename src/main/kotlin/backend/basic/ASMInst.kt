package backend.basic

import backend.controller.ASMVisitor
import kotlin.math.min

/**
 * Reference to LLVM Value, ASMInst should like instruction in LLVM, is a value, which is called Register here.
 * Register is directly similar to the idea in LLVM Value in many aspects.
 * They all could be referred by other instructions, and they all are basic unit of computation.
 * The only difference is that Register could be overlapped (due to physical register), while value in LLVM cannot.
 */
abstract class ASMInst(val comment: String) {
  lateinit var parent: ASMBlock

  abstract fun getRs(): List<Register>

  abstract fun getRd(): List<Register>

  abstract fun replaceRs(oldReg: Register, newReg: Register)

  abstract fun replaceRd(oldReg: Register, newReg: Register)

  fun insertAtIndexOf(newParent: ASMBlock, index: Int) {
    newParent.instList.add(index, this)
    parent = newParent
  }

  fun insertAtTheTailOf(newParent: ASMBlock) {
    newParent.instList.add(this)
    parent = newParent
  }

  fun insertAtTheHeadOf(newParent: ASMBlock) {
    newParent.instList.add(0, this)
    parent = newParent
  }

  fun insertedBefore(other: ASMInst) {
    val index = other.getIndexAtBlock()
    insertAtIndexOf(other.parent, index)
  }

  fun insertedAfter(other: ASMInst) {
    val index = other.getIndexAtBlock()
    insertAtIndexOf(other.parent, index + 1)
  }

  fun unlink() {
    parent.instList.remove(this)
  }

  fun getIndexAtBlock(): Int {
    for ((index, inst) in parent.instList.withIndex()) {
      if (inst === this) {
        return index
      }
    }
    throw Exception("cannot find instruction in its parent basic block")
  }

  abstract fun accept(visitor: ASMVisitor)
}

class ASMLoadInst(val byteNum: Int, var rd: Register, var imm: Immediate, var rs: Register, comment: String) :
  ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }

  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs == oldReg) {
      rs = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * @param rs2 is used as source
 * @param rs1 is used as destination
 */
class ASMStoreInst(val byteNum: Int, var rs2: Register, var imm: Immediate, var rs1: Register, comment: String) :
  ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs1, rs2)
  }

  override fun getRd(): List<Register> {
    return listOf()
  }

  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs1 == oldReg) {
      rs1 = newReg
    }
    if (rs2 == oldReg) {
      rs2 = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithInst(val op: String, var rd: Register, var rs1: Register, var rs2: Register, comment: String) :
  ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs1, rs2)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs1 == oldReg) {
      rs1 = newReg
    }
    if (rs2 == oldReg) {
      rs2 = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithiInst(val op: String, var rd: Register, var imm: Immediate, var rs: Register, comment: String) :
  ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }

  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs == oldReg) {
      rs = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpInst(val op: String, var rd: Register, var rs1: Register, var rs2: Register, comment: String) :
  ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs1, rs2)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs1 == oldReg) {
      rs1 = newReg
    }
    if (rs2 == oldReg) {
      rs2 = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpiInst(val op: String, var rd: Register, var rs1: Register, val imm: DeterminedImmediate, comment: String) :
  ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs1)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs1 == oldReg) {
      rs1 = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCallInst(val label: Label, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(PhyReg("ra")) + (0 until min(8, (label as ASMFunc).argsNum)).map { PhyReg(10 + it) }
//    return RegInfo.callerSavedRegList.map { PhyReg(it) }
//    return listOf(PhyReg("ra"))
  }

  override fun getRd(): List<Register> {
    return RegInfo.callerSavedRegList.map { PhyReg(it) }
  }

  override fun replaceRs(oldReg: Register, newReg: Register) {}

  override fun replaceRd(oldReg: Register, newReg: Register) {}

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * ASMBrzInst stands for some branch instructions in pseudo instructions,
 * which decides whether to jump by judging whether it's zero or not.
 * Brz stands for beqz, bnez, ...
 */
class ASMBzInst(val op: String, var rs: Register, val label: Label, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs)
  }

  override fun getRd(): List<Register> {
    return listOf()
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs == oldReg) {
      rs = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {}

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLiInst(var rd: Register, val imm: DeterminedImmediate, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf()
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMMvInst(var rd: Register, var rs: Register, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs == oldReg) {
      rs = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMRetInst(comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(PhyReg("ra"))
  }

  override fun getRd(): List<Register> {
    return listOf()
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {}

  override fun replaceRd(oldReg: Register, newReg: Register) {}

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMJInst(val label: Label, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf()
  }

  override fun getRd(): List<Register> {
    return listOf()
  }

  override fun replaceRs(oldReg: Register, newReg: Register) {}

  override fun replaceRd(oldReg: Register, newReg: Register) {}

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLaInst(var rd: Register, val symbol: ASMGlobalPointer, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf()
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {}

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpzInst(val op: String, var rd: Register, var rs: Register, comment: String) : ASMInst(comment) {
  override fun getRs(): List<Register> {
    return listOf(rs)
  }

  override fun getRd(): List<Register> {
    return listOf(rd)
  }


  override fun replaceRs(oldReg: Register, newReg: Register) {
    if (rs == oldReg) {
      rs = newReg
    }
  }

  override fun replaceRd(oldReg: Register, newReg: Register) {
    if (rd == oldReg) {
      rd = newReg
    }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}