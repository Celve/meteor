package middleend.basic

import middleend.helper.Twine

open class Instruction(type: Type, name: Twine? = null) : User(type, name) {
  var parent: BasicBlock? = null

  fun insertAtTail(block: BasicBlock) {
    block.addInst(block.instList.size, this)
    parent = block
  }

  fun insertAtHead(block: BasicBlock) {
    block.addInst(0, this)
    parent = block
  }

  fun insertAtIndex(block: BasicBlock, index: Int) {
    block.addInst(index, this)
    parent = block
  }
}

class BinaryInst(name: Twine, val op: String, type: Type, val lhs: Value, val rhs: Value) :
  Instruction(type, name) {
  override fun toString(): String {
    return "$name = $op $type ${lhs.toOperand()}, ${rhs.toOperand()}"
  }
}

class AllocaInst(name: Twine, type: Type, val align: Int) : Instruction(type, name) {
  override fun toString(): String {
    return "$name = alloca $type, align $align"
  }
}

class LoadInst(name: Twine, type: Type, val addr: Value) : Instruction(type, name) {
  init {
    addr.addUser(this)
  }

  override fun toString(): String {
    return "$name = load $type, $type* ${addr.toOperand()}"
  }
}

class StoreInst(type: Type, val value: Value, val addr: Value) : Instruction(type) {
  override fun toString(): String {
    return "store $type ${value.toOperand()}, $type* ${addr.toOperand()}"
  }
}


class CmpInst(name: Twine, val cond: Cond, type: Type, val lhs: Value, val rhs: Value) :
  Instruction(TypeFactory.createInt(1), name) {
  enum class Cond {
    eq, ne, ugt, uge, ult, ule, sgt, sge, slt, sle,
  }
}

class TruncInst(name: Twine, val originalTy: Type, val originalVal: Value, val toTy: Type) :
  Instruction(toTy, name) {
  override fun toString(): String {
    return "$name = trunc $originalTy ${originalVal.toOperand()} to $toTy"
  }
}

// class FenceInst

// class AtomicCmpXchgInst

// class AtomicRMWInst

//class ExtractElementInst

//class InsertElementInst

//class ExtractValueInst

//class InsertValueInst

//class PhiNode

//class ReturnInst

//class BranchInst

//class SwitchInst

//class IndirectBrInst

//class InvokeInst

//class CallBrInst

//class CatchReturnInst

//class TruncInst

//class ZExtInst

//class SExtInst

//class IntToPtrInst

//class PtrToIntInst

//class BitCastInst

//class FreezeInst
