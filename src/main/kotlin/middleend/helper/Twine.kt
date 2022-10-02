package middleend.helper

data class Twine(val symbol: Suffixed, val version: Int) {
  override fun toString(): String {
    return if (version != 0) "$symbol.$version" else symbol.toString()
  }
}

data class Suffixed(val symbol: String, val suffix: String?) {
  override fun toString(): String {
    return if (suffix == null) symbol else "${symbol}.${suffix}"
  }
}