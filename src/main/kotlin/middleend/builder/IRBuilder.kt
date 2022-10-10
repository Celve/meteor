package middleend.builder

import middleend.basic.*
import middleend.helper.ValueSymbolTable

object IRBuilder {
  private var func: Func? = null
  private var block: BasicBlock? = null
  private var point: Instruction? = null
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

  /**
   * This function is used for a registered block, whose name would be unique in LLVM representation.
   */
  fun resetInsertBlock(newBlock: BasicBlock) {
    func = newBlock.parent
    block = newBlock
    point = null
  }


  fun setReturnBlock(newBlock: BasicBlock) {
    vst.insertValue(newBlock)
    block = newBlock
    func!!.returnBlock = newBlock
  }

  fun getInsertBlock(): BasicBlock? {
    return block
  }

  fun getReturnBlock(): BasicBlock? {
    return func?.returnBlock
  }

  fun setInsertPoint(inst: Instruction) {
    point = inst
    block = inst.parent
    func = block!!.parent
  }

  private fun addInstAtPoint(inst: Instruction) {
    if (point == null) {
      inst.insertAtTheTailOf(block!!)
    } else {
      val index = point!!.getIndexAtBlock()
      inst.insertAtIndex(block!!, index + 1)
      point = inst
    }
  }

  private fun checki8Toi1(value: Value): Value {
    return if (value.type != TypeFactory.getIntType(1)) {
      createTrunc("trunc", value, TypeFactory.getIntType(1))
    } else {
      value
    }
  }

  private fun checki1Toi8(value: Value): Value {
    return if (value.type == TypeFactory.getIntType(1)) {
      createZExt("zext", value, TypeFactory.getIntType(8))
    } else {
      value
    }
  }

  fun createAlloca(result: String, type: Type): Value {
    val allocaInst = AllocaInst(vst.defineName(result), type)
    allocaInst.insertAtIndex(block!!, block!!.getLastAllocaInstIndex() + 1)
    vst.reinsertValue(allocaInst)
    return allocaInst
  }

  fun createLoad(result: String, ptr: Value): Value {
    val type = (ptr.type as PointerType).pointeeTy!!

    val loadInst = LoadInst(vst.defineName(result), type, ptr)
//    loadInst.insertAtTheTailOf(block!!)
    addInstAtPoint(loadInst)
    vst.reinsertValue(loadInst)

    loadInst.addUsee(ptr)

    return loadInst
  }

  fun createBinary(result: String, op: String, type: Type, lhs: Value, rhs: Value): Value {
    val binaryInst = BinaryInst(vst.defineName(result), op, type, lhs, rhs)
//    binaryInst.insertAtTheTailOf(block!!)
    addInstAtPoint(binaryInst)
    vst.reinsertValue(binaryInst)

    binaryInst.addUsee(lhs)
    binaryInst.addUsee(rhs)

    return binaryInst
  }

  fun createStore(value: Value, ptr: Value): Value {
    val storeInst = StoreInst(checki1Toi8(value), ptr)
//    storeInst.insertAtTheTailOf(block!!)
    addInstAtPoint(storeInst)

    storeInst.addUsee(value)
    storeInst.addUsee(ptr)

    return storeInst
  }

  fun createCmp(result: String, cond: String, type: Type, lhs: Value, rhs: Value): CmpInst {
    val cmpInst = CmpInst(vst.defineName(result), CmpInst.Cond.valueOf(cond), type, lhs, rhs)
//    cmpInst.insertAtTheTailOf(block!!)
    addInstAtPoint(cmpInst)
    vst.reinsertValue(cmpInst)

    cmpInst.addUsee(lhs)
    cmpInst.addUsee(rhs)

    return cmpInst
  }

  fun createTrunc(result: String, originalVal: Value, toTy: Type): TruncInst {
    val truncInst = TruncInst(vst.defineName(result), originalVal, toTy)
//    truncInst.insertAtTheTailOf(block!!)
    addInstAtPoint(truncInst)
    vst.reinsertValue(truncInst)

    truncInst.addUsee(originalVal)

    return truncInst
  }

  fun createRetVoid(): ReturnInst {
    val retInst = ReturnInst(TypeFactory.getVoidType(), null)
//    retInst.insertAtTheTailOf(block!!)
    addInstAtPoint(retInst)
    return retInst
  }

  fun createRet(value: Value): ReturnInst {
    val retInst = ReturnInst(value.type, value)
//    retInst.insertAtTheTailOf(block!!)
    addInstAtPoint(retInst)
    retInst.addUsee(value)
    return retInst
  }

  fun createBr(trueBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(null, trueBlock, null)
//    brInst.insertAtTheTailOf(block!!)
    addInstAtPoint(brInst)
    return brInst
  }

  fun createCall(
    funcType: FuncType,
    args: List<Value>,
    atHead: Boolean = false
  ): CallInst { // TODO: how about call others
    val callInst = if (funcType.result is VoidType) {
      CallInst(null, funcType, args)
    } else {
      val tempInst = CallInst(vst.defineName("call"), funcType, args)
      vst.reinsertValue(tempInst)
      tempInst
    }
    if (atHead) {
      callInst.insertAtTheHeadOf(block!!)
    } else {
      addInstAtPoint(callInst)
    }
    args.forEach { it.addUser(callInst) }
    return callInst
  }

  fun createGEP(op: String, name: String, value: Value, index: Value): GetElementPtrInst {
    val gepInst = GetElementPtrInst(op, vst.defineName(name), value, index)
//    gepInst.insertAtTheTailOf(block!!)
    addInstAtPoint(gepInst)
    vst.reinsertValue(gepInst)

    gepInst.addUsee(value)

    return gepInst
  }

  fun createBr(cond: Value, trueBlock: BasicBlock, falseBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(checki8Toi1(cond), trueBlock, falseBlock)
//    brInst.insertAtTheTailOf(block!!)
    addInstAtPoint(brInst)

    brInst.addUsee(cond)
    brInst.addUsee(trueBlock)
    brInst.addUsee(falseBlock)

    return brInst
  }

  fun createPhi(name: String, type: Type, candidates: MutableList<Pair<Value, BasicBlock>>): PhiInst {
    val phiInst = PhiInst(vst.defineName(name), type, candidates)
//    phiInst.insertAtTheTailOf(block!!)
    addInstAtPoint(phiInst)
    vst.reinsertValue(phiInst)

    candidates.forEach { phiInst.addUsee(it.first) }
    candidates.forEach { phiInst.addUsee(it.second) }

    return phiInst
  }

  fun createBitCast(name: String, castee: Value, toTy: Type): BitCastInst {
    val bitCallInst = BitCastInst(vst.defineName(name), castee, toTy)
//    bitCallInst.insertAtTheTailOf(block!!)
    addInstAtPoint(bitCallInst)
    vst.reinsertValue(bitCallInst)

    bitCallInst.addUsee(castee)

    return bitCallInst
  }

  fun createZExt(name: String, castee: Value, toTy: Type): ZExtInst {
    val zextInst = ZExtInst(vst.defineName(name), castee, toTy)
    addInstAtPoint(zextInst)
    vst.reinsertValue(zextInst)

    zextInst.addUsee(castee)

    return zextInst
  }
}