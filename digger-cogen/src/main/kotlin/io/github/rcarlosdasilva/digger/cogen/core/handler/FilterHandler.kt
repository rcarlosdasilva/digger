package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenFilterException
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.core.Database
import io.github.rcarlosdasilva.digger.cogen.info.TableInfo
import io.github.rcarlosdasilva.digger.core.throwRuntimeIf
import mu.KotlinLogging

class FilterHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    holder.allTables.isEmpty().throwRuntimeIf { DiggerCogenFilterException("未能获取到任何表数据") }

    val database = holder.configuration.database!!
    val filteredTables = mutableListOf<TableInfo>()
    holder.allTables.forEach {
      if (hitTable(database, it)) filteredTables.add(it)
    }
    holder.filteredTables = filteredTables
  }

  private fun hitTable(database: Database, it: TableInfo): Boolean {
    // TODO 过略表
    return false
  }

}