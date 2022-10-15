package backend.basic

abstract class Label(val name: String) {
  override fun toString(): String {
    return name
  }
}