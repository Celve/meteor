package middleend.basic

open class User(type: Type, name: String? = null) : Value(type, name) {
  var useeList: MutableList<Value> = mutableListOf()

  fun eliminate() {
    useeList.forEach { it.userList.remove(this) }
    useeList.clear()
  }

  fun replace(from: Value, to: Value) {
    useeList.replaceAll { if (it === from) to else it }
    from.userList.remove(this)
    to.userList.add(this)
  }

  fun replaceAll(applier: (Value) -> Value) {
    useeList.replaceAll {
      it.userList.remove(this)
      val value = applier(it)
      value.userList.add(this)
      value
    }
  }

  companion object {
    fun link(user: User, usee: Value) {
      user.useeList.add(usee)
      usee.userList.add(user)
    }

    fun cut(user: User, usee: Value) {
      user.useeList.remove(usee)
      usee.userList.remove(user)
    }
  }
}