package backend.basic

class Imm(val value: Int) : ASMValue() {
  override fun toString(): String {
    return value.toString()
  }
}