package middleend.pass

import middleend.basic.*
import middleend.helper.Utils

class IREmit : IRVisitor() {
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
    val argList = inst.funcType.argList.zip(inst.args).joinToString(", ") { "${it.first} ${it.second}" }
    println("${prefix}call ${inst.funcType.resultType} @${inst.funcType.funcName}($argList)")
  }

  override fun visit(inst: LoadInst) {
    println("%${inst.name} = load ${inst.type}, ${inst.type}* ${inst.addr}, align ${inst.type.getAlign()}")
  }

  override fun visit(inst: BitCastInst) {
    println("%${inst.name} = bitcast ${inst.fromTy} ${inst.castee} to ${inst.type}")
  }

  override fun visit(inst: PhiInst) {
    println("%${inst.name} = phi ${inst.type} ".plus(inst.predList.joinToString(", ") { "[ ${it.first}, ${it.second} ]" }))
  }

  override fun visit(inst: BinaryInst) {
    println("%${inst.name} = ${inst.op} ${inst.type} ${inst.rs}, ${inst.rt}")
  }

  override fun visit(inst: BranchInst) {
    println(
      if (inst.cond == null) {
        "br label ${inst.trueBlock}"
      } else {
        "br i1 ${inst.cond}, label ${inst.trueBlock}, label ${inst.falseBlock!!}"
      }
    )
  }

  override fun visit(inst: GetElementPtrInst) {
    val extra = if (inst.offset != null) ", i32 ${inst.offset} " else ""
    println("%${inst.name} = getelementptr inbounds ${Utils.getPointee(inst.ptr.type)}, ${inst.ptr.type} ${inst.ptr}, i32 ${inst.index}$extra")
  }

  override fun visit(inst: ZExtInst) {
    println("%${inst.name} = zext ${inst.originalVal.type} ${inst.originalVal} to ${inst.type}")
  }

  override fun visit(inst: TruncInst) {
    println("%${inst.name} = trunc ${inst.originalVal.type} ${inst.originalVal} to ${inst.toTy}")
  }

  override fun visit(inst: StoreInst) {
    val lhsTy = if (inst.value.type is PointerType && (inst.value.type as PointerType).pointeeTy == null) {
      (inst.addr.type as PointerType).pointeeTy
    } else {
      inst.value.type
    }
    println("store ${lhsTy} ${inst.value}, ${inst.addr.type} ${inst.addr}, align ${inst.value.type.getAlign()}")
  }

  override fun visit(inst: CmpInst) {
    println("%${inst.name} = icmp ${inst.cond} ${inst.rs.type} ${inst.rs}, ${inst.rt}")
  }

  override fun visit(inst: ReturnInst) {
    println(
      if (inst.retVal == null) {
        "ret ${inst.type}"
      } else {
        "ret ${inst.type} ${inst.retVal}"
      }
    )
  }

  override fun visit(inst: PCopyInst) {
    println("pcopy ".plus(inst.assignmentList.joinToString(", ") { "[ ${it.first} = ${it.second} ]" }))
  }
}