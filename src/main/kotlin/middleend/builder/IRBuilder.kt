package middleend.builder

import middleend.basic.*
import middleend.helper.ValueSymbolTable

object IRBuilder {
  private var block: BasicBlock? = null
  private var vst = ValueSymbolTable()

  fun setValueSymbolTable(newVst: ValueSymbolTable) {
    vst = newVst
  }

  fun setInsertPoint(newBlock: BasicBlock) {
    block = newBlock
  }

  fun getInsertPoint(): BasicBlock? {
    return block
  }

  fun createAlloca(result: String, type: Type, align: Int): Value {
    val allocaInst = AllocaInst(vst.defineName(result), type, align)
    allocaInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(allocaInst)
    return allocaInst
  }

  fun createLoad(result: String, type: Type, ptr: Value): Value {
    assert(ptr.type is PointerType)

    val loadInst = LoadInst(vst.defineName(result), type, ptr)
    loadInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(loadInst)

    loadInst.addUsee(ptr)

    return loadInst
  }

  fun createBinary(result: String, op: String, type: Type, lhs: Value, rhs: Value): Value {
    val binaryInst = BinaryInst(vst.defineName(result), op, type, lhs, rhs)
    binaryInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(binaryInst)

    binaryInst.addUsee(lhs)
    binaryInst.addUsee(rhs)

    return binaryInst
  }

  fun createStore(type: Type, value: Value, ptr: Value): Value {
    assert(ptr.type is PointerType)

    val storeInst = StoreInst(type, value, ptr)
    storeInst.insertAtTheTailOf(block!!)

    storeInst.addUsee(value)
    storeInst.addUsee(ptr)

    return storeInst
  }

  fun createCmp(result: String, cond: String, type: Type, lhs: Value, rhs: Value): CmpInst {
    val cmpInst = CmpInst(vst.defineName(result), CmpInst.Cond.valueOf(cond), type, lhs, rhs)
    cmpInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(cmpInst)

    cmpInst.addUsee(lhs)
    cmpInst.addUsee(rhs)

    return cmpInst
  }

  fun createTrunc(result: String, originalTy: Type, originalVal: Value, toTy: Type): TruncInst {
    val truncInst = TruncInst(vst.defineName(result), originalTy, originalVal, toTy)
    truncInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(truncInst)

    truncInst.addUsee(originalVal)

    return truncInst
  }

  fun createRetVoid(): ReturnInst {
    val retInst = ReturnInst(TypeFactory.getVoidType(), null)
    retInst.insertAtTheTailOf(block!!)
    return retInst
  }

  fun createRet(type: Type, value: Value): ReturnInst {
    val retInst = ReturnInst(type, value)
    retInst.insertAtTheTailOf(block!!)
    retInst.addUsee(value)
    return retInst
  }

  fun createBr(trueBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(null, trueBlock, null)
    brInst.insertAtTheTailOf(block!!)
    return brInst
  }

  fun createCall(
    name: String?,
    funcType: FuncType,
    args: List<Value>,
    atHead: Boolean = false
  ): CallInst { // TODO: how about call others
    val callInst = CallInst(name, funcType, args)
    if (atHead) {
      callInst.insertAtTheHeadOf(block!!)
    } else {
      callInst.insertAtTheTailOf(block!!)
    }
    args.forEach { it.addUser(callInst) }
    return callInst
  }

  fun createGEP(name: String, varType: StructType, value: Value, index: Int): GetElementPtrInst {
    val gepInst = GetElementPtrInst(name, varType, value, index)
    gepInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(gepInst)

    gepInst.addUsee(value)

    return gepInst
  }
}