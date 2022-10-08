package middleend.builder

import middleend.basic.*
import middleend.helper.ValueSymbolTable

object IRBuilder {
  private var func: Func? = null
  private var block: BasicBlock? = null
  private var vst = ValueSymbolTable()

  fun setCurrentFunc(newFunc: Func) {
    func = newFunc
  }

  fun getCurrentFunc(): Func? {
    return func
  }

  fun getCurrentFuncReturnType(): Type {
    return func!!.funcType.result
  }

  fun setValueSymbolTable(newVst: ValueSymbolTable) {
    vst = newVst
  }

  fun setInsertBlock(newBlock: BasicBlock) {
    vst.insertValue(newBlock)
    block = newBlock
    block!!.insertAtTheTailOf(func!!)
  }

  fun getInsertBlock(): BasicBlock? {
    return block
  }

  fun createAlloca(result: String, type: Type): Value {
    val allocaInst = AllocaInst(vst.defineName(result), type)
    allocaInst.insertAtIndex(block!!, block!!.getLastAllocaInstIndex() + 1)
    vst.reinsertValue(allocaInst)
    return allocaInst
  }

  fun createLoad(result: String, ptr: Value): Value {
    assert(ptr.type is PointerType)
    val type = (ptr.type as PointerType).pointeeTy!!

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
    println(type)
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
    val gepInst = GetElementPtrInst(vst.defineName(name), varType, value, index)
    gepInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(gepInst)

    gepInst.addUsee(value)

    return gepInst
  }

  fun createGEP(name: String, varType: PointerType, value: Value, index: Value): GetElementPtrInst {
    val gepInst = GetElementPtrInst(vst.defineName(name), varType, value, index)
    gepInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(gepInst)

    gepInst.addUsee(value)

    return gepInst
  }

  fun createBr(cond: Value, trueBlock: BasicBlock, falseBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(cond, trueBlock, falseBlock)
    brInst.insertAtTheTailOf(block!!)

    brInst.addUsee(cond)
    brInst.addUsee(trueBlock)
    brInst.addUsee(falseBlock)

    return brInst
  }

  fun createPhi(name: String, type: Type, candidates: List<Pair<Value, BasicBlock>>): PhiInst {
    val phiInst = PhiInst(vst.defineName(name), type, candidates)
    phiInst.insertAtTheTailOf(block!!)
    vst.reinsertValue(phiInst)

    candidates.forEach { phiInst.addUsee(it.first) }
    candidates.forEach { phiInst.addUsee(it.second) }

    return phiInst
  }
}