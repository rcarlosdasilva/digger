package io.github.rcarlosdasilva.digger.cogen.info

import io.github.rcarlosdasilva.digger.cogen.DbTypeWrapper

data class TableInfo(val name: String) {
  var comment: String? = null
  val columns = mutableListOf<ColumnInfo>()
}

data class ColumnInfo(val name: String) {
  var dbType: DbTypeWrapper? = null
  var comment: String? = null
  var defaultValue: String? = null
  var isPrimaryKey = false
}