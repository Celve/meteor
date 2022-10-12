package frontend.metadata

import frontend.utils.ClassScope
import frontend.utils.FuncScope

/**
 * ClassMetadata is used to identify a particular class, with all needed info inside.
 */
data class ClassMd(val className: String) {
  val memberToIndex: HashMap<String, Int> = hashMapOf()
  val classScope = ClassScope(null, className) // className is only needed for ctor
  var hasCustomCtor = false
}

/**
 * The FuncMetadata is used to identify a particular function, with all needed info inside.
 * @param ableOut is used to indicate whether the function is able to get out of its scope. A lambda without & could not get out.
 */
data class FuncMd(
  val funcName: String,
  var argList: List<Pair<String, TypeMd>>,
  var returnType: TypeMd?,
  val ableOut: Boolean = true
) {
  val funcScope = FuncScope(null, funcName, ableOut) // ableOut is only for lambda
}

/**
 * TypeMd is a class extends the ClassMetadata, by additionally taking dimension into considerations.
 * @param dim is used to indicate the dimension of the type. For example, int[][] has dim = 2.
 */
data class TypeMd(val cl: ClassMd, val dim: Int) {
  /** Compare whether two type are the same by comparing both class name and its dimension. */
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