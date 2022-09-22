package abst.nodes

import abst.control.Visitor

open abstract class Base {
  abstract var pos: Int

  abstract fun accept(visitor: Visitor);
}