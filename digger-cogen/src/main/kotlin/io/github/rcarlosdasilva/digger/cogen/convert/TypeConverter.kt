package io.github.rcarlosdasilva.digger.cogen.convert

import io.github.rcarlosdasilva.digger.cogen.DbTypeWrapper
import io.github.rcarlosdasilva.digger.cogen.JavaType
import io.github.rcarlosdasilva.digger.cogen.KotlinType

/**
 * 语言类型转换器接口
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface TypeConverter {
  /**
   * 数据库字段类型转换能为Java类型
   * @param dbType DbTypWrapper 字段类型
   * @return JavaType [JavaType]
   */
  fun convert4Java(dbType: DbTypeWrapper): JavaType

  /**
   * 数据库字段类型转换能为Kotlin类型
   * @param dbType DbTypWrapper 字段类型
   * @return KotlinType [KotlinType]
   */
  fun convert4Kotlin(dbType: DbTypeWrapper): KotlinType

  /**
   * 数据库字段类型转换
   * @param type String 字段类型
   * @return DbTypWrapper [DbTypeWrapper]
   */
  fun convert4Sql(type: String): DbTypeWrapper
}