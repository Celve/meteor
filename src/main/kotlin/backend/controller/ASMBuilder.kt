package backend.controller

import backend.basic.ASMBlock
import backend.basic.ASMFunc
import backend.basic.Register
import backend.basic.VirReg
import middleend.basic.BasicBlock
import middleend.basic.Func
import middleend.basic.Value

object ASMBuilder {
  var asmFunc: ASMFunc? = null
  var asmBlock: ASMBlock? = null
  var stackAlloca = 0
  private var value2Reg = hashMapOf<Value, Register>()
  private var reg2Value = hashMapOf<Register, Value>()
  var virRegId = 0

  fun newVirReg(): VirReg {
    return VirReg(virRegId++)
  }

  fun setCurrentFunc(func: Func) {
    asmFunc = ASMFunc(func)
    value2Reg = asmFunc!!.value2Reg
    reg2Value = asmFunc!!.reg2Value
  }

  fun setCurrentBlock(block: BasicBlock) {
    asmBlock = ASMBlock(block)
  }

  fun call(funcName: String) {

  }

  fun alloca(size: Int) {
    stackAlloca += size
  }

  fun mvVal2NewReg(src: Value): Register {
    val virReg = newVirReg()
    value2Reg[src] = virReg
    reg2Value[virReg] = src
    return virReg
  }

  fun mvReg2NewReg(src: Register): Register {
    val virReg = newVirReg()
    reg2Value[virReg] = reg2Value[src]!!
    value2Reg[reg2Value[src]!!] = virReg
    return virReg
  }

  fun mvVal2Reg(src: Value, dst: Register) {
    value2Reg[src] = dst
    reg2Value[dst] = src
  }

  fun load2NewReg(src: Value, dst: Value): Register {
    val virReg = newVirReg()
    // TODO: create load insnt with both src and dst
    value2Reg[dst] = virReg
    reg2Value[virReg] = dst
    return virReg
  }

  fun arith2NewReg(op: String, lhs: Value, rhs: Value, dst: Value): Register {
    val virReg = newVirReg()
    return virReg
  }

  fun ret() {}

  fun store(src: Value, dst: Value) {}
}