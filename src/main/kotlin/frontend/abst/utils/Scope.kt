package frontend.abst.utils

import frontend.abst.meta.ClassMeta
import frontend.abst.meta.FuncMeta
import frontend.abst.meta.TypeMeta

// scopes are a tree-structure
open class Scope(var parent: Scope?) {
  open fun setVar(name: String, type: TypeMeta) {
    parent?.setVar(name, type)
  }

  open fun getVar(name: String): TypeMeta? {
    return parent?.getVar(name)
  }

  open fun testVar(name: String): Boolean {
    return false
  }

  open fun setFunc(name: String, type: FuncMeta) {
    parent?.setFunc(name, type)
  }

  open fun getFunc(name: String): FuncMeta? {
    return parent?.getFunc(name)
  }

  open fun setClass(name: String, type: ClassMeta) {
    parent?.setClass(name, type)
  }

  open fun getClass(name: String): ClassMeta? {
    return parent?.getClass(name)
  }

  open fun getType(name: String): TypeMeta? {
    return parent?.getType(name)
  }
}

class GlobalScope(parent: Scope?) : Scope(parent) {
  private val vars: HashMap<String, TypeMeta> = HashMap()
  private val funcs: HashMap<String, FuncMeta> = HashMap()
  private val classes: HashMap<String, ClassMeta> = HashMap()

  override fun setVar(name: String, type: TypeMeta) {
    if (vars.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    vars[name] = type
  }

  override fun getVar(name: String): TypeMeta? {
    return vars[name] ?: parent?.getVar(name)
  }

  override fun testVar(name: String): Boolean {
    return vars.containsKey(name)
  }

  override fun setFunc(name: String, type: FuncMeta) {
//    if (funcs.containsKey(name)) {
//      throw Exception("redeclaration of $name")
//    }
    funcs[name] = type
  }

  override fun getFunc(name: String): FuncMeta? {
    return funcs[name] ?: parent?.getFunc(name)
  }

  override fun setClass(name: String, type: ClassMeta) {
//    if (classes.containsKey(name)) {
//      throw Exception("redeclaration of $name")
//    }
    classes[name] = type
  }

  override fun getClass(name: String): ClassMeta? {
    return classes[name] ?: parent?.getClass(name)
  }

  override fun getType(name: String): TypeMeta? {
    val (className, dimIndicator) = name.partition { it != '[' && it != ']' }
    val dim = dimIndicator.count { it == '[' }
    val classMeta = getClass(className) ?: return null
    return TypeMeta(classMeta, dim)
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

class ClassScope(parent: Scope?) : Scope(parent) {
  private val members: HashMap<String, TypeMeta> = HashMap()
  private val methods: HashMap<String, FuncMeta> = HashMap()

  override fun setVar(name: String, type: TypeMeta) {
    if (members.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    members[name] = type
  }

  override fun getVar(name: String): TypeMeta? {
    return members[name] ?: parent?.getVar(name)
  }

  override fun setFunc(name: String, type: FuncMeta) {
    if (methods.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    methods[name] = type
  }

  override fun getFunc(name: String): FuncMeta? {
    return methods[name] ?: parent?.getFunc(name)
  }
}

open class FieldScope(parent: Scope?) : Scope(parent) {
  private val vars: HashMap<String, TypeMeta> = HashMap()

  override fun setVar(name: String, type: TypeMeta) {
    if (vars.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    vars[name] = type
  }

  override fun getVar(name: String): TypeMeta? {
    return vars[name] ?: parent?.getVar(name)
  }
}

// honestly, the two are the same to func scope
// it is used to distinguish loop and func in favor of jump
class LoopScope(parent: Scope?) : FieldScope(parent)

class CondScope(parent: Scope?) : FieldScope(parent)

class FuncScope(parent: Scope?) : FieldScope(parent)
