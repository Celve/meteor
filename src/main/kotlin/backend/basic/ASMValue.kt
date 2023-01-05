package backend.basic

open class ASMValue(val name: String?) {
  val userSet = mutableSetOf<ASMUser>()
}