package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenPrepareException
import io.github.rcarlosdasilva.digger.cogen.DiggerCogenRuntimeException
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.core.throwRuntimeIf
import mu.KotlinLogging

class PrepareHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    logger.info("[config] - 翻牌子ing")

    holder.configuration.apply {
      (database == null).throwRuntimeIf { DiggerCogenPrepareException("没有数据库配置") }
      database!!.apply {
        (tables.isEmpty()).throwRuntimeIf { DiggerCogenPrepareException("未指定数据库的表配置") }
        (idName.isBlank()).throwRuntimeIf { DiggerCogenPrepareException("id字段名称不能为空") }
      }

      (codes.isEmpty()).throwRuntimeIf { DiggerCogenRuntimeException("没有代码生成配置") }

      codes.forEach {
        it.lang ?: run { it.lang = lang }
      }
    }
  }
}