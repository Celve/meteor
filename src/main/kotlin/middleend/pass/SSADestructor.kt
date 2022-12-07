package middleend.pass

import middleend.basic.*
import middleend.helper.SymbolTable

class SSADestructor : IRVisitor() {
  var module = TopModule()
  private var symbolTable = SymbolTable("")

  override fun visit(topModule: TopModule) {
    module = topModule
    module.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun phi2PCopy(block: BasicBlock) {
    if (block.instList.filterIsInstance<PhiInst>().isEmpty()) { // remove useless appending block
      return
    }
    val block2PCopyInst = hashMapOf<BasicBlock, PCopyInst>()
    for (prevBlock in block.prevBlockList.toList()) {
      val pCopyInst = PCopyInst()
      block2PCopyInst[prevBlock] = pCopyInst
      if (prevBlock.nextBlockList.size > 1) { // has several outgoing edges
        val middleBlock = BasicBlock(symbolTable.rename("edge.split"), block.execFreq) // FIXME: not sure about it

        val branchInst = prevBlock.instList.last() as BranchInst
        if (branchInst.trueBlock == block) {
          branchInst.trueBlock = middleBlock
        } else {
          branchInst.falseBlock = middleBlock
        }

        BasicBlock.unlink(prevBlock, block)
        BasicBlock.link(prevBlock, middleBlock)
        BasicBlock.link(middleBlock, block)

        middleBlock.addInst(0, pCopyInst)
        middleBlock.addInst(1, BranchInst(null, block, null))

        val blockList = block.parent!!.blockList
        val index = blockList.indexOf(prevBlock)
        blockList.add(index + 1, middleBlock)
      } else {
        prevBlock.instList.add(prevBlock.instList.size - 1, pCopyInst)
      }
    }
    for (phiInst in block.instList.filterIsInstance<PhiInst>()) {
      for (i in 0 until phiInst.predList.size) {
        val (predValue, predBlock) = phiInst.predList[i]
//        println("$alternative $predValue")
        block2PCopyInst[predBlock]!!.addAssignment(phiInst, predValue)
//        phiInst.predList[i] = Pair(phiInst, predBlock)
      }
    }
    block.instList.removeAll { it is PhiInst }
  }

  private fun pCopy2SCopy(block: BasicBlock) {
    for (pCopyInst in block.instList.filterIsInstance<PCopyInst>()) {
      val sCopyInstList = mutableListOf<MvInst>()
      while (pCopyInst.assignmentList.any { it.first != it.second }) {
        val removedInstList = mutableListOf<PCopyInst>()
        val processedAssignmentList = mutableListOf<Pair<Value, Value>>()
        for (assignment in pCopyInst.assignmentList) {
          if (pCopyInst.assignmentList.all { it.second != assignment.first }) {
            sCopyInstList.add(MvInst(assignment.first.name!!, assignment.second))
            removedInstList.add(pCopyInst)
            processedAssignmentList.add(assignment)
          }
        }

        pCopyInst.assignmentList.removeAll(processedAssignmentList)

        if (processedAssignmentList.isEmpty()) {
          val assignment = pCopyInst.assignmentList.find { it.first != it.second }!!
          val index = pCopyInst.assignmentList.indexOf(assignment)
          val value = Value(assignment.second.type, symbolTable.rename(".temp"))
          sCopyInstList.add(MvInst(value.name!!, assignment.second))
          pCopyInst.assignmentList[index] = Pair(assignment.first, value)
        }
      }
      block.instList.remove(pCopyInst)
      sCopyInstList.forEach { block.instList.add(block.instList.size - 1, it) }
    }
  }

  override fun visit(func: Func) {
    symbolTable = func.symbolTable

    for (block in func.blockList.toList()) {
      phi2PCopy(block)
    }

    for (block in func.blockList.toList()) {
      pCopy2SCopy(block)
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