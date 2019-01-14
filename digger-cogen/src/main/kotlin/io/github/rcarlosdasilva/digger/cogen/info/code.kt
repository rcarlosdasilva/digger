package io.github.rcarlosdasilva.digger.cogen.info

import io.github.rcarlosdasilva.digger.cogen.JavaType
import io.github.rcarlosdasilva.digger.cogen.KotlinType
import io.github.rcarlosdasilva.digger.cogen.core.Code

data class PackageInfo(val name: String, val path: String) {
  lateinit var config: Code
  val classes = mutableListOf<ClassInfo>()
}

data class ClassInfo(val name: String) {
  var comment: String? = null
  var extension = ""
  var extend: String? = null
  var implements: List<String>? = null
  val imports: Set<String>? = null
  val fields = mutableListOf<FieldInfo>()
  var path = ""
}

data class FieldInfo(val name: String) {
  var comment: String? = null
  var javaType: JavaType? = null
  var kotlinType: KotlinType? = null
}