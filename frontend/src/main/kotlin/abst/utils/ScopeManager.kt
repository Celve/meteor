package abst.utils

class ScopeManager {
  val scopes: ArrayDeque<Scope> = ArrayDeque()

  fun addLast(scope: Scope) {
    scope.parent = scopes.lastOrNull()
    scopes.addLast(scope)
  }

  fun removeLast() {
    scopes.removeLast()
  }

  fun first(): Scope {
    return scopes.first()
  }
}