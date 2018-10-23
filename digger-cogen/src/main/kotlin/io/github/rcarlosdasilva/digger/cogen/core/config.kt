package io.github.rcarlosdasilva.digger.cogen.core

import io.github.rcarlosdasilva.digger.cogen.Language
import io.github.rcarlosdasilva.digger.cogen.SupportDb
import io.github.rcarlosdasilva.digger.cogen.convert.MySqlTypeConverter

/**
 * 主配置
 * @property templatesDir String 模板目录
 * @property outputDir String 输出目录
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
data class Configuration(
    val templatesDir: String,
    val outputDir: String
) {

  /**
   * 生成代码语言
   */
  var lang = Language.JAVA
  /**
   * 生成类注释中作者
   */
  var authorName: String? = null
  /**
   * 生成类注释中作者邮件地址
   */
  var authorEmail: String? = null
  /**
   * 类生成时间
   */
  var isShowTime: Boolean = false
  /**
   * 版本信息
   */
  var version: String? = null
  /**
   * 是否覆盖已存在文件
   */
  var isCoverage = true
  /**
   * 执行完打开文件夹
   */
  var isOpenExplorer = false
  /**
   * 附加自定义参数，应用到模板中
   */
  val extras: MutableMap<String, String> by lazy { mutableMapOf<String, String>() }
  /**
   * 数据库配置
   */
  var database: Database? = null
  /**
   * 生成代码配置
   */
  val codes = mutableListOf<Code>()
}

/**
 * 数据库配置
 * @property db SupportDb 目标数据库
 * @property url String 数据库连接
 * @property username String 用户名
 * @property password String 密码
 */
data class Database(
    val db: SupportDb,
    val url: String,
    val username: String,
    val password: String
) {
  /**
   * 数据库与java类型转换，参数为true，使用原始类型
   */
  var dbTypeConverter = MySqlTypeConverter()
  /**
   * 表中id的字段名
   */
  var idName = "id"
  /**
   * 忽略ID，不生成id字段
   */
  var isIgnoreId = true
  /**
   * 表配置
   */
  val tables = mutableListOf<Table>()
  /**
   * 全局表中需要忽略掉的字段，字段名可以是全名，也可以是 * 号匹配，例如 flag_* 可匹配 flag_deleted 和 flag_disabled
   */
  var ignoreColumns: List<String>? = null
}

/**
 * 表配置
 * @property name String 准确表名，或表名前缀
 * @property isPrefix Boolean 是否匹配值，默认false
 */
data class Table @JvmOverloads constructor(val name: String, val isPrefix: Boolean = false) {
  /**
   * 仅在[isPrefix]=true时有效，在指定的表匹配范围内，排除哪些表。指定表名可以是表全名，也可以是不包含匹配部分的表名
   *
   * 例如：表sys_user、sys_role，excludes加入"user"或"sys_user"字符串，则会忽略掉sys_user表的代码生成
   */
  var excludes: List<String>? = null
  /**
   * 本表中需要忽略掉的字段，字段名可以是全名，也可以是 * 号匹配，例如 flag_* 可匹配 flag_deleted 和 flag_disabled
   */
  var ignoreColumns: List<String>? = null
}

/**
 * 代码配置
 * @property template String 模板文件名
 */
data class Code constructor(val template: String) {
  /**
   * 模块，用于项目有子模块的情况
   */
  var module: String = ""
  /**
   * 包名
   */
  var packageName = ""
  /**
   * 包下文件使用语言
   */
  var lang: Language? = null
  /**
   * 生成文件前缀
   */
  var prefix: String = ""
  /**
   * 生成文件后缀
   */
  var suffix: String = ""
  /**
   * 文件继承父类
   */
  var extend: Class<*>? = null
  /**
   * 文件实现的接口
   */
  var implements: List<Class<*>>? = null
  /**
   * 生成Getter方法
   */
  var isGetter = false
  /**
   * 生成Setter方法
   */
  var isSetter = false
}