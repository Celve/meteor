package backend.basic

import middleend.basic.Func
import middleend.basic.Value

class ASMFunc(func: Func) {
  val value2Reg = hashMapOf<Value, Register>()
  val reg2Value = hashMapOf<Register, Value>()
}