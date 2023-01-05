package backend.basic

abstract class Label(name: String): ASMValue(name) {
  override fun toString(): String {
    return name!!
  }
}