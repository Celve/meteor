package middleend.controller

import frontend.ast.controller.ASTVisitor
import frontend.ast.node.*
import frontend.utils.BuiltinScope
import frontend.utils.FuncScope
import frontend.utils.GlobalScope
import frontend.utils.ScopeManager
import middleend.basic.*
import middleend.helper.LoopManager
import middleend.helper.Utils
import middleend.helper.ValueTable
import kotlin.math.pow

class IRGenerator : ASTVisitor() {
  private val loopWeight = 10.0

  private val scopeManger = ScopeManager()
  private val loopManager = LoopManager()
  val topModule = TopModule()
  private var initFunc: Func? = null
  private var localSymbolTable = ValueTable()
  private var globalSymbolTable = ValueTable()
  private var str2Const = hashMapOf<String, ConstantStr>()

  private fun renameGlobal(name: String): String {
    return globalSymbolTable.rename(name)
  }

  private fun renameLocal(name: String): String {
    return localSymbolTable.rename(name)
  }

  override fun visit(curr: ProgNode) {
    /**
     * insert class definitions, method declarations, and function declarations first to support forward reference
     * class member isn't needed to be inserted
     * member declarations are omitted, because there are simply retrieve by index
     */
    scopeManger.addLast(curr.scope)
    val globalScope = curr.scope

    // iterate class definition
    for ((className, classMd) in globalScope.classes) {
      val localScope = classMd.classScope
      val structType = TypeFactory.getStructType(classMd)

      // create the concrete struct directly, instead of reference, which is done in elsewhere
      topModule.setStruct("class.$className", structType)

      for ((funcName, funcMd) in localScope.methods) {
        /**
         * arg's name should be reinserted into value symbol table in the following process, because it wasn't renamed
         * @see visit
         */
        val args = funcMd.argList.map { Value(TypeFactory.getAnyType(it.second), it.first) }.toMutableList()
        args.add(0, Value(TypeFactory.getPtrType(classMd), "this"))

        topModule.setFunc(
          "$className.$funcName",
          Func("$className.$funcName", TypeFactory.getFuncType(funcMd, classMd), args)
        )
      }

      // create implicit constructor
      if (!classMd.hasCustomCtor) {
        val ctor = topModule.getFunc("${className}.new")!!
        localSymbolTable = ctor.mulTable
        val entryBlock = BasicBlock(renameLocal("entry"), 1)
//        val returnBlock = BasicBlock(renameLocal("return"), 1)
        IRBuilder.setCurrentFunc(ctor)
        IRBuilder.setInsertBlock(entryBlock)
//        IRBuilder.createBr(returnBlock)
//        IRBuilder.setInsertBlock(returnBlock)
        IRBuilder.createRetVoid()
      }
    }

    // function definitions
    for ((funcName, funcMd) in globalScope.funcs) {
      val funcType = TypeFactory.getFuncType(funcMd)
      topModule.setFunc(
        funcName,
        Func(funcName, funcType, funcMd.argList.map { Value(TypeFactory.getAnyType(it.second), it.first) })
      )
    }

    // builtin function declarations
    for ((funcName, funcMd) in BuiltinScope.funcs) {
      val funcType = TypeFactory.getFuncType(funcMd)
      topModule.setBuiltinFunc(
        funcName,
        Func(funcName, funcType, funcMd.argList.map { Value(TypeFactory.getAnyType(it.second)) })
      )
    }

    /**
     * set up init func
     * all global initiation should be done in init func
     * because all variables are suffixed with ".addr", there is no need to distinguish them with function or class
     * @see visit
     * this naming custom might be changed in the future
     */
    val initFuncName = "_global_variable_init"
    initFunc = Func(initFuncName, FuncType(initFuncName, listOf(), TypeFactory.getVoidType()), listOf())
    localSymbolTable = initFunc!!.mulTable

    val entryBlock = BasicBlock(renameLocal("entry"), 1)
    entryBlock.insertAtTheTailOf(initFunc!!)

    curr.block.accept(this)

    // to check if there is a need to init global variable in main function
    if (entryBlock.instList.size > 0) {
      val returnBlock = BasicBlock(renameLocal("return"), 1)

      IRBuilder.resetInsertBlock(initFunc!!.blockList.last())
      IRBuilder.createBr(returnBlock)

      IRBuilder.setInsertBlock(returnBlock)
      IRBuilder.createRetVoid()

      topModule.setFunc(initFuncName, initFunc!!)
      val mainFunc = topModule.getFunc("main")!!
      val mainEntryBlock = mainFunc.blockList.first()
      IRBuilder.setInsertPointBefore(mainEntryBlock.instList.first())
      IRBuilder.createCall(null, initFunc!!, listOf())
    }

    scopeManger.removeLast()
  }

  override fun visit(curr: ProgBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: FuncBlockNode) {
    curr.children.forEach { it.accept(this) }
  }

  override fun visit(curr: ClassBlockNode) {
    curr.children.forEach { if (it !is VarDeclNode) it.accept(this) }
  }

  override fun visit(curr: SimpleBlockNode) {
    curr.child.accept(this)
  }

  override fun visit(curr: ClassDefNode) {
    scopeManger.addLast(curr.classMd)
    curr.classBlock?.accept(this)
    scopeManger.removeLast()
  }

  override fun visit(curr: ClassCtorNode) {
    scopeManger.addLast(curr.funcMd)
    val recentClass = scopeManger.getRecentClass()!!
    val funcName = "${recentClass.className}.new"
    val func = topModule.getFunc(funcName)!!
    val innerScope = scopeManger.last()
    IRBuilder.setCurrentFunc(func)
    localSymbolTable = func.mulTable

    val entryBlock = BasicBlock(renameLocal("entry"), 1)
    IRBuilder.setInsertBlock(entryBlock)

    /** it must contain only one arg, which should be the "this" pointer */
    assert(func.argList.size == 1 && func.argList.first().name == "this")
    val arg = func.argList.first()
    innerScope.setAddr("this", arg)

    curr.funcBlock?.accept(this)

    if (!IRBuilder.getInsertBlock().hasTerminator()) {
      val returnBlock = BasicBlock(renameLocal("return"), 1)
      IRBuilder.createBr(returnBlock)
      IRBuilder.setInsertBlock(returnBlock)
      IRBuilder.createRetVoid()
    }

    scopeManger.removeLast()
  }

  override fun visit(curr: FuncDefNode) {
    scopeManger.addLast(curr.funcMd)
    val innerScope = scopeManger.last() as FuncScope
    val recentClass = scopeManger.getRecentClass()

    val funcName = if (recentClass != null) "${recentClass.className}.${innerScope.funcName}" else innerScope.funcName
    val func = topModule.getFunc(funcName)!!
    IRBuilder.setCurrentFunc(func)
    localSymbolTable = func.mulTable

    /** avoid conflicts with global value */
    localSymbolTable.counter.putAll(topModule.globalVarMap.map { it.key to 1 })

    val funcType = func.type as FuncType

    val entryBlock = BasicBlock(renameLocal("entry"), 1)
    IRBuilder.setInsertBlock(entryBlock)

    /** setup for parameters */
    if (func.argList.isNotEmpty()) {
      for (arg in func.argList) {
        /**
         * This pointer should be inserted into method's value symbol table and scope, in order to retrieve it.
         * And there is no need to alloca and load it like other args.
         */
        val originalName = arg.name!!
        arg.name = renameLocal(arg.name!!)
        if (originalName == "this") {
          innerScope.setAddr("this", arg)
          continue
        }
        val allocaInst = IRBuilder.createAlloca("${arg.name!!}.addr", arg.type)
        innerScope.setAddr(originalName, allocaInst)
        IRBuilder.createStore(arg, allocaInst)
      }
    }

    /** setup for the return value */
    if (funcType.resultType != TypeFactory.getVoidType()) {
      val allocaInst = IRBuilder.createAlloca(".ret.addr", funcType.resultType)
      innerScope.setAddr(".ret", allocaInst)
      renameLocal(".ret") // just insert an entry
    }

    val returnBlock = BasicBlock(renameLocal("return"), 1)
    IRBuilder.setReturnBlock(returnBlock)


    IRBuilder.resetInsertBlock(entryBlock)
    curr.funcBlock?.accept(this)

    if (!IRBuilder.getInsertBlock().hasTerminator()) {
      IRBuilder.createBr(returnBlock)
    }

    // don't forget to push the return block into block list
    IRBuilder.setInsertBlock(returnBlock)
    if (funcType.resultType != TypeFactory.getVoidType()) {
      val loadInst = IRBuilder.createLoad(".ret", innerScope.getAddr(".ret")!!)
      IRBuilder.createRet(loadInst)
    } else {
      IRBuilder.createRetVoid()
    }

    scopeManger.removeLast()
  }

  override fun visit(curr: LambdaDefNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: VarDeclNode) {
    val innerScope = scopeManger.last()
    val type = TypeFactory.getAnyType(curr.varTypeMd!!)

    if (innerScope !is GlobalScope) {
      /** if it is a local variable */
      for (assign in curr.assigns) {
        val ptr = IRBuilder.createAlloca("${renameLocal(assign.first)}.addr", type)
        innerScope.setAddr(assign.first, ptr)
        if (assign.second != null) {
          assign.second!!.accept(this)
          IRBuilder.createStore(assign.second!!.value!!, ptr)
        } else if (type is PointerType) {
          // temporarily disable it
          IRBuilder.createStore(ConstantNull(), ptr)
        }
      }
    } else {
      /** is a global variable */
      IRBuilder.resetInsertBlock(initFunc!!.blockList.last())
      for (assign in curr.assigns) {
        val gloVarPtr = GlobalVariable("${assign.first}.addr", TypeFactory.getPtrType(type)) // no conflict might occur
        topModule.setGlobalAddr(assign.first, gloVarPtr)
        if (assign.second != null) {
          assign.second!!.accept(this)
          IRBuilder.createStore(assign.second!!.value!!, gloVarPtr)
        }
      }
    }
  }

  override fun visit(curr: ForSuiteNode) {
    scopeManger.addLast(curr.initScope)

    val nestedLoopsNum = loopManager.getNestedLoopsNum() + 1
    val execFreq = loopWeight.pow(nestedLoopsNum).toInt()

    if (false) { // normal construction
      val condBlock = BasicBlock(renameLocal("for.cond"), execFreq)
      val incBlock = BasicBlock(renameLocal("for.inc"), execFreq)
      val bodyBlock = BasicBlock(renameLocal("for.body"), execFreq)
      val endBlock = BasicBlock(renameLocal("for.end"), execFreq)
      loopManager.addLast(incBlock, endBlock)

      curr.init?.accept(this)
      IRBuilder.createBr(condBlock)

      IRBuilder.setInsertBlock(condBlock)
      if (curr.cond == null) {
        IRBuilder.createBr(bodyBlock)
      } else {
        curr.cond.accept(this)
        IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)
      }

      IRBuilder.setInsertBlock(bodyBlock)
      scopeManger.addLast(curr.bodyScope)
      curr.body.accept(this)
      scopeManger.removeLast()
      IRBuilder.createBr(incBlock)

      IRBuilder.setInsertBlock(incBlock)
      curr.inc?.accept(this)
      IRBuilder.createBr(condBlock)

      IRBuilder.setInsertBlock(endBlock)

      loopManager.removeLast()
    } else { // use do-while construction
      val testBlock = BasicBlock(renameLocal("for.test"), execFreq / loopWeight.toInt())
      val bodyBlock = BasicBlock(renameLocal("for.body"), execFreq)
      val incBlock = BasicBlock(renameLocal("for.inc"), execFreq)
      val condBlock = BasicBlock(renameLocal("for.cond"), execFreq)
      val endBlock = BasicBlock(renameLocal("for.end"), execFreq)
      loopManager.addLast(incBlock, endBlock)

      IRBuilder.createBr(testBlock)
      IRBuilder.setInsertBlock(testBlock)
      curr.init?.accept(this)
      if (curr.cond == null) {
        IRBuilder.createBr(bodyBlock)
      } else {
        curr.cond.accept(this)
        IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)
      }
      IRBuilder.setInsertBlock(bodyBlock)
      scopeManger.addLast(curr.bodyScope)
      curr.body.accept(this)
      scopeManger.removeLast()
      IRBuilder.createBr(incBlock)

      IRBuilder.setInsertBlock(incBlock)
      curr.inc?.accept(this)
      IRBuilder.createBr(condBlock)

      IRBuilder.setInsertBlock(condBlock)
      if (curr.cond == null) {
        IRBuilder.createBr(bodyBlock)
      } else {
        curr.cond.accept(this)
        IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)
      }

      IRBuilder.setInsertBlock(endBlock)

      loopManager.removeLast()
    }

    scopeManger.removeLast()
  }

  override fun visit(curr: WhileSuiteNode) {
    scopeManger.addLast(curr.scope)

    val nestedLoopsNum = loopManager.getNestedLoopsNum() + 1
    val execFreq = loopWeight.pow(nestedLoopsNum).toInt()

    if (false) { // normal construction
      val condBlock = BasicBlock(renameLocal("while.cond"), execFreq)
      val bodyBlock = BasicBlock(renameLocal("while.body"), execFreq)
      val endBlock = BasicBlock(renameLocal("while.end"), execFreq)

      loopManager.addLast(condBlock, endBlock)
      IRBuilder.createBr(condBlock)

      IRBuilder.setInsertBlock(condBlock)
      curr.cond.accept(this)
      IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)

      IRBuilder.setInsertBlock(bodyBlock)
      curr.body.accept(this)
      IRBuilder.createBr(condBlock)

      IRBuilder.setInsertBlock(endBlock)
      loopManager.removeLast()
    } else { // do-while construction
      val testBlock = BasicBlock(renameLocal("while.test"), execFreq / loopWeight.toInt())
      val bodyBlock = BasicBlock(renameLocal("while.body"), execFreq)
      val condBlock = BasicBlock(renameLocal("while.cond"), execFreq)
      val endBlock = BasicBlock(renameLocal("while.end"), execFreq)
      loopManager.addLast(condBlock, endBlock)

      IRBuilder.createBr(testBlock)
      IRBuilder.setInsertBlock(testBlock)
      curr.cond.accept(this)
      IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)

      IRBuilder.setInsertBlock(bodyBlock)
      curr.body.accept(this)
      IRBuilder.createBr(condBlock)

      IRBuilder.setInsertBlock(condBlock)
      curr.cond.accept(this)
      IRBuilder.createBr(curr.cond.value!!, bodyBlock, endBlock)

      IRBuilder.setInsertBlock(endBlock)
      loopManager.removeLast()
    }

    scopeManger.removeLast()
  }

  override fun visit(curr: CondSuiteNode) {
    curr.cond.accept(this)

    val nestedLoopsNum = loopManager.getNestedLoopsNum()
    val execFreq =
      loopWeight.pow(nestedLoopsNum).toInt() // according to EAC, it should be divided by the number of branches
    val endBlock = BasicBlock(renameLocal("if.end"), execFreq)
    val thenBlock = BasicBlock(renameLocal("if.then"), execFreq)
    val elseBlock: BasicBlock? = if (curr.elseDo != null) BasicBlock(renameLocal("if.else"), execFreq) else null
    if (curr.elseDo != null) {
      IRBuilder.createBr(curr.cond.value!!, thenBlock, elseBlock!!)
    } else {
      IRBuilder.createBr(curr.cond.value!!, thenBlock, endBlock)
    }

    scopeManger.addLast(curr.thenScope)
    IRBuilder.setInsertBlock(thenBlock)
    curr.thenDo.accept(this)
    IRBuilder.createBr(endBlock)
    scopeManger.removeLast()

    if (curr.elseDo != null) {
      scopeManger.addLast(curr.elseScope)
      IRBuilder.setInsertBlock(elseBlock!!)
      curr.elseDo.accept(this)
      IRBuilder.createBr(endBlock)
      scopeManger.removeLast()
    }

    IRBuilder.setInsertBlock(endBlock)
  }

  override fun visit(curr: FieldSuiteNode) {
    curr.block.accept(this)
  }

  override fun visit(curr: JumpNode) {
    when (curr.type) {
      "return" -> {
        if (curr.expr != null) {
          curr.expr.accept(this)
          IRBuilder.createStore(curr.expr.value!!, scopeManger.last().getAddr(".ret")!!)
        }
        IRBuilder.createBr(IRBuilder.getReturnBlock()!!)
      }

      "break" -> {
        IRBuilder.createBr(loopManager.getLastBreakBlock()!!)
      }

      "continue" -> {
        IRBuilder.createBr(loopManager.getLastContinueBlock()!!)
      }
    }
  }

  override fun visit(curr: ShortNode) {
    curr.expr?.accept(this)
  }

  override fun visit(curr: PriorExprNode) {
    curr.expr.accept(this)
    curr.value = curr.expr.value
  }

  override fun visit(curr: AtomNode) {
    val innerScope = scopeManger.last()
    curr.value = when (curr.id) {
      /** const int */
      0 -> ConstantInt(32, curr.literal.toInt())

      /** const str */
      1 -> {
        val str = curr.literal.substring(1, curr.literal.length - 1)
        val const = if (str2Const[str] == null) {
          str2Const[str] = ConstantStr(str, renameGlobal(".str"))
          str2Const[str]!!
        } else {
          str2Const[str]!!
        }
        topModule.setConst(const.name!!, const)
        IRBuilder.createGEP("array", renameLocal(".strl"), const, ConstantInt(32, 0), ConstantInt(32, 0))
      }

      /** identifier */
      2 -> {
        val (varName, varAddr) = getIdsNameAndItsPtr(curr)
        IRBuilder.createLoad(varName, varAddr)
      }

      /** this pointer */
      3 -> innerScope.getAddr("this")

      /** const bool for true */
      4 -> ConstantInt(8, 1)

      /** const bool for false */
      5 -> ConstantInt(8, 0)

      /** const null */
      else -> ConstantNull()
    }
  }

  private fun mallocElem(currType: Type): Value {
    val elemByteNum = ConstantInt(32, currType.getAlign())
    val malloc = topModule.getBuiltinFunc("_malloc")!!
    val call = IRBuilder.createCall(renameLocal(".malloc"), malloc, listOf(elemByteNum))
    val bitcastInst = IRBuilder.createBitCast(renameLocal(".bitcast"), call, TypeFactory.getPtrType(currType))
    if (currType is StructType) {
      val initFunc = topModule.getFunc(currType.getRealStructName() + ".new")!!
      IRBuilder.createCall(null, initFunc, listOf(bitcastInst))
    } else {
      TODO("Not yet implemented")
    }
    return bitcastInst
  }

  private fun mallocArray(currType: PointerType, arraySizeList: List<Value>, count: Int = 0): Value {
    val arraySize = arraySizeList.first()
    val containedType = currType.pointeeTy!!

    /** due to the size(), an extra space is needed to store the size of the array, which is right before the array */
    val pureArrayByteNum =
      IRBuilder.createBinary(
        renameLocal(".mul"),
        "mul",
        arraySize,
        ConstantInt(32, containedType.getAlign())
      )
    val extendedArrayByteNum =
      IRBuilder.createBinary(
        renameLocal(".add"),
        "add",
        pureArrayByteNum,
        ConstantInt(32, containedType.getAlign())
      )

    val malloc = topModule.getBuiltinFunc("_malloc")!!
    val createdSpace = IRBuilder.createCall(renameLocal(".malloc"), malloc, listOf(extendedArrayByteNum))
    val extendedArray = IRBuilder.createBitCast(renameLocal(".bitcast"), createdSpace, currType)
    val arraySizeAddr = if (extendedArray.type == TypeFactory.getPtrType("int")) {
      extendedArray
    } else {
      IRBuilder.createBitCast(renameLocal(".bitcast"), extendedArray, TypeFactory.getPtrType("int"))
    }
    IRBuilder.createStore(arraySize, arraySizeAddr)
    val pureArray = IRBuilder.createGEP("ptr", renameLocal(".array") + ".addr", extendedArray, ConstantInt(32, 1), null)

    /** there is no need to recurse more, because the size of arraySizeList indicates the remainder of dimension */
    if (arraySizeList.size == 1) {
      return pureArray
    }

    /** the start point is the last element of array, which iterate until the addr before the first element */
    val startPoint = IRBuilder.createGEP("ptr", renameLocal(".array") + ".addr", extendedArray, arraySize, null)
    val previousBlock = IRBuilder.getInsertBlock()
    val nestedLoopsNum = loopManager.getNestedLoopsNum() + count + 1
    val execFreq = loopWeight.pow(nestedLoopsNum).toInt()
    val condBlock = BasicBlock(renameLocal("while.cond"), execFreq)
    val bodyBlock = BasicBlock(renameLocal("while.body"), execFreq)
    val endBlock = BasicBlock(renameLocal("while.end"), execFreq)

    /** a small optimization by using phi instruction */
    IRBuilder.createBr(condBlock)
    IRBuilder.setInsertBlock(condBlock)
    val phiInst = IRBuilder.createPhi(
      ".phi",
      currType,
      mutableListOf(Pair(startPoint, previousBlock))
    )

    val cond = IRBuilder.createCmp(renameLocal(".cmp"), "ne", phiInst, extendedArray)
    IRBuilder.createBr(cond, bodyBlock, endBlock)

    IRBuilder.setInsertBlock(bodyBlock)
    val subArray =
      mallocArray(currType.pointeeTy as PointerType, arraySizeList.subList(1, arraySizeList.size), count + 1)
    IRBuilder.createStore(subArray, phiInst)

    val nextElement = IRBuilder.createGEP(
      "ptr",
      renameLocal(".array") + ".addr",
      phiInst,
      ConstantInt(32, -1),
      null
    )
    phiInst.addPred(nextElement, IRBuilder.getInsertBlock())
    IRBuilder.createBr(condBlock)

    IRBuilder.setInsertBlock(endBlock)
    return pureArray
  }

  override fun visit(curr: NewExprNode) {
    curr.arraySizeExprList.forEach { it?.accept(this) }
    curr.value = if (curr.arraySizeExprList.isNotEmpty()) {
      val currType = TypeFactory.getAnyType(curr.type!!) as PointerType
      val arraySizeList = curr.arraySizeExprList.map { it!!.value!! }
      mallocArray(currType, arraySizeList)
    } else {
      mallocElem(TypeFactory.getBasicType(curr.type!!))
    }
  }

  override fun visit(curr: LambdaCallNode) {
    TODO("Not yet implemented")
  }

  override fun visit(curr: FuncCallNode) {
    val recentClass = scopeManger.getRecentClass()
    if (recentClass != null && recentClass.classScope.testFunc(curr.funcName)) {
      val callee = topModule.getFunc("${recentClass.className}.${curr.funcName}")!!
      val thisPtr = scopeManger.last().getAddr("this")!!
      curr.value = IRBuilder.createCall(
        if (callee.funcType.resultType is VoidType) null else renameLocal(".call"),
        callee,
        listOf(thisPtr) + curr.argList.map { it.accept(this); it.value!! }
      )
    } else {
      val callee = topModule.getFunc(curr.funcName) ?: topModule.getBuiltinFunc(curr.funcName)!!
      curr.value = IRBuilder.createCall(
        if (callee.funcType.resultType is VoidType) null else renameLocal(".call"),
        callee,
        curr.argList.map { it.accept(this); it.value!! })
    }
  }

  override fun visit(curr: MethodCallNode) {
    curr.expr.accept(this)
    curr.value = if (curr.expr.type!!.isArray()) {
      /** for array, it has only one method called size() */
      assert(curr.method == "size")
      val arraySizeAddr =
        IRBuilder.createGEP("ptr", renameLocal(".arraysize") + ".addr", curr.expr.value!!, ConstantInt(32, -1), null)
      val finalInst = if (arraySizeAddr.type != TypeFactory.getPtrType("int")) {
        IRBuilder.createBitCast(renameLocal(".bitcast"), arraySizeAddr, TypeFactory.getPtrType("int"))
      } else {
        arraySizeAddr
      }
      IRBuilder.createLoad(renameLocal(".arraysize"), finalInst)
    } else {
      /** for others, it might be custom method or builtin method */
      val callee = topModule.getFunc("${curr.expr.type!!.cl.className}.${curr.method}")
        ?: topModule.getBuiltinFunc("_${curr.expr.type!!.cl.className}_${curr.method}")!!
      IRBuilder.createCall(
        if (callee.funcType.resultType is VoidType) null else renameLocal(".call"),
        callee,
        listOf(curr.expr.value!!) + curr.argList.map { it.accept(this); it.value!! })
    }
  }

  override fun visit(curr: MemberAccessNode) {
    curr.expr.accept(this)
    val memberName = renameLocal("${curr.expr.type!!.cl.className}.${curr.member}")
    val pointerType = curr.expr.value!!.type as PointerType
    val structType = pointerType.pointeeTy as StructType
    val gepInst = IRBuilder.createGEP(
      "struct",
      "$memberName.addr",
      curr.expr.value!!,
      ConstantInt(32, 0),
      ConstantInt(32, structType.getIndex(curr.member))
    )
    curr.value = IRBuilder.createLoad(renameLocal(memberName), gepInst)
  }

  override fun visit(curr: ArrayAccessNode) {
    curr.array.accept(this)
    curr.index.accept(this)
    val elemName = renameLocal(".elem")
    val ptr = IRBuilder.createGEP("ptr", "$elemName.addr", curr.array.value!!, curr.index.value!!, null)
    curr.value = IRBuilder.createLoad(elemName, ptr)
  }

  private fun getIdsNameAndItsPtr(curr: AtomNode): Pair<String, Value> {
    val innerScope = scopeManger.last()
    val id = curr.literal
    val recentClass = scopeManger.getRecentClass()
    val ptr = innerScope.getAddr(id)

    return if (ptr != null) {
      Pair(ptr.name!!.substringBeforeLast('.'), ptr) // remove .addr
    } else if (recentClass != null && recentClass.memberToIndex.containsKey(id)) {
      // Even though it's not in member access format, it could still be a member access when this happens in class's method definition.
      val memberName = renameLocal("${recentClass.className}.$id")
      val structType = topModule.getStruct("class.${recentClass.className}")

      Pair(
        memberName,
        IRBuilder.createGEP(
          "struct",
          "$memberName.addr",
          innerScope.getAddr("this")!!,
          ConstantInt(32, 0),
          ConstantInt(32, structType.getIndex(id))
        )
      )
    } else {
      val globalPtr = topModule.getGlobalAddr(id)
      Pair(globalPtr.name!!.substringBeforeLast('.'), globalPtr)
    }
  }

  private fun getVarsNameAndItsPtr(curr: ExprNode): Pair<String, Value> {
    return when (curr) {
      is AtomNode -> getIdsNameAndItsPtr(curr)

      is ArrayAccessNode -> {
        curr.array.accept(this)
        curr.index.accept(this)
        val elemName = renameLocal(".elem")
        Pair(
          elemName,
          IRBuilder.createGEP("ptr", "$elemName.addr", curr.array.value!!, curr.index.value!!, null)
        )
      }

      is MemberAccessNode -> {
        curr.expr.accept(this)
        val className = curr.expr.type!!.cl.className
        val memberName = renameLocal("$className.${curr.member}")
        val structType = topModule.getStruct("class.$className")
        Pair(
          memberName,
          IRBuilder.createGEP(
            "struct",
            renameLocal("$memberName.addr"),
            curr.expr.value!!,
            ConstantInt(32, 0),
            ConstantInt(32, structType.getIndex(curr.member))
          )
        )
      }

      else -> TODO("I don't know the exact implementation detail of struct")
    }
  }

  override fun visit(curr: SuffixExprNode) {
    val op = Utils.unaryOpToStr(curr.op)
    val (varName, varPtr) = getVarsNameAndItsPtr(curr.expr)
    val loadInst = IRBuilder.createLoad(varName, varPtr)
    curr.expr.value = IRBuilder.createBinary(renameLocal(".ori"), "add", loadInst, ConstantInt(32, 0))
    val binaryInst = IRBuilder.createBinary(
      renameLocal(".$op"),
      if (op == "inc") "add" else "sub",
      curr.expr.value!!,
      ConstantInt(32, 1)
    )
    IRBuilder.createStore(binaryInst, varPtr)

    /** because it's suffix, return the original one */
    curr.value = curr.expr.value
  }

  override fun visit(curr: PrefixExprNode) {
    val op = Utils.unaryOpToStr(curr.op)
    curr.value = when (curr.op) {
      "++", "--" -> {
        val (varName, varPtr) = getVarsNameAndItsPtr(curr.expr)
        curr.expr.value = IRBuilder.createLoad(varName, varPtr)
        val binaryInst = IRBuilder.createBinary(
          renameLocal(".$op"),
          if (op == "inc") "add" else "sub",
          curr.expr.value!!,
          ConstantInt(32, 1)
        )
        IRBuilder.createStore(binaryInst, varPtr)
        binaryInst
      }

      "+" -> {
        curr.expr.accept(this)
        curr.expr.value
      }

      "-" -> {
        curr.expr.accept(this)
        IRBuilder.createBinary(
          renameLocal(".$op"),
          "sub",
          ConstantInt(32, 0),
          curr.expr.value!!
        )
      }

      "~" -> {
        curr.expr.accept(this)
        IRBuilder.createBinary(
          renameLocal(".$op"),
          "xor",
          curr.expr.value!!,
          ConstantInt(32, -1)
        )
      }

      /** ! */
      else -> {
        curr.expr.accept(this)
        IRBuilder.createBinary(
          renameLocal(".$op"),
          "xor",
          IRBuilder.checki8Toi1(curr.expr.value!!),
          ConstantInt(1, 1)
        )
      }
    }
  }

  override fun visit(curr: BinaryExprNode) {
    curr.value = when (curr.ops.first()) {
      "+" -> {
        var lhs: Value? = null
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          val rhs = expr.value!!
          if (lhs == null) {
            lhs = rhs
            continue
          }
          val op = Utils.binaryOpToStr(curr.ops[index - 1])
          lhs = if (lhs.type is PointerType && rhs.type is PointerType) {
            val func = topModule.getBuiltinFunc("_string_concat")!!
            IRBuilder.createCall(renameLocal(".call"), func, listOf(lhs, rhs))
          } else {
            IRBuilder.createBinary(renameLocal(".$op"), op, lhs, rhs)
          }
        }
        lhs
      }

      "*", "/", "%", "-", "<<", ">>", "&", "^", "|" -> {
        var lhs: Value? = null
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          val rhs = expr.value!!
          if (lhs == null) {
            lhs = rhs
            continue
          }
          val op = Utils.binaryOpToStr(curr.ops[index - 1])
          lhs = IRBuilder.createBinary(renameLocal(".$op"), op, lhs, rhs)
        }
        lhs
      }

      /** namely <, <=, >, >=, ==, != */
      else -> {
        var lhs: Value? = null
        for ((index, expr) in curr.exprs.withIndex()) {
          expr.accept(this)
          val rhs = expr.value
          if (lhs == null) {
            lhs = rhs
            continue
          }
          val op = Utils.binaryOpToStr(curr.ops[index - 1])
          lhs = IRBuilder.createCmp(renameLocal(".cmp"), op, lhs, rhs!!)
        }
        lhs
      }
    }
  }

  private fun checkModification(curr: ExprNode): Boolean {
    return when (curr) {
      is PriorExprNode -> checkModification(curr.expr)
      is LambdaCallNode -> true
      is FuncCallNode -> true
      is MethodCallNode -> true
      is MemberAccessNode -> checkModification(curr.expr)
      is SuffixExprNode -> true
      is ArrayAccessNode -> true // to avoid the array access
      is PrefixExprNode -> if (curr.op == "++" || curr.op == "--") true else checkModification(curr.expr)
      is BinaryExprNode -> curr.exprs.any { checkModification(it) } || curr.ops.any { it == "/" }
      is LogicalOrExprNode -> curr.exprs.any { checkModification(it) }
      is LogicalAndExprNode -> curr.exprs.any { checkModification(it) }
      else -> false
    }
  }

  override fun visit(curr: LogicalOrExprNode) {
    val nestedLoopsNum = loopManager.getNestedLoopsNum()
    val execFreq = loopWeight.pow(nestedLoopsNum).toInt()
    if (checkModification(curr) || curr.exprs.size >= 3) {
      val endBlock = BasicBlock(renameLocal("lor.end"), execFreq)
      val candidates: MutableList<Pair<Value, BasicBlock>> = mutableListOf()
      val size = curr.exprs.size
      for ((index, expr) in curr.exprs.withIndex()) {
        expr.accept(this)

        /**
         * it's a short circuit operator, implemented by using phi instruction
         * for last, it's the comparison result assigned to phi; for all except last, it's 0 assigned to phi
         */
        candidates.add(
          Pair(
            if (index == size - 1) IRBuilder.checki8Toi1(expr.value!!) else ConstantInt(8, 1),
            IRBuilder.getInsertBlock()
          )
        )
        if (index == size - 1) {
          IRBuilder.createBr(endBlock)
        } else {
          val rhsBlock = BasicBlock(renameLocal(if (index != size - 2) "lor.lhs.false" else "lor.rhs"), execFreq)
          IRBuilder.createBr(expr.value!!, endBlock, rhsBlock)
          IRBuilder.setInsertBlock(rhsBlock)
        }
      }
      IRBuilder.setInsertBlock(endBlock)
      curr.value = IRBuilder.createPhi(renameLocal(".phi"), TypeFactory.getIntType(1), candidates)
    } else {
      var result: Value? = null
      for (expr in curr.exprs) {
        expr.accept(this)
        result = if (result == null) {
          expr.value
        } else {
          IRBuilder.createBinary(renameLocal(".or"), "or", result, expr.value!!)
        }
      }
      curr.value = result
    }
  }

  override fun visit(curr: LogicalAndExprNode) {
    val nestedLoopsNum = loopManager.getNestedLoopsNum()
    val execFreq = loopWeight.pow(nestedLoopsNum).toInt()
    if (checkModification(curr) || curr.exprs.size >= 3) {
      val endBlock = BasicBlock(renameLocal("land.end"), execFreq)
      val candidates: MutableList<Pair<Value, BasicBlock>> = mutableListOf()
      val size = curr.exprs.size
      for ((index, expr) in curr.exprs.withIndex()) {
        expr.accept(this)

        // it's a short circuit operator, implemented by using phi instruction
        // for the last situation, it's the comparison result assigned to phi; otherwise, it's 1 assigned to phi
        candidates.add(
          Pair(
            if (index == size - 1) IRBuilder.checki8Toi1(expr.value!!) else ConstantInt(8, 0),
            IRBuilder.getInsertBlock()
          )
        )
        if (index == size - 1) {
          IRBuilder.createBr(endBlock)
        } else {
          val rhsBlock = BasicBlock(renameLocal(if (index != size - 2) "land.lhs.true" else "land.rhs"), execFreq)
          IRBuilder.createBr(expr.value!!, rhsBlock, endBlock)
          IRBuilder.setInsertBlock(rhsBlock)
        }
      }
      IRBuilder.setInsertBlock(endBlock)
      curr.value = IRBuilder.createPhi(renameLocal(".phi"), TypeFactory.getIntType(1), candidates)
    } else {
      var result: Value? = null
      for (expr in curr.exprs) {
        expr.accept(this)
        result = if (result == null) {
          expr.value
        } else {
          IRBuilder.createBinary(renameLocal(".and"), "and", result, expr.value!!)
        }
      }
      curr.value = result
    }
  }

  override fun visit(curr: AssignExprNode) {
    val (_, ptr) = getVarsNameAndItsPtr(curr.lhs)
    curr.rhs.accept(this)
    IRBuilder.createStore(curr.rhs.value!!, ptr)
  }
}
