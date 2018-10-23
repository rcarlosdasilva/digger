package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.DiggerCogenFolderException
import io.github.rcarlosdasilva.digger.cogen.Language
import io.github.rcarlosdasilva.digger.cogen.core.Cogen
import io.github.rcarlosdasilva.digger.cogen.core.Configuration
import io.github.rcarlosdasilva.digger.cogen.core.Freemarker
import io.github.rcarlosdasilva.digger.cogen.core.handler.FileHandler.Companion.MAVEN_DIR_JAVA
import io.github.rcarlosdasilva.digger.cogen.core.handler.FileHandler.Companion.MAVEN_DIR_KOTLIN
import io.github.rcarlosdasilva.digger.cogen.info.PackageInfo
import io.github.rcarlosdasilva.digger.core.StringHelper
import io.github.rcarlosdasilva.digger.core.throwRuntimeIf
import io.github.rcarlosdasilva.digger.core.throwRuntimeUnless
import mu.KotlinLogging
import java.io.File

class FileHandler : CogenHandler {

  private val logger = KotlinLogging.logger { }

  override fun handle(holder: Cogen.CogenRuntimeHolder) {
    holder.configuration.apply {
      Freemarker.init(templatesDir)

      val packages = mutableListOf<PackageInfo>()
      codes.forEach {
        val path = it.path(outputDir)
        val pi = PackageInfo(it.packageName, path)
        packages.add(pi)

        val folder = File(path)
        if (!folder.exists()) {
          folder.mkdirs().throwRuntimeUnless { DiggerCogenFolderException("无法创建目录：{}", path) }
        } else {
          (folder.isFile).throwRuntimeIf { DiggerCogenFolderException("输出的目录为一个文件：{}", path) }
        }
      }
      holder.packages = packages
    }
  }

  companion object {
    internal const val MAVEN_DIR_JAVA = "src/main/java"
    internal const val MAVEN_DIR_KOTLIN = "src/main/kotlin"
  }
}

fun Configuration.Code.path(root: String): String {
  val parts = mutableListOf<String>()
  parts.add(root)
  parts.add(module)
  when (lang) {
    Language.JAVA -> parts.add(MAVEN_DIR_JAVA)
    Language.KOTLIN -> parts.add(MAVEN_DIR_KOTLIN)
    else -> parts.add("")
  }
  parts.add(packageName)

  return parts.toTypedArray().filter { it.isNotBlank() }
      .joinToString("/") {
        val t = it.replace("..", "#")
            .replace('.', '/')
            .replace("#", "..")
        StringHelper.trim(t, "/")
      }
}