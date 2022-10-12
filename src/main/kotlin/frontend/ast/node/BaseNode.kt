package frontend.ast.node

import frontend.ast.controller.ASTVisitor
import frontend.utils.SrcPos

// BaseNode is the base class for all nodes in AST
abstract class BaseNode(val pos: SrcPos) {
  abstract fun accept(visitor: ASTVisitor)
}