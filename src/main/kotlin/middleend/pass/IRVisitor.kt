package middleend.pass

import middleend.basic.*

abstract class IRVisitor(val topModule: TopModule) {
  abstract fun visit(func: Func)
  abstract fun visit(block: BasicBlock)
  abstract fun visit(inst: AllocaInst)
  abstract fun visit(inst: CallInst)
  abstract fun visit(inst: LoadInst)
  abstract fun visit(inst: BitCastInst)
  abstract fun visit(inst: PhiInst)
  abstract fun visit(inst: BinaryInst)
  abstract fun visit(inst: BranchInst)
  abstract fun visit(inst: GetElementPtrInst)
  abstract fun visit(inst: ZExtInst)
  abstract fun visit(inst: TruncInst)
  abstract fun visit(inst: StoreInst)
  abstract fun visit(inst: CmpInst)
  abstract fun visit(inst: ReturnInst)
}