package middleend.basic

class Argument(type: Type, name: String, func: Func) : Value(type, name) {
  constructor(value: Value, func: Func) : this(value.type, value.name!!, func)

  override fun toOperand(): String {
    return "$type $name"
  }
}