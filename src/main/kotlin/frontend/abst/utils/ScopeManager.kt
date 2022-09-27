package frontend.abst.utils

import frontend.abst.meta.ClassMeta
import frontend.abst.meta.TypeMeta

class ScopeManager {
  private val scopes: ArrayDeque<Scope> = ArrayDeque()
  private var loopCount = 0
  private var isClass: ClassMeta? = null
  private var isFunc: TypeMeta? = null

  fun addLast(scope: Scope, cl: ClassMeta? = null, type: TypeMeta? = null) {
    scope.parent = scopes.lastOrNull()
    scopes.addLast(scope)
    if (scope is LoopScope) {
      loopCount++
    } else if (scope is ClassScope) {
      isClass = cl!!
    } else if (scope is FuncScope) {
      // TODO: whether to check that isFunc should be null here?
      isFunc = type!!
    }
  }

  fun removeLast() {
    val scope = scopes.removeLast()
    if (scope is LoopScope) {
      loopCount--
    } else if (scope is FuncScope) {
      isFunc = null
    }
  }

  fun first(): Scope {
    return scopes.first()
  }

  fun last(): Scope {
    return scopes.last()
  }

  fun isInLoop(): Boolean {
    return loopCount != 0
  }

  fun getFunc(): TypeMeta? {
    return isFunc
  }

  fun getClass(): ClassMeta? {
    return isClass
  }
}