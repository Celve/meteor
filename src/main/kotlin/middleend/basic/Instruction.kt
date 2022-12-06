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

  open fun collectUses(): List<Value> {
    return listOf()
  }

  abstract fun apply(applier: (Value) -> Value)

  abstract fun accept(irVisitor: IRVisitor)
}

class BinaryInst(name: String, val op: String, var rs: Value, var rt: Value) :
  Instruction(rs.type, name) {

  override fun collectUses(): List<Value> {
    return listOf(rs, rt)
  }

  override fun apply(applier: (Value) -> Value) {
    rs = applier(rs)
    rt = applier(rt)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class AllocaInst(name: String, val varType: Type) : Instruction(TypeFactory.getPtrType(varType), name) {
  fun getAllocatedSize(): Int {
    return varType.getAlign()
  }

  override fun apply(applier: (Value) -> Value) {}

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class LoadInst(name: String, var addr: Value) : Instruction(Utils.getPointee(addr.type), name) {
  override fun collectUses(): List<Value> {
    return listOf(addr)
  }

  override fun apply(applier: (Value) -> Value) {
    addr = applier(addr)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class StoreInst(var value: Value, var addr: Value) : Instruction(TypeFactory.getVoidType()) {
  override fun collectUses(): List<Value> {
    return listOf(value, addr)
  }

  override fun apply(applier: (Value) -> Value) {
    value = applier(value)
    addr = applier(addr)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}


class CmpInst(name: String, val cond: Cond, var rs: Value, var rt: Value) :
  Instruction(TypeFactory.getIntType(1), name) {
  enum class Cond {
    eq, ne, ugt, uge, ult, ule, sgt, sge, slt, sle,
  }

  override fun collectUses(): List<Value> {
    return listOf(rs, rt)
  }

  override fun apply(applier: (Value) -> Value) {
    rs = applier(rs)
    rt = applier(rt)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class TruncInst(name: String, var originalVal: Value, val toTy: Type) :
  Instruction(toTy, name) {
  override fun collectUses(): List<Value> {
    return listOf(originalVal)
  }

  override fun apply(applier: (Value) -> Value) {
    originalVal = applier(originalVal)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class ReturnInst(type: Type, var retVal: Value?) : Instruction(type) {
  override fun collectUses(): List<Value> {
    return if (retVal == null) {
      listOf()
    } else {
      listOf(retVal!!) // make sure that retVal could not be assigned to null
    }
  }

  override fun apply(applier: (Value) -> Value) {
    if (retVal != null) {
      retVal = applier(retVal!!)
    }
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class BranchInst(var cond: Value?, var trueBlock: BasicBlock, var falseBlock: BasicBlock?) :
  Instruction(TypeFactory.getVoidType()) {
  override fun collectUses(): List<Value> {
    return if (cond == null) {
      listOf()
    } else {
      listOf(cond!!)
    }
  }

  override fun apply(applier: (Value) -> Value) {
    if (cond != null) {
      cond = applier(cond!!)
    }
  }

  override fun isTerminator(): Boolean {
    return true
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class CallInst(name: String?, val funcType: FuncType, var args: List<Value>) :
  Instruction(funcType.resultType, name) {
  override fun collectUses(): List<Value> {
    return args
  }

  override fun apply(applier: (Value) -> Value) {
    args = args.map { applier(it) }
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

/**
 * Form: <result> = getelementptr inbounds [<#elements> x <type>], [<#elements> x <type>]* <variable>, i32 <index>, i32 <offset>
 */
class GetElementPtrInst(val op: String, name: String, var ptr: Value, var index: Value, var offset: ConstantInt?) :
  Instruction(
    when (op) {
      "struct" -> TypeFactory.getPtrType((Utils.getPointee(ptr.type) as StructType).symbolList[(offset as ConstantInt).value].second)
      "array" -> TypeFactory.getPtrType((Utils.getPointee(ptr.type) as ArrayType).containedType)
      "ptr" -> ptr.type
      else -> throw Exception("the operation is forbidden")
    }, name
  ) {
  override fun collectUses(): List<Value> {
    return if (offset == null) {
      listOf(ptr, index)
    } else {
      listOf(ptr, index, offset!!)
    }
  }

  override fun apply(applier: (Value) -> Value) {
    ptr = applier(ptr)
    index = applier(index)
    if (offset != null) {
      offset = applier(offset!!) as ConstantInt
    }
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

/**
 * @param predList: it might come from latter blocks, therefore it's allowed to be modified later
 */
class PhiInst(name: String, type: Type, var predList: MutableList<Pair<Value, BasicBlock>>) :
  Instruction(type, name) {
  override fun collectUses(): List<Value> {
    return predList.map { it.first }
  }

  override fun apply(applier: (Value) -> Value) {
    predList = predList.map { Pair(applier(it.first), it.second) }.toMutableList()
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class BitCastInst(name: String, var castee: Value, toTy: Type) : Instruction(toTy, name) {
  val fromTy = castee.type

  override fun collectUses(): List<Value> {
    return listOf(castee)
  }

  override fun apply(applier: (Value) -> Value) {
    castee = applier(castee)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class ZExtInst(name: String, var originalVal: Value, toTy: Type) : Instruction(toTy, name) {
  override fun collectUses(): List<Value> {
    return listOf(originalVal)
  }

  override fun apply(applier: (Value) -> Value) {
    originalVal = applier(originalVal)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class PCopyInst : Instruction(TypeFactory.getVoidType(), null) {
  // the first is rd, and the second is rs
  var assignmentList = mutableListOf<Pair<Value, Value>>()

  fun addAssignment(rd: Value, rs: Value) {
    assignmentList.add(Pair(rd, rs))
  }

  override fun collectUses(): List<Value> {
    return assignmentList.map { it.second }
  }

  override fun apply(applier: (Value) -> Value) {
    assignmentList = assignmentList.map { Pair(it.first, applier(it.second)) }.toMutableList()
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}