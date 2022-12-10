package middleend.pass

import middleend.basic.*
import middleend.controller.IRBuilder
import middleend.helper.DomTree

object SSAConstructor : IRVisitor() {
  var module: TopModule? = null

  val counter = hashMapOf<String, Int>().withDefault { 0 }
  val stack = hashMapOf<String, MutableList<Value>>().withDefault { mutableListOf() }
  val globals = hashSetOf<String>()
  var name2DefBlockSet = hashMapOf<String, HashSet<BasicBlock>>().withDefault { hashSetOf() }
  var name2Type = hashMapOf<String, Type>()
  var domTree = DomTree(null)

  override fun visit(topModule: TopModule) {
    module = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    stack[globalVar.name!!] = mutableListOf(globalVar)
  }

  override fun visit(constStr: ConstantStr) {
    stack[constStr.name!!] = mutableListOf(constStr)
  }

  // modify the original name of input value
  private fun renameValue(value: Value) {
    val ver = counter.getValue(value.name!!)
    counter[value.name!!] = ver + 1
    val originalName = value.name!!
    if (ver != 0) {
      value.name += ".$ver"
    }
    stack.getOrPut(originalName) { mutableListOf() }.add(value)
  }

  private fun getValuesOriginalName(value: Value): String {
    val component = value.name!!.split(".").toMutableList()
    return if (component.size > 1 && component.last().toIntOrNull() != null) {
      component.removeLast()
      component.joinToString(".")
    } else {
      value.name!!
    }
  }

  private fun renameBlock(block: BasicBlock) {

    for (inst in block.instList) {
      if (inst !is PhiInst) {
        inst.replaceAll {
          if (it.isDef() && it.type !is LabelType && it.type !is FuncType) { // it can't be a basic block
            stack.getValue(getValuesOriginalName(it)).last()
          } else {
            it
          }
        }
      }

      // global values and constants could not be rename
      if (inst.isDef()) {
        renameValue(inst)
      }
    }

    block.nextBlockList.forEach { nextBlock ->
      nextBlock.instList.filterIsInstance<PhiInst>()
        .filter { !it.name!!.startsWith(".phi") } // ignore manually generated phi
        .forEach { it.addAssignment(stack.getValue(getValuesOriginalName(it)).last(), block) }
    }

    domTree.successors.getValue(block).forEach { renameBlock(it) }

    block.instList.filter { it.name != null }.forEach { stack.getValue(getValuesOriginalName(it)).removeLast() }
  }

  override fun visit(func: Func) {
    name2DefBlockSet = hashMapOf<String, HashSet<BasicBlock>>().withDefault { hashSetOf() }
    name2Type = hashMapOf()
    counter.clear()
    stack.clear()
    globals.clear()

    // add global and intial local variables
    module!!.globalVarMap.forEach { it.value.accept(this) }
    module!!.constStrMap.forEach { it.value.accept(this) }
    func.argList.forEach { renameValue(it) }

    domTree = DomTree(func)
    domTree.build()

    // collect global variables
    for (block in func.blockList) {
      val varKillSet = hashSetOf<String>()
      for (inst in block.instList) {
        if (inst !is PhiInst) { // ignore manually added phi
          globals.addAll(
            inst.useeList
              .filterIsInstance<Instruction>()
              .filter { it !is PhiInst } // still ignore manually added instruction
              .map { it.name!! }
              .filter { !varKillSet.contains(it) })
        }
        if (inst.isDef()) { // namely it's definition
          varKillSet.add(inst.name!!)
          name2DefBlockSet.getOrPut(inst.name!!) { hashSetOf() }.add(block)
          name2Type[inst.name!!] = inst.type
        }
      }
    }

    // insert phi-instructions
    for (valueName in globals) {
      val defBlocks = name2DefBlockSet.getValue(valueName)
      val workList: HashSet<BasicBlock> = defBlocks.toHashSet() // means duplication
      val finishedSet = hashSetOf<BasicBlock>()
      val valueType = name2Type[valueName]!!
      while (workList.isNotEmpty()) {
        val block = workList.first()
        workList.remove(block)
        for (df in domTree.domFrontiers.getValue(block)) {
          if (!finishedSet.contains(df)) {
            finishedSet.add(df)
            IRBuilder.setInsertPointBefore(df.instList.first())
            IRBuilder.createPhi(
              valueName,
              valueType,
              mutableListOf()
            )
            if (!defBlocks.contains(df)) {
              workList.add(df)
            }
          }
        }
      }
    }

    // renaming
    func.argList.forEach { stack.getOrPut(it.name!!) { mutableListOf() }.add(it) } // add args beforehand
    renameBlock(func.getEntryBlock())
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
