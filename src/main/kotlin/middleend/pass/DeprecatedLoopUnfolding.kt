package middleend.pass

import middleend.basic.*
import middleend.helper.Utils
import middleend.struct.IndVar
import middleend.struct.Loop

// this optimization requires strength reduction
object DeprecatedLoopUnfolding : IRVisitor() {
  private lateinit var currModule: TopModule
  private lateinit var currFunc: Func
  private lateinit var indVar: IndVar
  private val valueMap = hashMapOf<String, Value>()
  private val mul2Mul = hashMapOf<String, String>()

  override fun visit(topModule: TopModule) {
    currModule = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun rename(name: String): String {
    val withoutSSA = Utils.eliminateVersionWithDot(name)
    return currFunc.ssaTable.rename(withoutSSA)
  }

  /**
   * @return true if the loop is ended
   */
  private fun checkBrInst(block: BasicBlock, loopBlock: BasicBlock, outBlock: BasicBlock): Boolean {
    val brInst = block.getTerminator() as BranchInst
    val cond = valueMap[brInst.getCond()!!.name]
    val result = if (cond is CmpInst) {
      Utils.calculate(cond.cond, (cond.getLhs() as ConstantInt).value, (cond.getRhs() as ConstantInt).value)
    } else {
      throw Exception("Unexpected condition: $cond")
    }
    block.removeInst(cond, ConstantInt(8, 0))
    return if (result == 0 && brInst.getFalseBlock()!!.name!! == outBlock.name!!) {
      block.replaceBrInst( BranchInst(outBlock, null, null))
      true
    } else if (result == 1 && brInst.getTrueBlock().name!! == outBlock.name!!) {
      block.replaceBrInst( BranchInst(outBlock, null, null))
      true
    } else {
      block.replaceBrInst( BranchInst(loopBlock, null, null))
      false
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

  // guarantee that the first of list is phi instruction
  private fun getCycle(inst: Value): List<Instruction> {
    return if (inst is PhiInst) {
      listOf(inst, inst.useeList.filterIsInstance<BinaryInst>().first())
    } else if (inst is BinaryInst) {
      listOf(inst.useeList.filterIsInstance<PhiInst>().first(), inst)
    } else {
      throw Exception("Unexpected instruction: $inst")
    }
  }

  private fun unfold(loop: Loop, ivs: List<Instruction>) {
    // initialization
    val allBlocks = loop.allBlocks
    val copyBlocks = allBlocks.map { block -> block.instList.map { it.replicate() as Instruction } }
    val exitingBlock = loop.exitingBlocks.first()
    val name2Name = hashMapOf<String, String>() // instruction's name to iv's name
    val name2IV = hashMapOf<String, Value>() // iv's name to iv's value
    name2IV[ivs.first().name!!] = ivs.first()
    ivs.forEach { name2Name[it.name!!] = ivs.first().name!! }

    // initialization again
    var isInitial = true
    var nextBlock = BasicBlock(currFunc.mulTable.rename(allBlocks.first().name!!), exitingBlock.execFreq / 10)
    val outBlock = exitingBlock.getTerminator().useeList.filterIsInstance<BasicBlock>().find { it !in allBlocks }!!
    val initBlock = nextBlock
    val anchorBlock = currFunc.blockList[currFunc.getIndexOfBlock(allBlocks.first()) - 1]
    val allAdded = mutableListOf<BasicBlock>()

    while (true) {
      val roundAdded = mutableListOf<BasicBlock>()
      val phiInstList = mutableListOf<PhiInst>()
      for ((index, block) in allBlocks.withIndex()) {
        val currBlock = nextBlock
        val nextBlockName = if (index == allBlocks.size - 1) allBlocks.first().name!! else allBlocks[index + 1].name!!
        nextBlock = BasicBlock(currFunc.mulTable.rename(nextBlockName), allBlocks[index].execFreq)
        currBlock.instList = copyBlocks[index].map { it.replicate() as Instruction }.toMutableList()
        currBlock.instList.forEach { it.parent = currBlock }

        currBlock.instList.filterIsInstance<PhiInst>().forEach { inst ->
          if (inst.getPredList().map { it.second }.any { it.name == exitingBlock.name }) {
            inst.replaceAll {
              if (it.isDef() && it.type !is FuncType) {
                valueMap[it.name!!]!!
              } else {
                it
              }
            }
          } else if (!ivs.map { it.name!! }.contains(inst.name!!)) {
            phiInstList.add(inst)
          }
        }

        currBlock.instList.filterIsInstance<PhiInst>().forEach { inst ->
          if (ivs.map { it.name!! }.contains(inst.name!!)) {
            if (isInitial) {
              val const = inst.getPredList().map { it.first }.filterIsInstance<ConstantInt>().first()
              valueMap[inst.name!!] = const
              currBlock.removeInst(inst, const)
            } else {
              valueMap[inst.name!!] = name2IV[inst.name]!!
              currBlock.removeInst(inst, name2IV[inst.name]!!)
            }
          } else {
            val usedName = inst.name!!
            inst.name = rename(usedName)
            valueMap[usedName] = inst
          }
        }

        currBlock.instList.filter { it !is PhiInst }.forEach { inst ->
          if (inst.isDef() && ivs.map { it.name!! }.contains(inst.name!!)) {
            if (inst is BinaryInst) {
              val usedLhs = inst.getLhs()
              val usedRhs = inst.getRhs()
              val lhs = if (usedLhs is ConstantInt) usedLhs else valueMap[usedLhs.name] as ConstantInt
              val rhs = if (usedRhs is ConstantInt) usedRhs else valueMap[usedRhs.name] as ConstantInt
              val const = ConstantInt(32, Utils.calculate(inst.op, lhs.value, rhs.value))
              name2IV[name2Name[inst.name!!]!!] = const
              valueMap[inst.name!!] = const
              currBlock.removeInst(inst, const)
            } else {
              throw Exception("Unexpected instruction: $inst")
            }
          } else if (inst !is BranchInst) {
            inst.replaceAll {
              if (it.isDef() && it.type !is FuncType) {
                valueMap[it.name!!]!!
              } else {
                it
              }
            }
            if (inst.isDef()) {
              val usedName = inst.name!!
              inst.name = rename(usedName)
              valueMap[usedName] = inst
            }
          }
        }
        roundAdded.add(currBlock)
        valueMap[allBlocks[index].name!!] = currBlock
      }

      // deal with br instruction
      isInitial = false
      roundAdded.forEach { valueMap[it.name!!] = it }
      roundAdded.filter { roundAdded.indexOf(it) != allBlocks.indexOf(exitingBlock) }.map { it.getTerminator() }
        .forEach {
          it.replaceAll { valueMap[it.name!!]!! }
        }
      allAdded.addAll(roundAdded)

      // deal with phi instruction
      phiInstList.forEach { inst ->
        inst.replaceAll {
          if (it.isDef() && it.type !is FuncType) {
            valueMap[it.name!!]!!
          } else {
            it
          }
        }
      }

//      if (allAdded.size > 400) {
//        allAdded.flatMap { it.instList }.forEach {
//          if (it is BranchInst) {
//            it.parent.removeBrInst(it)
//          } else {
//            it.eliminate()
//          }
//        }
//        return false
//      }
      if (checkBrInst(valueMap[exitingBlock.name!!] as BasicBlock, nextBlock, outBlock)) {
        break
      }
    }

    // delete original blocks and modify branch instructions
    currFunc.blockList.removeAll(allBlocks)
    currFunc.blockList.addAll(currFunc.getIndexOfBlock(anchorBlock) + 1, allAdded)
    allAdded.forEach { it.parent = currFunc }
    anchorBlock.replaceBrInst(BranchInst(initBlock, null, null))

    // deal with inheritance
    allBlocks.forEach { block ->
      block.instList.filter { it.isDef() }.forEach {
        val sub = valueMap[it.name!!]!!
        it.substitutedBy(sub)
        if (sub is Instruction) {
          valueMap[sub.name!!] = sub
        }
      }
    }
    allBlocks.flatMap { it.instList }.forEach {
      if (it is BranchInst) {
        it.parent.removeBrInst(it)
      } else {
        it.eliminate()
      }
    }

    // deal with phi instructions
    val phiInstList = currFunc.blockList.flatMap { it.instList.filterIsInstance<PhiInst>() }
    phiInstList.forEach { inst ->
      inst.getPredList().map { it.second }.filter { it !in inst.parent.prevBlockSet }.forEach { inst.removePred(it) }
    }
    phiInstList.filter { it.getPredList().size == 1 }.forEach {
      it.parent.replaceInst(it, MvInst(rename(it.name!!), it.useeList.first()))
    }
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

  private fun attempt(loop: Loop): Boolean {
    var flag = false
    loop.succLoops.sortedBy { currFunc.blockList.indexOf(it.headerBlock) }.forEach { flag = flag or attempt(it) }
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
            unfold(loop, cycle)
            return true
          }
        }
      }
    }
    return false
  }

  override fun visit(func: Func) {
    currFunc = func
    indVar = func.indVar
    do {
      var flag = false
      valueMap.clear()
      currModule.globalVarMap.values.forEach { valueMap[it.name!!] = it }
      currModule.constStrMap.values.forEach { valueMap[it.name!!] = it }
      func.argList.forEach { valueMap[it.name!!] = it }
      func.blockList.flatMap { it.instList.filter { it.isDef() } }.forEach { valueMap[it.name!!] = it }
      func.blockList.forEach { valueMap[it.name!!] = it }
      func.loopNestTree.build()
      func.loopNestTree.roots.sortedBy { func.blockList.indexOf(it.headerBlock) }
        .forEach { flag = flag or attempt(it) }
    } while (flag)
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
