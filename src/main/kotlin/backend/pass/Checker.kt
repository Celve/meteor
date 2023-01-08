package backend.pass

import backend.basic.*

object Checker : ASMVisitor() {
  override fun visit(module: ASMModule) {
    module.funcList.forEach { it.accept(this) }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {}

  override fun visit(func: ASMFunc) {
    val instList = func.blockList.flatMap { it.instList }
    instList.forEach { inst -> inst.userSet.all { instList.contains(it) } }
    for (block in func.blockList) {
      assert(block.parent == func)
      block.accept(this)
    }
  }

  override fun visit(block: ASMBlock) {
    block.succList.forEach {
      assert(it.predList.contains(block))
      assert(block.parent.blockList.contains(it))
    }
    block.predList.forEach {
      assert(it.succList.contains(block))
      assert(block.parent.blockList.contains(it))
    }
    block.instList.forEach { inst ->
      assert(inst.parent == block)
      inst.useeList.forEach { assert(it.userSet.contains(inst)) }
      inst.userSet.forEach { assert(it.useeList.contains(inst)) }
    }
    val succSet = block.instList.filterIsInstance<ASMBrInst>().map { it.getLabel() } +
        block.instList.filterIsInstance<ASMBzInst>().map { it.getLabel() } +
        block.instList.filterIsInstance<ASMJInst>().map { it.getLabel() }
    assert(succSet.sortedBy { it.name } == block.succList.sortedBy { it.name })
  }

  override fun visit(inst: ASMStoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMBrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMBzInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMJInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMRetInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMArithInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMArithiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMMvInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMCmpzInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ASMLuiInst) {
    TODO("Not yet implemented")
  }
}