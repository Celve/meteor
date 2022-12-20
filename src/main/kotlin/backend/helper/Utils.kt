package backend.helper

object Utils {
  fun calculate(op: String, rs: Int, rt: Int): Int {
    return when (op) {
      "add" -> rs + rt
      "sub" -> rs - rt
      "mul" -> rs * rt
      "sdiv" -> rs / rt
      "srem" -> rs % rt
      "shl" -> rs shl rt
      "ashr" -> rs shr rt
      "xor" -> rs xor rt
      "and" -> rs and rt
      "or" -> rs or rt
      else -> throw Exception("Unknown op: $op")
    }
  }

  fun revCond(cond: String): String {
    return when (cond) {
      "slt" -> "sgt"
      "sle" -> "sge"
      "sgt" -> "slt"
      "sge" -> "sle"
      "eq" -> "eq"
      "ne" -> "ne"
      else -> throw Exception("Unknown condition: $cond")
    }
  }

  fun getPhyRegId(abi: String): Int {
    return when (abi) {
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
  }

  fun align2Identifier(alignment: Int): String {
    return when (alignment) {
      1 -> "byte"
      2 -> "half"
      else -> "word"
    }
  }
}