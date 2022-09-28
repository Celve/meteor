package frontend.semantic

import exceptions.SemanticException
import frontend.ast.controller.AstVisitor
import frontend.ast.nodes.*
import frontend.meta.FuncMeta
import frontend.meta.TypeMeta
import frontend.utils.ScopeManager
import java.util.*

/**
 * SymbolCollector is used to collect classes and funcs which support forward reference
 */
class SymbolCollector : AstVisitor() {
  private val scopeManager = ScopeManager()

  override fun visit(curr: ProgNode) {
    scopeManager.addLast(curr.scope)
    curr.suite.accept(this)
    scopeManager.removeLast()

    // and the main has to be int
    curr.scope.getFunc("main") ?: throw SemanticException(curr.pos, "No main function")
  }

  override fun visit(curr: ProgSuiteNode) {
    // check for redefinition and register classes and funcs
    val globalScope = scopeManager.first()
    for (it in curr.children) {
      if (it is ClassDefNode) {
        if (globalScope.testClass(it.className)) {
          throw SemanticException(curr.pos, "Redeclare class ${it.className}")
        } else if (globalScope.testFunc(it.className)) {
          throw SemanticException(curr.pos, "Class ${it.className} has the same name with another function")
        }
        globalScope.setClass(it.className, it.classMeta)
        // use this special format to stand for implicit class creator
        globalScope.setFunc(it.className, FuncMeta(it.className, listOf(), globalScope.getFuncType("null")))
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
    scopeManager.addLast(curr.classMeta)
    curr.classSuite?.accept(this)
    scopeManager.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    // omit this duplication for the time being
    val globalScope = scopeManager.first()
    val outerScope = scopeManager.last()
    val innerScope = curr.funcMeta.funcScope
    val paramInput: Vector<TypeMeta> = Vector()

    if (curr.className != scopeManager.getRecentClass()!!.className) {
      throw SemanticException(curr.pos, "Class can't have this constructor")
    }

    val previous = globalScope.getFunc(curr.className)
    if (previous != null && previous.returnType!!.cl.className != "null") {
      throw SemanticException(curr.pos, "Redeclare class constructor ${curr.className}")
    }

    // init params and add them into local scope
    for (it in curr.params) {
      val varType = outerScope.getVarType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.addElement(varType)
      innerScope.setVar(it.second, varType)
    }
    curr.funcMeta.paramInput = paramInput.elements().toList()

    curr.funcMeta.returnType = globalScope.getFuncType("void")
    globalScope.setFunc(curr.className, curr.funcMeta)
  }

  override fun visit(curr: FuncDefNode) {
    // omit this duplication for the time being
    val outerScope = scopeManager.last()
    val innerScope = curr.funcMeta.funcScope
    val paramInput: Vector<TypeMeta> = Vector()

    // need to check here, there is no check above
    if (outerScope.testFunc(curr.funcName)) {
      throw SemanticException(curr.pos, "Redeclare ${curr.funcName}")
    }

    // init params and add them into local scope
    for (it in curr.params) {
      val varType = outerScope.getVarType(it.first) ?: throw SemanticException(curr.pos, "No type called ${it.first}")
      paramInput.addElement(varType)
      innerScope.setVar(it.second, varType)
    }
    curr.funcMeta.paramInput = paramInput.elements().toList()

    // check for its return type
    curr.funcMeta.returnType =
      outerScope.getFuncType(curr.returnType) ?: throw SemanticException(curr.pos, "${curr.returnType} is not defined")

    // for main only
    if (curr.funcName == "main") {
      if (!curr.funcMeta.returnType!!.isInt()) {
        throw SemanticException(curr.pos, "Function main has to have a int return ")
      }
      if (curr.params.size != 0) {
        throw SemanticException(curr.pos, "Function main should not have parameters")
      }
    }

    outerScope.setFunc(curr.funcName, curr.funcMeta)
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  // this node would only be included in classDef
  override fun visit(curr: VarDeclNode) {
    val globalScope = scopeManager.first()
    val classScope = scopeManager.last()
    val varType =
      globalScope.getVarType(curr.varType) ?: throw SemanticException(curr.pos, "${curr.varType} is not defined")

    for (it in curr.assigns) {
      if (classScope.getVar(it.first) != null) {
        throw Exception("Redeclare class member ${it.first}")
      }
      // TODO: can the member's type to be the class itself?
      classScope.setVar(it.first, varType)
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

  override fun visit(curr: ShortNode) {
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

  override fun visit(curr: LambdaCallNode) {
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