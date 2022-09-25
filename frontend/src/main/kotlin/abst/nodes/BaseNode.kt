package abst.nodes

import abst.control.Visitor
import abst.utils.CodePos

open abstract class BaseNode(val pos: CodePos) {
  abstract fun accept(visitor: Visitor)
}