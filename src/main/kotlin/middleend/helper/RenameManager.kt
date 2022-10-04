package middleend.helper

object RenameManager {
  val nameToVersion: HashMap<String, Int> = hashMapOf()

  fun rename(name: String): String {
    println(name)
    return if (nameToVersion.containsKey(name)) {
      val newVersion = nameToVersion[name]!! + 1
      nameToVersion[name] = newVersion
      "${name}.${newVersion}"
    } else {
      nameToVersion[name] = 0
      name
    }
  }
}