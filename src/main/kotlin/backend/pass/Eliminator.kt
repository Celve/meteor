package backend.pass

import backend.basic.*

object Eliminator : ASMVisitor() {
  override fun visit(module: ASMModule) {
    module.funcList.forEach { it.accept(this) }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {}

  override fun visit(func: ASMFunc) {
    var changed: Boolean
    do {
      changed = false
      func.blockList.toList().forEach { block ->
        if (block.instList.size == 1 && block.instList.first() is ASMJInst) {
          val target = (block.instList.first() as ASMJInst).getLabel() as ASMBlock
          changed = true
          block.userSet.filterIsInstance<ASMInst>().forEach {
            it.parent.succList.remove(block)
            it.parent.succList.add(target)
          }
          block.substituteAll(target)
          assert(func.blockList.remove(block))
        }
      }
    } while (changed)
  }

  override fun visit(block: ASMBlock) {
    TODO("Not yet implemented")
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
}