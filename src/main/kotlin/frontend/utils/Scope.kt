package frontend.utils

import frontend.metadata.ClassMetadata
import frontend.metadata.FuncMetadata
import frontend.metadata.TypeMetadata

// scopes are a tree-structure
open class Scope(var parent: Scope?) {
  open fun setVar(name: String, type: TypeMetadata) {
    parent?.setVar(name, type)
  }

  open fun getVar(name: String): TypeMetadata? {
    return parent?.getVar(name)
  }

  open fun testVar(name: String): Boolean {
    return parent?.getClass(name) != null
  }

  open fun setFunc(name: String, type: FuncMetadata) {
    parent?.setFunc(name, type)
  }

  open fun getFunc(name: String): FuncMetadata? {
    return parent?.getFunc(name)
  }

  open fun testFunc(name: String): Boolean {
    return parent?.getClass(name) != null
  }

  open fun setClass(name: String, type: ClassMetadata) {
    parent?.setClass(name, type)
  }

  open fun getClass(name: String): ClassMetadata? {
    return parent?.getClass(name)
  }

  open fun testClass(name: String): Boolean {
    return parent?.getClass(name) != null
  }

  open fun getVarType(name: String): TypeMetadata? {
    return parent?.getVarType(name)
  }

  open fun getFuncType(name: String): TypeMetadata? {
    return parent?.getFuncType(name)
  }
}

class GlobalScope(parent: Scope?) : Scope(parent) {
  private val vars: HashMap<String, TypeMetadata> = HashMap()
  private val funcs: HashMap<String, FuncMetadata> = HashMap()
  private val classes: HashMap<String, ClassMetadata> = HashMap()

  override fun setVar(name: String, type: TypeMetadata) {
    vars[name] = type
  }

  override fun getVar(name: String): TypeMetadata? {
    return vars[name] ?: parent?.getVar(name)
  }

  override fun testVar(name: String): Boolean {
    return vars.containsKey(name)
  }

  override fun setFunc(name: String, type: FuncMetadata) {
    funcs[name] = type
  }

  override fun getFunc(name: String): FuncMetadata? {
    return funcs[name] ?: parent?.getFunc(name)
  }

  override fun testFunc(name: String): Boolean {
    // this might be duplicate, because every class would init a ctor in global scope
    return funcs[name] != null || classes[name] != null
  }

  override fun setClass(name: String, type: ClassMetadata) {
    classes[name] = type
  }

  override fun getClass(name: String): ClassMetadata? {
    return classes[name] ?: parent?.getClass(name)
  }

  override fun testClass(name: String): Boolean {
    return classes[name] != null || funcs[name] != null
  }

  private fun resolveTypeMeta(name: String): TypeMetadata? {
    val (className, dimIndicator) = name.partition { it != '[' && it != ']' }
    val dim = dimIndicator.count { it == '[' }
    val classMeta = getClass(className) ?: return null
    return TypeMetadata(classMeta, dim)
  }

  override fun getVarType(name: String): TypeMetadata? {
    return if (name.startsWith("void")) null else resolveTypeMeta(name)
  }

  override fun getFuncType(name: String): TypeMetadata? {
    return resolveTypeMeta(name)
  }

  fun debug() {
    println("[globalScope.vars]")
    vars.forEach { print(it.key + " ") }
    println()
    println("[globalScope.classes]")
    classes.forEach { print(it.key + " ") }
    println()
    println("[globalScope.funcs]")
    funcs.forEach { println(it.key + " ") }
    for (it in funcs) {
      if (it.value.returnType == null) {
        println("${it.key}'s return type is null")
      }
    }
    println()
  }
}

class ClassScope(parent: Scope?, val className: String) : Scope(parent) {
  private val members: HashMap<String, TypeMetadata> = HashMap()
  private val methods: HashMap<String, FuncMetadata> = HashMap()

  override fun setVar(name: String, type: TypeMetadata) {
    members[name] = type
  }

  override fun getVar(name: String): TypeMetadata? {
    return members[name] ?: parent?.getVar(name)
  }

  override fun setFunc(name: String, type: FuncMetadata) {
    if (methods.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    methods[name] = type
  }

  override fun getFunc(name: String): FuncMetadata? {
    return methods[name] ?: parent?.getFunc(name)
  }
}

open class FieldScope(parent: Scope?) : Scope(parent) {
  protected val vars: HashMap<String, TypeMetadata> = HashMap()

  override fun setVar(name: String, type: TypeMetadata) {
    if (vars.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    vars[name] = type
  }

  override fun getVar(name: String): TypeMetadata? {
    return vars[name] ?: parent?.getVar(name)
  }
}

// honestly, the two are the same to func scope
// it is used to distinguish loop and func in favor of jump
class LoopScope(parent: Scope?) : FieldScope(parent)

class CondScope(parent: Scope?) : FieldScope(parent)

class FuncScope(parent: Scope?, private val ableOut: Boolean) : FieldScope(parent) {
  override fun getVar(name: String): TypeMetadata? {
    return vars[name] ?: if (ableOut) parent?.getVar(name) else null
  }
}
