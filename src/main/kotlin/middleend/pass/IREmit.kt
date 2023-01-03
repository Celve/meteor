package middleend.pass

import middleend.basic.*
import middleend.helper.Utils

object IREmit : IRVisitor() {
  // enable pcopy, mv or not
  var printOption: Boolean = true

  override fun visit(topModule: TopModule) {
    println(
      "; ModuleID = 'test'\n" +
          "source_filename = \"test\"\n" +
          "target datalayout = \"e-m:o-i64:64-i128:128-n32:64-S128\"\n" +
          "target triple = \"riscv32\"\n"
    )

    for ((_, func) in topModule.builtinFuncMap) {
      println("declare ${func.funcType.resultType} @${func.name}(${func.argList.joinToString(", ") { it.type.toString() }})")
    }
    println()

    for ((_, structType) in topModule.structTypeMap) {
      println("%${structType.structName} = type { ${structType.symbolList.joinToString(", ") { it.second.toString() }} }\n")
    }

    topModule.globalVarMap.forEach { it.value.accept(this) }
    if (topModule.globalVarMap.size >= 1) {
      println()
    }

    topModule.constStrMap.forEach { it.value.accept(this) }
    if (topModule.constStrMap.size >= 1) {
      println()
    }

    topModule.funcMap.forEach { it.value.accept(this) }
  }

  override fun visit(globalVar: GlobalVariable) {
    println("@${globalVar.name} = global ${(globalVar.type as PointerType).pointeeTy!!} zeroinitializer, align ${globalVar.type.getAlign()}")
  }

  override fun visit(constStr: ConstantStr) {
    val outputString = constStr.str.replace("\\", "\\\\").replace("\n", "\\0A").replace("\"", "\\22").plus("\\00")
    println("@${constStr.name} = private unnamed_addr constant [${constStr.length} x i8] c\"$outputString\", align 1")
  }

  override fun visit(func: Func) {
    println("define ${func.funcType.resultType} @${func.name}(${func.argList.joinToString(", ") { "${it.type} %${it.name}" }}) {")
    for (block in func.blockList) {
      block.accept(this)
      if (block != func.blockList.last()) {
        println()
      }
    }
    println("}")
    println()
  }

  override fun visit(block: BasicBlock) {
    println("${block.name}:")
    for (inst in block.instList) {
      print("\t")
      inst.accept(this)
    }
  }

  override fun visit(inst: AllocaInst) {
    println("%${inst.name} = alloca ${inst.varType}, align ${inst.varType.getAlign()}")
  }

  override fun visit(inst: CallInst) {
    val prefix: String = if (inst.name == null) "" else "%${inst.name} = "
    val argList =
      inst.getCallee().funcType.argList.zip(inst.getArgList()).joinToString(", ") { "${it.first} ${it.second}" }
    println("${prefix}call ${inst.getCallee().funcType.resultType} @${inst.getCallee().funcType.funcName}($argList)")
  }

  override fun visit(inst: LoadInst) {
    println("%${inst.name} = load ${inst.type}, ${inst.type}* ${inst.getAddr()}, align ${inst.type.getAlign()}")
  }

  override fun visit(inst: BitCastInst) {
    println("%${inst.name} = bitcast ${inst.fromTy} ${inst.getCastee()} to ${inst.type}")
  }

  override fun visit(inst: PhiInst) {
    println(
      "%${inst.name} = phi ${inst.type} ".plus(
        inst.getPredList().joinToString(", ") { "[ ${it.first}, ${it.second} ]" })
    )
  }

  override fun visit(inst: BinaryInst) {
    println("%${inst.name} = ${inst.op} ${inst.type} ${inst.getLhs()}, ${inst.getRhs()}")
  }

  override fun visit(inst: BranchInst) {
    val trueBlock = inst.getTrueBlock()
    val falseBlock = inst.getFalseBlock()
    println(
      if (inst.getCond() == null) {
        "br label $trueBlock"
      } else {
        "br i1 ${inst.getCond()}, label $trueBlock, label ${falseBlock!!}"
      }
    )
  }

  override fun visit(inst: GetElementPtrInst) {
    val extra = if (inst.getIndex() != null) ", i32 ${inst.getIndex()} " else ""
    if (inst.getPtr().type !is PointerType) {
      println(inst.getPtr())
    }
    println("%${inst.name} = getelementptr inbounds ${Utils.getPointee(inst.getPtr().type)}, ${inst.getPtr().type} ${inst.getPtr()}, i32 ${inst.getOffset()}$extra")
  }

  override fun visit(inst: ZExtInst) {
    println("%${inst.name} = zext ${inst.getOriginalVal().type} ${inst.getOriginalVal()} to ${inst.type}")
  }

  override fun visit(inst: TruncInst) {
    println("%${inst.name} = trunc ${inst.getOriginalVal().type} ${inst.getOriginalVal()} to ${inst.toTy}")
  }

  override fun visit(inst: StoreInst) {
    val lhsTy = if (inst.getValue().type is PointerType && (inst.getValue().type as PointerType).pointeeTy == null) {
      (inst.getAddr().type as PointerType).pointeeTy
    } else {
      inst.getValue().type
    }
    println("store $lhsTy ${inst.getValue()}, ${inst.getAddr().type} ${inst.getAddr()}, align ${inst.getValue().type.getAlign()}")
  }

  override fun visit(inst: CmpInst) {
    println("%${inst.name} = icmp ${inst.cond} ${inst.getLhs().type} ${inst.getLhs()}, ${inst.getRhs()}")
  }

  override fun visit(inst: ReturnInst) {
    println(
      if (inst.getRetVal() == null) {
        "ret ${inst.type}"
      } else {
        "ret ${inst.type} ${inst.getRetVal()}"
      }
    )
  }

  override fun visit(inst: PCopyInst) {
    println("pcopy ".plus(inst.getAssignmentList().joinToString(", ") { "[ ${it.first} = ${it.second} ]" }))
  }

  override fun visit(inst: MvInst) {
    if (printOption) {
      println("%${inst.name} = mv ${inst.type} ${inst.getSrc()}")
    } else {
      val src = inst.getSrc()
      val srcType = if (src is ConstantNull) inst.type else src.type
      println("%${inst.name} = bitcast $srcType $src to ${inst.type}")
    }
  }
}