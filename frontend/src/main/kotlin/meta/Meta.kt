package meta

import abst.utils.ClassScope


class ClassMeta(
  val className: String,
  val classMembers: List<VarMeta>,
  val classMethods: List<FuncMeta>,
) {
  val classScope = ClassScope(null)
}

class FuncMeta(
  val funcName: String,
  val paramTypes: List<ClassMeta>,
  val paramNames: List<String>,
  val returnType: ClassMeta?,
) {
  val funcScope = ClassScope(null)
}

class VarMeta(val varName: String, val varType: ClassMeta)