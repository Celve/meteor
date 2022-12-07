package middleend.helper

class SymbolTable(val limit: String) {
  var counter = mutableMapOf<String, Int>().withDefault { 0 }

  fun rename(name: String): String {
    val withoutVersion = name.replace("\\d+$".toRegex(), "") // remove tail number
    val withoutLimit = if (withoutVersion.endsWith(limit)) {
      withoutVersion.dropLast(limit.length)
    } else {
      withoutVersion
    }
    val ver = counter.getValue(withoutLimit)
    counter[withoutLimit] = ver + 1
    return if (ver == 0) {
      withoutLimit
    } else {
      "$withoutLimit$limit$ver"
    }
  }

  fun clear() {
    counter.clear()
  }
}