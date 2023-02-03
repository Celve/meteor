package middleend.pass

import backend.helper.Utils
import middleend.basic.*
import java.util.*

object OperandReordering : IRVisitor() {
  private lateinit var currFunc: Func
  private lateinit var currBlock: BasicBlock
  private val rank = hashMapOf<Value, Int>()
  private val isRoot = hashSetOf<Value>()

  override fun visit(topModule: TopModule) {
    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {}

  override fun visit(constStr: ConstantStr) {}

  override fun visit(func: Func) {
    currFunc = func
    func.blockList.forEach { it.accept(this) }
  }

  private fun isTarget(inst: BinaryInst): Boolean {
    return inst.op == "add" || inst.op == "mul"
  }

  private fun flatten(curr: Value, base: PriorityQueue<Value>, removed: MutableList<Value>): Int {
    when (curr) {
      is ConstantInt -> {
        rank[curr] = 0
        base.add(curr)
      }

      is Instruction -> {
        when {
          curr !is BinaryInst -> {
//            rank[curr] = if (curr.parent == currBlock) 2 else 1
            rank[curr] = 1
            base.add(curr)
          }

          curr in isRoot -> { // including op not equal
            balance(curr)
            base.add(curr)
          }

          curr.parent != currBlock -> {
            rank[curr] = 1
            base.add(curr)
          }

          !isTarget(curr) -> {
//            rank[curr] = 2
            rank[curr] = 1
            base.add(curr)
          }

          else -> {
            removed.add(curr)
            flatten(curr.getLhs(), base, removed)
            flatten(curr.getRhs(), base, removed)
            rank[curr] = rank[curr.getLhs()]!! + rank[curr.getRhs()]!!
          }
        }
      }

      in currFunc.argList -> {
        rank[curr] = 1
        base.add(curr)
      }

      else -> {
        throw Exception("Unknown value type")
      }
    }
    return rank[curr]!!
  }

  private fun rebuild(queue: PriorityQueue<Value>, op: String): List<Value> {
    val news = mutableListOf<Value>()
    while (queue.size >= 2) {
      val lhs = queue.poll()!!
      val rhs = queue.poll()!!
      if (lhs is ConstantInt && rhs is ConstantInt) {
        val const = ConstantInt(32, Utils.calculate(op, lhs.value, rhs.value))
        rank[const] = 0
        queue.add(const)
      } else {
        val (newLhs, newRhs) = if (rhs is ConstantInt) {
          Pair(rhs, lhs)
        } else {
          Pair(lhs, rhs)
        }
        val newInst = BinaryInst(currFunc.mulTable.rename(".$op"), op, newLhs, newRhs)
        newInst.parent = currBlock
        rank[newInst] = rank[lhs]!! + rank[rhs]!!
        queue.add(newInst)
        news.add(newInst)
      }
    }
    if (news.isEmpty()) {
      news.add(queue.first())
    }
    return news
  }

  private fun modify(root: BinaryInst, olds: List<Value>, news: List<Value>) {
    val sub = news.last()

    if (sub !is Instruction) {
      currBlock.removeInst(root, sub)
    } else {
      if (olds.any { it !in currBlock.instList }) {
        throw Exception("Error in OperandReordering")
      }
      currBlock.replaceInst(root, sub)
      currBlock.instList.removeAll(olds.toSet())
      olds.filterIsInstance<User>().forEach { it.eliminate() }
      currBlock.instList.addAll(currBlock.getIndexOfInst(sub), news.filterIsInstance<Instruction>().dropLast(1))
    }
  }

  private fun balance(root: BinaryInst) {
    if (rank[root]!! >= 0) {
      return // have already been processed
    }
    val queue = PriorityQueue<Value> { lhs, rhs ->
      if (rank[lhs]!! != rank[rhs]!!) {
        rank[lhs]!! - rank[rhs]!!
      } else if (lhs in currFunc.argList || rhs in currFunc.argList) {
        when {
          lhs in currFunc.argList && rhs in currFunc.argList -> 0
          lhs in currFunc.argList -> -1
          else -> 1
        }
      } else if (lhs !is Instruction || rhs !is Instruction) {
        0
      } else {
        val lbIndex = currFunc.getIndexOfBlock(lhs.parent)
        val rbIndex = currFunc.getIndexOfBlock(rhs.parent)
        lbIndex - rbIndex
      }
    }
    val ori = mutableListOf<Value>()
    rank[root] = flatten(root.getLhs(), queue, ori) + flatten(root.getRhs(), queue, ori)
    val sub = rebuild(queue, root.op)
    modify(root, ori, sub)
    if (sub.last() is Instruction) {
      isRoot.add(sub.last())
    }
  }


  private fun isDifferentBinInst(inst1: Value, inst2: Value): Boolean {
    return inst1 !is BinaryInst || inst2 !is BinaryInst || inst1.op != inst2.op
  }

  override fun visit(block: BasicBlock) {
    currBlock = block
    val roots = mutableListOf<BinaryInst>()
    rank.clear()
    isRoot.clear()
    block.instList.filterIsInstance<BinaryInst>().filter { isTarget(it) }.forEach { inst ->
      rank[inst] = -1
      if (inst.userList.size > 1 || (inst.userList.size == 1 && isDifferentBinInst(inst, inst.userList[0]))) {
        isRoot.add(inst)
        roots.add(inst)
      }
    }
    roots.sortedWith { lhs, rhs ->
      when (lhs.op) {
        rhs.op -> 0
        "add" -> -1
        else -> 1
      }
    }

    while (roots.isNotEmpty()) {
      val root = roots.removeFirst()
      balance(root)
    }
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