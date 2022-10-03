package middleend.basic

open class GlobalValue(name: String, type: Type) : Constant(type, name) {
  override fun toOperand(): String {
    return "@$name"
  }
}