package io.github.rcarlosdasilva.digger.cogen.core.handler

import com.sun.jndi.toolkit.url.Uri
import io.github.rcarlosdasilva.digger.cogen.DiggerCogenPrepareException
import io.github.rcarlosdasilva.digger.cogen.DiggerCogenRuntimeException
import io.github.rcarlosdasilva.digger.cogen.Language
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.info.DatabaseInfo
import io.github.rcarlosdasilva.digger.core.throwRuntimeIf
import mu.KotlinLogging
import java.net.URI
import java.net.URL

class PrepareHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    logger.info("[config] - 翻牌子ing")

    holder.configuration.apply {
      database ?: throw DiggerCogenPrepareException("没有数据库配置")
      database!!.apply {
        (tables.isEmpty()).throwRuntimeIf { DiggerCogenPrepareException("未指定数据库的表配置") }
        (idName.isBlank()).throwRuntimeIf { DiggerCogenPrepareException("id字段名称不能为空") }
      }

      (codes.isEmpty()).throwRuntimeIf { DiggerCogenRuntimeException("没有代码生成配置") }

      codes.forEach {
        it.lang = if (it.lang == Language.UNASSIGNED) this.lang
        else it.lang
      }
    }

    holder.database = DatabaseInfo("")
  }
}

fun main(args: Array<String>) {
  val uri = URI.create("mysql://10.10.123.86:3306/cityre_mis_v2018?characterEncoding=utf8&autoReconnect=true")
  print(uri)
}