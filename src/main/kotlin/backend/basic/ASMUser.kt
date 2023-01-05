package backend.basic

open class ASMUser(name: String?) : ASMValue(name) {
  val useeList = mutableListOf<ASMValue>()

  companion object {
    fun link(user: ASMUser, usee: ASMValue) {
      user.useeList.add(usee)
      usee.userSet.add(user)
    }

    // This function is not safe because a value might have multiple occurrence inside one useeList.
    // Use position cut instead.
    fun cut(user: ASMUser, usee: ASMValue) {
      user.useeList.remove(usee)
      usee.userSet.remove(user)
    }

    // This function is the position cut.
    fun cut(user: ASMUser, index: Int) {
      val usee = user.useeList[index]
      user.useeList.removeAt(index)
      usee.userSet.remove(user)
    }
  }
}