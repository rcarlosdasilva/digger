package io.github.rcarlosdasilva.digger.core.misc

import java.io.Serializable

/**
 * Pair键值对
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
data class Pair<out K, out V>(
    val key: K,
    val value: V) : Serializable