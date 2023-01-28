package middleend.basic

/**
 * Value is the basic class of almost all classes in LLVM IR design.
 * One value is defined formally in an instruction.
 * By definition, every LLVM Value should have its distinct name.
 * In this project, this constraint is achieved by using a map to store all values, which is SymbolTable.
 */
open class Value(var type: Type, var name: String? = null) {
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

  open fun substitutedBy(value: Value) {
    value.userList.addAll(userList)
    for (user in userList) {
      user.useeList.replaceAll { if (it === this) value else it }
    }
    userList.clear()
  }

  open fun substitutedByExcept(value: Value, except: Set<User>) {
    for (user in userList) {
      if (!except.contains(user)) {
        value.userList.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userList = except.toMutableList()
  }

  open fun substitutedByExcept(value: Value, except: (User) -> Boolean) {
    for (user in userList) {
      if (!except(user)) {
        value.userList.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userList = userList.filter { except(it) }.toMutableList()
  }

  open fun substitutedByWhen(value: Value, cond: (User) -> Boolean) {
    for (user in userList) {
      if (cond(user)) {
        value.userList.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userList = userList.filter { !cond(it) }.toMutableList()
  }

  override fun toString(): String {
    return "%${name ?: "unnamed"}"
  }
}