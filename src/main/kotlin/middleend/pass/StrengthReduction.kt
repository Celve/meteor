package middleend.pass

import backend.helper.Utils
import middleend.basic.*
import middleend.struct.DirectedGraph

object StrengthReduction : IRVisitor() {
  private val lookupTable = hashMapOf<Triple<String, Value, Value>, Instruction>()
  private lateinit var header: HashMap<Instruction, PhiInst> // header contains the base phi instruction of induction variables
  private lateinit var currFunc: Func

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  private fun isIvAndRc(iv: Value, rc: Value): Boolean {
    return if (rc !is Instruction) {
      rc is Constant && header[iv] != null
    } else {
      val head = header[iv]
      iv is Instruction && head != null && currFunc.domTree.doms.getValue(head.parent)
        .contains(rc.parent) && rc.parent != head.parent
    }
  }

  private fun isIvsAndRc(ivs: HashSet<Instruction>, rc: Value): Boolean {
    return if (rc !is Instruction) {
      rc is Constant
    } else {
      ivs.all { currFunc.domTree.doms.getValue(it.parent).contains(rc.parent) }
    }
  }

  private fun isValidUpdate(opSet: HashSet<Instruction>): Boolean {
    return opSet.all { operation ->
      when (operation) {
        is BinaryInst -> {
          val op = operation.op
          val lhs = operation.getLhs()
          val rhs = operation.getRhs()
          (op == "add" || op == "sub") && (isIvsAndRc(opSet, lhs) || isIvsAndRc(opSet, rhs))
        }

        is PhiInst -> true
        is MvInst -> opSet.contains(operation.getSrc())
        else -> false
      }
    }
  }

  private fun getIvAndRc(operation: Value): Pair<Instruction, Value>? {
    return when (operation) {
      is BinaryInst -> {
        when (operation.op) {
          "add", "mul" -> {
            val lhs = operation.getLhs()
            val rhs = operation.getRhs()
            when {
              isIvAndRc(lhs, rhs) -> Pair(lhs as Instruction, rhs)
              isIvAndRc(rhs, lhs) -> Pair(rhs as Instruction, lhs)
              else -> null
            }
          }

          "sub" -> {
            val lhs = operation.getLhs()
            val rhs = operation.getRhs()
            when {
              isIvAndRc(lhs, rhs) -> Pair(lhs as Instruction, rhs)
              else -> null
            }
          }

          else -> null
        }
      }

      is MvInst -> Pair(operation.getSrc() as Instruction, ConstantInt(32, 0))
      else -> null
    }
  }

  private fun classifyIV(valueSet: HashSet<Value>) {
    val operationSet = valueSet.filterIsInstance<Instruction>().toHashSet()
    if (isValidUpdate(operationSet)) {
      val postOrder = currFunc.domTree.block2Postorder
      val h = operationSet.minWithOrNull { lhs, rhs ->
        if (postOrder[lhs.parent]!! != postOrder[rhs.parent]!!) {
          postOrder[rhs.parent]!! - postOrder[lhs.parent]!!// bigger is better
        } else {
          lhs.parent.instList.indexOf(lhs) - rhs.parent.instList.indexOf(rhs) // smaller is better
        }
      }!!
      operationSet.forEach { header[it] = h as PhiInst }
    } else {
      operationSet.forEach {
        val result = getIvAndRc(it)
        if (result != null) {
          replace(it, result.first, result.second)
        }
      }
    }
  }

  private fun replace(inst: Instruction, iv: Instruction, rc: Value) {
    val op = when (inst) {
      is BinaryInst -> inst.op
      is MvInst -> "add"
      else -> throw Exception("Invalid instruction")
    }
    val result = reduce(op, iv, rc)
    val mvInst = MvInst(currFunc.mulTable.rename(".iv"), result)
    inst.parent.replaceInst(inst, mvInst)
    header[mvInst] = header[iv]!!
  }

  private fun utilize(op: String, operand1: Value, operand2: Value): Value {
    return lookupTable[Triple(op, operand1, operand2)]
      ?: if (isIvAndRc(operand1, operand2)) {
        reduce(op, operand1 as Instruction, operand2)
      } else if (isIvAndRc(operand2, operand1)) {
        reduce(op, operand2 as Instruction, operand1)
      } else if (operand1 is ConstantInt && operand2 is ConstantInt) {
        ConstantInt(32, Utils.calculate(op, operand1.value, operand2.value))
      } else {
        val binaryInst = BinaryInst(currFunc.mulTable.rename(".$op"), op, operand1, operand2)
        lookupTable[Triple(op, operand1, operand2)] = binaryInst
        val blockSet = when {
          operand1 is Instruction && operand2 is Instruction -> currFunc.domTree.domeds.getValue(operand1.parent)
            .intersect(currFunc.domTree.domeds.getValue(operand2.parent))

          operand1 is Instruction -> currFunc.domTree.domeds.getValue(operand1.parent)
          operand2 is Instruction -> currFunc.domTree.domeds.getValue(operand2.parent)
          else -> throw Exception("Invalid operands")
        }
        val insertedBlock = blockSet.maxByOrNull { currFunc.domTree.block2Postorder[it]!! }!!
        insertedBlock.addInst(insertedBlock.instList.size - 1, binaryInst)
        binaryInst
      }
  }

  private fun reduce(op: String, iv: Instruction, rc: Value): Instruction {
    val result = lookupTable[Triple(op, iv, rc)]
    return if (result == null) {
      val duplicate = iv.clone(currFunc.mulTable.rename(".iv"))
      val block = iv.parent
      block.addInst(block.instList.indexOf(iv) + 1, duplicate)
      lookupTable[Triple(op, iv, rc)] = duplicate
      header[duplicate] = header[iv]!!
      for (operand in duplicate.useeList.filterNot { it is BasicBlock }) {
        if (operand is Instruction && header[operand] == header[iv]) {
          duplicate.replace(operand, reduce(op, operand, rc))
        } else if (op == "mul" || duplicate is PhiInst) {
          duplicate.replace(operand, utilize(op, operand, rc))
        }
      }
      duplicate
    } else {
      result
    }
  }

  private fun process(opSet: HashSet<Value>) {
    if (opSet.size == 1) { // it has only one member
      val op = opSet.first()
      if (op is BinaryInst && op.op == "mul") {
        val result = getIvAndRc(op)
        result?.let { replace(op, result.first, result.second) }
      }
    } else {
      classifyIV(opSet)
    }
  }

  override fun visit(func: Func) {
    currFunc = func
    func.domTree.build()
    lookupTable.clear()
    header = func.indVar.all2Phi
    header.clear()

    val instList = func.blockList.flatMap { block -> block.instList.filter { it.isDef() } }
    val graph = DirectedGraph<Value>()
    instList.forEach { inst -> inst.useeList.forEach { graph.addEdge(inst, it) } }
    graph.tarjan()
    graph.rebuild()
    graph.topSort()
    graph.topOrder.reversed().forEach {
      process(graph.sccSet.getValue(it))
    }
  }

  override fun visit(block: BasicBlock) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: AllocaInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CallInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: LoadInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BitCastInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PhiInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BinaryInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: BranchInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: GetElementPtrInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ZExtInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: TruncInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: StoreInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: CmpInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: ReturnInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: PCopyInst) {
    TODO("Not yet implemented")
  }

  override fun visit(inst: MvInst) {
    TODO("Not yet implemented")
  }
}