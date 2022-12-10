import backend.BackEndManager
import frontend.FrontEndManager
import middleend.MiddleEndManager
import middleend.basic.pointerNumBits
import middleend.pass.IREmit

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    throw Exception("No build option")
  } else {
    val mode = args[0]
    var testIR = false
    var testASM = false

    when (mode) {
      "custom" -> testIR = true

      "ir" -> {
        testIR = true
        IREmit.printOption = false
      }

      "asm" -> {
        testASM = true
        pointerNumBits = 32 // riscv32
      }

      else -> throw Exception("Unknown build option")
    }

    val inputStream = System.`in`

    val progNode = FrontEndManager.visit(inputStream)
    val topModule = MiddleEndManager.visit(progNode, testIR)
    BackEndManager.visit(topModule, testASM)
  }
}