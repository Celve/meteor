package middleend.basic

class Slice(val blockList: MutableList<BasicBlock>) { // for jvm
  private var isInserted = false
  private val undefinedTable = hashMapOf<String, HashSet<Value>>()
  val valueTable = hashMapOf<String, Value>()
  val nameTable = hashMapOf<Value, String>()

  init {
    // use its defs to replace uses
    blockList.forEach {
      valueTable[it.name!!] = it
      nameTable[it] = it.name!!
    }
    val instList = blockList.flatMap { it.instList }
    instList.filter { it.isDef() }.forEach {
      valueTable[it.name!!] = it
      nameTable[it] = it.name!!
    }
    instList.forEach { inst ->
      inst.replaceAll {
        if (it.isDef() && valueTable.contains(it.name!!)) {
          valueTable[it.name!!]!!
        } else {
          if (it.isDef()) {
            undefinedTable.getOrPut(it.name!!) { hashSetOf() }.add(it)
          }
          it
        }
      }
    }
  }

  // rename inside values to cater outside world
  fun rename(applier: (String) -> String) {
    blockList.flatMap { it.instList }.filter { it.isDef() }.forEach {
      it.name = applier(it.name!!)
    }
    blockList.forEach {
      it.name = applier(it.name!!)
    }
  }

  // remove those blocks that have only one predecessor and one successor
  fun flatten() {
    val removed = hashSetOf<BasicBlock>()
    blockList.filter { it.prevBlockSet.size == 1 }.forEach {
      val prevBlock = it.prevBlockSet.first()
      if (prevBlock.nextBlockSet.size == 1) {
        it.instList.addAll(0, prevBlock.instList.dropLast(1))
        prevBlock.substitutedBy(it)
        removed.add(prevBlock)
      }
    }
    blockList.removeAll(removed)
  }

  // resolve those undefined values
  fun resolve(valueTable: HashMap<String, Value>) {
    val removed = hashSetOf<String>()
    undefinedTable.forEach { (name, valueSet) ->
      valueTable[name]?.let { sub ->
        valueSet.forEach { value ->
          if (value is BasicBlock) {
            value.prevBlockSet.forEach {
              it.removeNextBlock(value)
              it.addNextBlock(sub as BasicBlock)
            }
          }
          value.substitutedBy(sub)
        }
        removed.add(name)
      }
    }
    removed.forEach { undefinedTable.remove(it) }
  }

  fun concat(slice: Slice) {
    blockList.addAll(slice.blockList)
    slice.resolve(valueTable)
    valueTable.putAll(slice.valueTable)
    nameTable.putAll(slice.nameTable)

    // it is merging, but not replacing
    slice.undefinedTable.forEach { (name, valueSet) ->
      undefinedTable.getOrPut(name) { hashSetOf() }.addAll(valueSet)
    }
  }

  fun eliminate() {
    blockList.flatMap { it.instList }.forEach {
      if (it is BranchInst) {
        it.parent.removeBrInst(it)
      }
      it.eliminate()
    }
  }

  fun apply(applier: (Value) -> Unit) {
    blockList.flatMap { it.instList }.forEach { applier(it) }
  }

  fun getExitingBlocks(): List<BasicBlock> {
    return blockList.filter { block -> block.nextBlockSet.any { it !in blockList } }
  }

  fun isUndefined(value: Value): Boolean {
    return undefinedTable.contains(value.name!!)
  }

  fun insert(fromBlock: BasicBlock, toBlock: BasicBlock) {
    if (isInserted) {
      throw Exception("Slice containing ${blockList} has been inserted.")
    }
    isInserted = true

    // 1. modify the control flow graph and the branch instruction
    val fromBrInst = fromBlock.getTerminator() as BranchInst
    fromBrInst.replace(toBlock, blockList.first()) // CAVEAT: this might be wrong

    val exitingBlocks = getExitingBlocks()
    if (exitingBlocks.size != 1) {
      throw Exception("Slice has more than one exiting block.")
    }
    val exitingBlock = exitingBlocks.first()

    val exitingBrInst = exitingBlock.getTerminator() as BranchInst
    val outsideBlocks = exitingBrInst.useeList.filterIsInstance<BasicBlock>().filter { isUndefined(it) }
    if (outsideBlocks.size != 1) {
      throw Exception("Exiting block has more than one outside block.")
    }
    exitingBrInst.replace(outsideBlocks.first(), toBlock)

    // 2. update phi instructions
    toBlock.updatePhiInsts(fromBlock, exitingBlock)

    // 3. update blocks' parent
    blockList.forEach { it.parent = fromBlock.parent }
  }
}