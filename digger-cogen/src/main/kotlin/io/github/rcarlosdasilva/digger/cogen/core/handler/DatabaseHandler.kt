package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenDatabaseException
import io.github.rcarlosdasilva.digger.cogen.Sql
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.info.ColumnInfo
import io.github.rcarlosdasilva.digger.cogen.info.TableInfo
import jdk.nashorn.internal.objects.NativeArray.forEach
import mu.KotlinLogging
import java.sql.Connection
import java.sql.DriverManager

class DatabaseHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    holder.configuration.database!!.apply {
      val connection = connection(db.driver, url, username, password)

      connection.use {
        readTable(holder, it, db.sql)
        readColumn(holder, it, db.sql)
      }
    }
  }

  private fun readTable(holder: Cogen.CogenRuntimeHolder, conn: Connection, sql: Sql) {
    conn.prepareStatement(sql.tableComments()).use { ps ->
      ps.executeQuery().use { results ->
        while (results.next()) {
          val name = results.getString(sql.tableName())
          val comment = results.getString(sql.tableComment())

          val ti = TableInfo(name)
          ti.comment = comment

          holder.database.allTables.add(ti)
        }
      }
    }
  }

  private fun readColumn(holder: Cogen.CogenRuntimeHolder, conn: Connection, sql: Sql) {
    val database = holder.configuration.database!!
    holder.database.allTables.forEach { table ->
      conn.prepareStatement(sql.columns(table.name)).use { ps ->
        ps.executeQuery().use { results ->
          while (results.next()) {
            val name = results.getString(sql.columnName())
            val comment = results.getString(sql.columnComment())
            val type = results.getString(sql.columnType())
//            val key = results.getString(sql.columnKey())
            val defaultValue = results.getString(sql.columnDefault())

            val ci = ColumnInfo(name)
            ci.comment = comment
            ci.dbType = database.dbTypeConverter.convert4Sql(type)
            ci.defaultValue = defaultValue

            val isId = database.idName == name
            if (isId && !database.isIgnoreId) {
              ci.isPrimaryKey = true
            } else {
              continue
            }

            table.columns.add(ci)
          }
        }
      }
    }
  }

  private fun connection(driver: String, url: String, username: String, password: String): Connection =
      driver.let {
        try {
          Class.forName(it)
          DriverManager.getConnection(url, username, password)
        } catch (ex: Exception) {
          throw DiggerCogenDatabaseException(ex)
        }
      }

}
