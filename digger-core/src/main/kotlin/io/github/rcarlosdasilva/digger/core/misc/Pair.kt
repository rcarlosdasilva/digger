package io.github.rcarlosdasilva.digger.core.misc

import java.io.Serializable

/**
 * Pair键值对（不可变）
 * @param F 第一个元素类型
 * @param S 第二个元素类型
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
data class Pair<out F, out S>(
    val first: F?,
    val second: S?) : Serializable {

  companion object {

    /**
     * 通过[Map.Entry]转换为[Pair]
     * @param entry Map.Entry<K, V> original
     * @return Pair<K, V> converted
     */
    @JvmStatic
    fun <K, V> from(entry: Map.Entry<K, V>): Pair<K, V> = Pair(entry.key, entry.value)
  }
}