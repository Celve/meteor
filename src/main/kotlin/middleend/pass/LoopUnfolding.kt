package middleend.pass

import middleend.basic.*
import middleend.helper.Utils
import middleend.struct.Loop

// a clear and easy to understand version of loop unfolding
object LoopUnfolding : IRVisitor() {
  private lateinit var currModule: TopModule
  private lateinit var currFunc: Func
  private var valueTable = hashMapOf<String, Value>()

  override fun visit(topModule: TopModule) {
    currModule = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun rename(name: String): String {
    return currFunc.ssaTable.rename(Utils.eliminateVersionWithDot(name))
  }

  private fun unfold(loop: Loop, times: Int) {
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
      } else {
        phiInstList.forEach { it.removePred(currExitingBlock) }
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

    // don't forget the last block
    lastExitingBlock?.let {
      val brInst = it.getTerminator() as BranchInst
      val cmpInst = brInst.getCond() as CmpInst
      val trueBlock = brInst.getTrueBlock()
      val falseBlock = brInst.getFalseBlock()!!
      if (trueBlock == newSlice.blockList[headerIndex]) {
        it.replaceBrInst(BranchInst(falseBlock, null, null))
      } else {
        it.replaceBrInst(BranchInst(trueBlock, null, null))
      }
      it.instList.remove(cmpInst)
      cmpInst.eliminate()
    }

    val newHeaderBlock = allSlice.blockList[headerIndex]
    headerBlock.substitutedByWhen(newHeaderBlock) { it is BranchInst && it.parent !in allBlocks }
    headerBlock.prevBlockSet.forEach {
      it.removeNextBlock(headerBlock)
      it.addNextBlock(newHeaderBlock)
      newHeaderBlock.addPrevBlock(it)
    }

    val anchorBlock = currFunc.blockList[currFunc.getIndexOfBlock(allBlocks.first()) - 1]
    currFunc.blockList.removeAll(allBlocks)
    currFunc.blockList.addAll(currFunc.getIndexOfBlock(anchorBlock) + 1, allSlice.blockList)
    allSlice.blockList.forEach { it.parent = currFunc }
    headerBlock.substitutedByWhen(allSlice.blockList.first()) { it is BranchInst }

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
//    currFunc.blockList.forEach { println("${it.hashCode()} ${it.prevBlockSet} ${it.nextBlockSet}");IREmit.visit(it) }
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
    } while (cnt <= 150)
    return cnt
  }

  private fun getCycle(inst: Value): List<Instruction> {
    return if (inst is PhiInst) {
      listOf(inst, inst.useeList.filterIsInstance<BinaryInst>().first())
    } else if (inst is BinaryInst) {
      listOf(inst.useeList.filterIsInstance<PhiInst>().first(), inst)
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
    } else if (inst is BinaryInst) {
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
        val iv = when {
          lhs is ConstantInt -> rhs
          rhs is ConstantInt -> lhs
          else -> return false
        }
        if (isIV(iv)) {
          val cycle = getCycle(iv)
          val endCond = loop.allBlocks.contains(brInst.getFalseBlock()!!).compareTo(false)
          val times = calcRepeatTime(cycle[0] as PhiInst, cycle[1] as BinaryInst, cmpInst, endCond)
          if (times <= 150 && times * loop.allBlocks.sumOf { it.instList.size } <= 1500) {
            unfold(loop, times)
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