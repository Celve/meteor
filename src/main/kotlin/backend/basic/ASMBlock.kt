package backend.basic

import backend.pass.ASMVisitor

class ASMBlock(val pureName: String, val parent: ASMFunc, val execFreq: Int) :
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

  fun removeInst(inst: ASMInst) {
    inst.eliminate()
    instList.remove(inst)
  }

  fun replaceInst(oldInst: ASMInst, newInst: ASMInst) {
    oldInst.eliminate()
    newInst.parent = this
    val index = instList.indexOf(oldInst)
    instList[index] = newInst
  }

  fun accept(visitor: ASMVisitor) {
    visitor.visit(this)
  }

  fun firstBrInstOrNull(): ASMInst? {
    return instList.firstOrNull { it is ASMBzInst || it is ASMJInst }
  }
}