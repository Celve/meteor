package middleend.basic

import middleend.helper.Utils
import middleend.pass.IRVisitor

open class Constant(type: Type, name: String? = null) : User(type, name) {
  override fun isConst(): Boolean {
    return true
  }
}

/// It would never make sense to RAUW them.
open class ConstantData(type: Type, name: String? = null) : Constant(type, name)

class ConstantInt(val numOfBits: Int, val value: Int) : ConstantData(TypeFactory.getIntType(numOfBits)) {
  override fun duplicate(): Value {
    return ConstantInt(numOfBits, value)
  }

  override fun toString(): String {
    return value.toString()
  }
}

class ConstantNull : ConstantData(TypeFactory.getNullType()) {
  override fun duplicate(): Value {
    return ConstantNull()
  }

  override fun toString(): String {
    return "null"
  }
}

/// An array constant whose element type is a simple 1-byte integer.
/// It's actually a representation of string.
class ConstantStr(var str: String, name: String) :
  ConstantData(
    TypeFactory.getPtrType(TypeFactory.getArrayType(TypeFactory.getIntType(8), Utils.stringLength(str))),
    name
  ) {
  val length = Utils.stringLength(str)

  override fun duplicate(): Value {
    return ConstantStr(str, name!!)
  }

  init {
    // those methods don't modify themselves
    str = str.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\")
  }

  override fun toString(): String {
    return "@$name"
  }

  fun accept(visitor: IRVisitor) {
    visitor.visit(this)
  }
}