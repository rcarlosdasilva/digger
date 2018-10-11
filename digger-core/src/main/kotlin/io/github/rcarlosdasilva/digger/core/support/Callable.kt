package io.github.rcarlosdasilva.digger.core.support

interface Callable {

  fun allowCaller(callerClass: Class<*>, callerMethod: String): Boolean

}