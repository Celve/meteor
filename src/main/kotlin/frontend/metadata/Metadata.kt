package frontend.metadata

import frontend.utils.ClassScope
import frontend.utils.FuncScope
import middleend.basic.FuncType
import middleend.basic.StructType
import middleend.basic.Type

/// ClassMetadata is used to identify a particular class, with all info inside.
data class ClassMd(val className: String) {
  val classScope = ClassScope(null, className) // className is only needed for ctor
  var structIr: StructType? = null
  val funcIrList: MutableList<FuncType> = mutableListOf()
}

/// The FuncMetadata is the same to ClassMetadata.
data class FuncMd(
  val funcName: String,
  var paramInput: List<TypeMd>,
  var returnType: TypeMd?,
  val ableOut: Boolean = true
) {
  val funcScope = FuncScope(null, funcName, ableOut) // ableOut is only for lambda
  var funcIr: FuncType? = null
}

/// This is a class extends the ClassMetadata.
/// A ClassMetadata could be regarded as an instance type, and TypeMetadata could be regarded as a general type.
/// Because it additionally takes dimension into considerations.
data class TypeMd(val cl: ClassMd, val dim: Int) {
  val typeIr: Type? = null

  // one is concrete and one is null is matched, which is differed from ==
  fun matchesWith(obj: TypeMd): Boolean {
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