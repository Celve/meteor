package backend.basic

open class Immediate : ASMValue(null)

/**
 * 0-base.
 */
data class UndeterminedImmediate(
  val stackFrame: StackFrame,
  val hierarchy: OffsetType,
  val id: Int,
  val delta: Int = 0,
  val sign: Int = 1
) : Immediate() {
  fun get(): DeterminedImmediate {
    return DeterminedImmediate(
      with(stackFrame) {
        when (hierarchy) {
          OffsetType.ALLOCA_SPACE -> (calleeArgumentId + localVariableId + savedRegisterId + callerArgumentId + returnAddressId) * 4
          OffsetType.RETURN_ADDRESS -> (calleeArgumentId + localVariableId + savedRegisterId + callerArgumentId) * 4
          OffsetType.CALLER_ARGUMENT -> (calleeArgumentId + localVariableId + savedRegisterId + id) * 4
          OffsetType.SAVED_REGISTER -> (calleeArgumentId + localVariableId + id) * 4
          OffsetType.LOCAL_VARIABLE -> (calleeArgumentId + id) * 4
          OffsetType.CALLEE_ARGUMENT -> id * 4
        }
      }.plus(delta).times(sign)
    )
  }

  operator fun unaryMinus(): UndeterminedImmediate {
    return UndeterminedImmediate(stackFrame, hierarchy, id, delta, -sign)
  }

  operator fun plus(operand: Int): UndeterminedImmediate {
    return UndeterminedImmediate(stackFrame, hierarchy, id, delta + operand * sign, sign)
  }

  override fun toString(): String {
    return "$hierarchy-$id"
  }
}

data class DeterminedImmediate(val value: Int) : Immediate() {
  override fun toString(): String {
    return value.toString()
  }
}

data class RelocationHi(val target: ASMGlobalPointer) : Immediate() {
  override fun toString(): String {
    return "%hi($target)"
  }
}

data class RelocationLo(val target: ASMGlobalPointer) : Immediate() {
  override fun toString(): String {
    return "%lo($target)"
  }
}