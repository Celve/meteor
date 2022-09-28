package exceptions

import frontend.utils.CodePos

class SemanticException(pos: CodePos, message: String) : Exception("$message ${pos.row}:${pos.column}")