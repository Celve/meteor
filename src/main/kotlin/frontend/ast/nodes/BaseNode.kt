package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos

abstract class BaseNode(val pos: CodePos) {
  abstract fun accept(visitor: AstVisitor)
}