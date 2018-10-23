package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenFinishException
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import mu.KotlinLogging
import java.io.IOException

class FinishHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    holder.configuration.apply {
      if (!isOpenExplorer) {
        return
      }

      logger.info("[explorer] - 验货ing")
      try {
        val osName = System.getProperty("os.name")
        when {
          osName?.contains("Mac") == true -> Runtime.getRuntime().exec("open $outputDir")
          osName?.contains("Windows") == true -> Runtime.getRuntime().exec("cmd /c start $outputDir")
          else -> logger.info("文件输出目录: $outputDir")
        }
      } catch (ex: IOException) {
        throw DiggerCogenFinishException(ex)
      }
    }
  }
}