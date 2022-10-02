package middleend.builder

import middleend.basic.*
import middleend.helper.Suffixed
import middleend.helper.ValueSymbolTable

object IRBuilder {
  private var block: BasicBlock? = null
  private var vst = ValueSymbolTable()

  fun setValueSymbolTable(newVst: ValueSymbolTable) {
    vst = newVst
  }

  fun setInsertPoint(newBlock: BasicBlock) {
    println("get changed")
    block = newBlock
  }

  fun getInsertPoint(): BasicBlock? {
    return block
  }

  fun createAlloca(result: Suffixed, type: Type, align: Int): Value {
    val allocaInst = AllocaInst(vst.defineTwine(result), type, align)
    allocaInst.insertAtTail(block!!)
    vst.defineValue(allocaInst)
    return allocaInst
  }

  fun createLoad(result: Suffixed, type: Type, ptr: Value): Value {
    assert(ptr.type is PointerType)

    val loadInst = LoadInst(vst.defineTwine(result), type, ptr)
    loadInst.insertAtTail(block!!)
    vst.defineValue(loadInst)

    loadInst.addUsee(ptr)

    return loadInst
  }

  fun createBinary(result: Suffixed, op: String, type: Type, lhs: Value, rhs: Value): Value {
    val binaryInst = BinaryInst(vst.defineTwine(result), op, type, lhs, rhs)
    binaryInst.insertAtTail(block!!)
    vst.defineValue(binaryInst)

    binaryInst.addUsee(lhs)
    binaryInst.addUsee(rhs)

    return binaryInst
  }

  fun createStore(type: Type, value: Value, ptr: Value): Value {
    assert(ptr.type is PointerType)

    val storeInst = StoreInst(type, value, ptr)
    storeInst.insertAtTail(block!!)

    storeInst.addUsee(value)
    storeInst.addUsee(ptr)

    return storeInst
  }

  fun createCmp(result: Suffixed, cond: String, type: Type, lhs: Value, rhs: Value): CmpInst {
    val cmpInst = CmpInst(vst.defineTwine(result), CmpInst.Cond.valueOf(cond), type, lhs, rhs)
    cmpInst.insertAtTail(block!!)
    vst.defineValue(cmpInst)

    cmpInst.addUsee(lhs)
    cmpInst.addUsee(rhs)

    return cmpInst
  }

  fun createTrunc(result: Suffixed, originalTy: Type, originalVal: Value, toTy: Type): TruncInst {
    val truncInst = TruncInst(vst.defineTwine(result), originalTy, originalVal, toTy)
    truncInst.insertAtTail(block!!)
    vst.defineValue(truncInst)

    truncInst.addUsee(originalVal)

    return truncInst
  }

  fun debug() {
    println(block)
  }
}