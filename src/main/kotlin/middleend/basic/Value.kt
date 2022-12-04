package middleend.basic

/**
 * Value is the basic class of almost all classes in LLVM IR design.
 * One value is defined formally in an instruction.
 * By definition, every LLVM Value should have its distinct name.
 * In this project, this constraint is achieved by using a map to store all values, which is ValueSymbolTable.
 * @see middleend.helper.ValueSymbolTable
 */
open class Value(val type: Type, var name: String? = null) {
  val userList: MutableList<User> = mutableListOf()

  fun isDef(): Boolean {
    return name != null
  }

  open fun isConst(): Boolean {
    return false
  }

  override fun toString(): String {
    return "%${name ?: "unnamed"}"
  }

  /**
   * This function is bidirectional. When user is added, its useeList would be updated, too.
   */
  fun addUser(user: User) {
    userList.add(user)
    user.useeList.add(this)
  }

  fun removeUser(user: User) {
    user.useeList.remove(this)
    userList.remove(user)
  }
}