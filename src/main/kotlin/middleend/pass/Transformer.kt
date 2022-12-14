package middleend.pass

import middleend.basic.*

/**
 * remove useless loads and stores from program
 */
object Transformer : IRVisitor() {
  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    // collect alloca
    // eliminate instruction with load and store to alloca
    val name2Value = hashMapOf<String, Instruction>()
    val entryBlock = func.blockList.first()

    // deal with alloca instructions
    val replaceUserSet = entryBlock.instList
      .filterIsInstance<AllocaInst>()
      .filter { it.varType is IntType }
      .map { it.userList.toSet() }
      .foldRight(setOf<User>()) { acc, set -> acc + set }

    entryBlock.instList = entryBlock.instList.map { inst ->
      if (inst is AllocaInst && inst.varType is IntType) {
        val oriName = inst.name!!.substringBeforeLast(".")
        val mvInst = MvInst(
          inst.name!!.substringBeforeLast("."),
          if (!func.argList.any { it.name!! == oriName }) {
            ConstantInt(inst.varType.getNumBits(), 0)
          } else {
            func.argList.find { it.name!! == oriName }!!
          },
        )
        mvInst.parent = entryBlock
//        name2Value[oriName] = mvInst
        inst.eliminate()

        mvInst
      } else {
        inst
      }
    }.toMutableList()

    // deal with load and store instructions
    for (block in func.blockList) {
      // remove load & convert store into assignment
      val eliminated =
        block.instList.filter { it is LoadInst && replaceUserSet.contains(it) }

      block.instList = block.instList
        .filter { it !is LoadInst || !replaceUserSet.contains(it) }
        .map { inst ->
          if (inst is StoreInst && replaceUserSet.contains(inst)) {
            val varName = inst.getAddr().name!!.substringBeforeLast('.')
            val mvInst = MvInst(varName, inst.getValue())
            mvInst.parent = block // don't forget to set parent
            name2Value[varName] = mvInst
            inst.eliminate()

            mvInst
          } else {
            inst.replaceAll { name2Value[it.name] ?: it }
            inst
          }
        }.toMutableList()

      // eliminate after all have been done
      eliminated.forEach { it.eliminate() }
    }

    // rearrange alloca inst
    for (block in func.blockList) {
      val allocaInstList = block.instList.filterIsInstance<AllocaInst>()
      block.instList.removeAll(allocaInstList)
      block.instList.addAll(0, allocaInstList)
    }
  }

  override fun visit(block: BasicBlock) {
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