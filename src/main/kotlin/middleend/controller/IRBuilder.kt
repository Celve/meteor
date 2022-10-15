package middleend.controller

import middleend.basic.*
import middleend.helper.ValueSymbolTable

object IRBuilder {
  private var func: Func? = null
  private var block: BasicBlock? = null
  private var point: Instruction? = null
  private var vst = ValueSymbolTable()

  fun setCurrentFunc(newFunc: Func) {
    func = newFunc
    vst = newFunc.vst
  }

  fun getCurrentFunc(): Func? {
    return func
  }

  fun getCurrentFuncReturnType(): Type {
    return func!!.funcType.result
  }

  /**
   * This function is used to register and set a block in IR builder.
   */
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
    func!!.returnBlock = newBlock // TODO: optimize this process, which should be encapsulated in a function
    newBlock.parent = func
  }

  fun getInsertBlock(): BasicBlock? {
    return block
  }

  fun getReturnBlock(): BasicBlock? {
    return func?.returnBlock
  }

  fun setInsertPointBefore(inst: Instruction) {
    point = inst
    block = inst.parent
    func = block!!.parent
  }

  fun setInsertPointAfter(inst: Instruction) {
    block = inst.parent
    func = block!!.parent
    point = block!!.instList[inst.getIndexAtBlock() + 1]
  }

  private fun insertInstBeforeInsertPoint(inst: Instruction) {
    if (point == null) {
      inst.insertAtTheTailOf(block!!)
    } else {
      val index = point!!.getIndexAtBlock()
      inst.insertAtIndexOf(block!!, index)
    }
  }

  fun checki8Toi1(value: Value): Value {
    return if (value.type != TypeFactory.getIntType(1)) {
      createTrunc("tobool", value, TypeFactory.getIntType(1))
    } else {
      value
    }
  }

  fun checki1Toi8(value: Value): Value {
    return if (value.type == TypeFactory.getIntType(1)) {
      createZExt("frombool", value, TypeFactory.getIntType(8))
    } else {
      value
    }
  }

  fun createAlloca(result: String, type: Type): Value {
    val allocaInst = AllocaInst(vst.defineName(result), type)
    val allocaBlock = func!!.blockList.first()
    allocaInst.insertAtIndexOf(allocaBlock, allocaBlock.getLastAllocaInstIndex() + 1)
    vst.reinsertValue(allocaInst)
    return allocaInst
  }

  fun createLoad(result: String, ptr: Value): Value {
    val loadInst = LoadInst(vst.defineName(result), ptr)
    insertInstBeforeInsertPoint(loadInst)
    vst.reinsertValue(loadInst)

    loadInst.addUsee(ptr)

    return loadInst
  }

  fun createBinary(result: String, op: String, lhs: Value, rhs: Value): Value {
    val binaryInst = BinaryInst(vst.defineName(result), op, lhs, rhs)
    insertInstBeforeInsertPoint(binaryInst)
    vst.reinsertValue(binaryInst)

    binaryInst.addUsee(lhs)
    binaryInst.addUsee(rhs)

    return binaryInst
  }

  fun createStore(value: Value, ptr: Value): Value {
    val storeInst = StoreInst(checki1Toi8(value), ptr)
    insertInstBeforeInsertPoint(storeInst)

    storeInst.addUsee(value)
    storeInst.addUsee(ptr)

    return storeInst
  }

  fun createCmp(result: String, cond: String, lhs: Value, rhs: Value): CmpInst {
    val cmpInst = CmpInst(vst.defineName(result), CmpInst.Cond.valueOf(cond), lhs, rhs)
    insertInstBeforeInsertPoint(cmpInst)
    vst.reinsertValue(cmpInst)

    cmpInst.addUsee(lhs)
    cmpInst.addUsee(rhs)

    return cmpInst
  }

  fun createTrunc(result: String, originalVal: Value, toTy: Type): TruncInst {
    val truncInst = TruncInst(vst.defineName(result), originalVal, toTy)
    insertInstBeforeInsertPoint(truncInst)
    vst.reinsertValue(truncInst)

    truncInst.addUsee(originalVal)

    return truncInst
  }

  fun createRetVoid(): ReturnInst {
    val retInst = ReturnInst(TypeFactory.getVoidType(), null)
    insertInstBeforeInsertPoint(retInst)
    return retInst
  }

  fun createRet(value: Value): ReturnInst {
    val retInst = ReturnInst(value.type, value)
    insertInstBeforeInsertPoint(retInst)
    retInst.addUsee(value)
    return retInst
  }

  fun createBr(trueBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(null, trueBlock, null)
    insertInstBeforeInsertPoint(brInst)
    return brInst
  }

  fun createCall(funcType: FuncType, args: List<Value>): CallInst { // TODO: how about call others
    val callInst = if (funcType.result is VoidType) {
      CallInst(null, funcType, args)
    } else {
      val tempInst = CallInst(vst.defineName("call"), funcType, args)
      vst.reinsertValue(tempInst)
      tempInst
    }
    insertInstBeforeInsertPoint(callInst)
    args.forEach { it.addUser(callInst) }
    return callInst
  }

  fun createGEP(op: String, name: String, value: Value, index: Value): GetElementPtrInst {
    val gepInst = GetElementPtrInst(op, vst.defineName(name), value, index)
    insertInstBeforeInsertPoint(gepInst)
    vst.reinsertValue(gepInst)

    gepInst.addUsee(value)

    return gepInst
  }

  fun createBr(cond: Value, trueBlock: BasicBlock, falseBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(checki8Toi1(cond), trueBlock, falseBlock)
//    brInst.insertAtTheTailOf(block!!)
    insertInstBeforeInsertPoint(brInst)

    brInst.addUsee(cond)
    brInst.addUsee(trueBlock)
    brInst.addUsee(falseBlock)

    return brInst
  }

  fun createPhi(name: String, type: Type, candidates: MutableList<Pair<Value, BasicBlock>>): PhiInst {
    val phiInst = PhiInst(vst.defineName(name), type, candidates)
//    phiInst.insertAtTheTailOf(block!!)
    insertInstBeforeInsertPoint(phiInst)
    vst.reinsertValue(phiInst)

    candidates.forEach { phiInst.addUsee(it.first) }
    candidates.forEach { phiInst.addUsee(it.second) }

    return phiInst
  }

  fun createBitCast(name: String, castee: Value, toTy: Type): BitCastInst {
    val bitCallInst = BitCastInst(vst.defineName(name), castee, toTy)
//    bitCallInst.insertAtTheTailOf(block!!)
    insertInstBeforeInsertPoint(bitCallInst)
    vst.reinsertValue(bitCallInst)

    bitCallInst.addUsee(castee)

    return bitCallInst
  }

  fun createZExt(name: String, castee: Value, toTy: Type): ZExtInst {
    val zextInst = ZExtInst(vst.defineName(name), castee, toTy)
    insertInstBeforeInsertPoint(zextInst)
    vst.reinsertValue(zextInst)

    zextInst.addUsee(castee)

    return zextInst
  }
}