package io.github.rcarlosdasilva.digger.cogen.info

import io.github.rcarlosdasilva.digger.cogen.DbTypeWrapper
import io.github.rcarlosdasilva.digger.cogen.core.Table

data class DatabaseInfo(val schema: String) {
  val allTables = mutableListOf<TableInfo>()
  lateinit var filteredTables: List<TableInfo>
}

data class TableInfo(val name: String) {
  var comment: String? = null
  lateinit var config: Table
  val columns = mutableListOf<ColumnInfo>()
  lateinit var filteredColumns: List<ColumnInfo>
}

data class ColumnInfo(val name: String) {
  var dbType: DbTypeWrapper? = null
  var comment: String? = null
  var defaultValue: String? = null
  var isPrimaryKey = false
}