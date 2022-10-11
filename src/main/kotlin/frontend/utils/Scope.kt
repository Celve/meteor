package frontend.utils

import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import frontend.metadata.TypeMd
import middleend.basic.Value

// scope represents a namespace for any kind of block
// it could register var, class, or func, determined by the block's property
// scopes form a tree in scope manager
open class Scope(var parent: Scope?) {
  val namedValues: HashMap<String, Value> = hashMapOf()

  // to get every variable a unique name
  fun getValue(name: String): Value? {
    return namedValues[name] ?: parent?.getValue(name)
  }

  fun setValue(name: String, value: Value) {
    namedValues[name] = value
  }

  open fun setVar(name: String, type: TypeMd) {
    parent?.setVar(name, type)
  }

  open fun getVar(name: String): TypeMd? {
    return parent?.getVar(name)
  }

  open fun testVar(name: String): Boolean {
    return false
  }

  open fun setFunc(name: String, type: FuncMd) {
    parent?.setFunc(name, type)
  }

  open fun getFunc(name: String): FuncMd? {
    return parent?.getFunc(name)
  }

  open fun testFunc(name: String): Boolean {
    return false
  }

  open fun setClass(name: String, type: ClassMd) {
    parent?.setClass(name, type)
  }

  open fun getClass(name: String): ClassMd? {
    return parent?.getClass(name)
  }

  open fun testClass(name: String): Boolean {
    return false
  }

  open fun getVarType(name: String): TypeMd? {
    return parent?.getVarType(name)
  }

  open fun getFuncType(name: String): TypeMd? {
    return parent?.getFuncType(name)
  }
}

// the largest scope
// be able to register var, func, and class
// generally, it must be the root in the scope tree
class GlobalScope(parent: Scope?) : Scope(parent) {
  val vars: HashMap<String, TypeMd> = HashMap()
  val funcs: HashMap<String, FuncMd> = HashMap()
  val classes: HashMap<String, ClassMd> = HashMap()

  override fun setVar(name: String, type: TypeMd) {
    vars[name] = type
  }

  override fun getVar(name: String): TypeMd? {
    return vars[name] ?: parent?.getVar(name)
  }

  override fun testVar(name: String): Boolean {
    return vars.containsKey(name)
  }

  override fun setFunc(name: String, type: FuncMd) {
    funcs[name] = type
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

// the scope owned by class
// be able to register var and func, namely member and method
class ClassScope(parent: Scope?, val className: String) : Scope(parent) {
  val members: HashMap<String, TypeMd> = HashMap()
  val methods: HashMap<String, FuncMd> = HashMap()

  override fun setVar(name: String, type: TypeMd) {
    members[name] = type
  }

  override fun getVar(name: String): TypeMd? {
    return members[name] ?: parent?.getVar(name)
  }

  override fun testVar(name: String): Boolean {
    return members.containsKey(name)
  }

  override fun setFunc(name: String, type: FuncMd) {
    methods[name] = type
  }

  override fun getFunc(name: String): FuncMd? {
    return methods[name] ?: parent?.getFunc(name)
  }

  override fun testFunc(name: String): Boolean {
    return methods.containsKey(name)
  }
}

// a particular kind of block which could only register var
// it's the scope type of loop, func, and mere {}
open class FieldScope(parent: Scope?) : Scope(parent) {
  val vars: HashMap<String, TypeMd> = HashMap()

  override fun setVar(name: String, type: TypeMd) {
    vars[name] = type
  }

  override fun getVar(name: String): TypeMd? {
    return vars[name] ?: parent?.getVar(name)
  }

  override fun testVar(name: String): Boolean {
    return vars.containsKey(name)
  }
}

// it is used to distinguish loop and func in favor of jump
class InitScope(parent: Scope?) : FieldScope(parent)

class LoopScope(parent: Scope?, val name: String) : FieldScope(parent)

class CondScope(parent: Scope?) : FieldScope(parent)

class FuncScope(parent: Scope?, val funcName: String, private val ableOut: Boolean) : FieldScope(parent) {
  override fun getVar(name: String): TypeMd? {
    return vars[name] ?: if (ableOut) parent?.getVar(name) else null
  }
}

