package io.github.rcarlosdasilva.digger.core

import io.github.rcarlosdasilva.digger.core.misc.Pair
import java.awt.Color
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Random Helper
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class RandomHelper private constructor() {

  companion object {
    private const val MAX_COLOR_RANGE = 255

    const val NUMBERS = "0123456789"
    const val NUMBERS_WITHOUT_ZERO = "123456789"

    const val LETTERS_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz"
    const val LETTERS_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LETTERS = LETTERS_LOWER_CASE + LETTERS_UPPER_CASE

    const val NUMBERS_AND_LETTERS = NUMBERS + LETTERS
    const val NUMBERS_AND_LETTERS_LOWER_CASE = NUMBERS + LETTERS_LOWER_CASE
    const val NUMBERS_AND_LETTERS_UPPER_CASE = NUMBERS + LETTERS_UPPER_CASE

    /**
     * 私用
     */
    fun getRandom() = ThreadLocalRandom.current()!!

    /**
     * 生成随机字符串，默认使用[RandomHelper.NUMBERS_AND_LETTERS]作为随机候选字符集
     * ```
     * RandomHelper.string(10);
     * RandomHelper.string(10, RandomHelper.NUMBERS);
     * ```
     * 预置候选字符：
     * - [RandomHelper.NUMBERS]
     * - [RandomHelper.NUMBERS_WITHOUT_ZERO]
     * - [RandomHelper.LETTERS_LOWER_CASE]
     * - [RandomHelper.LETTERS_UPPER_CASE]
     * - [RandomHelper.LETTERS]
     * - [RandomHelper.NUMBERS_AND_LETTERS]
     * - [RandomHelper.NUMBERS_AND_LETTERS_LOWER_CASE]
     * - [RandomHelper.NUMBERS_AND_LETTERS_UPPER_CASE]
     * @param length Int 生成字符串长度
     * @param chars String 随机字符串的候选字符，默认[RandomHelper.NUMBERS_AND_LETTERS]
     * @return String 随机字符串
     */
    @JvmStatic
    @JvmOverloads
    fun string(length: Int, chars: String = RandomHelper.NUMBERS_AND_LETTERS): String {
      if (length <= 0 || chars.isEmpty()) return ""

      val cs = CharArray(length)
      val size = chars.length
      for (i in 0 until length) {
        cs[i] = chars[getRandom().nextInt(size)]
      }

      return String(cs)
    }

    /**
     * 从[Collection]集合中检出随机元素
     * ```
     * ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
     * RandomHelper.element(list);
     * ```
     * @param target Collection<E> 集合
     * @return E 随机元素
     */
    @JvmStatic
    fun <E> element(target: Collection<E>) =
        target.let { target.elementAt(getRandom().nextInt(it.size)) }

    /**
     * 从[Map]集合中检出随机元素（随机Entry，并转换为键值对[Pair]）
     * ```
     * Map<String, Integer> map = Maps.newHashMap();
     * map.put("a", 1);
     * map.put("b", 2);
     * map.put("c", 3);
     * Pair<String, Integer> pair = RandomHelper.element(map);
     * ```
     * @param target Map<K, V> 集合
     * @return Pair<K, V> 保存Key，Value的键值对[Pair]
     */
    @JvmStatic
    fun <K, V> element(target: Map<K, V>) =
        target.entries.let { Pair.from(element(it)) }

    /**
     * 随机RGB颜色
     * ```
     * Color color = RandomHelper.color();
    ```
     * @return Color [Color]
     */
    @JvmStatic
    fun color() =
        getRandom().let { Color(it.nextInt(MAX_COLOR_RANGE), it.nextInt(MAX_COLOR_RANGE), it.nextInt(MAX_COLOR_RANGE)) }

    /**
     * 随机十六进制颜色
     * ```
     * String colorHex = RandomHelper.colorHex();
     * ```
     * @return String #000000 十六进制颜色
     */
    @JvmStatic
    fun colorHex() =
        getRandom().let { String.format("#%02x%02x%02x", it.nextInt(MAX_COLOR_RANGE), it.nextInt(MAX_COLOR_RANGE), it.nextInt(MAX_COLOR_RANGE)) }

    /**
     * 随机时间，将返回在第一个和第二个参数时间质检的一个随机时间，如果某一个参数为空，则默认为当前时间。
     * 时间范围range中的两个时间不能同时为空，并且要保证第一个时间必须在第二个时间之前（如果第一个时间为空，
     * 则第二个时间必须在当前时间之后，反之亦然）
     * ```
     * Pair<Date, Date> range = new Pair<>(beginDate, endDate);
     * Date date = RandomHelper.date(range);
     * ```
     * @param range Pair<Date, Date> 时间范围，不能同时为空
     * @return Date 随机时间
     */
    @JvmStatic
    fun date(range: Pair<Date, Date>): Date =
        with(range) {
          val date = localDateTime(Pair(
              this.first?.let { DateHelper.toLocalDateTime(it) },
              this.second?.let { DateHelper.toLocalDateTime(it) }
          ))
          DateHelper.toDate(date)
        }

    /**
     * 随机时间，将返回在第一个和第二个参数时间质检的一个随机时间，如果某一个参数为空，则默认为当前时间。
     * 时间范围range中的两个时间不能同时为空，并且要保证第一个时间必须在第二个时间之前（如果第一个时间为空，
     * 则第二个时间必须在当前时间之后，反之亦然）
     * ```
     * Pair<LocalDateTime, LocalDateTime> range = new Pair<>(beginLocalDateTime, endLocalDateTime);
     * LocalDateTime localDateTime = RandomHelper.localDateTime(range);
     * ```
     * @param range Pair<LocalDateTime, LocalDateTime> 时间范围，不能同时为空
     * @return LocalDateTime 随机时间
     */
    @JvmStatic
    fun localDateTime(range: Pair<LocalDateTime, LocalDateTime>): LocalDateTime =
        with(range) {
          (first == null && second == null).throwRuntimeIf { DiggerRandomException("range中至少要提供first, second任意一个时间点") }

          val baseline = if (first != null && second != null) {
            (first.isAfter(second)).throwRuntimeIf { DiggerRandomException("range中的second时间必须在first之后") }
            Pair(first, second)
          } else {
            Pair(
                first?.apply {
                  (this.isAfter(LocalDateTime.now())).throwRuntimeIf { DiggerRandomException("range中的first时间在不提供second的情况下，必须在当前时间之前") }
                } ?: LocalDateTime.now(),
                second?.apply {
                  (this.isBefore(LocalDateTime.now())).throwRuntimeIf { DiggerRandomException("range中的second时间在不提供first的情况下，必须在当前时间之后") }
                } ?: LocalDateTime.now()
            )
          }

          val duration = Duration.between(baseline.first, baseline.second)
          val randomSeconds = getRandom().nextLong(duration.seconds)

          baseline.first!!.plusSeconds(randomSeconds)
        }

  }
}