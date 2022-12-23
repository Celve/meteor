package backend.basic

import backend.controller.ASMVisitor

class ASMBlock(val pureName: String, val parent: ASMFunc, val executionFrequency: Int) :
  Label("${parent.name}.$pureName") {
  val instList = mutableListOf<ASMInst>()
  var liveInSet = setOf<Register>()
  var liveOutSet = setOf<Register>()
  var useSet = mutableSetOf<Register>()
  var defSet = mutableSetOf<Register>()
  val predList = mutableListOf<ASMBlock>() // antecedent list
  val succList = mutableListOf<ASMBlock>() // successor list

  fun addPredBlock(block: ASMBlock) {
    predList.add(block)
    block.succList.add(this)
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }

  fun firstBrInstOrNull(): ASMInst? {
    return instList.firstOrNull { it is ASMBzInst || it is ASMJInst }
  }
}