package middleend.basic

import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import frontend.metadata.TypeMd

object TypeFactory {
  private val existedType: HashMap<Type, Type> = hashMapOf()

  private fun screen(type: Type): Type {
    if (existedType.containsKey(type)) {
      return existedType[type]!!
    }
    existedType[type] = type
    return type
  }

  fun getLabelType(labelName: String): LabelType {
    return screen(LabelType(labelName)) as LabelType
  }

  fun getIntType(numOfBits: Int): IntType {
    return screen(IntType(numOfBits)) as IntType
  }

  fun getNullType(): PointerType {
    return getPtrType(null)
  }

  fun getPtrType(type: Type?): PointerType {
    return screen(PointerType(type)) as PointerType
  }

  fun getVoidType(): VoidType {
    return screen(VoidType()) as VoidType
  }

  fun getArrayType(type: Type, numElements: Int): ArrayType {
    return screen(ArrayType(type, numElements)) as ArrayType
  }

  fun getPtrToArrayType(typeMd: TypeMd): PointerType {
    var ptrToArray = getPtrToType(typeMd)
    for (i in 1..typeMd.dim) {
      ptrToArray = screen(PointerType(ptrToArray)) as PointerType
    }
    return ptrToArray
  }

  /// For primitive types, this function returns its exact type.
  /// For non-primitive types, like arrays and structs, this function returns a pointer.
  /// There is no exception control inside, make sure that all parameters are right.
  fun getPtrToType(typeMd: TypeMd): PointerType {
    return screen(
      when {
        typeMd.isVoid() -> PointerType(VoidType()) // FIXME: ???
        typeMd.isInt() -> PointerType(IntType(32))
        typeMd.isBool() -> PointerType(IntType(1))
        typeMd.isString() -> PointerType(IntType(8)) // TODO: make sure that this is ok
        typeMd.isArray() -> getPtrToArrayType(typeMd)
        else -> PointerType(StructType(typeMd.cl))
      }
    ) as PointerType
  }

  fun getStructType(classMd: ClassMd): StructType {
    return screen(StructType(classMd)) as StructType
  }

  /// This function is for func type.
  /// Because there is no function pointer in Mx*, therefore it doesn't need to be distinguished.
  fun getFuncType(funcMd: FuncMd): FuncType {
    return screen(FuncType(funcMd)) as FuncType
  }

  fun getFuncType(funcName: String, params: List<Type>, result: Type): FuncType {
    return screen(FuncType(funcName, params, result)) as FuncType
  }

  fun getType(typeMd: TypeMd): Type {
    return screen(
      when {
        typeMd.isVoid() -> VoidType()
        typeMd.isInt() -> IntType(32)
        typeMd.isBool() -> IntType(8)
        typeMd.isString() -> IntType(8) // TODO: make sure that this is ok
        typeMd.isArray() -> getPtrToArrayType(typeMd)
        else -> PointerType(StructType(typeMd.cl))
      }
    )
  }
}

/// All constructors inside type and its subclasses are forbidden.
abstract class Type {
  abstract fun getNumBits(): Int

  fun getAlign(): Int {
    return getNumBits() / 8
  }
}


data class VoidType(val uselessName: String = "void") : Type() {
  override fun getNumBits(): Int {
    return 0
  }

  override fun toString(): String {
    return "void"
  }
}

data class IntType(val numOfBits: Int) : Type() {
  override fun getNumBits(): Int {
    return numOfBits
  }

  override fun toString(): String {
    return "i$numOfBits"
  }
}

data class PointerType(val pointeeTy: Type?) : Type() {
  override fun getNumBits(): Int {
    return 32
  }

  override fun toString(): String {
    return "$pointeeTy*"
  }
}

/// In Mx*, all arrays are regarded as references.
data class ArrayType internal constructor(val containedType: Type, val numElements: Int) :
  Type() {
  internal constructor(typeMd: TypeMd, len: Int) : this(
    TypeFactory.getPtrToType(TypeMd(typeMd.cl, typeMd.dim - 1)),
    len
  )

  override fun getNumBits(): Int {
    return numElements * containedType.getNumBits()
  }
}

/// Basically, the definition of struct is the same as in the Kotlin.
/// The only exception is that the reference is by index instead of by name.
/// StructType provides a mapping from name to index, with its type clarified.
/// This is basically a part of class, because it only contains members.
/// As for methods, it should be obtained in other ways. The AST will handle this.
data class StructType internal constructor(val structName: String) : Type() {
  private val symbolList: MutableList<Pair<String, Type>> = mutableListOf()
  private var numOfBits = 0

  internal constructor(classMd: ClassMd) : this(classMd.className) {
    for ((memberName, memberValue) in classMd.classScope.members) {
      val memberType = TypeFactory.getPtrToType(memberValue)
      symbolList.add(Pair(memberName, memberType))
      numOfBits += memberType.getNumBits()
    }
  }

  override fun getNumBits(): Int {
    return numOfBits
  }

  override fun toString(): String {
    return "$structName = type { ${symbolList.joinToString(", ") { it.second.toString() }} }\n"
  }
}

/// It would be guaranteed that the funcName is always unchanged.
data class FuncType internal constructor(val funcName: String, val params: List<Type>, val result: Type) : Type() {
  internal constructor(funcMd: FuncMd) : this(
    funcMd.funcName,
    funcMd.paramInput.map { TypeFactory.getType(it) },
    TypeFactory.getType(funcMd.returnType!!)
  )

  override fun getNumBits(): Int {
    return 0
  }

  override fun toString(): String {
    return "(${params.joinToString(",") { it.toString() }}) -> $result"
  }
}

data class LabelType(val labelName: String) : Type() {
  override fun getNumBits(): Int {
    return 0
  }
}