package backend.basic

import backend.helper.Utils

abstract class Register(name: String) : ASMValue(name) {
  abstract fun getRegId(): Int
}

data class VirReg(val id: Int) : Register("v$id") {
  override fun getRegId(): Int {
    return id
  }

  override fun toString(): String {
    return name!!
  }
}

data class PhyReg(val id: Int) : Register(Utils.getPhyRegName(id)) {
  constructor(abi: String) : this(Utils.getPhyRegId(abi))

  override fun getRegId(): Int {
    return id
  }

  override fun toString(): String {
    return name!!
  }
}

object RegInfo {
  val callerSavedRegList = ((0..6).map { "t$it" } + (0..7).map { "a$it" } + "ra").map { Utils.getPhyRegId(it) }
  val calleeSavedRegList = ((0..11).map { "s$it" }).map { Utils.getPhyRegId(it) }
  val assignableRegList = calleeSavedRegList + callerSavedRegList
}