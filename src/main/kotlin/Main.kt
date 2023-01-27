import backend.BackEndManager
import frontend.FrontEndManager
import middleend.MiddleEndManager
import middleend.basic.pointerNumBits
import middleend.pass.IREmit

const val OnlineJudge = false

fun main(args: Array<String>) {
  val buildOptions = if (OnlineJudge) {
    hashSetOf(
      "--inline",
      "--licm",
      "--sccp",
      "--dvnt",
      "--adce",
      "--asm",
      "--localize",
      "--peephole",
      "--gcp",
      "--dmnt",
      "--sr"
    )
  } else {
    args.toHashSet()
  }

  if (buildOptions.isEmpty()) {
    throw Exception("No build option")
  } else {
    var testIR = false
    var testASM = false

    when {
      buildOptions.contains("--custom") -> testIR = true

      buildOptions.contains("--ir") -> {
        testIR = true
        IREmit.printOption = false
      }

      buildOptions.contains("--asm") -> {
        testASM = true
        pointerNumBits = 32 // riscv32
      }

      else -> throw Exception("Unknown build option")
    }

    val inputStream = System.`in`

    val progNode = FrontEndManager.visit(inputStream)
    val topModule = MiddleEndManager.visit(progNode, testIR, buildOptions)
    BackEndManager.visit(topModule, testASM, buildOptions)
  }
}