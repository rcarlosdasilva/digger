package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenFolderException
import io.github.rcarlosdasilva.digger.cogen.core.Code
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.core.Freemarker
import io.github.rcarlosdasilva.digger.cogen.info.PackageInfo
import io.github.rcarlosdasilva.digger.core.StringHelper
import io.github.rcarlosdasilva.digger.core.throwRuntimeIf
import io.github.rcarlosdasilva.digger.core.throwRuntimeUnless
import mu.KotlinLogging
import java.io.File

class FolderHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  private lateinit var outputRoot: String

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    outputRoot = holder.configuration.outputDir
    Freemarker.init(holder.configuration.templatesDir)

    holder.packages = holder.configuration.codes.map(this::turnToPackage)
    holder.packages.forEach {
      val path = it.path
      val folder = File(path)
      if (!folder.exists()) {
        folder.mkdirs().throwRuntimeUnless { DiggerCogenFolderException("无法创建目录：{}", path) }
      } else {
        (folder.isFile).throwRuntimeIf { DiggerCogenFolderException("输出的目录为一个文件：{}", path) }
      }
    }
  }

  private fun turnToPackage(code: Code): PackageInfo =
      code.let {
        val path = it.path(outputRoot)
        PackageInfo(it.packageName, path).apply { config = code }
      }

  private fun Code.path(root: String): String {
    val parts = mutableListOf<String>()
    parts.add(root)
    parts.add(module)
    parts.add(lang.mavenRoot)
    parts.add(packageName)

    return parts.toTypedArray().filter { it.isNotBlank() }
        .joinToString(File.separator) {
          val t = it.replace("..", "#")
              .replace(".", File.separator)
              .replace("#", "..")
          StringHelper.trim(t, File.separator)
        }
  }
}