package frontend.abst.nodes

import frontend.abst.controller.AbstVisitor
import frontend.utils.CodePos

abstract class BaseNode(val pos: CodePos) {
  abstract fun accept(visitor: AbstVisitor)
}