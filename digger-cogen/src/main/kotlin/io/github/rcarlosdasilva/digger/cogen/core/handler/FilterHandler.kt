package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenFilterException
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.core.Database
import io.github.rcarlosdasilva.digger.cogen.core.handler.CogenHandler.Companion.NAME_PATTERN_CHAR
import io.github.rcarlosdasilva.digger.cogen.core.handler.CogenHandler.Companion.NAME_PATTERN_MATCHING
import io.github.rcarlosdasilva.digger.cogen.info.ColumnInfo
import io.github.rcarlosdasilva.digger.cogen.info.TableInfo
import io.github.rcarlosdasilva.digger.core.throwRuntimeIf
import mu.KotlinLogging

class FilterHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  private lateinit var database: Database

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    database = holder.configuration.database!!

    holder.database.allTables.isEmpty().throwRuntimeIf { DiggerCogenFilterException("未能获取到任何表数据") }

    holder.database.filteredTables = holder.database.allTables.asSequence().filter(this::hitTable).toList()
    holder.database.filteredTables.asSequence().forEach(this::hitColumns)
  }

  private fun hitTable(tableInfo: TableInfo): Boolean {
    val tablesConfiguration = database.tables
    val tableName = tableInfo.name

    val hits = tablesConfiguration.filter { hitName(tableName, it.name) }

    if (hits.isEmpty()) return false
    (hits.size > 1).throwRuntimeIf { DiggerCogenFilterException("根据表名({})，找到多条相似的表配置", tableName) }

    hits.first().apply {
      if (excludes?.isNotEmpty() == true) {
        excludes!!.find { hitName(tableName, name) }?.also { return false }
      } else if (includes?.isNotEmpty() == true) {
        includes!!.find { hitName(tableName, name) } ?: return false
      }

      tableInfo.config = this
    }

    return true
  }

  private fun hitColumns(tableInfo: TableInfo) {
    tableInfo.columns.isEmpty().throwRuntimeIf { DiggerCogenFilterException("未能获取到表({})中任何字段数据", tableInfo.name) }

    tableInfo.filteredColumns = tableInfo.columns.asSequence().filter { hitColumn(tableInfo, it) }.toList()
  }

  private fun hitColumn(tableInfo: TableInfo, columnInfo: ColumnInfo): Boolean {
    val columnName = columnInfo.name

    if (columnInfo.isPrimaryKey && database.isIgnoreId) return false
    if (database.ignoreColumns?.isNotEmpty() == true) {
      database.ignoreColumns!!.find { hitName(columnName, it) }?.also { return false }
    }
    if (tableInfo.config.ignoreColumns?.isNotEmpty() == true) {
      tableInfo.config.ignoreColumns!!.find { hitName(columnName, it) }?.also { return false }
    }
    return true
  }

  private fun hitName(name: String, pattern: String): Boolean {
    val isMatching = pattern.contains(NAME_PATTERN_CHAR)

    return if (isMatching) {
      val regex = pattern.replace(NAME_PATTERN_CHAR, NAME_PATTERN_MATCHING).toRegex()
      name.matches(regex)
    } else name == pattern
  }

}