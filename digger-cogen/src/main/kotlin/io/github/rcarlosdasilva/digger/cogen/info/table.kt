package io.github.rcarlosdasilva.digger.cogen.info

import io.github.rcarlosdasilva.digger.cogen.MySqlType

data class TableInfo(val name: String) {
  var comment: String? = null
  val columns = mutableListOf<ColumnInfo>()
}

data class ColumnInfo(val name: String) {
  var mySqlType: MySqlType? = null
  var comment: String? = null
  var defaultValue: Any? = null
  var isPrimaryKey = false
}