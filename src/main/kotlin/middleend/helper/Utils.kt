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
      "!=" -> "neq"
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
}