package backend.basic

import backend.pass.ASMVisitor
import kotlin.math.min

/**
 * Reference to LLVM Value, ASMInst should like instruction in LLVM, is a value, which is called Register here.
 * Register is directly similar to the idea in LLVM Value in many aspects.
 * They all could be referred by other instructions, and they all are basic unit of computation.
 * The only difference is that Register could be overlapped (due to physical register), while value in LLVM cannot.
 */
abstract class ASMInst : ASMUser(null) {
  lateinit var parent: ASMBlock
  protected var def: Register? = null

  fun getRd(): Register? {
    return def
  }

  // the *use* here is different from usee
  open fun getUseList(): List<Register> {
    return useeList.filterIsInstance<Register>() // filter imms, labels, etc.
  }

  open fun getDefSet(): List<Register> {
    return if (def == null) {
      listOf()
    } else {
      listOf(def!!)
    }
  }

  open fun replaceUse(old: ASMValue, new: ASMValue) {
    useeList.replaceAll { if (it === old) new else it }
    old.userSet.remove(this)
    new.userSet.add(this)
  }

  open fun replaceDef(old: Register, new: Register) {
    if (def == old) {
      def = new
    }
  }

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

class ASMLoadInst(val byteNum: Int, rd: Register, imm: Immediate, rs: Register) : ASMInst() {
  init {
    def = rd
    link(this, imm)
    link(this, rs)
  }

  fun getImm(): Immediate {
    return useeList[0] as Immediate
  }

  fun getRs(): Register {
    return useeList[1] as Register
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * @param rs2 is used as source
 * @param rs1 is used as destination
 */
class ASMStoreInst(val byteNum: Int, rs2: Register, imm: Immediate, rs1: Register) :
  ASMInst() {
  init {
    link(this, rs2)
    link(this, imm)
    link(this, rs1)
  }

  fun getRs2(): Register {
    return useeList[0] as Register
  }

  fun getImm(): Immediate {
    return useeList[1] as Immediate
  }

  fun getRs1(): Register {
    return useeList[2] as Register
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithInst(val op: String, rd: Register, rs1: Register, rs2: Register) : ASMInst() {
  init {
    def = rd
    link(this, rs1)
    link(this, rs2)
  }

  fun getRs1(): Register {
    return useeList[0] as Register
  }

  fun getRs2(): Register {
    return useeList[1] as Register
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMArithiInst(val op: String, rd: Register, rs: Register, imm: Immediate) : ASMInst() {
  init {
    def = rd
    link(this, rs)
    link(this, imm)
  }

  fun getRs(): Register {
    return useeList[0] as Register
  }

  fun getImm(): Immediate {
    return useeList[1] as Immediate
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpInst(val op: String, rd: Register, rs1: Register, rs2: Register) : ASMInst() {
  init {
    def = rd
    link(this, rs1)
    link(this, rs2)
  }

  fun getRs1(): Register {
    return useeList[0] as Register
  }

  fun getRs2(): Register {
    return useeList[1] as Register
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpiInst(val op: String, rd: Register, rs: Register, imm: DeterminedImmediate) : ASMInst() {
  init {
    def = rd
    link(this, rs)
    link(this, imm)
  }

  fun getRs(): Register {
    return useeList[0] as Register
  }

  fun getImm(): DeterminedImmediate {
    return useeList[1] as DeterminedImmediate
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCallInst(label: Label) : ASMInst() {
  init {
    link(this, label)
  }

  fun getLabel(): Label {
    return useeList[0] as Label
  }

  // this operation is different from others
  // because the call instruction might incur a lot of modifications on caller-saved registers
  override fun getUseList(): List<Register> {
    return listOf(PhyReg("ra")) + (0 until min(8, (getLabel() as ASMFunc).argsNum)).map { PhyReg(10 + it) }
  }

  override fun getDefSet(): List<Register> {
    return RegInfo.callerSavedRegList.map { PhyReg(it) }
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMBrInst(val op: String, rs1: Register, rs2: Register, label: Label) : ASMInst() {
  init {
    link(this, rs1)
    link(this, rs2)
    link(this, label)
  }

  fun getRs1(): Register {
    return useeList[0] as Register
  }

  fun getRs2(): Register {
    return useeList[1] as Register
  }

  fun getLabel(): Label {
    return useeList[2] as Label
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

/**
 * ASMBrzInst stands for some branch instructions in pseudo instructions,
 * which decides whether to jump by judging whether it's zero or not.
 * Brz stands for beqz, bnez, ...
 */
class ASMBzInst(val op: String, rs: Register, label: Label) : ASMInst() {
  init {
    link(this, rs)
    link(this, label)
  }

  fun getRs(): Register {
    return useeList[0] as Register
  }

  fun getLabel(): Label {
    return useeList[1] as Label
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLiInst(rd: Register, imm: DeterminedImmediate) : ASMInst() {
  init {
    def = rd
    link(this, imm)
  }

  fun getImm(): DeterminedImmediate {
    return useeList[0] as DeterminedImmediate
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMMvInst(rd: Register, rs: Register) : ASMInst() {
  init {
    def = rd
    link(this, rs)
  }

  fun getRs(): Register {
    return useeList[0] as Register
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMRetInst : ASMInst() {
  override fun getUseList(): List<Register> {
    return listOf(PhyReg("ra"))
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMJInst(label: Label) : ASMInst() {
  init {
    link(this, label)
  }

  fun getLabel(): Label {
    return useeList[0] as Label
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMLaInst(rd: Register, symbol: ASMGlobalPointer) : ASMInst() {
  init {
    def = rd
    link(this, symbol)
  }

  fun getSymbol(): ASMGlobalPointer {
    return useeList[0] as ASMGlobalPointer
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}

class ASMCmpzInst(val op: String, rd: Register, rs: Register) : ASMInst() {
  init {
    def = rd
    link(this, rs)
  }

  fun getRs(): Register {
    return useeList[0] as Register
  }

  override fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}