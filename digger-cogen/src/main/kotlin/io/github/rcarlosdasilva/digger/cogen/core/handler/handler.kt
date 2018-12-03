package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.core.Cogen

interface CogenHandler {
  fun handle(holder: Cogen.CogenRuntimeHolder)

  companion object {
    internal const val NAME_PATTERN_CHAR = "*"
    internal const val NAME_PATTERN_MATCHING = ".*"
    internal const val NAME_PATTERN_FIND = "(.*)"
  }
}