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
class ConstantStr(val strValue: String) :
  ConstantData(TypeFactory.getArrayType(TypeFactory.getIntType(8), strValue.length))