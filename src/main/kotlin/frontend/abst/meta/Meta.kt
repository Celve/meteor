package frontend.abst.meta

import frontend.abst.utils.ClassScope
import frontend.abst.utils.FuncScope


data class ClassMeta(
  val className: String,
) {
  val classScope = ClassScope(null)
}

data class FuncMeta(
  val funcName: String,
  var paramInput: List<TypeMeta>,
  var returnType: TypeMeta?,
) {
  val funcScope = FuncScope(null)
}

data class TypeMeta(val cl: ClassMeta, val dim: Int) {
  fun isBool(): Boolean {
    return cl.className == "bool" && dim == 0
  }

  fun isInt(): Boolean {
    return cl.className == "int" && dim == 0
  }

  fun isVoid(): Boolean {
    return cl.className == "void" && dim == 0
  }

  fun isString(): Boolean {
    return cl.className == "string" && dim == 0
  }

  fun isArray(): Boolean {
    return dim != 0
  }

  override fun toString(): String {
    return cl.className + "[]".repeat(dim)
  }
}