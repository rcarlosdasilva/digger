package io.github.rcarlosdasilva.digger.core

import io.github.rcarlosdasilva.digger.common.exception.DiggerRuntimeException

class DiggerStringException : DiggerRuntimeException {
  constructor() : super()
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(StringHelper.format(message, arguments.asList()))
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, StringHelper.format(message, arguments))
  constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}