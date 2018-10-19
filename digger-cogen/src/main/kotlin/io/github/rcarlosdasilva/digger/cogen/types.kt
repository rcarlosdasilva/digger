package io.github.rcarlosdasilva.digger.cogen

/**
 * Java代码类型
 * @property primitive String? 原生类型名
 * @property className String 类名
 * @property fullQualifiedName String? 类全限定名
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class JavaType(val primitive: String?,
                    val className: String,
                    val fullQualifiedName: String?) {
  INT("int", "Integer", null),
  SHORT("short", "Short", null),
  LONG("long", "Long", null),
  BOOLEAN("boolean", "Boolean", null),
  FLOAT("float", "Float", null),
  DOUBLE("double", "Double", null),
  CHAR("char", "Character", null),
  BYTE("byte", "Byte", null),
  BYTE_ARRAY("byte[]", "Byte[]", null),
  STRING(null, "String", null),
  OBJECT(null, "Object", null),

  DATE(null, "Date", "java.util.Date"),
  TIME(null, "Time", "java.sql.Time"),
  TIMESTAMP(null, "Timestamp", "java.sql.Timestamp"),
  LOCAL_DATE(null, "LocalDate", "java.time.LocalDate"),
  LOCAL_TIME(null, "LocalTime", "java.time.LocalTime"),
  LOCAL_DATE_TIME(null, "LocalDateTime", "java.time.LocalDateTime"),
  DATE_TIME(null, "DateTime", "org.joda.time.DateTime"),

  BLOB(null, "Blob", "java.sql.Blob"),
  CLOB(null, "Clob", "java.sql.Clob"),
  BIG_DECIMAL(null, "BigDecimal", "java.math.BigDecimal"),
  BIG_INTEGER(null, "BigInteger", "java.math.BigInteger")
}

/**
 * Kotlin代码类型
 * @property className String 类名
 * @property fullQualifiedName String? 类全限定名
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class KotlinType(val className: String,
                      val fullQualifiedName: String?) {
  INT("Int", null),
  SHORT("Short", null),
  LONG("Long", null),
  BOOLEAN("Boolean", null),
  FLOAT("Float", null),
  DOUBLE("Double", null),
  CHAR("Char", null),
  BYTE("Byte", null),
  BYTE_ARRAY("ByteArray", null),
  STRING("String", null),
  ANY("Any", null),

  DATE("Date", "java.util.Date"),
  TIME("Time", "java.sql.Time"),
  TIMESTAMP("Timestamp", "java.sql.Timestamp"),
  LOCAL_DATE("LocalDate", "java.time.LocalDate"),
  LOCAL_TIME("LocalTime", "java.time.LocalTime"),
  LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
  DATE_TIME("DateTime", "org.joda.time.DateTime"),

  BLOB("Blob", "java.sql.Blob"),
  CLOB("Clob", "java.sql.Clob"),
  BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),
  BIG_INTEGER("BigInteger", "java.math.BigInteger")
}

/**
 * MySQL字段类型
 */
enum class MySqlType {
  BIT,
  TINYINT,
  BOOL,
  BOOLEAN,
  SMALLINT,
  MEDIUMINT,
  INT,
  INTEGER,
  BIGINT,
  DECIMAL,
  DEC,
  FLOAT,
  DOUBLE,
  DOUBLE_PRECISION,

  DATE,
  DATETIME,
  TIMESTAMP,
  TIME,
  YEAR,

  CHAR,
  VARCHAR,
  BINARY,
  VARBINARY,
  TINYBLOB,
  TINYTEXT,
  BLOB,
  TEXT,
  MEDIUMBLOB,
  MEDIUMTEXT,
  LONGBLOB,
  LONGTEXT,
  ENUM,
  SET
}