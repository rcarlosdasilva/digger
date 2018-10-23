package io.github.rcarlosdasilva.digger.cogen.core.handler

import io.github.rcarlosdasilva.digger.cogen.core.Cogen

interface CogenHandler {
  fun handle(holder: Cogen.CogenRuntimeHolder)
}