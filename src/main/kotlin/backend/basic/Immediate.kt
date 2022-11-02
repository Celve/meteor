package backend.basic

abstract class Immediate

/**
 * 0-base.
 */
data class UndeterminedImmediate(
  val stackFrame: StackFrame,
  val hierarchy: OffsetType,
  val id: Int,
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
      }.times(sign)
    )
  }

  operator fun unaryMinus(): UndeterminedImmediate {
    return UndeterminedImmediate(stackFrame, hierarchy, id, -sign)
  }
}

data class DeterminedImmediate(val value: Int) : Immediate() {
  override fun toString(): String {
    return value.toString()
  }
}