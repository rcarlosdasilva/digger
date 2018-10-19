package io.github.rcarlosdasilva.digger.cogen.info

import io.github.rcarlosdasilva.digger.cogen.JavaType
import io.github.rcarlosdasilva.digger.cogen.KotlinType

data class PackageInfo(val name: String) {
  val classes = mutableListOf<ClassInfo>()
}

data class ClassInfo(val name: String) {
  var comment: String? = null
  var extend: String? = null
  var implements: List<String>? = null
  val imports: List<String>? = null
  val fields = mutableListOf<FieldInfo>()
}

data class FieldInfo(val name: String) {
  var comment: String? = null
  var javaType: JavaType? = null
  var kotlinType: KotlinType? = null

  var isGetter = true
  var isSetter = true
}