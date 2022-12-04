package middleend.basic

open class Constant(type: Type, name: String? = null) : User(type, name) {
  override fun isConst(): Boolean {
    return true
  }
}
