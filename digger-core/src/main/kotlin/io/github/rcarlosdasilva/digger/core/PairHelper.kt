package io.github.rcarlosdasilva.digger.core

import io.github.rcarlosdasilva.digger.core.misc.Pair

/**
 * Pair Helper
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class PairHelper private constructor() {

  @Suppress("UselessCallOnCollection")
  companion object {

    /**
     * 将多个[Pair]，按给定的顺序，抽出value值，组成新的List。适合处理Pair中key全部一样的集合
     * ```
     * Pair<String, Integer> p1 = new Pair<>("key", 1);
     * Pair<String, Integer> p2 = new Pair<>("key", 2);
     * Pair<String, Integer> p3 = new Pair<>("key", 3);
     * Pair<String, Integer> p4 = new Pair<>("key", 4);
     * Pair<String, Integer> p5 = new Pair<>("key", 5);
     *
     * List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, p3, null, p4, p5);
     * // return [1, 2, 3, 4, 5]
     * List<Integer> list = PairHelper.toList(pairs);
     * ```
     * @param pairs Collection<Pair<K, V>> Pair集合
     * @return List<V> 只包含[Pair.value]的List
     */
    @JvmStatic
    fun <K, V> toList(pairs: Collection<Pair<K, V>>) =
        pairs.filterNotNull().map { it.value }

    /**
     * 将多个[Pair]，转换成Map。适合处理Pair中key不一样的集合，如果存在重复的key，将只保留pairs队列中出现的最后一个key
     * ```
     * Pair<String, Integer> p1 = new Pair<>("a", 1);
     * Pair<String, Integer> p2 = new Pair<>("b", 2);
     * Pair<String, Integer> p3 = new Pair<>("c", 3);
     * Pair<String, Integer> p4 = new Pair<>("d", 4);
     * Pair<String, Integer> p5 = new Pair<>("e", 5);
     *
     * List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, p3, null, p4, p5);
     * // return {a: 1, b: 2, c: 3, d: 4, e: 5}
     * Map<String, Integer> map = PairHelper.toMap(pairs);
     * ```
     * @param pairs Collection<Pair<K, V>> Pair集合
     * @return Map<K, V> 转换后的Map
     */
    @JvmStatic
    fun <K, V> toMap(pairs: Collection<Pair<K, V>>) =
        pairs.filterNotNull().associate { kotlin.Pair(it.key, it.value) }

    /**
     * 组装[Pair]集合为一个完整的字符串
     * ```
     * Pair<String, Integer> p1 = new Pair<>("a", 1);
     * Pair<String, Integer> p2 = new Pair<>("b", 2);
     * Pair<String, Integer> p3 = new Pair<>("c", 3);
     *
     * List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, null, p3);
     * // return "a->1,b->2,c->3"
     * String custom = PairHelper.assembly(pairs, "->", ",");
     * ```
     * @param pairs Collection<Pair<*, *>> Pair集合
     * @param associate String 连接Pair中 key和value的字符串
     * @param join String 连接Pair之间的字符串
     * @return String 组装后的字符串
     */
    @JvmStatic
    fun assembly(pairs: Collection<Pair<*, *>>, associate: String, join: String) =
        pairs.filterNotNull().map { "${it.key}$associate${it.value}" }.joinToString(join)

    /**
     * 将[Pair]集合组成参数字符串，例如：param1=123&param2=456
     * ```
     * Pair<String, Integer> p1 = new Pair<>("a", 1);
     * Pair<String, Integer> p2 = new Pair<>("b", 2);
     * Pair<String, Integer> p3 = new Pair<>("c", 3);
     *
     * List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, null, p3);
     * // return "a=1&b=2&c=3"
     * String parameters = PairHelper.toParameters(pairs);
     * ```
     * @param pairs Collection<Pair<*, *>> Pair集合
     * @return String 参数字符串
     */
    @JvmStatic
    fun toParameters(pairs: Collection<Pair<*, *>>) = assembly(pairs, "=", "&")
  }

}