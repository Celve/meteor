package middleend.controller

import middleend.basic.*

object IRBuilder {
  private var func: Func? = null
  private var block: BasicBlock? = null
  private var point: Instruction? = null
  private var returnBlock: BasicBlock? = null
  private var isBefore = false
  private val name2Version = hashMapOf<String, Int>().withDefault { 0 }

  fun setCurrentFunc(newFunc: Func) {
    func = newFunc
    returnBlock = null
  }

  fun getCurrentFunc(): Func? {
    return func
  }

  fun getCurrentFuncReturnType(): Type {
    return func!!.funcType.resultType
  }

  /**
   * insert a block into func's block list and switch control flow to it
   */
  fun setInsertBlock(newBlock: BasicBlock) {
    block = newBlock
    block!!.insertAtTheTailOf(func!!)
    point = null
  }

  /**
   * simply switch to the block without inserting
   */
  fun resetInsertBlock(newBlock: BasicBlock) {
    func = newBlock.parent
    block = newBlock
    point = null
  }


  fun setReturnBlock(newBlock: BasicBlock) {
    returnBlock = newBlock // TODO: optimize this process, which should be encapsulated in a function
  }

  fun getInsertBlock(): BasicBlock? {
    return block
  }

  fun getReturnBlock(): BasicBlock? {
    return returnBlock
  }

  fun setInsertPointBefore(inst: Instruction) {
    point = inst
    block = inst.parent
    func = block!!.parent
    isBefore = true
  }

  fun setInsertPointAfter(inst: Instruction) {
    block = inst.parent
    func = block!!.parent
//    point = block!!.instList[inst.getIndexAtBlock() + 1]
    point = inst
    isBefore = false
  }

  private fun insertInstBeforeInsertPoint(inst: Instruction) {
    if (point == null) {
      inst.insertAtTheTailOf(block!!)
    } else {
      val index = point!!.getIndexAtBlock()
      if (isBefore) {
        inst.insertAtIndexOf(block!!, index)
      } else {
        inst.insertAtIndexOf(block!!, index + 1)
      }
    }
  }

  /**
   * This function is especially used for converting i8 bool to i1 bool.
   */
  fun checki8Toi1(value: Value): Value {
    return if (value.type != TypeFactory.getIntType(1)) {
      createTrunc("tobool", value, TypeFactory.getIntType(1))
    } else {
      value
    }
  }

  /**
   * This function is especially used for converting i1 bool to i8 bool.
   */
  fun checki1Toi8(value: Value): Value {
    return if (value.type == TypeFactory.getIntType(1)) {
      createZExt("frombool", value, TypeFactory.getIntType(8))
    } else {
      value
    }
  }

  fun createAlloca(result: String, type: Type): Value {
    val allocaInst = AllocaInst(result, type)
    val allocaBlock = func!!.blockList.first()
    allocaInst.insertAtIndexOf(allocaBlock, allocaBlock.getLastAllocaInstIndex() + 1)
    return allocaInst
  }

  fun createLoad(result: String, ptr: Value): Value {
    val loadInst = LoadInst(result, ptr)
    insertInstBeforeInsertPoint(loadInst)
    return loadInst
  }

  fun createBinary(result: String, op: String, lhs: Value, rhs: Value): Value {
    val binaryInst = BinaryInst(result, op, lhs, rhs)
    insertInstBeforeInsertPoint(binaryInst)
    return binaryInst
  }

  fun createStore(value: Value, ptr: Value): Value {
    val storeInst = StoreInst(checki1Toi8(value), ptr)
    insertInstBeforeInsertPoint(storeInst)
    return storeInst
  }

  fun createCmp(name: String, cond: String, lhs: Value, rhs: Value): CmpInst {
    val cmpInst = CmpInst(name, cond, lhs, rhs)
    insertInstBeforeInsertPoint(cmpInst)
    return cmpInst
  }

  fun createTrunc(result: String, originalVal: Value, toTy: Type): TruncInst {
    val truncInst = TruncInst(result, originalVal, toTy)
    insertInstBeforeInsertPoint(truncInst)
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
    return retInst
  }

  fun createBr(trueBlock: BasicBlock): BranchInst? {
    return if (!block!!.hasTerminator()) {
      val brInst = BranchInst(trueBlock, null, null)
      insertInstBeforeInsertPoint(brInst)

      // build control flow graph
      block!!.addNextBlock(trueBlock)
      trueBlock.addPrevBlock(block!!)

      brInst
    } else {
      null
    }
  }

  fun createCall(name: String?, func: Func, args: List<Value>): CallInst { // TODO: how about call others
    val callInst = CallInst(name, func, args)
    insertInstBeforeInsertPoint(callInst)
    return callInst
  }

  fun createGEP(op: String, name: String, value: Value, index: Value, offset: ConstantInt?): GetElementPtrInst {
    val gepInst = GetElementPtrInst(op, name, value, index, offset)
    insertInstBeforeInsertPoint(gepInst)
    return gepInst
  }

  fun createBr(cond: Value, trueBlock: BasicBlock, falseBlock: BasicBlock): BranchInst {
    val brInst = BranchInst(trueBlock, checki8Toi1(cond), falseBlock)
//    brInst.insertAtTheTailOf(block!!)
    insertInstBeforeInsertPoint(brInst)

    // build control flow graph
    block!!.addNextBlock(trueBlock)
    trueBlock.addPrevBlock(block!!)
    block!!.addNextBlock(falseBlock)
    falseBlock.addPrevBlock(block!!)
    return brInst
  }

  fun createPhi(name: String, type: Type, candidates: MutableList<Pair<Value, BasicBlock>>): PhiInst {
    val phiInst = PhiInst(name, type, candidates)
//    phiInst.insertAtTheTailOf(block!!)
    insertInstBeforeInsertPoint(phiInst)
    return phiInst
  }

  fun createBitCast(name: String, castee: Value, toTy: Type): BitCastInst {
    val bitCallInst = BitCastInst(name, castee, toTy)
//    bitCallInst.insertAtTheTailOf(block!!)
    insertInstBeforeInsertPoint(bitCallInst)
    return bitCallInst
  }

  fun createZExt(name: String, castee: Value, toTy: Type): ZExtInst {
    val zextInst = ZExtInst(name, castee, toTy)
    insertInstBeforeInsertPoint(zextInst)
    return zextInst
  }
}