package exceptions

import frontend.utils.SrcPos

class SemanticException(pos: SrcPos, message: String) : Exception("$message ${pos.row}:${pos.column}")