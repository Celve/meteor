package middleend.basic

/// It would never make sense to RAUW them.
open class ConstantData(type: Type, name: String? = null) : Constant(type, name)

class ConstantInt(val numOfBits: Int, val intValue: Int) : ConstantData(TypeFactory.getIntType(numOfBits)) {
//  fun getInt(): Int {
//    return name.toString().toInt()
//  }

  override fun toOperand(): String {
    return intValue.toString()
  }

  override fun toString(): String {
    return intValue.toString()
  }
}

class ConstantNull : ConstantData(TypeFactory.getNullType())

/// An array constant whose element type is a simple 1-byte integer.
/// It's actually a representation of string.
class ConstantStr(val str: String, name: String) :
  ConstantData(TypeFactory.getArrayType(TypeFactory.getIntType(8), str.length + 1), name) {
  override fun toString(): String {
    return "@$name = private unnamed_addr constant [${str.length + 1} x i8] c\"$str\\00\", align 1\n"
  }

  override fun toOperand(): String {
    return "@$name"
  }
}