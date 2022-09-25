package exceptions

import frontend.abst.utils.CodePos

class SemanticException(pos: CodePos, message: String) : Exception("$message ${pos.row}:${pos.column}")