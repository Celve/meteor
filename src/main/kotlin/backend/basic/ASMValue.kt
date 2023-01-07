package backend.basic

import middleend.basic.User
import middleend.basic.Value

open class ASMValue(val name: String?) {
  var userSet = mutableSetOf<ASMUser>()

  fun substituteAll(value: ASMValue) {
    value.userSet.addAll(userSet)
    for (user in userSet) {
      user.useeList.replaceAll { if (it === this) value else it }
    }
    userSet.clear()
  }

  fun substituteAllExcept(value: ASMValue, except: Set<ASMUser>) {
    for (user in userSet) {
      if (!except.contains(user)) {
        value.userSet.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userSet = except.toMutableSet()
  }

  fun substituteAllExcept(value: ASMValue, except: (ASMUser) -> Boolean) {
    for (user in userSet) {
      if (!except(user)) {
        value.userSet.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userSet = userSet.filter { except(it) }.toMutableSet()
  }

  fun substituteOnly(value: ASMValue, only: (ASMUser) -> Boolean) {
    for (user in userSet) {
      if (only(user)) {
        value.userSet.add(user)
        user.useeList.replaceAll { if (it === this) value else it }
      }
    }
    userSet = userSet.filter { !only(it) }.toMutableSet()
  }
}