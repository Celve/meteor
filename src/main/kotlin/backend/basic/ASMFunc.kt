package backend.basic

import backend.controller.ASMVisitor

/**
 * The intention to maintain a class for holding function in assembly code it to make it clearer to distinguish between code blocks.
 * Just like the Func in LLVM IR, the ASMFunc is actually holding a number of code blocks.
 * @see middleend.basic.Func
 */
class ASMFunc(name: String) : Label(name) {
  val blockList = mutableListOf<ASMBlock>()
  var usedVirRegNum = 0
  var stackAlloca = 0

  /**
   * @param pureName refers to the name of block without any prefix
   */
  fun getBlockByPureName(pureName: String): ASMBlock? {
    val compositeName = "${name}.$pureName"
    return getBlockByCompositeName(compositeName)
  }

  /**
   * @param compositeName refers to the name of block with prefix, which is the name of its parent function
   */
  fun getBlockByCompositeName(compositeName: String): ASMBlock? {
    return blockList.find { it.name == compositeName }
  }

  /**
   * This function add block at the tail of the block list.
   */
  fun addBlock(block: ASMBlock) {
    blockList.add(block)
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }
}