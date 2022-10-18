package backend.controller

import backend.basic.*

abstract class ASMVisitor {
  abstract fun visit(module: ASMModule)
  abstract fun visit(globalPtr: ASMGlobalPointer)
  abstract fun visit(func: ASMFunc)
  abstract fun visit(block: ASMBlock)
  abstract fun visit(inst: ASMStoreInst)
  abstract fun visit(inst: ASMLoadInst)
  abstract fun visit(inst: ASMBzInst)
  abstract fun visit(inst: ASMJInst)
  abstract fun visit(inst: ASMRetInst)
  abstract fun visit(inst: ASMArithInst)
  abstract fun visit(inst: ASMArithiInst)
  abstract fun visit(inst: ASMCmpInst)
  abstract fun visit(inst: ASMCmpiInst)
  abstract fun visit(inst: ASMCallInst)
  abstract fun visit(inst: ASMLiInst)
  abstract fun visit(inst: ASMMvInst)
}