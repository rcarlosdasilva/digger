package io.github.rcarlosdasilva.digger.cogen.convert

import io.github.rcarlosdasilva.digger.cogen.*
import io.github.rcarlosdasilva.digger.core.StringHelper

class MySqlTypeConverter : TypeConverter {
  override fun convert4Java(dbType: DbTypeWrapper): JavaType =
      when (dbType.mySqlType) {
        MySqlType.CHAR, MySqlType.VARCHAR, MySqlType.TEXT, MySqlType.TINYTEXT, MySqlType.MEDIUMTEXT, MySqlType.LONGBLOB -> JavaType.STRING
        MySqlType.DATE, MySqlType.DATETIME, MySqlType.TIME, MySqlType.YEAR, MySqlType.TIMESTAMP -> JavaType.DATE
        MySqlType.BIT -> JavaType.BOOLEAN
        MySqlType.TINYINT -> if (dbType.length == 1) JavaType.BOOLEAN else JavaType.INT
        MySqlType.INT, MySqlType.INTEGER -> JavaType.INT
        MySqlType.BIGINT -> JavaType.LONG
        MySqlType.FLOAT -> JavaType.FLOAT
        MySqlType.DOUBLE, MySqlType.DOUBLE_PRECISION -> JavaType.DOUBLE
        MySqlType.DECIMAL -> JavaType.BIG_DECIMAL
        MySqlType.BINARY -> JavaType.BYTE_ARRAY
        MySqlType.BLOB -> JavaType.BLOB
        MySqlType.ENUM -> JavaType.STRING
        else -> throw DiggerCogenRuntimeException("暂不支持的Java类型转换")
      }

  override fun convert4Kotlin(dbType: DbTypeWrapper): KotlinType =
      when (dbType.mySqlType) {
        MySqlType.CHAR, MySqlType.VARCHAR, MySqlType.TEXT, MySqlType.TINYTEXT, MySqlType.MEDIUMTEXT, MySqlType.LONGBLOB -> KotlinType.STRING
        MySqlType.DATE, MySqlType.DATETIME, MySqlType.TIME, MySqlType.YEAR, MySqlType.TIMESTAMP -> KotlinType.DATE
        MySqlType.BIT -> KotlinType.BOOLEAN
        MySqlType.TINYINT -> if (dbType.length == 1) KotlinType.BOOLEAN else KotlinType.INT
        MySqlType.INT, MySqlType.INTEGER -> KotlinType.INT
        MySqlType.BIGINT -> KotlinType.LONG
        MySqlType.FLOAT -> KotlinType.FLOAT
        MySqlType.DOUBLE, MySqlType.DOUBLE_PRECISION -> KotlinType.DOUBLE
        MySqlType.DECIMAL -> KotlinType.BIG_DECIMAL
        MySqlType.BINARY -> KotlinType.BYTE_ARRAY
        MySqlType.BLOB -> KotlinType.BLOB
        MySqlType.ENUM -> KotlinType.STRING
        else -> throw DiggerCogenRuntimeException("暂不支持的Kotlin类型转换")
      }

  override fun convert4Sql(type: String): DbTypeWrapper =
      type.toLowerCase().let {
        DbTypeWrapper(SupportDb.MYSQL).apply {
          val between = StringHelper.between(it, "(", ")")
          if (between.isNotEmpty()) {
            length = between[0].toInt()
          }

          mySqlType = when {
            it.startsWith("bit", true) -> MySqlType.BIT
            it.startsWith("tinyint", true) -> MySqlType.TINYINT
            it.startsWith("bool", true) -> MySqlType.BOOL
            it.startsWith("boolean", true) -> MySqlType.BOOLEAN
            it.startsWith("smallint", true) -> MySqlType.SMALLINT
            it.startsWith("mediumint", true) -> MySqlType.MEDIUMINT
            it.startsWith("int", true) -> MySqlType.INT
            it.startsWith("integer", true) -> MySqlType.INTEGER
            it.startsWith("bigint", true) -> MySqlType.BIGINT
            it.startsWith("decimal", true) -> MySqlType.DECIMAL
            it.startsWith("dec", true) -> MySqlType.DEC
            it.startsWith("float", true) -> MySqlType.FLOAT
            it.startsWith("double", true) -> MySqlType.DOUBLE
            it.startsWith("double_precision", true) -> MySqlType.DOUBLE_PRECISION

            it.startsWith("date", true) -> MySqlType.DATE
            it.startsWith("datetime", true) -> MySqlType.DATETIME
            it.startsWith("timestamp", true) -> MySqlType.TIMESTAMP
            it.startsWith("time", true) -> MySqlType.TIME
            it.startsWith("year", true) -> MySqlType.YEAR

            it.startsWith("char", true) -> MySqlType.CHAR
            it.startsWith("varchar", true) -> MySqlType.VARCHAR
            it.startsWith("binary", true) -> MySqlType.BINARY
            it.startsWith("varbinary", true) -> MySqlType.VARBINARY
            it.startsWith("tinyblob", true) -> MySqlType.TINYBLOB
            it.startsWith("tinytext", true) -> MySqlType.TINYTEXT
            it.startsWith("blob", true) -> MySqlType.BLOB
            it.startsWith("text", true) -> MySqlType.TEXT
            it.startsWith("mediumblob", true) -> MySqlType.MEDIUMBLOB
            it.startsWith("mediumtext", true) -> MySqlType.MEDIUMTEXT
            it.startsWith("longblob", true) -> MySqlType.LONGBLOB
            it.startsWith("longtext", true) -> MySqlType.LONGTEXT
            it.startsWith("enum", true) -> MySqlType.ENUM
            it.startsWith("set", true) -> MySqlType.SET
            else -> throw DiggerCogenRuntimeException("暂不支持的Mysql类型")
          }
        }
      }

}