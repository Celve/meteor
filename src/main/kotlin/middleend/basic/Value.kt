package middleend.basic

/**
 * Value is the basic class of almost all classes in LLVM IR design.
 * One value is defined formally in an instruction.
 * By definition, every LLVM Value should have its distinct name.
 * In this project, this constraint is achieved by using a map to store all values, which is SymbolTable.
 */
open class Value(val type: Type, var name: String? = null) {
  var userList: MutableList<User> = mutableListOf()

  fun isDef(): Boolean {
    return name != null
  }

  open fun isConst(): Boolean {
    return false
  }

  // only copy name and type or no-name value
  open fun duplicate(): Value {
    return Value(type, name)
  }

  open fun replicate(): Value {
    return Value(type, name)
  }

  fun substituteAll(value: Value) {
    value.userList.addAll(userList)
    for (user in userList) {
      user.useeList.replaceAll { if (it === this) value else it }
    }
    userList.clear()
  }

  fun substituteAllExcept(value: Value, except: Set<User>) {
    for (user in userList) {
      if (!except.contains(user)) {
        value.userList.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userList = except.toMutableList()
  }

  fun substituteAllExcept(value: Value, except: (User) -> Boolean) {
    for (user in userList) {
      if (!except(user)) {
        value.userList.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userList = userList.filter { except(it) }.toMutableList()
  }

  fun substituteOnly(value: Value, only: (User) -> Boolean) {
    for (user in userList) {
      if (only(user)) {
        value.userList.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userList = userList.filter { !only(it) }.toMutableList()
  }

  override fun toString(): String {
    return "%${name ?: "unnamed"}"
  }
}