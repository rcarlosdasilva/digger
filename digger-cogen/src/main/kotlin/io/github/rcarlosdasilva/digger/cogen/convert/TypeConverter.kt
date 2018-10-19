package io.github.rcarlosdasilva.digger.cogen.convert

import io.github.rcarlosdasilva.digger.cogen.JavaType
import io.github.rcarlosdasilva.digger.cogen.KotlinType

/**
 * 数据库字段类型转换器接口
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface TypeConverter {
  /**
   * 数据库字段类型转换能为Java类型
   * @param type String 字段类型
   * @return JavaType [JavaType]
   */
  fun convert4Java(type: String): JavaType

  /**
   * 数据库字段类型转换能为Kotlin类型
   * @param type String 字段类型
   * @return KotlinType [KotlinType]
   */
  fun convert4Kotlin(type: String): KotlinType
}