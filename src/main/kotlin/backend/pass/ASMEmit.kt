package backend.pass

import backend.basic.*
import backend.controller.ASMVisitor

class ASMEmit : ASMVisitor() {
  override fun visit(module: ASMModule) {
    module.funcList.forEach { it.accept(this) }
    module.globalPtr.forEach { it.value.accept(this) }
  }

  override fun visit(globalPtr: ASMGlobalPointer) {
    globalPtr.defDirList.forEach { println("\t${it.op}\t${it.argList.joinToString(",")}") }
    println("${globalPtr.name}:")
    for (it in globalPtr.emitDirList) {
      if (it.op == ".asciz") {
        print("\t${it.op}\t")
        for ((index, char) in it.argList[0].withIndex()) {
          if (index != 0 && index != it.argList[0].length - 1) {
            when (char) {
              '\"' -> print("\\\"")
              '\\' -> print("\\\\")
              '\n' -> print("\\n")
              else -> print(char)
            }
          } else {
            print(char)
          }
        }
        println("")
      } else {
        println("\t${it.op}\t${it.argList.joinToString(",")}")
      }
    }
    println("")
  }

  override fun visit(func: ASMFunc) {
    println(".text")
    println(".globl	${func.name}")
    println(".p2align	1")
    println(".type	${func.name},@function")
    println("${func.name}: ")
    func.blockList.forEach { it.accept(this) }
    println("")
  }

  override fun visit(block: ASMBlock) {
    println("${block.name}: ")
    block.instList.forEach { it.accept(this) }
    println("")
  }

  fun printPs(comment: String) {
    if (comment != "") {
      println("\t\t; ${comment}")
    } else {
      println("")
    }
  }

  override fun visit(inst: ASMStoreInst) {
    val instName = when (inst.byteNum) {
      1 -> "sb"
      2 -> "sh"
      4 -> "sw"
      else -> {
        println(inst.byteNum)
        "sd"
      }
    }
    print("\t$instName ${inst.rs2}, ${inst.imm}(${inst.rs1})")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMLoadInst) {
    val instName = when (inst.byteNum) {
      1 -> "lb"
      2 -> "lh"
      4 -> "lw"
      else -> "ld"
    }
    print("\t$instName ${inst.rd}, ${inst.imm}(${inst.rs})")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMBzInst) {
    print("\t${inst.op} ${inst.rs}, ${inst.label}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMJInst) {
    print("\tj ${inst.label}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMRetInst) {
    print("\tret")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMArithInst) {
    print("\t${inst.op} ${inst.rd}, ${inst.rs1}, ${inst.rs2}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMArithiInst) {
    print("\t${inst.op} ${inst.rd}, ${inst.rs}, ${inst.imm}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMCmpInst) {
    print("\t${inst.op} ${inst.rd}, ${inst.rs1}, ${inst.rs2}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMCmpiInst) {
    print("\t${inst.op} ${inst.rs1}, ${inst.imm}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMCallInst) {
    print("\tcall ${inst.label.name}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMLiInst) {
    print("\tli ${inst.rd}, ${inst.imm}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMMvInst) {
    print("\tmv ${inst.rd}, ${inst.rs}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMLaInst) {
    print("\tla ${inst.rd}, ${inst.symbol}")
    printPs(inst.comment)
  }

  override fun visit(inst: ASMCmpzInst) {
    print("\t${inst.op} ${inst.rd}, ${inst.rs1}")
    printPs(inst.comment)
  }
}