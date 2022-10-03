package middleend.basic

open class User(type: Type, name: String? = null) : Value(type, name) {
  val useeList: MutableList<Value> = mutableListOf()

  /// This function is bidirectional.
  /// When need to add something, use one of the two.
  fun addUsee(value: Value) {
    value.userList.add(this)
    useeList.add(value)
  }
}