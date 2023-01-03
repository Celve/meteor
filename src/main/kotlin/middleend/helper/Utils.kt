package middleend.helper

import middleend.basic.PointerType
import middleend.basic.Type

object Utils {
  fun binaryOpToStr(op: String): String {
    return when (op) {
      "*" -> "mul"
      "/" -> "sdiv"
      "%" -> "srem"
      "+" -> "add"
      "-" -> "sub"
      "<<" -> "shl"
      ">>" -> "ashr"
      "<=" -> "sle"
      "<" -> "slt"
      ">=" -> "sge"
      ">" -> "sgt"
      "==" -> "eq"
      "!=" -> "ne"
      "&" -> "and"
      "|" -> "or"
      "^" -> "xor"
      "&&" -> "land"
      "||" -> "lor"
      else -> throw Exception("unexpected operator")
    }
  }

  fun unaryOpToStr(op: String): String {
    return when (op) {
      "++" -> "inc"
      "--" -> "dec"
      "+" -> "pos"
      "-" -> "neg"
      "~" -> "bnot"
      "!" -> "lnot"
      else -> throw Exception("unexpected operator")
    }
  }

  fun getPointee(type: Type): Type {
    return (type as PointerType).pointeeTy!!
  }

  fun stringLength(str: String): Int {
    var transMode = false
    var length = str.length + 1
    for (index in 0..str.length - 1) {
      if (!transMode && str[index] == '\\') {
        transMode = true
        --length
      } else if (transMode) {
        transMode = false
      }
    }
    return length
  }

  fun calculate(op: String, lhs: Int?, rhs: Int?): Int {
    return if (lhs == null || rhs == null) {
      when (op) {
        "eq" -> if (lhs == rhs) 1 else 0
        "ne" -> if (lhs != rhs) 1 else 0
        else -> throw Exception("unexpected operator")
      }
    } else {
      when (op) {
        "mul" -> lhs * rhs
        "sdiv" -> lhs / rhs
        "srem" -> lhs % rhs
        "add" -> lhs + rhs
        "sub" -> lhs - rhs
        "shl" -> lhs shl rhs
        "ashr" -> lhs shr rhs
        "sle" -> if (lhs <= rhs) 1 else 0
        "slt" -> if (lhs < rhs) 1 else 0
        "sge" -> if (lhs >= rhs) 1 else 0
        "sgt" -> if (lhs > rhs) 1 else 0
        "eq" -> if (lhs == rhs) 1 else 0
        "ne" -> if (lhs != rhs) 1 else 0
        "and" -> lhs and rhs
        "or" -> lhs or rhs
        "xor" -> lhs xor rhs
        "land" -> if (lhs != 0 && rhs != 0) 1 else 0
        "lor" -> if (lhs != 0 || rhs != 0) 1 else 0
        else -> throw Exception("unexpected operator")
      }
    }
  }

  fun eliminateVersionWithoutDot(name: String): String {
    return name.replace("\\d+$".toRegex(), "")
  }

  fun eliminateVersionWithDot(name: String): String {
    return name.replace("\\.\\d+$".toRegex(), "") // remove tail number
  }
}
