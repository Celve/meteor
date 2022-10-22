package backend.basic

import backend.controller.ASMVisitor

/**
 * Reference to LLVM Value, ASMInst should like instruction in LLVM, is a value, which is called Register here.
 * Register is directly similar to the idea in LLVM Value in many aspects.
 * They all could be referred by other instructions, and they all are basic unit of computation.
 * The only difference is that Register could be overlapped (due to physical register), while value in LLVM cannot.
 */
abstract class ASMInst(val comment: String) {
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

  fun insertedBefore(other: ASMInst) {
    val index = other.getIndexAtBlock()
    insertAtIndexOf(other.parent!!, index)
  }

  fun insertedAfter(other: ASMInst) {
    val index = other.getIndexAtBlock()
    insertAtIndexOf(other.parent!!, index + 1)
  }

  fun unlink() {
    parent!!.instList.remove(this)
    parent = null
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

class ASMLoadInst(val byteNum: Int, val rd: Register, val imm: Immediate, val rs: Register, comment: String) :
  ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * @param rs2 is used as source
 * @param rs1 is used as destination
 */
class ASMStoreInst(val byteNum: Int, val rs2: Register, val imm: Immediate, val rs1: Register, comment: String) :
  ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithInst(val op: String, val rd: Register, val rs1: Register, val rs2: Register, comment: String) :
  ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithiInst(val op: String, val rd: Register, var imm: Immediate, val rs: Register, comment: String) :
  ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpInst(val op: String, val rd: Register, val rs1: Register, val rs2: Register, comment: String) :
  ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpiInst(val op: String, val rs1: Register, val imm: Immediate, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCallInst(val label: Label, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * ASMBrzInst stands for some branch instructions in pseudo instructions,
 * which decides whether to jump by judging whether it's zero or not.
 * Brz stands for beqz, bnez, ...
 */
class ASMBzInst(val op: String, val rs: Register, val label: Label, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLiInst(val rd: Register, val imm: Immediate, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMMvInst(val rd: Register, val rs: Register, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMRetInst(comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMJInst(val label: Label, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLaInst(val rd: Register, val symbol: ASMSymbol, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpzInst(val op: String, val rd: Register, val rs1: Register, comment: String) : ASMInst(comment) {
  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}