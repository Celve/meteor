package backend.basic

import backend.controller.ASMVisitor

class ASMBlock(val pureName: String, val parent: ASMFunc) : Label("${parent.name}.$pureName") {
  val instList = mutableListOf<ASMInst>()
  var liveInSet = mutableSetOf<Register>()
  var liveOutSet = mutableSetOf<Register>()
  var useSet = mutableSetOf<Register>()
  var defSet = mutableSetOf<Register>()
  val antcdList = mutableListOf<ASMBlock>() // antecedent list
  val succList = mutableListOf<ASMBlock>() // successor list

  fun addPredBlock(block: ASMBlock) {
    antcdList.add(block)
    block.succList.add(this)
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }

  fun firstBrInstOrNull(): ASMInst? {
    return instList.firstOrNull { it is ASMBzInst || it is ASMJInst }
  }
}