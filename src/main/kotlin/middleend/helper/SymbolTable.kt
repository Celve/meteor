package middleend.helper

class SymbolTable {
  private var counter = mutableMapOf<String, Int>().withDefault { 0 }

  fun rename(name: String): String {
    val pureName = name.replace("\\d+$".toRegex(), "") // remove tail number
    val ver = counter.getValue(pureName)
    counter[pureName] = ver + 1
    return if (ver == 0) {
      pureName
    } else {
      "$pureName$ver"
    }
  }

  fun clear() {
    counter.clear()
  }
}