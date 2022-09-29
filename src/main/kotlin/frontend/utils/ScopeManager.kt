package frontend.utils

import frontend.metadata.ClassMetadata
import frontend.metadata.FuncMetadata
import java.util.*

class ScopeManager {
  private val scopes: Vector<Scope> = Vector()
  private var classMetadata: ClassMetadata? = null
  private var funcMetadata: Vector<FuncMetadata> = Vector()

  fun addLast(scope: Scope) {
    scope.parent = scopes.lastOrNull()
    scopes.addElement(scope)
  }

  fun addLast(input: ClassMetadata) {
    addLast(input.classScope)
    classMetadata = input
  }

  fun addLast(input: FuncMetadata) {
    addLast(input.funcScope)
    funcMetadata.addElement(input)
  }

  fun removeLast() {
    val scope = scopes.removeLast()
    if (scope is FuncScope) {
      funcMetadata.removeLast()
    } else if (scope is ClassScope) {
      classMetadata = null
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

  fun getRecentFunc(): FuncMetadata? {
    return funcMetadata.lastOrNull()
  }

  fun getRecentClass(): ClassMetadata? {
    return classMetadata
  }
}