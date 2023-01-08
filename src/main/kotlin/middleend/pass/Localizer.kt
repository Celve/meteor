package middleend.pass

import middleend.basic.*
import middleend.struct.CallGraph

object Localizer : IRVisitor() {
  private val globalVar2ReadScc = hashMapOf<GlobalVariable, HashSet<Int>>()
  private val globalVar2WriteScc = hashMapOf<GlobalVariable, HashSet<Int>>()
  private val globalVars = hashSetOf<GlobalVariable>()
  private lateinit var callGraph: CallGraph
  private lateinit var module: TopModule

  override fun visit(topModule: TopModule) {
    module = topModule
    callGraph = topModule.callGraph

    // localizer depends on call graph to analyze loads and stores inside procedure calls
    callGraph.build()
    topModule.globalVarMap.forEach { it.value.accept(this) } // calculate read set and write set

    // establish toposort
    var changed: Boolean
    do {
      changed = false
      for (globalVar in globalVars) {
        val readScc = globalVar2ReadScc[globalVar]!!.toSet()
        val writeScc = globalVar2WriteScc[globalVar]!!.toSet()
        readScc.forEach { globalVar2ReadScc[globalVar]!!.addAll(callGraph.scc2CallerSet.getValue(it)) }
        writeScc.forEach { globalVar2WriteScc[globalVar]!!.addAll(callGraph.scc2CallerSet.getValue(it)) }

        if (readScc != globalVar2ReadScc[globalVar]!! || writeScc != globalVar2WriteScc[globalVar]!!) {
          changed = true
        }
      }
    } while (changed)

    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    globalVar2ReadScc[globalVar] =
      globalVar.userList.filterIsInstance<LoadInst>().map { callGraph.sccId.getValue(it.parent.parent) }.toHashSet()
    globalVar2WriteScc[globalVar] =
      globalVar.userList.filterIsInstance<StoreInst>().map { callGraph.sccId.getValue(it.parent.parent) }.toHashSet()
    globalVars.add(globalVar)
  }

  override fun visit(constStr: ConstantStr) {}

  private fun rewrite(globalVar: GlobalVariable, func: Func) {
    // finds out all its occurrence
    val loadInstList = globalVar.userList.filterIsInstance<LoadInst>().filter { it.parent.parent == func }
    val storeInstList = globalVar.userList.filterIsInstance<StoreInst>().filter { it.parent.parent == func }

    val localVarName = func.mulTable.rename(globalVar.name!!.dropLast(5)) // remove .addr, and rename it
    // make sure that a function that is write-only would not attempt to read from a global variable
    val aliasInst =
      if (loadInstList.isNotEmpty()) LoadInst(localVarName, globalVar) else MvInst(localVarName, ConstantInt(32, 0))
    loadInstList.forEach { it.parent.removeInst(it, aliasInst) }

    // add aliasInst in the beginning
    val entryBlock = func.getEntryBlock()
    val initFunc = module.funcMap["_global_variable_init"]
    if (func.name == "main" && initFunc != null) {
      entryBlock.addInst(entryBlock.instList.indexOf(initFunc.userList.first()) + 1, aliasInst)
    } else {
      entryBlock.addInst(0, aliasInst)
    }

    // make sure that a function that is read-only would not attempt to write to a global variable
    if (storeInstList.isNotEmpty()) {
      val returnBlock = func.getReturnBlock()
      val storeInst = StoreInst(aliasInst, globalVar)
      storeInstList.forEach { it.parent.replaceInst(it, MvInst(aliasInst.name!!, it.getValue())) }
      returnBlock.addInst(0, storeInst)
    }

    // add load or store around a call inst
    val callInstList = func.blockList.flatMap { it.instList.filterIsInstance<CallInst>() }
    for (callInst in callInstList) {
      if (globalVar2ReadScc[globalVar]!!.contains(callGraph.sccId.getValue(callInst.getCallee())) && storeInstList.isNotEmpty()) {
        callInst.parent.addInst(callInst.parent.instList.indexOf(callInst), StoreInst(aliasInst, globalVar))
      }

      if (globalVar2WriteScc[globalVar]!!.contains(callGraph.sccId.getValue(callInst.getCallee())) && loadInstList.isNotEmpty()) {
        callInst.parent.addInst(callInst.parent.instList.indexOf(callInst) + 1, LoadInst(aliasInst.name!!, globalVar))
      }
    }
  }

  private fun checkWithHeuristic(globalVar: GlobalVariable, func: Func): Boolean {
    val loadInstList = globalVar.userList.filterIsInstance<LoadInst>().filter { it.parent.parent == func }
    val storeInstList = globalVar.userList.filterIsInstance<StoreInst>().filter { it.parent.parent == func }
    val primitiveCost = loadInstList.sumOf { it.parent.execFreq } + storeInstList.sumOf { it.parent.execFreq }

    val callInstList = func.blockList.flatMap { it.instList.filterIsInstance<CallInst>() }
    var freshCost = callInstList.sumOf {
      var sum = 0
      if (storeInstList.isNotEmpty() && globalVar2ReadScc[globalVar]!!.contains(callGraph.sccId[it.getCallee()])) {
        sum += it.parent.execFreq
      }
      if (loadInstList.isNotEmpty() && globalVar2WriteScc[globalVar]!!.contains(callGraph.sccId[it.getCallee()])) {
        sum += it.parent.execFreq
      }
      sum
    }
    if (loadInstList.isNotEmpty()) {
      freshCost++
    }
    if (storeInstList.isNotEmpty()) {
      freshCost++
    }
    return freshCost < primitiveCost
  }

  override fun visit(func: Func) {
    globalVars.filter { it.userList.filterIsInstance<Instruction>().any { it.parent.parent == func } }
      .forEach { globalVar ->
        if (checkWithHeuristic(globalVar, func)) {
          rewrite(globalVar, func)
        }
      }
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
