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

  fun createLabel(): LabelType {
    return screen(LabelType()) as LabelType
  }

  fun createInt(numOfBits: Int): IntType {
    return screen(IntType(numOfBits)) as IntType
  }

  fun createNull(): PointerType {
    return createPtr(null)
  }

  fun createPtr(type: Type?): PointerType {
    return screen(PointerType(type)) as PointerType
  }

  fun createArray(type: Type, numElements: Int): ArrayType {
    return screen(ArrayType(type, numElements)) as ArrayType
  }

  fun createPtrToArray(typeMd: TypeMd): PointerType {
    var ptrToArray = createPtrToType(typeMd)
    for (i in 1..typeMd.dim) {
      ptrToArray = screen(PointerType(ptrToArray)) as PointerType
    }
    return ptrToArray
  }

  /// For primitive types, this function returns its exact type.
  /// For non-primitive types, like arrays and structs, this function returns a pointer.
  /// There is no exception control inside, make sure that all parameters are right.
  fun createPtrToType(typeMd: TypeMd): PointerType {
    return screen(
      when {
        typeMd.isVoid() -> PointerType(VoidType())
        typeMd.isInt() -> PointerType(IntType(32))
        typeMd.isBool() -> PointerType(IntType(1))
        typeMd.isString() -> PointerType(IntType(8)) // TODO: make sure that this is ok
        typeMd.isArray() -> createPtrToArray(typeMd)
        else -> PointerType(StructType(typeMd.cl))
      }
    ) as PointerType
  }

  fun createStruct(classMd: ClassMd): StructType {
    return screen(StructType(classMd)) as StructType
  }

  /// This function is for func type.
  /// Because there is no function pointer in Mx*, therefore it doesn't need to be distinguished.
  fun createFunc(funcMd: FuncMd): FuncType {
    return screen(FuncType(funcMd)) as FuncType
  }

  fun createType(typeMd: TypeMd): Type {
    return screen(
      when {
        typeMd.isVoid() -> VoidType()
        typeMd.isInt() -> IntType(32)
        typeMd.isBool() -> IntType(8)
        typeMd.isString() -> IntType(8) // TODO: make sure that this is ok
        typeMd.isArray() -> createPtrToArray(typeMd)
        else -> PointerType(StructType(typeMd.cl))
      }
    )
  }
}

/// All constructors inside type and its subclasses are forbidden.
abstract class Type {
  abstract fun getNumBits(): Int
}


class VoidType : Type() {
  override fun getNumBits(): Int {
    return 0
  }

  override fun toString(): String {
    return "void"
  }
}

class IntType(val numOfBits: Int) : Type() {
  override fun getNumBits(): Int {
    return numOfBits
  }

  override fun toString(): String {
    return "i$numOfBits"
  }
}

class PointerType(val pointeeTy: Type?) : Type() {
  override fun getNumBits(): Int {
    return 32
  }

  override fun toString(): String {
    return "$pointeeTy*"
  }
}

/// In Mx*, all arrays are regarded as references.
class ArrayType internal constructor(val containedType: Type, val numElements: Int) :
  Type() {
  internal constructor(typeMd: TypeMd, len: Int) : this(
    TypeFactory.createPtrToType(TypeMd(typeMd.cl, typeMd.dim - 1)),
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
class StructType internal constructor(val structName: String) : Type() {
  private val symbolList: MutableList<Pair<String, Type>> = mutableListOf()
  private var numOfBits = 0

  internal constructor(classMd: ClassMd) : this(classMd.className) {
    for ((memberName, memberValue) in classMd.classScope.members) {
      val memberType = TypeFactory.createPtrToType(memberValue)
      symbolList.add(Pair(memberName, memberType))
      numOfBits += memberType.getNumBits()
    }
  }

  override fun getNumBits(): Int {
    return numOfBits
  }

//  fun addSymbol(symbolName: String, symbolType: Type) {
//    symbolList.add(Pair(symbolName, symbolType))
//  }
}

class FuncType internal constructor(val funcName: String, val returnType: Type) : Type() {
  internal constructor(funcMd: FuncMd) : this(funcMd.funcName, TypeFactory.createPtrToType(funcMd.returnType!!))

  override fun getNumBits(): Int {
    return 0
  }
}

class LabelType : Type() {
  override fun getNumBits(): Int {
    return 0
  }
}