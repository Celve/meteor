package middleend.struct

import middleend.basic.Func
import middleend.basic.Instruction
import middleend.basic.PhiInst
import middleend.basic.Value

class IndVar(val func: Func) {
  val all2Phi = hashMapOf<Instruction, PhiInst>()

  fun isIV(inst: Value): Boolean {
    return all2Phi.containsKey(inst)
  }

  fun getBase(inst: Instruction): PhiInst? {
    return all2Phi[inst]
  }
}