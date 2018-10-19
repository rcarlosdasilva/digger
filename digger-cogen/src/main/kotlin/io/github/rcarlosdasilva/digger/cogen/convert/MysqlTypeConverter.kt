package io.github.rcarlosdasilva.digger.cogen.convert

import io.github.rcarlosdasilva.digger.cogen.JavaType
import io.github.rcarlosdasilva.digger.cogen.KotlinType

class MysqlTypeConverter : TypeConverter {
  override fun convert4Java(type: String): JavaType =
      with(type.toLowerCase()) {
        when {
          this.contains("char") || this.contains("text") -> JavaType.STRING
          this.contains("bigint") -> JavaType.LONG
          this.contains("tinyint(1)") -> JavaType.BOOLEAN
          this.contains("int") -> JavaType.INT
          this.contains("date") || this.contains("time") || this.contains("year") -> JavaType.DATE
          this.contains("text") -> JavaType.STRING
          this.contains("bit") -> JavaType.BOOLEAN
          this.contains("decimal") -> JavaType.BIG_DECIMAL
          this.contains("clob") -> JavaType.CLOB
          this.contains("blob") -> JavaType.BLOB
          this.contains("binary") -> JavaType.BYTE_ARRAY
          this.contains("float") -> JavaType.FLOAT
          this.contains("double") -> JavaType.DOUBLE
          this.contains("json") || this.contains("enum") -> JavaType.STRING
          else -> JavaType.STRING
        }
      }

  override fun convert4Kotlin(type: String): KotlinType =
      with(type.toLowerCase()) {
        when {
          this.contains("char") || this.contains("text") -> KotlinType.STRING
          this.contains("bigint") -> KotlinType.LONG
          this.contains("tinyint(1)") -> KotlinType.BOOLEAN
          this.contains("int") -> KotlinType.INT
          this.contains("date") || this.contains("time") || this.contains("year") -> KotlinType.DATE
          this.contains("text") -> KotlinType.STRING
          this.contains("bit") -> KotlinType.BOOLEAN
          this.contains("decimal") -> KotlinType.BIG_DECIMAL
          this.contains("clob") -> KotlinType.CLOB
          this.contains("blob") -> KotlinType.BLOB
          this.contains("binary") -> KotlinType.BYTE_ARRAY
          this.contains("float") -> KotlinType.FLOAT
          this.contains("double") -> KotlinType.DOUBLE
          this.contains("json") || this.contains("enum") -> KotlinType.STRING
          else -> KotlinType.STRING
        }
      }

}