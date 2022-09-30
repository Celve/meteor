package frontend.utils

import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import java.util.*

// it forms a tree structure for scopes
// when a var or something else is not discovered in the current scope, it would look upward to search
class ScopeManager {
  private val scopes: Vector<Scope> = Vector()
  private var classMd: ClassMd? = null
  private var funcMd: Vector<FuncMd> = Vector()

  fun addLast(scope: Scope) {
    scope.parent = scopes.lastOrNull()
    scopes.addElement(scope)
  }

  fun addLast(input: ClassMd) {
    addLast(input.classScope)
    classMd = input
  }

  fun addLast(input: FuncMd) {
    addLast(input.funcScope)
    funcMd.addElement(input)
  }

  fun removeLast() {
    val scope = scopes.removeLast()
    if (scope is FuncScope) {
      funcMd.removeLast()
    } else if (scope is ClassScope) {
      classMd = null
    }
  }

  fun first(): Scope {
    return scopes.first()
  }

  fun last(): Scope {
    return scopes.last()
  }

  fun isInLoop(): Boolean {
    for (i in scopes.size - 1 downTo 0) {
      if (scopes[i] is LoopScope) {
        return true
      } else if (scopes[i] is FuncScope) {
        return false
      }
    }
    return false
  }

  fun getRecentFunc(): FuncMd? {
    return funcMd.lastOrNull()
  }

  fun getRecentClass(): ClassMd? {
    return classMd
  }
}