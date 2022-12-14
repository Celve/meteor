package middleend.pass

import middleend.basic.*

abstract class IRVisitor {
  abstract fun visit(topModule: TopModule)
  abstract fun visit(globalVar: GlobalVariable)
  abstract fun visit(constStr: ConstantStr)
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
  abstract fun visit(inst: PCopyInst)
  abstract fun visit(inst: MvInst)
}