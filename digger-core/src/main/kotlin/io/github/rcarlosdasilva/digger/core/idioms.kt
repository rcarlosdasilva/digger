package io.github.rcarlosdasilva.digger.core

import io.github.rcarlosdasilva.digger.common.exception.DiggerException
import io.github.rcarlosdasilva.digger.common.exception.DiggerRuntimeException

/**
 * 表达式为true时，执行代码块
 */
inline fun <T, R> T.runIf(condition: Boolean, block: T.() -> R): R? = if (condition) block() else null

/**
 * 为true时，执行代码块
 */
inline fun <R> Boolean.runIf(block: () -> R): R? = if (this) block() else null

/**
 * 表达式为false时，执行代码块
 */
inline fun <T, R> T.runUnless(condition: Boolean, block: T.() -> R): R? = if (!condition) block() else null

/**
 * 为false时，执行代码块
 */
inline fun <R> Boolean.runUnless(block: () -> R): R? = if (!this) block() else null

/**
 * 表达式为true时，执行代码块
 */
inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
  if (condition) block()
  return this
}

/**
 * 为true时，执行代码块
 */
inline fun Boolean.applyIf(block: () -> Unit): Boolean {
  if (this) block()
  return this
}

/**
 * 表达式为false时，执行代码块
 */
inline fun <T> T.applyUnless(condition: Boolean, block: T.() -> Unit): T {
  if (!condition) block()
  return this
}

/**
 * 为false时，执行代码块
 */
inline fun Boolean.applyUnless(block: () -> Unit): Boolean {
  if (!this) block()
  return this
}

/**
 * 表达式为true时，抛出异常
 */
inline fun <T, E : DiggerException> T.throwIf(condition: Boolean, block: T.() -> E) {
  if (condition) throw block()
}

/**
 * 为true时，抛出异常
 */
inline fun <E : DiggerException> Boolean.throwIf(block: () -> E) {
  if (this) throw block()
}

/**
 * 表达式为false时，抛出异常
 */
inline fun <T, E : DiggerException> T.throwUnless(condition: Boolean, block: T.() -> E) {
  if (!condition) throw block()
}

/**
 * 为false时，抛出异常
 */
inline fun <E : DiggerException> Boolean.throwUnless(block: () -> E) {
  if (!this) throw block()
}

/**
 * 表达式为true时，抛出异常
 */
inline fun <T, E : DiggerRuntimeException> T.throwRuntimeIf(condition: Boolean, block: T.() -> E) {
  if (condition) throw block()
}

/**
 * 为true时，抛出异常
 */
inline fun <E : DiggerRuntimeException> Boolean.throwRuntimeIf(block: () -> E) {
  if (this) throw block()
}

/**
 * 表达式为false时，抛出异常
 */
inline fun <T, E : DiggerRuntimeException> T.throwRuntimeUnless(condition: Boolean, block: T.() -> E) {
  if (!condition) throw block()
}

/**
 * 为false时，抛出异常
 */
inline fun <E : DiggerRuntimeException> Boolean.throwRuntimeUnless(block: () -> E) {
  if (!this) throw block()
}