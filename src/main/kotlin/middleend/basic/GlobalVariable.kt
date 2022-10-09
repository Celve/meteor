package middleend.basic

class GlobalVariable(name: String, type: Type) : GlobalValue(name, type) {
  override fun toString(): String {
    return "@$name = global ${(type as PointerType).pointeeTy!!} zeroinitializer, align ${type.getAlign()}\n"
  }
}