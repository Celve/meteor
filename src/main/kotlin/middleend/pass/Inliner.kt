package middleend.pass

import middleend.basic.*
import middleend.helper.Utils

object Inliner : IRVisitor() {
  private const val funcMaxSize = 1000

  lateinit var module: TopModule
  private lateinit var currCaller: Func
  private val old2Value = hashMapOf<String, Value>()
  private val mul2Mul = hashMapOf<String, String>()

  private fun safeInlinable(inst: CallInst): Boolean {
    // it's heuristic, now I use the simplest approach
    return module.callGraph.func2CalleeSet.getValue(inst.getCallee()).isEmpty()
        && calcInstCnt(inst.getCallee()) + calcInstCnt(inst.parent.parent) < funcMaxSize
  }

  private fun isNotRecursion(func: Func): Boolean {
    return !module.callGraph.func2CalleeSet.getValue(func).contains(func)
  }

  private fun aggressiveInlinable(inst: CallInst): Boolean {
    val caller = inst.parent.parent
    val callee = inst.getCallee()
    return caller != callee
        && isNotRecursion(callee)
        && calcInstCnt(callee) + calcInstCnt(caller) < funcMaxSize
  }

  private fun attempt(func: Func, isSafe: Boolean): Int {
    var changed = 0
    val callInstList = func.blockList
      .flatMap { it.instList.filterIsInstance<CallInst>() }
      .filter { !module.builtinFuncMap.contains(it.getCallee().name!!) }
      .sortedByDescending { it.parent.execFreq }
    for (callInst in callInstList) {
      val callee = callInst.getCallee()
      if (module.funcMap.contains(callee.name)) {
        if ((isSafe && safeInlinable(callInst)) || (!isSafe && aggressiveInlinable(callInst))) {
          changed++
          inline(callInst)
        }
      }
    }
    return changed
  }

  override fun visit(topModule: TopModule) {
    module = topModule
    var changed: Int
    do {
      topModule.callGraph.build()
      changed =
        topModule.funcMap.values.sumOf { attempt(it, true) }
    } while (changed > 0)

    do {
      topModule.callGraph.build()
      changed =
        topModule.funcMap.values.sumOf { attempt(it, false) }
    } while (changed > 0)

    // remove useless function
    topModule.callGraph.build()
    topModule.funcMap
      .filter { topModule.callGraph.func2CallerSet.getValue(it.value).isEmpty() && it.value.name != "main" }
      .forEach { topModule.funcMap.remove(it.key) }
  }

  override fun visit(globalVar: GlobalVariable) {
    TODO("Not yet implemented")
  }

  override fun visit(constStr: ConstantStr) {
    TODO("Not yet implemented")
  }

  private fun calcInstCnt(func: Func): Int {
    return func.blockList.sumOf { it.instList.size }
  }

  private fun rename(name: String): String {
    val withoutSSA = Utils.eliminateVersionWithDot(name)
    return if (withoutSSA.endsWith(".addr")) {
      val withoutAddr = withoutSSA.removeSuffix(".addr")
      if (mul2Mul.contains(withoutAddr)) {
        mul2Mul.getValue(withoutAddr) + ".addr"
      } else {
        val withoutMultiply = Utils.eliminateVersionWithoutDot(withoutAddr)
        val mulName = currCaller.mulTable.rename(withoutMultiply)
        currCaller.ssaTable.rename("$mulName.addr")
        mul2Mul[withoutAddr] = mulName
        "$mulName.addr"
      }
    } else if (mul2Mul.contains(withoutSSA)) {
      currCaller.ssaTable.rename(mul2Mul.getValue(withoutSSA))
    } else {
      val withoutMultiply = Utils.eliminateVersionWithoutDot(withoutSSA)
      val mulName = currCaller.mulTable.rename(withoutMultiply)
      currCaller.ssaTable.rename(mulName)
      mul2Mul[name] = mulName
      mulName
    }
  }

  private fun inline(callInst: CallInst) {
    val callerBlock = callInst.parent
    val caller = callerBlock.parent
    val callee = callInst.getCallee()
    val insertedBlockList = mutableListOf<BasicBlock>()

    currCaller = caller
    old2Value.clear()
    mul2Mul.clear()

    callee.blockList.forEach {
      old2Value[it.name!!] =
        BasicBlock(caller.mulTable.rename("inline.${callee.name!!}.${it.name!!}"), it.execFreq)
    }

    val calleeEntryBlock = old2Value.getValue(callee.blockList.first().name!!) as BasicBlock
    callee.argList.zip(callInst.getArgList()).reversed().forEach {
      val mvInst = MvInst(rename(it.first.name!!), it.second)
      mvInst.parent = calleeEntryBlock
      calleeEntryBlock.instList.add(0, mvInst)
      old2Value[it.first.name!!] = mvInst
    }

    // duplicate the block
    for (calleeBlock in callee.blockList) {
      // construct new blocks
      val newCalleeBlock = old2Value[calleeBlock.name]!! as BasicBlock
      newCalleeBlock.parent = caller
      insertedBlockList.add(newCalleeBlock)
      for (inst in calleeBlock.instList) {
        val newInst = inst.replicate() as Instruction
        if (newInst.name != null) {
          old2Value[newInst.name!!] = newInst
          newInst.name = rename(newInst.name!!)
        }
        if (inst !is PhiInst) {
          newInst.replaceAll {
            if (it.isDef() && it.type !is FuncType) {
              old2Value[it.name!!] ?: it
            } else {
              it
            }
          }
        }

        if (inst is AllocaInst) {
          val callerEntryBlock = caller.blockList.first()
          newInst.parent = callerEntryBlock
          callerEntryBlock.instList.add(callerEntryBlock.getLastAllocaInstIndex() + 1, newInst)
        } else {
          newInst.parent = newCalleeBlock
          newCalleeBlock.instList.add(newInst)

          // rebuild dependency graph
          if (newInst is BranchInst) {
            val trueBlock = newInst.getTrueBlock()
            val falseBlock = newInst.getFalseBlock()
            newCalleeBlock.addNextBlock(trueBlock)
            trueBlock.addPrevBlock(newCalleeBlock)
            if (falseBlock != null) {
              newCalleeBlock.addNextBlock(falseBlock)
              falseBlock.addPrevBlock(newCalleeBlock)
            }
          }
        }
      }
    }

    // replace phi
    insertedBlockList.forEach { block ->
      block.instList.filterIsInstance<PhiInst>().forEach { phiInst ->
        phiInst.replaceAll { if (it.isDef() && it.type !is FuncType) old2Value[it.name!!] ?: it else it }
      }
    }

    // split caller block
    val callerBlockPos = caller.blockList.indexOf(callerBlock)
    val splitPos = callerBlock.instList.indexOf(callInst)
    val newCallerBlock = BasicBlock(caller.mulTable.rename(callerBlock.name!!), callerBlock.execFreq)
    newCallerBlock.parent = caller
    newCallerBlock.instList.addAll(callerBlock.instList.subList(splitPos + 1, callerBlock.instList.size))
    newCallerBlock.instList.forEach { it.parent = newCallerBlock }
    callInst.eliminate()
    caller.blockList.add(callerBlockPos + 1, newCallerBlock)
    callerBlock.instList.removeAll(callerBlock.instList.subList(splitPos, callerBlock.instList.size))
    callerBlock.substitutedByWhen(newCallerBlock) { it is PhiInst }

    newCallerBlock.nextBlockSet.addAll(callerBlock.nextBlockSet)
    callerBlock.nextBlockSet.clear()
    callerBlock.nextBlockSet.add(calleeEntryBlock)
    newCallerBlock.nextBlockSet.forEach {
      it.prevBlockSet.remove(callerBlock)
      it.prevBlockSet.add(newCallerBlock)
    }

    // connect callee return block
    val calleeReturnBlock = old2Value.getValue(callee.blockList.last().name!!) as BasicBlock
    val returnValue = calleeReturnBlock.instList.removeLast() as ReturnInst
    if (returnValue.getRetVal() != null) {
      val newCall = MvInst(callInst.name!!, returnValue.getRetVal()!!)
      newCall.parent = calleeReturnBlock
      callInst.substitutedBy(newCall)
      calleeReturnBlock.instList.add(newCall)
    }
    val callee2Caller = BranchInst(newCallerBlock, null, null)
    callee2Caller.parent = calleeReturnBlock
    calleeReturnBlock.instList.add(callee2Caller)
    calleeReturnBlock.nextBlockSet.add(newCallerBlock)
    newCallerBlock.prevBlockSet.add(calleeReturnBlock)

    // replace callInst with jmp
    val caller2Callee = BranchInst(calleeEntryBlock, null, null)
    caller2Callee.parent = callerBlock
    callerBlock.instList.add(caller2Callee)
    insertedBlockList.reversed().forEach { caller.blockList.add(callerBlockPos + 1, it) }
    callerBlock.nextBlockSet.add(calleeEntryBlock)
    calleeEntryBlock.prevBlockSet.add(callerBlock)
  }

  override fun visit(func: Func) {}

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