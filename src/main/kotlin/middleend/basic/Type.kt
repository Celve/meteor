package middleend.basic

import frontend.metadata.ClassMd
import frontend.metadata.FuncMd
import frontend.metadata.TypeMd

const val pointerNumBits = 32

/**
 * TypeFactory is the only way to create a type, and there is no other legal way to create a type.
 * Type manufactured by TypeFactory is unique, as long as two types are semantically the same, they are the same object.
 * This obeys to the LLVM rule.
 */
object TypeFactory {
  private val existedType: HashMap<Type, Type> = hashMapOf()

  /**
   * Key function to keep the uniqueness of types.
   * Every type directly created by constructor of type should be passed to this function.
   */
  private fun screen(type: Type): Type {
    if (existedType.containsKey(type)) {
      return existedType[type]!!
    }
    existedType[type] = type
    return type
  }

  fun getIntType(numOfBits: Int): IntType {
    return screen(IntType(numOfBits)) as IntType
  }

  fun getLabelType(labelName: String): LabelType {
    return screen(LabelType(labelName)) as LabelType
  }

  fun getNullType(): PointerType {
    return getPtrType(null)
  }

  fun getPtrType(type: Type?): PointerType {
    return screen(PointerType(type)) as PointerType
  }

  fun getPtrType(classMd: ClassMd): PointerType {
    return getPtrType(getStructType(classMd))
  }

  fun getVoidType(): VoidType {
    return screen(VoidType()) as VoidType
  }

  fun getArrayType(type: Type, numElements: Int): ArrayType {
    return screen(ArrayType(type, numElements)) as ArrayType
  }

  fun getFuncType(funcMd: FuncMd): FuncType {
    return screen(FuncType(funcMd)) as FuncType
  }

  fun getFuncType(funcMd: FuncMd, classMd: ClassMd): FuncType {
    return screen(FuncType(funcMd, classMd)) as FuncType
  }

  fun getStructType(classMd: ClassMd): StructType {
    val structType = screen(StructType("class.${classMd.className}")) as StructType
    if (!structType.registered) {
      structType.registered = true
      structType.init(classMd)
    }
    return structType
  }

  /**
   * This function is used to construct a pointer to a multidimensional array.
   * @return is a pointer of a pointer of a pointer of ...
   */
  fun getNestedPtr(typeMd: TypeMd): PointerType {
    return if (typeMd.dim == 1) {
      getPtrType(getAnyType(TypeMd(typeMd.cl, 0)))
    } else {
      getPtrType(getNestedPtr(TypeMd(typeMd.cl, typeMd.dim - 1)))
    }
  }

  /**
   * For primitive types, this function returns its exact type.
   * For non-primitive types, like arrays and structs, this function returns a pointer.
   * There is no exception control inside, make sure that all parameters are right.
   */
  fun getPtrType(typeMd: TypeMd): PointerType {
    return when {
      typeMd.isVoid() -> getPtrType(getVoidType()) // FIXME: ???
      typeMd.isInt() -> getPtrType(getIntType(32))
      typeMd.isBool() -> getPtrType(getIntType(8))
      typeMd.isString() -> getPtrType(getIntType(8)) // TODO: make sure that this is ok
      typeMd.isArray() -> getNestedPtr(typeMd)
      else -> getPtrType(getStructType(typeMd.cl))
    }
  }

  fun getPtrType(type: String): PointerType {
    return when (type) {
      "void" -> getPtrType(getVoidType())
      "int" -> getPtrType(getIntType(32))
      "bool" -> getPtrType(getIntType(8))
      "string" -> getPtrType(getIntType(8))
      else -> TODO("Not yet implemented")
    }
  }

  /**
   * This function get the exact type of primitive type, and get the pointer type of non-primitive type.
   */
  fun getAnyType(typeMd: TypeMd): Type {
    return when {
      typeMd.isVoid() -> getVoidType()
      typeMd.isInt() -> getIntType(32)
      typeMd.isBool() -> getIntType(8)
      typeMd.isString() -> getPtrType(getIntType(8)) // TODO: make sure that this is ok
      typeMd.isArray() -> getNestedPtr(typeMd)
      else -> getPtrType(getStructType(typeMd.cl))
    }
  }

  /**
   * This function get the exact type of primitive type and struct type.
   * Please don't give it string and array, which it cannot handle.
   */
  fun getBasicType(typeMd: TypeMd): Type {
    return screen(
      when {
        typeMd.isVoid() -> VoidType()
        typeMd.isInt() -> IntType(32)
        typeMd.isBool() -> IntType(8)
        else -> getStructType(typeMd.cl)
      }
    )
  }
}

abstract class Type {
  abstract fun getNumBits(): Int

  fun getAlign(): Int {
    return if (getNumBits() == 1) 1 else getNumBits() / 8
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

/**
 * IntType is the type of integer.
 * For convenience, the bool type is also regarded as integer type.
 * @param numOfBits stands for the number of bits of this integer type.
 */
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
    return pointerNumBits
  }

  override fun toString(): String {
    return "$pointeeTy*"
  }
}

/**
 * For now, the array type only used for string.
 */
data class ArrayType(val containedType: Type, val numElements: Int) : Type() {
  override fun getNumBits(): Int {
    return numElements * containedType.getNumBits()
  }

  override fun toString(): String {
    return "[$numElements x $containedType]"
  }
}

/**
 * Basically, the definition of struct is the same as in the Kotlin.
 * The only exception is that the reference is by index instead of by name.
 * StructType provides a mapping from name to index, with its type clarified.
 * This is basically a part of class, because it only contains members.
 * As for methods, it should be obtained in other ways. The AST will handle this.
 */
class StructType(val structName: String) : Type() {
  val symbolList: MutableList<Pair<String, Type>> = mutableListOf()
  private var numOfBits = 0
  var registered = false

  fun getRealStructName(): String {
    return structName.substring(6)
  }

  fun init(classMd: ClassMd) {
    for ((memberName, memberValue) in classMd.classScope.members) {
      val memberType = TypeFactory.getAnyType(memberValue)
      symbolList.add(Pair(memberName, memberType))
      numOfBits += memberType.getNumBits()
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is StructType) {
      other.structName == structName
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return structName.hashCode()
  }

  fun getIndex(symbol: String): Int {
    for (index in 0 until symbolList.size) {
      if (symbolList[index].first == symbol) {
        return index
      }
    }
    throw Exception("member is not found")
  }

  override fun getNumBits(): Int {
    return numOfBits
  }

  override fun toString(): String {
    return "%$structName"
  }

  fun toDeclaration(): String {
    return "%$structName = type { ${symbolList.joinToString(", ") { it.second.toString() }} }\n"
  }

}

data class FuncType(val funcName: String, val argList: List<Type>, val result: Type) : Type() {
  constructor(funcMd: FuncMd) : this(
    funcMd.funcName,
    funcMd.argList.map { TypeFactory.getAnyType(it.second) },
    TypeFactory.getAnyType(funcMd.returnType!!)
  )

  constructor(funcMd: FuncMd, classMd: ClassMd) : this(
    "${classMd.className}.${funcMd.funcName}",
    listOf(TypeFactory.getPtrType(classMd)) + funcMd.argList.map { TypeFactory.getAnyType(it.second) },
    TypeFactory.getAnyType(funcMd.returnType!!)
  )

  override fun getNumBits(): Int {
    return 0
  }

  override fun toString(): String {
    return "(${argList.joinToString(",") { it.toString() }}) -> $result"
  }
}

/**
 * LabelType is actually the type of BasicBlock.
 */
data class LabelType(val labelName: String) : Type() {
  override fun getNumBits(): Int {
    return 0
  }
}