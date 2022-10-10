package middleend.basic

import middleend.helper.ValueSymbolTable

class Func(name: String, val funcType: FuncType, val args: List<Value>) : GlobalValue(name, funcType) {
  val blockList: MutableList<BasicBlock> = mutableListOf()
  var returnBlock: BasicBlock? = null
  val vst = ValueSymbolTable()

  fun addBasicBlock(index: Int, basicBlock: BasicBlock) {
    blockList.add(index, basicBlock)
  }

  override fun toString(): String {
    return "define ${funcType.result} @$name(${args.joinToString(", ") { "${it.type} %${it.name}" }}) {\n${
      blockList.joinToString(
        "\n"
      ).plus(if (returnBlock == null) "" else "\n$returnBlock")
    }}\n"
  }

  fun toDeclaration(): String {
    return "declare ${funcType.result} @$name(${args.joinToString(", ") { it.type.toString() }})\n"
  }
}
