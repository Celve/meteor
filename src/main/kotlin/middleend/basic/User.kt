package middleend.basic

import middleend.helper.Twine

open class User(type: Type, name: Twine? = null) : Value(type, name) {
  val useeList: MutableList<Value> = mutableListOf()

  /// This function is bidirectional.
  /// When need to add something, use one of the two.
  fun addUsee(value: Value) {
    value.userList.add(this)
    useeList.add(value)
  }
}