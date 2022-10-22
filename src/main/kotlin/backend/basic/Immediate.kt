package backend.basic

abstract class Immediate

class Literal(val value: Int) : Immediate() {
  override fun toString(): String {
    return value.toString()
  }
}

class Modifier(val op: String, val symbol: ASMGlobalPointer) : Immediate() {
  override fun toString(): String {
    return "%$op($symbol)"
  }
}