package frontend.abst.nodes

import frontend.abst.control.Visitor
import frontend.abst.utils.CodePos

open abstract class BaseNode(val pos: CodePos) {
  abstract fun accept(visitor: Visitor)
}