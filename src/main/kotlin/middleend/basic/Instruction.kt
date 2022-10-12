package middleend.basic

import middleend.helper.Utils

open class Instruction(type: Type, name: String? = null) : User(type, name) {
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

  fun insertAtIndex(block: BasicBlock, index: Int) {
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
}

class BinaryInst(name: String, val op: String, val lhs: Value, val rhs: Value) :
  Instruction(lhs.type, name) {
  override fun toString(): String {
    return "%$name = $op $type ${lhs.toOperand()}, ${rhs.toOperand()}"
  }
}

class AllocaInst(name: String, val varType: Type) : Instruction(TypeFactory.getPtrType(varType), name) {
  override fun toString(): String {
    return "%$name = alloca $varType, align ${varType.getAlign()}"
  }
}

class LoadInst(name: String, val addr: Value) : Instruction(Utils.getPointee(addr.type), name) {
  init {
    addr.addUser(this)
  }

  override fun toString(): String {
    return "%$name = load $type, $type* ${addr.toOperand()}, align ${type.getAlign()}"
  }
}

class StoreInst(val value: Value, val addr: Value) : Instruction(TypeFactory.getVoidType()) {
  override fun toString(): String {
    val lhsTy = if (value.type is PointerType && value.type.pointeeTy == null) {
      (addr.type as PointerType).pointeeTy
    } else {
      value.type
    }
    return "store ${lhsTy} ${value.toOperand()}, ${addr.type} ${addr.toOperand()}, align ${value.type.getAlign()}"
  }
}


class CmpInst(name: String, val cond: Cond, val lhs: Value, val rhs: Value) :
  Instruction(TypeFactory.getIntType(1), name) {
  enum class Cond {
    eq, ne, ugt, uge, ult, ule, sgt, sge, slt, sle,
  }

  override fun toString(): String {
    return "%$name = icmp $cond ${lhs.type} ${lhs.toOperand()}, ${rhs.toOperand()}"
  }
}

class TruncInst(name: String, val originalVal: Value, val toTy: Type) :
  Instruction(toTy, name) {
  override fun toString(): String {
    return "%$name = trunc ${originalVal.type} ${originalVal.toOperand()} to $toTy"
  }
}

class ReturnInst(type: Type, val value: Value?) : Instruction(type) {
  override fun toString(): String {
    return if (value == null) {
      "ret $type"
    } else {
      "ret $type ${value.toOperand()}"
    }
  }
}

class BranchInst(val cond: Value?, val trueBlock: BasicBlock, val falseBlock: BasicBlock?) :
  Instruction(TypeFactory.getVoidType()) {
  override fun toString(): String {
    return if (cond == null) {
      "br label ${trueBlock.toOperand()}"
    } else {
      "br i1 ${cond.toOperand()}, label ${trueBlock.toOperand()}, label ${falseBlock!!.toOperand()}"
    }
  }

  override fun isTerminator(): Boolean {
    return true
  }
}

class CallInst(name: String?, val funcType: FuncType, val args: List<Value>) :
  Instruction(funcType.result, name) {
  override fun toString(): String {
    val prefix: String = if (name == null) "" else "%$name = "
    return "${prefix}call ${funcType.result} @${funcType.funcName}(${
      funcType.params.zip(args).joinToString(", ") { "${it.first} ${it.second.toOperand()}" }
    })"
  }
}

//<result> = getelementptr inbounds [<#elements> x <type>], [<#elements> x <type>]* <variable>, i32 0, i32 <index>
class GetElementPtrInst(val op: String, name: String, val value: Value, val index: Value) : Instruction(
  when (op) {
    "struct" -> TypeFactory.getPtrType((Utils.getPointee(value.type) as StructType).symbolList[(index as ConstantInt).intValue].second)
    "array" -> TypeFactory.getPtrType((Utils.getPointee(value.type) as ArrayType).containedType)
    "ptr" -> value.type
    else -> throw Exception("the operation is forbidden")
  }, name
) {

  override fun toString(): String {
    val extra = if (op != "ptr") "i32 0, " else ""
    return "%$name = getelementptr inbounds ${Utils.getPointee(value.type)}, ${value.type} ${value.toOperand()}, ${extra}i32 ${index.toOperand()}"
  }
}

/**
 * @param candidates: it might come from latter blocks, therefore it's allowed to be modified later
 */
class PhiInst(name: String, type: Type, val candidates: MutableList<Pair<Value, BasicBlock>>) :
  Instruction(type, name) {
  override fun toString(): String {
    return "%$name = phi $type ".plus(candidates.joinToString(", ") { "[ ${it.first.toOperand()}, ${it.second.toOperand()} ]" })
  }
}

class BitCastInst(name: String, val castee: Value, toTy: Type) : Instruction(toTy, name) {
  private val fromTy = castee.type
  override fun toString(): String {
    return "%$name = bitcast $fromTy ${castee.toOperand()} to $type"
  }
}

class ZExtInst(name: String, val originalVal: Value, toTy: Type) : Instruction(toTy, name) {
  override fun toString(): String {
    return "%$name = zext ${originalVal.type} ${originalVal.toOperand()} to $type"
  }
}