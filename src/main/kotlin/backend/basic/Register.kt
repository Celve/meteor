package backend.basic

abstract class Register : ASMValue()

data class VirReg(val id: Int) : Register() {
  override fun toString(): String {
    return "v$id"
  }
}

data class PhyReg(val id: Int) : Register() {
  constructor(abi: String) : this(
    when (abi) {
      "zero" -> 0
      "ra" -> 1
      "sp" -> 2
      "gp" -> 3
      "tp" -> 4
      "t0" -> 5
      "t1" -> 6
      "t2" -> 7
      "s0" -> 8
      "s1" -> 9
      "a0" -> 10
      "a1" -> 11
      "a2" -> 12
      "a3" -> 13
      "a4" -> 14
      "a5" -> 15
      "a6" -> 16
      "a7" -> 17
      "s2" -> 18
      "s3" -> 19
      "s4" -> 20
      "s5" -> 21
      "s6" -> 22
      "s7" -> 23
      "s8" -> 24
      "s9" -> 25
      "s10" -> 26
      "s11" -> 27
      "t3" -> 28
      "t4" -> 29
      "t5" -> 30
      "t6" -> 31
      else -> 0
    }
  )

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
      else -> "zero"
    }
  }
}

class GloReg : Register()