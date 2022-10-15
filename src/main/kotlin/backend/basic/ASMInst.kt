package backend.basic

import backend.controller.ASMVisitor

/**
 * Reference to LLVM Value, ASMInst should like instruction in LLVM, is a value, which is called Register here.
 * Register is directly similar to the idea in LLVM Value in many aspects.
 * They all could be referred by other instructions, and they all are basic unit of computation.
 * The only difference is that Register could be overlapped (due to physical register), while value in LLVM cannot.
 */
abstract class ASMInst {
  var parent: ASMBlock? = null
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

  fun getIndexAtBlock(): Int {
    for ((index, inst) in parent!!.instList.withIndex()) {
      if (inst === this) {
        return index
      }
    }
    throw Exception("cannot find instruction in its parent basic block")
  }

  abstract fun accept(visitor: ASMVisitor)
}

class ASMLoadInst(val byteNum: Int, val rs: Register, val rd: Register, val imm: Imm) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMStoreInst(val byteNum: Int, val rs: Register, val rd: Register, val imm: Imm) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithInst(val op: String, val rs2: Register, val rs1: Register, val rd: Register) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithiInst(val op: String, val rs1: Register, val imm: Imm, val rd: Register) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpInst(val op: String, val rs2: Register, val rs1: Register, val rd: Register) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpiInst(val op: String, val rs1: Register, val imm: Imm) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCallInst(val label: Label) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * ASMBrzInst stands for some branch instructions in pseudo instructions,
 * which decides whether to jump by judging whether it's zero or not.
 * Brz stands for beqz, bnez, ...
 */
class ASMBrzInst(val op: String, val rs: ASMValue, val label: Label) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMJalInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
//    visitor.visit(this)
  }
}

class ASMJalrInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
  }
}

class ASMLiInst(val rd: Register, val imm: Imm) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLuiInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
  }
}

class ASMAuipcInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
  }
}

class ASMMvInst(val rs: Register, val rd: Register) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMNotInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
  }
}

class ASMNegInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
  }
}

class ASMZextInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
  }
}

class ASMRetInst : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMJInst(val label: Label) : ASMInst() {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}