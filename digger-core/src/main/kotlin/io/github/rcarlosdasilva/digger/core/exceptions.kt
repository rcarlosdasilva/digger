package io.github.rcarlosdasilva.digger.core

import io.github.rcarlosdasilva.digger.common.exception.DiggerException
import io.github.rcarlosdasilva.digger.common.exception.DiggerRuntimeException

/**
 * Core模块根Exception
 */
open class DiggerCoreException : DiggerException {
  constructor() : super()
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(StringHelper.format(message, arguments.asList()))
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(message, cause)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(StringHelper.format(message, arguments), cause)
  constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}

/**
 * Core模块根RuntimeException
 */
open class DiggerCoreRuntimeException : DiggerRuntimeException {
  constructor() : super()
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(StringHelper.format(message, arguments.asList()))
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(message, cause)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(StringHelper.format(message, arguments), cause)
  constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}

class DiggerStringException : DiggerCoreRuntimeException {
  constructor() : super()
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, *arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, *arguments)
  constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}