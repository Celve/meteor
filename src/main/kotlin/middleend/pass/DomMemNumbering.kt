package middleend.pass

import middleend.basic.*
import middleend.helper.HashTable
import middleend.helper.MemTable
import middleend.struct.DomTree
import middleend.struct.EqSet
import middleend.struct.ValNum

object DomMemNumbering : IRVisitor() {
  private lateinit var module: TopModule
  private val block2Invalid = hashMapOf<BasicBlock, HashSet<String>>()
  private val block2Callable = hashMapOf<BasicBlock, Boolean>()
  private lateinit var eqSet: EqSet
  private lateinit var valNum: ValNum
  private lateinit var domTree: DomTree

  override fun visit(topModule: TopModule) {
    module = topModule
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    valNum = func.valNum
    eqSet = func.eqSet
    domTree = func.domTree
    eqSet.build()
    domTree.build()

    block2Invalid[func.getEntryBlock()] = hashSetOf()
    block2Callable[func.getEntryBlock()] = false
    memNumbering(func.getEntryBlock(), null, null)
  }

  private fun invalidate(block: BasicBlock) {
    for (succ in domTree.successors.getValue(block)) {
      block2Invalid[succ] = hashSetOf()
      block2Callable[succ] = false

      val visited = hashSetOf<BasicBlock>()
      val workList = mutableListOf(succ)
      while (workList.isNotEmpty()) {
        val curr = workList.removeFirst()
        if (curr !in visited && curr != block) {
          visited.add(curr)
          workList.addAll(curr.prevBlockSet)
          if (curr != succ) {
            block2Invalid[succ]!!.addAll(
              curr.instList.filterIsInstance<StoreInst>().flatMap { eqSet.get(it.getAddr()) })
          }
          val hasCall = curr.instList.any { it is CallInst && !module.builtinFuncMap.contains(it.getCallee().name) }
          block2Callable[succ] = block2Callable[succ]!! or hasCall
        }
      }
    }
  }

  private fun isAffectiveInst(inst: Instruction): Boolean {
    return inst is LoadInst || inst is StoreInst || inst is CallInst || inst is GetElementPtrInst
  }

  private fun memNumbering(block: BasicBlock, parentMemTable: MemTable?, parentHashTable: HashTable?) {
    val memTable = MemTable(if (block2Callable[block]!!) null else parentMemTable, block2Invalid[block]!!)
    val hashTable = HashTable(parentHashTable)

    block.instList.filter { isAffectiveInst(it) }.forEach { inst ->
      when (inst) {
        is CallInst -> {
          if (!module.builtinFuncMap.contains(inst.getCallee().name)) {
            memTable.removeAll()
          }
        }

        is StoreInst -> {
          eqSet.get(inst.getAddr()).forEach { memTable.remove(it) }
        }

        is LoadInst -> {
          val num = valNum.get(inst.getAddr())
          val result = memTable.get(num)
          if (result != null) { // redundant
            block.removeInst(inst, result)
          } else {
            memTable.add(num, inst)
          }
        }

        is GetElementPtrInst -> {
          val result = hashTable.get("gep", inst.useeList.toList())
          if (result != null) {
            block.removeInst(inst, result)
          } else {
            hashTable.add("gep", inst.useeList.toList(), inst)
          }
        }
      }
    }

    invalidate(block)
    domTree.successors.getValue(block).forEach { memNumbering(it, memTable, hashTable) }
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