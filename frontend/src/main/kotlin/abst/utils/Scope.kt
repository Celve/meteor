package abst.utils

// scopes are a tree-structure
class Scope(var parent: Scope?) {
  // mapping rules:
  // class -> class
  // class() -> (type, type): explicit/implicit
  // func() -> (type, type): type
  // variable -> var type
  // class.method() -> (type, type): type
  // class.member -> var type
  private val variables: HashMap<String, String> = HashMap()

  fun set(name: String, type: String) {
    if (variables.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    variables[name] = type
  }

  fun get(name: String): String {
    return variables[name] ?: (parent?.get(name) ?: throw Exception("no declaration of $name"))
  }
}
