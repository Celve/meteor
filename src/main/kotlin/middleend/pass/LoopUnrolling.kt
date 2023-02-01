package middleend.pass

import middleend.basic.*
import middleend.helper.Utils
import middleend.struct.Loop
import kotlin.math.min

// a clear and easy to understand version of loop unrolling
object LoopUnrolling : IRVisitor() {
  private val disabled = hashSetOf<BasicBlock>()
  private lateinit var currModule: TopModule
  private lateinit var currFunc: Func
  private var valueTable = hashMapOf<String, Value>()
  private const val maxRepeatedTimes = 150
  private const val maxLoopInstSize = 1500

  override fun visit(topModule: TopModule) {
    currModule = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun rename(name: String): String {
    return currFunc.ssaTable.rename(Utils.eliminateVersionWithDot(name))
  }

  // process all blocks except the last block
  private fun createSlice(loop: Loop, times: Int): Triple<Slice, Slice, BasicBlock?> {
    val allSlice = Slice(mutableListOf())
    val headerBlock = loop.headerBlock
    val allBlocks = loop.allBlocks
    val headerIndex = allBlocks.indexOf(headerBlock)
    val exitingIndex = allBlocks.indexOf(loop.exitingBlocks.first())
    var lastExitingBlock: BasicBlock? = null
    lateinit var newSlice: Slice
    repeat(times) {
      newSlice = Slice(loop.allBlocks.map { it.replicate() as BasicBlock }.toMutableList())
      newSlice.rename { rename(it) }

      // phi inst inside header block should be modified
      val currExitingBlock = newSlice.blockList[exitingIndex]
      val phiInstList = newSlice.blockList[headerIndex].instList.filterIsInstance<PhiInst>()
      if (lastExitingBlock != null) {
        phiInstList.forEach { inst ->
          inst.replaceAll {
            if (it == currExitingBlock) {
              lastExitingBlock!!
            } else if (it is Instruction) {
              allSlice.valueTable[newSlice.nameTable[it]!!]!!
            } else {
              it
            }
          }
        }
      }

      lastExitingBlock?.let {
        // the cmp inst used by br inst should be eliminated
        val cmpInst = (it.getTerminator() as BranchInst).getCond() as CmpInst

        // branch inst should be converted into jump inst
        it.replaceBrInst(BranchInst(newSlice.blockList[headerIndex], null, null))

        it.instList.remove(cmpInst)
        cmpInst.eliminate()
      }
      lastExitingBlock = currExitingBlock

      allSlice.concat(newSlice)
    }

    // only after it would the end block be reasonable
    allSlice.resolve(valueTable)

    return Triple(allSlice, newSlice, lastExitingBlock)
  }

  private fun unroll(loop: Loop, times: Int) {
    val (allSlice, lastSlice, lastExitingBlock) = createSlice(loop, times)
    val allBlocks = loop.allBlocks
    val headerBlock = loop.headerBlock
    val headerIndex = loop.allBlocks.indexOf(headerBlock)

    // don't forget the last block
    lastExitingBlock?.let {
      val brInst = it.getTerminator() as BranchInst
      val cmpInst = brInst.getCond() as CmpInst
      val trueBlock = brInst.getTrueBlock()
      val falseBlock = brInst.getFalseBlock()!!
      if (trueBlock == lastSlice.blockList[headerIndex]) {
        it.replaceBrInst(BranchInst(falseBlock, null, null))
      } else {
        it.replaceBrInst(BranchInst(trueBlock, null, null))
      }
      it.instList.remove(cmpInst)
      cmpInst.eliminate()
    }

    val allHeaderBlock = allSlice.blockList[headerIndex]
    headerBlock.substitutedByWhen(allHeaderBlock) { it is BranchInst && it.parent !in allBlocks }
    headerBlock.prevBlockSet.filter { it !in allBlocks }.forEach {
      it.removeNextBlock(headerBlock)
      it.addNextBlock(allHeaderBlock)
      allHeaderBlock.addPrevBlock(it)
    }

    // remove pred in header block's phi insts
    allHeaderBlock.instList.filterIsInstance<PhiInst>().forEach { it.removePred(allHeaderBlock) }

    // insertion
    val anchorBlock = currFunc.blockList[currFunc.getIndexOfBlock(allBlocks.first()) - 1]
    currFunc.blockList.removeAll(allBlocks)
    currFunc.blockList.addAll(currFunc.getIndexOfBlock(anchorBlock) + 1, allSlice.blockList)
    allSlice.blockList.forEach { it.parent = currFunc }

    allBlocks.forEach { block ->
      block.instList.filter { it.isDef() }.forEach {
        val sub = allSlice.valueTable[it.name!!]
        if (sub != null) {
          it.substitutedBy(sub)
          if (sub is Instruction) {
            valueTable[sub.name!!] = sub
          }
        }
      }
      block.substitutedBy(allSlice.valueTable[block.name!!]!!)
    }

    allBlocks.flatMap { it.instList }.forEach {
      if (it is BranchInst) {
        it.parent.removeBrInst(it)
      }
      it.eliminate()
    }

    val phiInstList = allSlice.blockList.flatMap { it.instList.filterIsInstance<PhiInst>() }
    phiInstList.forEach { inst ->
      inst.getPredList().map { it.second }.filter { it !in inst.parent.prevBlockSet }.forEach { inst.removePred(it) }
    }
    phiInstList.filter { it.getPredList().size == 1 }.forEach {
      it.parent.replaceInst(it, MvInst(rename(it.name!!), it.useeList.first()))
    }

//    currFunc.blockList.forEach {
//      println("${it.prevBlockSet} ${it.nextBlockSet}")
//      IREmit.visit(it)
//    }
  }

  private fun pow(op: String, iv: Value, const: ConstantInt, times: Int): BinaryInst {
    return BinaryInst(
      currFunc.mulTable.rename(".$op"), op, iv, when (op) {
        "add", "sub" -> const * times
        "mul", "sdiv" -> const.pow(times)
        "srem" -> const
        else -> throw Exception("unsupported op: $op")
      }
    )
  }

  private fun createPreHeader(
    phiInst: PhiInst,
    phiInsts: List<PhiInst>,
    binInst: BinaryInst,
    cmpInst: CmpInst,
    brInst: BranchInst,
    loop: Loop,
    bodyBlock: BasicBlock,
    endBlock: BasicBlock,
    times: Int
  ): BasicBlock {
    val preHeaderBlock =
      BasicBlock(currFunc.mulTable.rename("preheader"), loop.headerBlock.execFreq / 10) // freq doesn't matter
    val newPhiInsts = phiInsts.map { it.replicate() as PhiInst }
    val newPhiInst = newPhiInsts.find { it.name == phiInst.name }!!
//    val newBinInst = binInst.replicate() as BinaryInst
    val newBinInst = pow(binInst.op, newPhiInst, binInst.useeList.filterIsInstance<ConstantInt>().first(), times)
    val newCmpInst = cmpInst.replicate() as CmpInst
    val newBrInst = brInst.replicate() as BranchInst
    val undefined = mutableListOf<Value>()
    newPhiInsts.forEach { preHeaderBlock.addInst(it) }
    preHeaderBlock.addInst(newBinInst)
    preHeaderBlock.addInst(newCmpInst)
    preHeaderBlock.addInst(newBrInst)

    // for phi, remove pred
    newPhiInsts.forEach { inst ->
      inst.getBlockList().filter { it.name == loop.exitingBlocks.first().name }.forEach { inst.removePred(it) }
    }
    newPhiInsts.forEach { inst -> undefined.addAll(inst.useeList.filter { it.isDef() }) }

    // for cmp, replace bin
    newCmpInst.replaceAll {
      if (it.isDef() && it.name == binInst.name) {
        newBinInst
      } else {
        undefined.add(it)
        it
      }
    }

    // for br, replace cmp
    newBrInst.replaceAll {
      if (it is BasicBlock) {
        if (it.name == loop.headerBlock.name) {
          bodyBlock
        } else {
          endBlock
        }
      } else {
        newCmpInst
      }
    }

    preHeaderBlock.instList.filter { it.isDef() }.forEach { it.name = rename(it.name!!) }
    undefined.forEach { it.substitutedBy(valueTable[it.name!!]!!) }
    return preHeaderBlock
  }

  private fun unroll(loop: Loop, cycle: Pair<PhiInst, BinaryInst>, times: Int) {
    val (allSlice, lastSlice, lastExitingBlock) = createSlice(loop, times)
    val (addSlice, _, addExitingBlock) = createSlice(loop, 1)
    val allBlocks = loop.allBlocks
    val headerBlock = loop.headerBlock
    val headerIndex = allBlocks.indexOf(headerBlock)
    val exitingBlock = loop.exitingBlocks.first()
    val exitingIndex = allBlocks.indexOf(exitingBlock)
    val firstExitingBlock = allSlice.blockList[exitingIndex]

    val initPhiInst = cycle.first
    val allPhiInsts =
      headerBlock.instList.filterIsInstance<PhiInst>().filter { it.getBlockList().contains(exitingBlock) }
    val initBinInst = cycle.second
    val initBrInst = exitingBlock.getTerminator() as BranchInst
    val initCmpInst = initBrInst.getCond() as CmpInst
    val isLeft = initCmpInst.getLhs() == initBinInst

    // don't forget the last block
    lastExitingBlock!!.let { block ->
      val brInst = block.getTerminator() as BranchInst
      brInst.replaceAll {
        when (it) {
          lastSlice.blockList[headerIndex] -> allSlice.blockList[headerIndex]
          is BasicBlock -> addSlice.blockList[headerIndex]// it must point to the exiting block of last slice
          else -> it
        }
      }

      // get the correct cmp inst
      val cmpInst = brInst.getCond() as CmpInst
      val binaryInst = (if (isLeft) cmpInst.getLhs() else cmpInst.getRhs()) as BinaryInst
      val const = binaryInst.useeList.filterIsInstance<ConstantInt>().first()
      val newBinaryInst = pow(binaryInst.op, binaryInst, const, times)
      block.addInst(block.instList.indexOf(cmpInst), newBinaryInst)
      cmpInst.replaceAll { if (it == binaryInst) newBinaryInst else it }
    }

    // for block graph
    addExitingBlock?.let {
      val brInst = it.getTerminator() as BranchInst
      it.replaceBrInst(BranchInst(brInst.getTrueBlock(), brInst.getCond(), brInst.getFalseBlock()))
    }

    // and the last block of add slice don't need to modify

    // the pre-header block
    val allHeaderBlock = allSlice.blockList[headerIndex]
    val addHeaderBlock = addSlice.blockList[headerIndex]
    val preHeaderBlock = createPreHeader(
      initPhiInst, allPhiInsts, initBinInst, initCmpInst, initBrInst, loop, allHeaderBlock, addHeaderBlock, times
    )

    // modify br insts that precedes the loop
    headerBlock.substitutedByWhen(preHeaderBlock) { it is BranchInst && it.parent !in allBlocks }
    headerBlock.prevBlockSet.filter { it !in allBlocks }.forEach {
      it.removeNextBlock(headerBlock)
      it.addNextBlock(preHeaderBlock)
      preHeaderBlock.addPrevBlock(it)
    }

    // init phi table, which contains all phi instruction in the unrolled loop
    val ivTable = hashMapOf<Pair<BasicBlock, Int>, Instruction>()
    preHeaderBlock.instList.filterIsInstance<PhiInst>()
      .forEach { ivTable[preHeaderBlock to preHeaderBlock.getIndexOfInst(it)] = it }
    allHeaderBlock.instList.filterIsInstance<PhiInst>().filter { it.getBlockList().contains(firstExitingBlock) }
      .forEach {
        val originName = allSlice.nameTable[it.getPred(firstExitingBlock)!!.first as Instruction]!!
        ivTable[it.parent to it.parent.getIndexOfInst(it)] = allSlice.valueTable[originName] as Instruction
      }

//    allSlice.blockList.forEach { IREmit.visit(it) }
//    println(allSlice.nameTable)

    // modify parts of the phi instructions
    allHeaderBlock.instList.filterIsInstance<PhiInst>().forEach { inst ->
      inst.replaceAll {
        if (it == firstExitingBlock) {
          lastSlice.blockList[exitingIndex] // modify the back edge
        } else if (it is Instruction && allSlice.nameTable.contains(it)) {
          allSlice.valueTable[allSlice.nameTable[it]]!! // replaced by the final value
        } else {
          it // wait for processing or constant
        }
      }
    }

    allHeaderBlock.instList.filterIsInstance<PhiInst>().forEach { inst ->
      // remove all values irrelated
      inst.getBlockList().filter { it !in inst.parent.prevBlockSet }.forEach { inst.removePred(it) }

      // add assignment from pre-header
      inst.addPred(ivTable[preHeaderBlock to allHeaderBlock.getIndexOfInst(inst)]!!, preHeaderBlock)
    }

    // for phi inst in add header block
    addHeaderBlock.instList.filterIsInstance<PhiInst>().filter { it.getBlockList().contains(addExitingBlock) }
      .forEach { inst ->
        // remove all values from outside
        inst.getBlockList().filter { it !in inst.parent.prevBlockSet }.forEach { inst.removePred(it) }

        // add assignment from pre-header
        inst.addPred(ivTable[preHeaderBlock to addHeaderBlock.getIndexOfInst(inst)]!!, preHeaderBlock)
        inst.addPred(ivTable[allHeaderBlock to addHeaderBlock.getIndexOfInst(inst)]!!, lastExitingBlock)
      }

    // concatenate two slices
    allSlice.concat(addSlice)

    // insertion
    val anchorBlock = currFunc.blockList[currFunc.getIndexOfBlock(allBlocks.first()) - 1]
    currFunc.blockList.removeAll(allBlocks)
    val anchorIndex = currFunc.getIndexOfBlock(anchorBlock)
    currFunc.blockList.add(anchorIndex + 1, preHeaderBlock)
    currFunc.blockList.addAll(anchorIndex + 2, allSlice.blockList)
    preHeaderBlock.parent = currFunc
    allSlice.blockList.forEach { it.parent = currFunc }

    allBlocks.forEach { block ->
      block.instList.filter { it.isDef() }.forEach {
        val sub = allSlice.valueTable[it.name!!]
        if (sub != null) {
          it.substitutedBy(sub)
          if (sub is Instruction) {
            valueTable[sub.name!!] = sub
          }
        }
      }
      block.substitutedBy(allSlice.valueTable[block.name!!]!!)
    }

    allBlocks.flatMap { it.instList }.forEach {
      if (it is BranchInst) {
        it.parent.removeBrInst(it)
      }
      it.eliminate()
    }

    val phiInstList =
      allSlice.blockList.flatMap { it.instList.filterIsInstance<PhiInst>() } + preHeaderBlock.instList.filterIsInstance<PhiInst>()
    phiInstList.forEach { inst ->
      inst.getPredList().map { it.second }.filter { it !in inst.parent.prevBlockSet }.forEach { inst.removePred(it) }
    }
    phiInstList.filter { it.getPredList().size == 1 }.forEach {
      it.parent.replaceInst(it, MvInst(rename(it.name!!), it.useeList.first()))
    }

    disabled.addAll(allSlice.blockList)

//    currFunc.blockList.forEach {
//      println("${it.prevBlockSet} ${it.nextBlockSet}")
//      IREmit.visit(it)
//    }
  }

  private fun calcRepeatTime(phiInst: PhiInst, binaryInst: BinaryInst, cmpInst: CmpInst, endCond: Int): Int {
    var phiVal = phiInst.useeList.filterIsInstance<ConstantInt>().first().value
    val binaryOp = binaryInst.op
    val (binLeft, binVal) = if (binaryInst.getLhs() is ConstantInt) {
      true to (binaryInst.getLhs() as ConstantInt).value
    } else {
      false to (binaryInst.getRhs() as ConstantInt).value
    }
    val cmpOp = cmpInst.cond
    val (cmpLeft, cmpVal) = if (cmpInst.getLhs() is ConstantInt) {
      true to (cmpInst.getLhs() as ConstantInt).value
    } else {
      false to (cmpInst.getRhs() as ConstantInt).value
    }
    var cnt = 0
    do {
      ++cnt
      phiVal = if (binLeft) {
        Utils.calculate(binaryOp, binVal, phiVal)
      } else {
        Utils.calculate(binaryOp, phiVal, binVal)
      }

      val cond = if (cmpLeft) {
        Utils.calculate(cmpOp, cmpVal, phiVal)
      } else {
        Utils.calculate(cmpOp, phiVal, cmpVal)
      }

      if (cond == endCond) {
        break
      }
    } while (cnt <= maxRepeatedTimes)
    return cnt
  }

  private fun getCycle(inst: Value): Pair<PhiInst, BinaryInst> {
    return if (inst is PhiInst) {
      inst to inst.useeList.filterIsInstance<BinaryInst>().first()
    } else if (inst is BinaryInst) {
      inst.useeList.filterIsInstance<PhiInst>().first() to inst
    } else {
      throw Exception("Unexpected instruction: $inst")
    }
  }

  private fun isIV(inst: Value): Boolean {
    if (inst is PhiInst) {
      val valueList = inst.getPredList().map { it.first }
      if (valueList.size == 2 && valueList.any { it is ConstantInt } && valueList.any { it !is ConstantInt }) {
        val inc = valueList.find { it !is ConstantInt }!!
        return inc is BinaryInst && inc.useeList.contains(inst) && inc.useeList.any { it is ConstantInt }
      }
    } else {
      return isEnhancedIV(inst)
    }
    return false
  }

  private fun isEnhancedIV(inst: Value): Boolean {
    if (inst is BinaryInst) {
      if (inst.getLhs() is Constant || inst.getRhs() is Constant) {
        val phiInst = inst.useeList.filterIsInstance<PhiInst>().firstOrNull()
        if (phiInst != null) {
          val valueList = phiInst.getPredList().map { it.first }
          return valueList.size == 2 && valueList.any { it is ConstantInt } && valueList.contains(inst)
        }
      }
    }
    return false
  }

  private fun attempt(loop: Loop): Boolean {
    var flag = false
    loop.succLoops.sortedBy { currFunc.blockList.indexOf(it.headerBlock) }.forEach {
      flag = flag or attempt(it)
    }
    if (flag) {
      return true
    }

    if (loop.exitingBlocks.size == 1) {
      val exitingBlock = loop.exitingBlocks.first()
      val brInst = exitingBlock.getTerminator() as BranchInst
      val cmpInst = brInst.getCond()!!
      if (cmpInst is CmpInst) { // as long as it's a loop, the assertion is true
        val lhs = cmpInst.getLhs()
        val rhs = cmpInst.getRhs()
        if (lhs is ConstantInt || rhs is ConstantInt) {
          val iv = if (lhs is ConstantInt) rhs else lhs
          if (isIV(iv)) {
            val cycle = getCycle(iv)
            val endCond = loop.allBlocks.contains(brInst.getFalseBlock()!!).compareTo(false)
            val times = calcRepeatTime(cycle.first, cycle.second, cmpInst, endCond)
            if (times <= maxRepeatedTimes && times * loop.allBlocks.sumOf { it.instList.size } <= maxLoopInstSize) {
              unroll(loop, times)
              return true
            }
          }
        } else if (exitingBlock !in disabled) {
          val (iv, const) = when {
            isEnhancedIV(lhs) -> lhs to rhs
            isEnhancedIV(rhs) -> rhs to lhs
            else -> return false
          }
          if (const !is Instruction || const.parent !in loop.allBlocks) {
            val times = min(10, maxLoopInstSize / loop.allBlocks.sumOf { it.instList.size })
            if (times > 1) {
              unroll(loop, getCycle(iv), times)
            }
            return true
          }
        }
      }
    }
    return false
  }

  override fun visit(func: Func) {
    currFunc = func
    valueTable.clear()
    currModule.globalVarMap.values.forEach { valueTable[it.name!!] = it }
    currModule.constStrMap.values.forEach { valueTable[it.name!!] = it }
    func.argList.forEach { valueTable[it.name!!] = it }
    func.blockList.flatMap { it.instList.filter { it.isDef() } }.forEach { valueTable[it.name!!] = it }
    func.blockList.forEach { valueTable[it.name!!] = it }
    func.loopNestTree.build()
    func.loopNestTree.roots.sortedBy { func.blockList.indexOf(it.headerBlock) }.forEach { attempt(it) }
  }

  override fun visit(block: BasicBlock) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: AllocaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: LoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BinaryInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BranchInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: GetElementPtrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ZExtInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: TruncInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: StoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ReturnInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PCopyInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: MvInst) {
    TODO("Not yet implemented")
  }
}