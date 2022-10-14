package backend.basic

abstract class ASMInst

class ASMLoadInst(val byteNum: Int) : ASMInst()

class ASMStoreInst(val byteNum: Int) : ASMInst()

class ASMArithInst(val op: String, val lhs: Operand, val rhs: Operand) : ASMInst()

class ASMBrInst : ASMInst()

class ASMJalInst : ASMInst()

class ASMJalrInst : ASMInst()

class ASMLiInst : ASMInst()

class ASMLuiInst : ASMInst()

class ASMAuipcInst : ASMInst()

class ASMMvInst : ASMInst()

class ASMNotInst : ASMInst()

class ASMNegInst : ASMInst()

class ASMZextInst : ASMInst()