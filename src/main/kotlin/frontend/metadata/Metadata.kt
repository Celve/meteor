package frontend.metadata

import frontend.utils.ClassScope
import frontend.utils.FuncScope

// ClassMetadata is used to identify a particular class, with all info inside
data class ClassMetadata(val className: String) {
  val classScope = ClassScope(null, className) // className is only needed for ctor
}

// the FuncMetadata is the same to ClassMetadata
data class FuncMetadata(
  val funcName: String,
  var paramInput: List<TypeMetadata>,
  var returnType: TypeMetadata?,
  val ableOut: Boolean = true
) {
  val funcScope = FuncScope(null, ableOut) // ableOut is only for lambda
}

// this is a class extends the ClassMetadata
// a ClassMetadata could be regarded as an instance type, and TypeMetadata coul be regarded as a general type
// because it additionally takes dimension into considerations
data class TypeMetadata(val cl: ClassMetadata, val dim: Int) {
  // one is concrete and one is null is matched, which is differed from ==
  fun matchesWith(obj: TypeMetadata): Boolean {
    if (isNull() || obj.isNull()) {
      return !isPrimitive() && !obj.isPrimitive()
    }
    return cl.className == obj.cl.className && dim == obj.dim
  }

  private fun isPrimitive(): Boolean {
    return isBool() || isInt() || isString()
  }

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

  fun isNull(): Boolean {
    return cl.className == "null" && dim == 0
  }

  override fun toString(): String {
    return cl.className + "[]".repeat(dim)
  }
}