package middleend.basic

open class Instruction(type: Type, name: String? = null) : User(type, name) {
  var parent: BasicBlock? = null

  open fun isTerminator(): Boolean {
    return false
  }

  fun insertAtTheTailOf(block: BasicBlock) {
    block.addInst(block.instList.size, this)
    parent = block
  }

  fun insertAtTheHeadOf(block: BasicBlock) {
    block.addInst(0, this)
    parent = block
  }

  fun insertAtIndex(block: BasicBlock, index: Int) {
    block.addInst(index, this)
    parent = block
  }
}

class BinaryInst(name: String, val op: String, type: Type, val lhs: Value, val rhs: Value) :
  Instruction(type, name) {
  override fun toString(): String {
    return "%$name = $op $type ${lhs.toOperand()}, ${rhs.toOperand()}"
  }
}

class AllocaInst(name: String, val varType: Type) : Instruction(TypeFactory.getPtrType(varType), name) {
  override fun toString(): String {
    return "%$name = alloca $varType, align ${varType.getAlign()}"
  }
}

class LoadInst(name: String, type: Type, val addr: Value) : Instruction(type, name) {
  init {
    addr.addUser(this)
  }

  override fun toString(): String {
    return "%$name = load $type, $type* ${addr.toOperand()}, align ${type.getAlign()}"
  }
}

class StoreInst(val storedType: Type, val value: Value, val addr: Value) : Instruction(TypeFactory.getVoidType()) {
  override fun toString(): String {
    return "store $storedType ${value.toOperand()}, $storedType* ${addr.toOperand()}"
  }
}


class CmpInst(name: String, val cond: Cond, val varType: Type, val lhs: Value, val rhs: Value) :
  Instruction(TypeFactory.getIntType(1), name) {
  enum class Cond {
    eq, ne, ugt, uge, ult, ule, sgt, sge, slt, sle,
  }

  override fun toString(): String {
    return "%$name = icmp $cond $varType ${lhs.toOperand()}, ${rhs.toOperand()}"
  }
}

class TruncInst(name: String, val originalTy: Type, val originalVal: Value, val toTy: Type) :
  Instruction(toTy, name) {
  override fun toString(): String {
    return "%$name = trunc $originalTy ${originalVal.toOperand()} to $toTy"
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
    return "${prefix}call ${funcType.result} @${funcType.funcName}(${args.joinToString(", ") { it.toOperand() }})"
  }
}

//<result> = getelementptr inbounds [<#elements> x <type>], [<#elements> x <type>]* <variable>, i32 0, i32 <index>
class GetElementPtrInst(name: String, type: Type, val varType: Type, val value: Value, val index: Value) :
  Instruction(type, name) {
  // constructor for structType
  constructor(name: String, varType: StructType, value: Value, index: Int) : this(
    name,
    TypeFactory.getPtrType(varType.symbolList[index].second),
    varType,
    value,
    ConstantInt(32, index)
  )

  // constructor for arrayType
  constructor(name: String, varType: ArrayType, value: Value, index: Value) : this(
    name,
    varType.containedType,
    varType,
    value,
    index
  )

  // constructor for arrayType in pointer
  constructor(name: String, varType: PointerType, value: Value, index: Value) : this(
    name,
    varType.pointeeTy!!,
    varType,
    value,
    index
  )

  override fun toString(): String {
    return "%$name = getelementptr inbounds $varType, $varType* ${value.toOperand()}, i32 0, i32 $index"
  }
}

class PhiInst(name: String, type: Type, val candidates: List<Pair<Value, BasicBlock>>) : Instruction(type, name) {
  override fun toString(): String {
    return "%$name = phi $type ".plus(candidates.joinToString(", ") { "[ ${it.first.toOperand()}, ${it.second.toOperand()} ]" })
  }
}