package abst.utils

import meta.ClassMeta
import meta.FuncMeta

// scopes are a tree-structure
open class Scope(var parent: Scope?) {
  open fun setVar(name: String, type: ClassMeta) {
    parent?.setVar(name, type) ?: throw Exception("invalid declaration of variable")
  }

  open fun getVar(name: String): ClassMeta? {
    return parent?.getVar(name) ?: throw Exception("invalid query of non-existing variable")
  }

  open fun testVar(name: String): Boolean {
    return false
  }

  open fun setFunc(name: String, type: FuncMeta) {
    parent?.setFunc(name, type) ?: throw Exception("invalid declaration of function")
  }

  open fun getFunc(name: String): FuncMeta? {
    return parent?.getFunc(name) ?: throw Exception("invalid query of non-existing function")
  }

  open fun setClass(name: String, type: ClassMeta) {
    parent?.setClass(name, type) ?: throw Exception("invalid declaration of class")
  }

  open fun getClass(name: String): ClassMeta? {
    return parent?.getClass(name) ?: throw Exception("invalid query of non-existing class")
  }
}

class GlobalScope(parent: Scope?) : Scope(parent) {
  private val vars: HashMap<String, ClassMeta> = HashMap()
  private val funcs: HashMap<String, FuncMeta> = HashMap()
  private val classes: HashMap<String, ClassMeta> = HashMap()

  override fun setVar(name: String, type: ClassMeta) {
    if (vars.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    vars[name] = type
  }

  override fun getVar(name: String): ClassMeta {
    return vars[name] ?: (parent?.getVar(name) ?: throw Exception("no declaration of $name"))
  }

  override fun testVar(name: String): Boolean {
    return vars.containsKey(name)
  }

  override fun setFunc(name: String, type: FuncMeta) {
    if (funcs.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    funcs[name] = type
  }

  override fun getFunc(name: String): FuncMeta {
    return funcs[name] ?: (parent?.getFunc(name) ?: throw Exception("no declaration of $name"))
  }

  override fun setClass(name: String, type: ClassMeta) {
    if (classes.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    classes[name] = type
  }

  override fun getClass(name: String): ClassMeta {
    return classes[name] ?: (parent?.getClass(name) ?: throw Exception("no declaration of $name"))
  }
}

class ClassScope(parent: Scope?) : Scope(parent) {
  private val members: HashMap<String, ClassMeta> = HashMap()
  private val methods: HashMap<String, FuncMeta> = HashMap()

  override fun setVar(name: String, type: ClassMeta) {
    if (members.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    members[name] = type
  }

  override fun getVar(name: String): ClassMeta {
    return members[name] ?: (parent?.getVar(name) ?: throw Exception("no declaration of $name"))
  }

  override fun setFunc(name: String, type: FuncMeta) {
    if (methods.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    methods[name] = type
  }

  override fun getFunc(name: String): FuncMeta {
    return methods[name] ?: (parent?.getFunc(name) ?: throw Exception("no declaration of $name"))
  }
}

class FuncScope(parent: Scope?) : Scope(parent) {
  private val vars: HashMap<String, ClassMeta> = HashMap()

  override fun setVar(name: String, type: ClassMeta) {
    if (vars.containsKey(name)) {
      throw Exception("redeclaration of $name")
    }
    vars[name] = type
  }

  override fun getVar(name: String): ClassMeta {
    return vars[name] ?: (parent?.getVar(name) ?: throw Exception("no declaration of $name"))
  }
}
