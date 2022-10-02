package middleend.basic

import middleend.helper.Twine

/// Value is the basic class of almost all classes in LLVM IR design.
/// One value is defined formally in an instruction.
/// In order to have good readability, all values including temporary values should be named in a specified way.
/// For a unique valueName determined in scopeManager, following constraints would be guaranteed:
/// Because valueName is unique, valueName.suffix must be unique.
/// Everytime a valueName is added into symbolTable, it must have a suffix, which is different forms of symbol.
/// If we want to find the original symbol, we remove the suffix to get it.
open class Value(val type: Type, val name: Twine? = null) {
  val userList: MutableList<User> = mutableListOf()

  open fun toOperand(): String {
    return name.toString()
  }

  /// This function is bidirectional.
  fun addUser(user: User) {
    userList.add(user)
    user.useeList.add(this)
  }
}