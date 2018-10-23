@file:Suppress("unused")

package io.github.rcarlosdasilva.digger.cogen

import io.github.rcarlosdasilva.digger.common.exception.DiggerRuntimeException
import io.github.rcarlosdasilva.digger.core.StringHelper

/**
 * Cogen模块根RuntimeException
 */
open class DiggerCogenRuntimeException : DiggerRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(StringHelper.format(message, arguments.asList()))
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, StringHelper.format(message, arguments))
}

class DiggerCogenPrepareException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}

class DiggerCogenDatabaseException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}

class DiggerCogenFolderException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}

class DiggerCogenFilterException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}

class DiggerCogenCodeException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}

class DiggerCogenOutputException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}

class DiggerCogenFinishException : DiggerCogenRuntimeException {
  constructor(message: String) : super(message)
  constructor(message: String, vararg arguments: Any) : super(message, arguments)
  constructor(cause: Throwable) : super(cause)
  constructor(cause: Throwable, message: String) : super(cause, message)
  constructor(cause: Throwable, message: String, vararg arguments: Any) : super(cause, message, arguments)
}
