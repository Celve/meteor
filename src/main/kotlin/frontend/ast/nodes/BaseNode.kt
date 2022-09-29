package frontend.ast.nodes

import frontend.ast.controller.AstVisitor
import frontend.utils.CodePos

// BaseNode is the base class for all nodes in AST
abstract class BaseNode(val pos: CodePos) {
  abstract fun accept(visitor: AstVisitor)
}