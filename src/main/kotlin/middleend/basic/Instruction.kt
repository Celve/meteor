package middleend.basic

import middleend.helper.Utils
import middleend.pass.IRVisitor

abstract class Instruction(type: Type, name: String? = null) : User(type, name) {
  lateinit var parent: BasicBlock

  // only name and type are reserved, all user-usee relationship are ignored
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
    for ((index, inst) in parent.instList.withIndex()) {
      if (inst === this) {
        return index
      }
    }
    throw Exception("cannot find instruction in its parent basic block")
  }

  abstract fun accept(irVisitor: IRVisitor)
}

class BinaryInst(name: String, val op: String, rs: Value, rt: Value) :
  Instruction(rs.type, name) {
  init { // rs, rt
    link(this, rs)
    link(this, rt)
  }

  fun getLhs(): Value {
    return useeList[0]
  }

  fun getRhs(): Value {
    return useeList[1]
  }

  override fun replicate(): Instruction {
    return BinaryInst(name!!, op, getLhs().duplicate(), getRhs().duplicate())
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class AllocaInst(name: String, val varType: Type) : Instruction(TypeFactory.getPtrType(varType), name) {
  fun getAllocatedSize(): Int {
    return varType.getAlign()
  }

  override fun replicate(): Instruction {
    return AllocaInst(name!!, varType)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class LoadInst(name: String, addr: Value) : Instruction(Utils.getPointee(addr.type), name) {
  init { // addr
    link(this, addr)
  }

  fun getAddr(): Value {
    return useeList[0]
  }

  override fun replicate(): Instruction {
    return LoadInst(name!!, getAddr().duplicate())
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class StoreInst(value: Value, addr: Value) : Instruction(TypeFactory.getVoidType()) {
  init { // value, addr
    link(this, value)
    link(this, addr)
  }

  fun getValue(): Value {
    return useeList[0]
  }

  fun getAddr(): Value {
    return useeList[1]
  }

  override fun replicate(): Instruction {
    return StoreInst(getValue().duplicate(), getAddr().duplicate())
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}


class CmpInst(name: String, val cond: String, rs: Value, rt: Value) :
  Instruction(TypeFactory.getIntType(1), name) {
  init { // rs, rt
    link(this, rs)
    link(this, rt)
  }

  fun getLhs(): Value {
    return useeList[0]
  }

  fun getRhs(): Value {
    return useeList[1]
  }

  override fun replicate(): Instruction {
    return CmpInst(name!!, cond, getLhs().duplicate(), getRhs().duplicate())
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class TruncInst(name: String, originalVal: Value, val toTy: Type) :
  Instruction(toTy, name) {
  init { // originalVal
    link(this, originalVal)
  }

  fun getOriginalVal(): Value {
    return useeList[0]
  }

  override fun replicate(): Instruction {
    return TruncInst(name!!, getOriginalVal().duplicate(), toTy)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class ReturnInst(type: Type, retVal: Value?) : Instruction(type) {
  init { // retVal?
    if (retVal != null) {
      link(this, retVal)
    }
  }

  fun getRetVal(): Value? {
    return if (useeList.isEmpty()) null else useeList[0]
  }

  override fun replicate(): Instruction {
    return ReturnInst(type, getRetVal()?.duplicate())
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class BranchInst(trueBlock: BasicBlock, cond: Value?, falseBlock: BasicBlock?) :
  Instruction(TypeFactory.getVoidType()) {
  init { // trueBlock, cond?, falseBlock?
    link(this, trueBlock)
    if (cond != null) {
      link(this, cond)
    }
    if (falseBlock != null) {
      link(this, falseBlock)
    }
  }

  fun isJump(): Boolean {
    return useeList.size == 1
  }

  fun getTrueBlock(): BasicBlock {
    return useeList[0] as BasicBlock
  }

  fun setTrueBlock(trueBlock: BasicBlock) {
    useeList[0].userList.remove(this)
    useeList[0] = trueBlock
    useeList[0].userList.add(this)
  }

  fun getCond(): Value? {
    return if (useeList.size > 1) useeList[1] else null
  }

  fun setCond(cond: Value) {
    if (useeList.size <= 1) {
      throw Exception("cannot set cond for branch instruction")
    }
    useeList[1].userList.remove(this)
    useeList[1] = cond
    cond.userList.add(this)
  }

  fun getFalseBlock(): BasicBlock? {
    return if (useeList.size > 2) useeList[2] as BasicBlock else null
  }

  fun setFalseBlock(falseBlock: BasicBlock) {
    if (useeList.size <= 2) {
      throw Exception("cannot set false block for branch instruction")
    }
    cut(this, 2)
    link(this, falseBlock)
  }

  override fun replicate(): Instruction {
    return BranchInst(
      getTrueBlock().duplicate() as BasicBlock,
      getCond()?.duplicate(),
      getFalseBlock()?.duplicate() as BasicBlock?
    )
  }

  override fun isTerminator(): Boolean {
    return true
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class CallInst(name: String?, func: Func, argList: List<Value>) :
  Instruction(func.funcType.resultType, name) {
  init { // args...
    link(this, func)
    argList.forEach { link(this, it) }
  }

  fun getCallee(): Func {
    return useeList[0] as Func
  }

  fun getArgList(): List<Value> {
    return useeList.subList(1, useeList.size)
  }

  override fun replicate(): Instruction {
    return CallInst(name, getCallee(), getArgList().map { it.duplicate() })
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

/**
 * Form: <result> = getelementptr inbounds [<#elements> x <type>], [<#elements> x <type>]* <variable>, i32 <index>, i32 <offset>
 */
class GetElementPtrInst(val op: String, name: String, ptr: Value, offset: Value, index: ConstantInt?) :
  Instruction(
    when (op) {
      "struct" -> TypeFactory.getPtrType((Utils.getPointee(ptr.type) as StructType).symbolList[(index as ConstantInt).value].second)
      "array" -> TypeFactory.getPtrType((Utils.getPointee(ptr.type) as ArrayType).containedType)
      "ptr" -> ptr.type
      else -> throw Exception("the operation is forbidden")
    }, name
  ) {
  init { // ptr, offset, index?
    link(this, ptr)
    link(this, offset)
    if (index != null) {
      link(this, index)
    }
  }

  fun getPtr(): Value {
    return useeList[0]
  }

  fun getOffset(): Value {
    return useeList[1]
  }

  fun getIndex(): ConstantInt? {
    return if (useeList.size > 2) useeList[2] as ConstantInt else null
  }

  override fun replicate(): Instruction {
    return GetElementPtrInst(
      op,
      name!!,
      getPtr().duplicate(),
      getOffset().duplicate(),
      getIndex()?.duplicate() as ConstantInt?
    )
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

/**
 * @param predList: it might come from latter blocks, therefore it's allowed to be modified later
 */
class PhiInst(name: String, type: Type, predList: MutableList<Pair<Value, BasicBlock>>) :
  Instruction(type, name) {
  init { // predList
    predList.forEach {
      link(this, it.first)
      link(this, it.second)
    }
  }

  fun addAssignment(value: Value, block: BasicBlock) {
    link(this, value)
    link(this, block)
  }

  fun getSize(): Int {
    return useeList.size / 2
  }

  fun getPredList(): List<Pair<Value, BasicBlock>> {
    val predList = mutableListOf<Pair<Value, BasicBlock>>()
    for (i in 0 until getSize()) {
      predList.add(Pair(useeList[2 * i], useeList[2 * i + 1] as BasicBlock))
    }
    return predList
  }

  fun getPred(index: Int): Pair<Value, BasicBlock> {
    return Pair(useeList[index * 2], useeList[index * 2 + 1] as BasicBlock)
  }

  fun getPred(block: BasicBlock): Pair<Value, BasicBlock>? {
    val index = useeList.indexOf(block)
    return if (index == -1) {
      null
    } else {
      Pair(useeList[index - 1], useeList[index] as BasicBlock)
    }
  }

  fun getIndex(block: BasicBlock): Int {
    val index = useeList.indexOf(block)
    return index / 2
  }

  fun removePred(block: BasicBlock) {
    val index = useeList.indexOf(block)
    cut(this, index)
    cut(this, index - 1)
  }

  override fun replicate(): Instruction {
    return PhiInst(
      name!!,
      type,
      getPredList().map { Pair(it.first.duplicate(), it.second.duplicate() as BasicBlock) }
        .toMutableList()
    )
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class BitCastInst(name: String, castee: Value, toTy: Type) : Instruction(toTy, name) {
  val fromTy = castee.type

  init { // castee
    link(this, castee)
  }

  fun getCastee(): Value {
    return useeList[0]
  }

  override fun replicate(): Instruction {
    return BitCastInst(name!!, getCastee().duplicate(), type)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class ZExtInst(name: String, originalVal: Value, toTy: Type) : Instruction(toTy, name) {
  init { // originalVal
    link(this, originalVal)
  }

  fun getOriginalVal(): Value {
    return useeList[0]
  }

  override fun replicate(): Instruction {
    return ZExtInst(name!!, getOriginalVal().duplicate(), type)
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class PCopyInst : Instruction(TypeFactory.getVoidType(), null) {
  val destList = mutableListOf<Value>()

  fun addAssignment(rd: Value, rs: Value) {
    destList.add(rd)
    link(this, rs)
  }

  fun getAssignment(index: Int): Pair<Value, Value> {
    return Pair(destList[index], useeList[index])
  }

  fun getAssignmentList(): List<Pair<Value, Value>> {
    return destList.zip(useeList)
  }

  fun setAssignment(index: Int, dst: Value, src: Value) {
    destList[index] = dst
    cut(this, index)
    useeList[index] = src
    link(this, src)
  }

  override fun replicate(): Instruction {
    val inst = PCopyInst()
    getAssignmentList().forEach { inst.addAssignment(it.first.duplicate(), it.second.duplicate()) }
    return inst
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}

class MvInst(name: String, rs: Value) : Instruction(rs.type, name) {
  init { // rs
    link(this, rs)
  }

  fun getSrc(): Value {
    return useeList[0]
  }

  override fun replicate(): Instruction {
    return MvInst(name!!, getSrc().duplicate())
  }

  override fun accept(irVisitor: IRVisitor) {
    irVisitor.visit(this)
  }
}