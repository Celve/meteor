package backend.pass

import backend.basic.*
import backend.controller.ASMVisitor

class ASMEmit : ASMVisitor() {
  override fun visit(module: ASMModule) {
    module.funcList.forEach { it.accept(this) }
  }

  override fun visit(func: ASMFunc) {
    println("${func.name}: ")
    func.blockList.forEach { it.accept(this) }
    println("")
  }

  override fun visit(block: ASMBlock) {
    println("${block.name}: ")
    block.instList.forEach { it.accept(this) }
    println("")
  }

  override fun visit(inst: ASMStoreInst) {
    val instName = when (inst.byteNum) {
      1 -> "sb"
      2 -> "sh"
      4 -> "sw"
      else -> "sd"
    }
    println("\t$instName ${inst.rd}, ${inst.imm.value}(${inst.rs})")
  }

  override fun visit(inst: ASMLoadInst) {
    val instName = when (inst.byteNum) {
      1 -> "lb"
      2 -> "lh"
      4 -> "lw"
      else -> "ld"
    }
    println("\t$instName ${inst.rd}, ${inst.imm.value}(${inst.rs})")
  }

  override fun visit(inst: ASMBrzInst) {
    println("\t${inst.op} ${inst.rs}, ${inst.label}")
  }

  override fun visit(inst: ASMJInst) {
    println("\tj ${inst.label}")
  }

  override fun visit(inst: ASMRetInst) {
    println("\tret")
  }

  override fun visit(inst: ASMArithInst) {
    println("\t${inst.op} ${inst.rd}, ${inst.rs1}, ${inst.rs2}")
  }

  override fun visit(inst: ASMArithiInst) {
    println("\t${inst.op} ${inst.rd}, ${inst.rs1}, ${inst.imm.value}")
  }

  override fun visit(inst: ASMCmpInst) {
    println("\t${inst.op} ${inst.rd}, ${inst.rs1}, ${inst.rs2}")
  }

  override fun visit(inst: ASMCmpiInst) {
    println("\t${inst.op} ${inst.rs1}, ${inst.imm.value}")
  }

  override fun visit(inst: ASMCallInst) {
    println("\tcall ${inst.label.name}")
  }

  override fun visit(inst: ASMLiInst) {
    println("\tli ${inst.rd}, ${inst.imm.value}")
  }

  override fun visit(inst: ASMMvInst) {
    println("\tmv ${inst.rd}, ${inst.rs}")
  }
}