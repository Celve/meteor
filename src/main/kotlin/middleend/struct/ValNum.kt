package middleend.struct

import middleend.basic.*

class ValNum {
  private val valNum = hashMapOf<Value, String>()
  private var cnt = 0

  fun get(value: Value): String? {
    return when (value) {
      is BasicBlock -> "b${value.name}"
      is GlobalVariable -> "g${value.name}"
      is ConstantStr -> "c${value.name}"
      is ConstantInt -> "i${value.value}"
      is ConstantNull -> "null"
      else -> valNum[value]
    }
  }

  fun alias(new: Value, old: Value) {
    valNum[new] = get(old)!!
  }

  fun new(value: Value) {
    valNum[value] = "v${cnt++}"
  }
}