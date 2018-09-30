package io.github.rcarlosdasilva.digger.core

import java.util.*

class CollectionHelper {

  companion object {
    /**
     * 剔除集合中的null元素，如果参数为空，返回空集合
     * @param collection Iterable<E>? 集合
     * @return List<E> 剔除null后的集合，可能是空集合，但不会为null
     */
    @JvmStatic
    fun <E> removeNull(collection: Iterable<E>?): List<E> =
        collection?.filter { e -> e != null } ?: Collections.emptyList()

  }
}