package backend.basic

import backend.helper.Utils

abstract class Register {
  abstract fun getRegId(): Int
}

data class VirReg(val id: Int) : Register() {
  override fun getRegId(): Int {
    return id
  }

  override fun toString(): String {
    return "v$id"
  }
}

data class PhyReg(val id: Int) : Register() {
  constructor(abi: String) : this(Utils.getPhyRegId(abi))

  override fun getRegId(): Int {
    return id
  }

  override fun toString(): String {
    return when (id) {
      0 -> "zero"
      1 -> "ra"
      2 -> "sp"
      3 -> "gp"
      4 -> "tp"
      5 -> "t0"
      6 -> "t1"
      7 -> "t2"
      8 -> "s0"
      9 -> "s1"
      10 -> "a0"
      11 -> "a1"
      12 -> "a2"
      13 -> "a3"
      14 -> "a4"
      15 -> "a5"
      16 -> "a6"
      17 -> "a7"
      18 -> "s2"
      19 -> "s3"
      20 -> "s4"
      21 -> "s5"
      22 -> "s6"
      23 -> "s7"
      24 -> "s8"
      25 -> "s9"
      26 -> "s10"
      27 -> "s11"
      28 -> "t3"
      29 -> "t4"
      30 -> "t5"
      31 -> "t6"
      else -> "error($id)"
    }
  }
}

object RegInfo {
  val callerSavedRegList = ((0..6).map { "t$it" } + (0..7).map { "a$it" } + "ra").map { Utils.getPhyRegId(it) }
  val calleeSavedRegList = ((0..11).map { "s$it" }).map { Utils.getPhyRegId(it) }
  val assignableRegList = calleeSavedRegList + callerSavedRegList
}