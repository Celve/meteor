package frontend.abst.utils

import frontend.abst.meta.ClassMeta
import frontend.abst.meta.FuncMeta
import java.util.*

class ScopeManager {
  private val scopes: Vector<Scope> = Vector()
  private var classMeta: ClassMeta? = null
  private var funcMetas: Vector<FuncMeta> = Vector()

  fun addLast(scope: Scope) {
    scope.parent = scopes.lastOrNull()
    scopes.addElement(scope)
  }

  fun addLast(input: ClassMeta) {
    addLast(input.classScope)
    classMeta = input
  }

  fun addLast(input: FuncMeta) {
    addLast(input.funcScope)
    funcMetas.addElement(input)
  }

  fun removeLast() {
    val scope = scopes.removeLast()
    if (scope is FuncScope) {
      funcMetas.removeLast()
    } else if (scope is ClassScope) {
      classMeta = null
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

  fun getRecentFunc(): FuncMeta? {
    return funcMetas.lastOrNull()
  }

  fun getRecentClass(): ClassMeta? {
    return classMeta
  }
}