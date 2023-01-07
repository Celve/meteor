package backend.pass

import backend.basic.*
import backend.controller.ASMVisitor

object ASMEmit : ASMVisitor() {
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
    println("\t$instName ${inst.getRs2()}, ${inst.getImm()}(${inst.getRs1()})")
  }

  override fun visit(inst: ASMLoadInst) {
    val instName = when (inst.byteNum) {
      1 -> "lb"
      2 -> "lh"
      4 -> "lw"
      else -> "ld"
    }
    println("\t$instName ${inst.getRd()}, ${inst.getImm()}(${inst.getRs()})")
  }

  override fun visit(inst: ASMBrInst) {
    println("\t${inst.op} ${inst.getRs1()}, ${inst.getRs2()}, ${inst.getLabel()}")
  }

  override fun visit(inst: ASMBzInst) {
    println("\t${inst.op} ${inst.getRs()}, ${inst.getLabel()}")
  }

  override fun visit(inst: ASMJInst) {
    println("\tj ${inst.getLabel()}")
  }

  override fun visit(inst: ASMRetInst) {
    println("\tret")
  }

  override fun visit(inst: ASMArithInst) {
    println("\t${inst.op} ${inst.getRd()}, ${inst.getRs1()}, ${inst.getRs2()}")
  }

  override fun visit(inst: ASMArithiInst) {
    println("\t${inst.op} ${inst.getRd()}, ${inst.getRs()}, ${inst.getImm()}")
  }

  override fun visit(inst: ASMCmpInst) {
    println("\t${inst.op} ${inst.getRd()}, ${inst.getRs1()}, ${inst.getRs2()}")
  }

  override fun visit(inst: ASMCmpiInst) {
    println("\t${inst.op} ${inst.getRd()}, ${inst.getRs()}, ${inst.getImm()}")
  }

  override fun visit(inst: ASMCallInst) {
    println("\tcall ${inst.getLabel().name}")
  }

  override fun visit(inst: ASMLiInst) {
    println("\tli ${inst.getRd()}, ${inst.getImm()}")
  }

  override fun visit(inst: ASMMvInst) {
    println("\tmv ${inst.getRd()}, ${inst.getRs()}")
  }

  override fun visit(inst: ASMLaInst) {
    println("\tla ${inst.getRd()}, ${inst.getSymbol()}")
  }

  override fun visit(inst: ASMCmpzInst) {
    println("\t${inst.op} ${inst.getRd()}, ${inst.getRs()}")
  }
}