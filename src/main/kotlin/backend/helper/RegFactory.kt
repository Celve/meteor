package backend.helper

import backend.basic.ASMFunc
import backend.basic.PhyReg
import backend.basic.VirReg


class RegFactory {
  var virRegId = 0
  val id2Vir = hashMapOf<Int, VirReg>()
  val id2Phy = hashMapOf<Int, PhyReg>()
  var position: ASMFunc? = null

  fun newVirReg(): VirReg {
    val id = virRegId++
    id2Vir[id] = VirReg(id)
    position!!.usedVirRegNum++
    return id2Vir[id]!!
  }

  fun getPhyReg(abi: String): PhyReg {
    val id = Utils.getPhyRegId(abi)
    if (!id2Phy.containsKey(id)) {
      id2Phy[id] = PhyReg(id)
    }
    return id2Phy[id]!!
  }

  fun getPhyReg(id: Int): PhyReg {
    if (!id2Phy.containsKey(id)) {
      id2Phy[id] = PhyReg(id)
    }
    return id2Phy[id]!!
  }

  fun getVirReg(id: Int): VirReg? {
    return id2Vir[id]
  }
}