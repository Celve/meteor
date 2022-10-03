package frontend.utils

import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import frontend.metadata.TypeMd
import middleend.helper.RenameManager

// it would never be included in scopeManager
// however, the first scope's pointer is always pointing to it
object BuiltinScope : Scope(null) {
  val funcs: HashMap<String, FuncMd> = HashMap()
  val classes: HashMap<String, ClassMd> = HashMap()

  init {
    classes["void"] = ClassMd("void")
    classes["int"] = ClassMd("int")
    classes["bool"] = ClassMd("bool")
    classes["null"] = ClassMd("null")
    classes["string"] = ClassMd("string")

    val voidType = getFuncType("void")!!
    val intType = getVarType("int")!!
    val stringType = getVarType("string")!!

    funcs["print"] = FuncMd("print", listOf(Pair("", stringType)), voidType)
    funcs["println"] = FuncMd("println", listOf(Pair("", stringType)), voidType)
    funcs["printInt"] = FuncMd("printInt", listOf(Pair("", intType)), voidType)
    funcs["printlnInt"] = FuncMd("printlnInt", listOf(Pair("", intType)), voidType)
    funcs["getString"] = FuncMd("getString", listOf(), stringType)
    funcs["getInt"] = FuncMd("getInt", listOf(), intType)
    funcs["toString"] = FuncMd("toString", listOf(Pair("", intType)), stringType)

    val classMeta = getClass("string")!!
    classMeta.classScope.setFunc("length", FuncMd("length", listOf(), intType))
    classMeta.classScope.setFunc(
      "substring",
      FuncMd("substring", listOf(Pair("", intType), Pair("", intType)), stringType)
    )
    classMeta.classScope.setFunc("parseInt", FuncMd("parseInt", listOf(), intType))
    classMeta.classScope.setFunc("ord", FuncMd("ord", listOf(Pair("", intType)), intType))
  }

  override fun setFunc(name: String, type: FuncMd) {
    funcs[name] = type
    uniqueNames[name] = RenameManager.rename(name)
  }

  override fun getFunc(name: String): FuncMd? {
    return funcs[name] ?: parent?.getFunc(name)
  }

  override fun testFunc(name: String): Boolean {
    // this might be duplicate, because every class would init a ctor in global scope
    return funcs[name] != null || classes[name] != null
  }

  override fun setClass(name: String, type: ClassMd) {
    classes[name] = type
    uniqueNames[name] = RenameManager.rename(name)
  }

  override fun getClass(name: String): ClassMd? {
    return classes[name] ?: parent?.getClass(name)
  }

  override fun testClass(name: String): Boolean {
    return classes[name] != null || funcs[name] != null
  }

  private fun resolveTypeMeta(name: String): TypeMd? {
    val (className, dimIndicator) = name.partition { it != '[' && it != ']' }
    val dim = dimIndicator.count { it == '[' }
    val classMeta = getClass(className) ?: return null
    return TypeMd(classMeta, dim)
  }

  override fun getVarType(name: String): TypeMd? {
    return if (name.startsWith("void")) null else resolveTypeMeta(name)
  }

  override fun getFuncType(name: String): TypeMd? {
    return resolveTypeMeta(name)
  }
}