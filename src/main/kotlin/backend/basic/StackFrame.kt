package backend.basic

import kotlin.math.max

/**
 * I try a lot to avoid this data structure, however, I fail, due to the RISC-V calling conventions.
 * According to the convention, a stack frame is divided into these parts:
 *                   .
 *                   .
 *      +->          .
 *     |   +-----------------+   |
 *     |   | return address  |   |
 *     |   |   previous fp ------+
 *     |   | saved registers |
 *     |   | local variables |
 *     |   |       ...       | <-+
 *     |   +-----------------+   |
 *     |   | return address  |   |
 *     +------ previous fp   |   |
 *         | saved registers |   |
 *         | local variables |   |
 *     +-> |       ...       |   |
 *     |   +-----------------+   |
 *     |   | return address  |   |
 *     |   |   previous fp ------+
 *     |   | saved registers |
 *     |   | local variables |
 *     |   |       ...       | <-+
 *     |   +-----------------+   |
 *     |   | return address  |   |
 *     +------ previous fp   |   |
 *         | saved registers |   |
 *         | local variables |   |
 * $fp --> |       ...       |   |
 *         +-----------------+   |
 *         | return address  |   |
 *         |   previous fp ------+
 *         | saved registers |
 * $sp --> | local variables |
 *         +-----------------+
 * At the bottom of the frame, there is a ... to represent the possible overflow arguments for the callee.
 * By now, we could describe the hierarchy:
 * - return address
 * - arguments stored in a register
 * - saved registers
 * - local variables
 * - arguments stored in the stack
 */
class StackFrame {
  var returnAddressId: Int = 0
  var callerArgumentId: Int = 0
  var savedRegisterId: Int = 0
  var localVariableId: Int = 0
  var calleeArgumentId: Int = 0

  /**
   * It's invalid to newReturnAddress twice in the function
   */
  fun newReturnAddress(): UndeterminedImmediate {
    returnAddressId = 0
    return UndeterminedImmediate(this, OffsetType.RETURN_ADDRESS, returnAddressId++)
  }

  fun getCallerArgument(id: Int): UndeterminedImmediate {
    return UndeterminedImmediate(this, OffsetType.CALLER_ARGUMENT, id)
  }

  fun newCallerArgument(): UndeterminedImmediate {
    return UndeterminedImmediate(this, OffsetType.CALLER_ARGUMENT, callerArgumentId++)
  }

  fun newSavedRegister(): UndeterminedImmediate {
    return UndeterminedImmediate(this, OffsetType.SAVED_REGISTER, savedRegisterId++)
  }

  fun newLocalVariable(): UndeterminedImmediate {
    return UndeterminedImmediate(this, OffsetType.LOCAL_VARIABLE, localVariableId++)
  }

  fun getCalleeArgument(id: Int): UndeterminedImmediate {
    calleeArgumentId = max(id + 1, calleeArgumentId)
    return UndeterminedImmediate(this, OffsetType.CALLEE_ARGUMENT, id)
  }

  fun newCalleeArgument(): UndeterminedImmediate {
    return UndeterminedImmediate(this, OffsetType.CALLEE_ARGUMENT, calleeArgumentId++)
  }

  fun newAllocaSpace(): UndeterminedImmediate {
    return UndeterminedImmediate(this, OffsetType.ALLOCA_SPACE, 0)
  }
}

enum class OffsetType {
  ALLOCA_SPACE,
  RETURN_ADDRESS,
  CALLER_ARGUMENT,
  SAVED_REGISTER,
  LOCAL_VARIABLE,
  CALLEE_ARGUMENT
}