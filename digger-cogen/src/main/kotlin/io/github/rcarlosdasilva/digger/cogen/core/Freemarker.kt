package io.github.rcarlosdasilva.digger.cogen.core

import freemarker.template.Configuration
import java.io.File
import java.io.StringWriter

object Freemarker {

  private val cfg = Configuration(Configuration.VERSION_2_3_28)

  fun init(dir: String) {
    cfg.setDirectoryForTemplateLoading(File(dir))
    cfg.defaultEncoding = "UTF-8"
    cfg.logTemplateExceptions = false
  }

  fun output(templateName: String, data: Map<String, Any>): String =
      StringWriter().let {
        Freemarker.template(templateName).process(data, it)
        it.toString()
      }

  private fun template(name: String) = cfg.getTemplate(name)
}