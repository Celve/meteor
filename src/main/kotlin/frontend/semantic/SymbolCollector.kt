package frontend.semantic

import exceptions.SemanticException
import frontend.abst.control.Visitor
import frontend.abst.meta.FuncMeta
import frontend.abst.meta.TypeMeta
import frontend.abst.nodes.*
import frontend.abst.utils.ScopeManager
import java.util.*

/**
 * SymbolCollector is used to collect classes and funcs which support forward reference
 */
class SymbolCollector : Visitor() {
  private val scopeManager = ScopeManager()
  private val currClassName = String()

  override fun visit(curr: ProgNode) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
    scopeManager.removeLast()

    curr.scope.getFunc("main") ?: throw SemanticException(curr.pos, "No main function")
  }

  override fun visit(curr: SuiteNode) {
    // check for redefinition and register classes and funcs
    val globalScope = scopeManager.first()
    for (it in curr.children) {
      if (it is ClassDefNode) {
        if (globalScope.getClass(it.className) != null) {
          throw SemanticException(curr.pos, "Redeclare class ${it.className}")
        } else if (globalScope.getFunc(it.className) != null) {
          throw SemanticException(curr.pos, "Redeclare func ${it.className}")
        }
        globalScope.setClass(it.className, it.classMeta)
        // use this special format to stand for implicit class creator
        globalScope.setFunc(it.className, FuncMeta(it.className, listOf(), globalScope.getType("null")))
      }
    }

    // register classes' members and methods
    // separate from above to support forward reference
    for (it in curr.children) {
      if (it is ClassDefNode) {
        it.accept(this)
      }
    }
    for (it in curr.children) {
      if (it is FuncDefNode) {
        it.accept(this)
      }
    }
  }

  override fun visit(curr: FuncSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ClassSuiteNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: SimpleSuiteNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ClassDefNode) {
    scopeManager.addLast(curr.classMeta.classScope)
    curr.classSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    // omit this duplication for the time being
    val globalScope = scopeManager.first()
    val outerScope = scopeManager.last()
    val innerScope = curr.funcMeta.funcScope
    val paramInput: Vector<TypeMeta> = Vector()

    if (curr.className != currClassName) {
      throw SemanticException(curr.pos, "Class can't have this constructor")
    }

    val previous = globalScope.getFunc(curr.className)
    if (previous != null && previous.returnType!!.cl.className != "null") {
      throw SemanticException(curr.pos, "Redeclare class constructor ${curr.className}")
    }

    // init params and add them into local scope
    for (it in curr.params) {
      val type = outerScope.getType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.addElement(type)
      innerScope.setVar(it.second, type)
    }
    curr.funcMeta.paramInput = paramInput.elements().toList()

    curr.funcMeta.returnType = globalScope.getType("void")
    globalScope.setFunc(curr.className, curr.funcMeta)
  }

  override fun visit(curr: FuncDefNode) {
    // omit this duplication for the time being
    val outerScope = scopeManager.last()
    val innerScope = curr.funcMeta.funcScope
    val paramInput: Vector<TypeMeta> = Vector()

    // do not need to check here, which is checked above
//    if (outerScope.getFunc(curr.funcName) != null) {
//      throw SemanticException(curr.pos, "Redeclare ${curr.funcName}")
//    }

    // init params and add them into local scope
    for (it in curr.params) {
      val type = outerScope.getType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.addElement(type)
      innerScope.setVar(it.second, type)
    }
    curr.funcMeta.paramInput = paramInput.elements().toList()

    // check for its return type
    curr.funcMeta.returnType =
      outerScope.getType(curr.returnType) ?: throw SemanticException(curr.pos, "${curr.returnType} is not defined")
    outerScope.setFunc(curr.funcName, curr.funcMeta)
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  // this node would only be included in classDef
  override fun visit(curr: VarDeclNode) {
    val globalScope = scopeManager.first()
    val classScope = scopeManager.last()
    val type =
      globalScope.getType(curr.type) ?: throw SemanticException(curr.pos, "${curr.type} is not defined")

    for (it in curr.assigns) {
      if (classScope.getVar(it.first) != null) {
        throw Exception("Redeclare class member ${it.first}")
      }
      // TODO: can the member's type to be the class itself?
      classScope.setVar(it.first, type)
    }
  }

  override fun visit(curr: ForNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: WhileNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: CondNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FieldNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: JumpNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: PriorExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: AtomNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: InitExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncCallNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: MethodAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: MemberAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: ArrayAccessNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: SuffixExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: PrefixExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: BinaryExprNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: AssignExprNode) {
    TODO("Not yet implemented")
  }
}