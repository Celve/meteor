package middleend.basic

open class User(type: Type, name: String? = null) : Value(type, name) {
  var useeList: MutableList<Value> = mutableListOf()

  open fun eliminate() {
    useeList.forEach { it.userList.remove(this) }
    useeList.clear()
  }

  open fun replace(from: Value, to: Value) {
    useeList.replaceAll { if (it === from) to else it }
    from.userList.remove(this)
    to.userList.add(this)
  }

  open fun replaceAll(applier: (Value) -> Value) {
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

    // This function is not safe because a value might have multiple occurrence inside one useeList.
    // Use position cut instead.
    fun cut(user: User, usee: Value) {
      user.useeList.remove(usee)
      usee.userList.remove(user)
    }

    // This function is the position cut.
    fun cut(user: User, index: Int) {
      val usee = user.useeList[index]
      user.useeList.removeAt(index)
      usee.userList.remove(user)
    }
  }
}