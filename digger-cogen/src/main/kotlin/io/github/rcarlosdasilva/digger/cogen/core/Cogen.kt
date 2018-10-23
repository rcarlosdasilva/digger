package io.github.rcarlosdasilva.digger.cogen.core

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenRuntimeException
import io.github.rcarlosdasilva.digger.cogen.core.handler.*
import io.github.rcarlosdasilva.digger.cogen.info.PackageInfo
import io.github.rcarlosdasilva.digger.cogen.info.TableInfo
import mu.KotlinLogging

/**
 * 代码生成器主入口
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object Cogen {

  private val logger = KotlinLogging.logger { }

  fun build(configuration: Configuration) = CogenRunner(CogenRuntimeHolder(configuration))

  data class CogenRuntimeHolder internal constructor(val configuration: Configuration) {
    lateinit var allTables: List<TableInfo>
    lateinit var filteredTables: List<TableInfo>
    lateinit var packages: List<PackageInfo>
  }

  class CogenRunner internal constructor(private val holder: CogenRuntimeHolder) {

    /**
     * 生成代码
     */
    fun run() {
      logger.info("============== Cogen Begin ==============")
      try {
        handlers.forEach { handler -> handler.handle(holder) }
      } catch (ex: DiggerCogenRuntimeException) {
        logger.error("", ex)
      }
      logger.info("============== Cogen Done ==============")
    }

    companion object {
      private val handlers: List<CogenHandler> by lazy {
        listOf(
            PrepareHandler(),
            DatabaseHandler(),
            FileHandler(),
            FilterHandler(),
            CodeHandler(),
            OutputHandler(),
            FinishHandler()
        )
      }
    }
  }
}