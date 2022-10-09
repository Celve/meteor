package middleend.helper

import middleend.basic.BasicBlock

class LoopManager {
  private val continueBlockStack = ArrayDeque<BasicBlock>()
  private val breakBlockStack = ArrayDeque<BasicBlock>()

  fun addLast(continueBlock: BasicBlock, breakBlock: BasicBlock) {
    continueBlockStack.addLast(continueBlock)
    breakBlockStack.addLast(breakBlock)
  }

  fun getLastContinueBlock(): BasicBlock? {
    return continueBlockStack.lastOrNull()
  }

  fun getLastBreakBlock(): BasicBlock? {
    return breakBlockStack.lastOrNull()
  }

  fun removeLast() {
    continueBlockStack.removeLast()
    breakBlockStack.removeLast()
  }
}