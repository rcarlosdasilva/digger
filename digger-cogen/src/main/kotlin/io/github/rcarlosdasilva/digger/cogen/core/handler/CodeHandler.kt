package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.NamingStyle
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.info.ClassInfo
import io.github.rcarlosdasilva.digger.cogen.info.ColumnInfo
import io.github.rcarlosdasilva.digger.cogen.info.PackageInfo
import io.github.rcarlosdasilva.digger.cogen.info.TableInfo
import io.github.rcarlosdasilva.digger.core.StringHelper
import mu.KotlinLogging

class CodeHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  private lateinit var packages: List<PackageInfo>

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    packages = holder.packages

    holder.database.filteredTables.forEach(this::codeFromTable)
  }

  private fun codeFromTable(tableInfo: TableInfo) {
    packages.forEach { codeForPackage(tableInfo, it) }
  }

  private fun codeForPackage(tableInfo: TableInfo, packageInfo: PackageInfo) {
    val tableConfiguration = tableInfo.config
    val codeConfiguration = packageInfo.config

    val tableName = transformName(tableInfo.name, tableConfiguration.namingStrategies)
    val className = naming(tableName, tableConfiguration.tableNaming).apply {
      "${codeConfiguration.prefix}$this${codeConfiguration.suffix}"
    }

    val classInfo = ClassInfo(className)
    classInfo.extension = codeConfiguration.lang.extension
    classInfo.path = "${packageInfo.path}/$className${classInfo.extension}"
    classInfo.comment = tableInfo.comment
    classInfo.extend = codeConfiguration.extend?.name
    classInfo.implements = codeConfiguration.implements?.map { it.name }
    packageInfo.classes.add(classInfo)

    tableInfo.filteredColumns.forEach { codeForClassField(tableInfo, it, packageInfo, classInfo) }
  }

  private fun codeForClassField(tableInfo: TableInfo, columnInfo: ColumnInfo, packageInfo: PackageInfo, classInfo: ClassInfo) {

  }

  private fun transformName(name: String, namingStrategies: Map<String, String>?): String =
      namingStrategies?.let { it ->
        var finalName = name
        for (strategy in it) {
          val findName = findName(name, strategy.key)
          findName?.run {
            finalName = completionName(this, strategy.value)
          }
          if (findName != null) break
        }
        finalName
      } ?: name

  private fun naming(name: String, namingStyle: NamingStyle): String =
      when (namingStyle) {
        NamingStyle.LOWER_CAMEL -> StringHelper.camelCase(name)
        NamingStyle.UPPER_CAMEL -> StringHelper.studlyCase(name)
        NamingStyle.SNAKE_CASE -> StringHelper.snakeCase(name)
        NamingStyle.KEBAB_CASE -> StringHelper.kebabCase(name)
      }

  private fun findName(name: String, pattern: String): String? {
    val isMatching = pattern.contains(CogenHandler.NAME_PATTERN_CHAR)

    return if (isMatching) {
      val regex = pattern.replace(CogenHandler.NAME_PATTERN_CHAR, CogenHandler.NAME_PATTERN_FIND).toRegex()
      val found = regex.find(name)
      found?.groups?.get(1)?.value
    } else name
  }

  private fun completionName(name: String, pattern: String): String {
    return ""
  }

}
