package middleend.helper

abstract class SymbolTable {
  var counter = mutableMapOf<String, Int>().withDefault { 0 }
  abstract fun rename(name: String): String

  fun clear() {
    counter.clear()
  }
}

class ValueTable : SymbolTable() {
  override fun rename(name: String): String {
    val withoutVersion = name.replace("\\d+$".toRegex(), "") // remove tail number
    val ver = counter.getValue(withoutVersion)
    counter[withoutVersion] = ver + 1
    return if (ver == 0) {
      withoutVersion
    } else {
      "$withoutVersion$ver"
    }
  }
}

class SSATable : SymbolTable() {
  override fun rename(name: String): String {
    val withoutVersion = name.replace("\\.\\d+$".toRegex(), "") // remove tail number
    val ver = counter.getValue(withoutVersion)
    counter[withoutVersion] = ver + 1
    return if (ver == 0) {
      withoutVersion
    } else {
      "$withoutVersion.$ver"
    }
  }
}