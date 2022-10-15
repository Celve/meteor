package middleend.basic

import middleend.helper.Utils

/// It would never make sense to RAUW them.
open class ConstantData(type: Type, name: String? = null) : Constant(type, name)

class ConstantInt(val numOfBits: Int, val value: Int) : ConstantData(TypeFactory.getIntType(numOfBits)) {
  override fun toOperand(): String {
    return value.toString()
  }

  override fun toString(): String {
    return value.toString()
  }
}

class ConstantNull : ConstantData(TypeFactory.getNullType()) {
  override fun toOperand(): String {
    return "null"
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

  init {
    // those methods don't modify themselves
    str = str.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\")
  }

  override fun toString(): String {
    val outputString = str.replace("\\", "\\\\").replace("\n", "\\0A").replace("\"", "\\22").plus("\\00")
    return "@$name = private unnamed_addr constant [${length} x i8] c\"$outputString\", align 1\n"
  }

  override fun toOperand(): String {
    return "@$name"
  }
}