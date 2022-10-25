package middleend.basic

import middleend.helper.Utils
import middleend.pass.IRVisitor

abstract class Instruction(type: Type, name: String? = null) : User(type, name) {
  var parent: BasicBlock? = null

  open fun isTerminator(): Boolean {
    return false
  }

  fun insertAtTheTailOf(block: BasicBlock) {
    if (!block.hasTerminator()) {
      block.addInst(block.instList.size, this)
      parent = block
    }
  }

  fun insertAtTheHeadOf(block: BasicBlock) {
    block.addInst(0, this)
    parent = block
  }

  fun insertAtIndexOf(block: BasicBlock, index: Int) {
    block.addInst(index, this)
    parent = block
  }

  fun getIndexAtBlock(): Int {
    for ((index, inst) in parent!!.instList.withIndex()) {
      if (inst === this) {
        return index
      }
    }
    throw Exception("cannot find instruction in its parent basic block")
  }

  abstract fun accept(irVisitor: IRVisitor)
}

class BinaryInst(name: String, val op: String, val lhs: Value, val rhs: Value) :
  Instruction(lhs.type, name) {

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class AllocaInst(name: String, val varType: Type) : Instruction(TypeFactory.getPtrType(varType), name) {
  fun getAllocatedSize(): Int {
    return varType.getAlign()
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class LoadInst(name: String, val addr: Value) : Instruction(Utils.getPointee(addr.type), name) {
  init {
    addr.addUser(this)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class StoreInst(val value: Value, val addr: Value) : Instruction(TypeFactory.getVoidType()) {
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}


class CmpInst(name: String, val cond: Cond, val lhs: Value, val rhs: Value) :
  Instruction(TypeFactory.getIntType(1), name) {
  enum class Cond {
    eq, ne, ugt, uge, ult, ule, sgt, sge, slt, sle,
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class TruncInst(name: String, val originalVal: Value, val toTy: Type) :
  Instruction(toTy, name) {
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class ReturnInst(type: Type, val retVal: Value?) : Instruction(type) {
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class BranchInst(val cond: Value?, val trueBlock: BasicBlock, val falseBlock: BasicBlock?) :
  Instruction(TypeFactory.getVoidType()) {
  override fun isTerminator(): Boolean {
    return true
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class CallInst(name: String?, val funcType: FuncType, val args: List<Value>) :
  Instruction(funcType.result, name) {
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

/**
 * Form: <result> = getelementptr inbounds [<#elements> x <type>], [<#elements> x <type>]* <variable>, i32 <index>, i32 <offset>
 */
class GetElementPtrInst(val op: String, name: String, val ptr: Value, val index: Value, val offset: ConstantInt?) :
  Instruction(
    when (op) {
      "struct" -> TypeFactory.getPtrType((Utils.getPointee(ptr.type) as StructType).symbolList[(offset as ConstantInt).value].second)
      "array" -> TypeFactory.getPtrType((Utils.getPointee(ptr.type) as ArrayType).containedType)
      "ptr" -> ptr.type
      else -> throw Exception("the operation is forbidden")
    }, name
  ) {

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

/**
 * @param preds: it might come from latter blocks, therefore it's allowed to be modified later
 */
class PhiInst(name: String, type: Type, val preds: MutableList<Pair<Value, BasicBlock>>) :
  Instruction(type, name) {
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class BitCastInst(name: String, val castee: Value, toTy: Type) : Instruction(toTy, name) {
  val fromTy = castee.type
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class ZExtInst(name: String, val originalVal: Value, toTy: Type) : Instruction(toTy, name) {
  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}