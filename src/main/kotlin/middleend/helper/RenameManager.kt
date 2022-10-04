package middleend.helper

object RenameManager {
  private val nameToVersion: HashMap<String, Int> = hashMapOf(
    "retval" to 0,
    "retptr" to 0,
    "mul" to 0,
    "div" to 0,
    "mod" to 0,
    "add" to 0,
    "sub" to 0,
    "shl" to 0,
    "ashr" to 0,
    "sle" to 0,
    "slt" to 0,
    "sge" to 0,
    "sgt" to 0,
    "eq" to 0,
    "neq" to 0,
    "and" to 0,
    "or" to 0,
    "xor" to 0,
    "add" to 0,
    "sub" to 0,
    "pos" to 0,
    "neg" to 0,
    "bnot" to 0,
    "lnot" to 0,
  )


  fun rename(name: String): String {
    println(name)
    return if (nameToVersion.containsKey(name)) {
      val newVersion = nameToVersion[name]!! + 1
      nameToVersion[name] = newVersion
      "${name}.r${newVersion}" // it means reference
    } else {
      nameToVersion[name] = 0
      name
    }
  }
}

