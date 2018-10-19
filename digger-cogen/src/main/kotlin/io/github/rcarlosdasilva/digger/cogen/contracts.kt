package io.github.rcarlosdasilva.digger.cogen

/**
 * 支持的数据库
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class SupportDb { MYSQL, MSSQL, ORACLE }

/**
 * 命名风格
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class NameStyle {
  /**
   * 小写驼峰
   */
  LOWER_CAMEL,
  /**
   * 大写驼峰
   */
  UPPER_CAMEL,
  /**
   * 下划线
   */
  SNAKE_CASE,
  /**
   *  中划线
   */
  STRIKE_CASE
}

/**
 * 支持的生成代码语言
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class Language {
  JAVA, KOTLIN
}