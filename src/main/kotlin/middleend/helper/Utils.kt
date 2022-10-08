package middleend.helper

object Utils {
  fun binaryOpToStr(op: String): String {
    return when (op) {
      "*" -> "mul"
      "/" -> "div"
      "%" -> "mod"
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
}