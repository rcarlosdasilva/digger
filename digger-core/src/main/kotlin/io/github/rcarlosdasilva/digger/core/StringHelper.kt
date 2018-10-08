package io.github.rcarlosdasilva.digger.core

import com.google.common.base.Ascii
import java.util.*


class StringHelper {

  companion object {
    private val RANDOM = Random()

    private const val DELIMITER_START = '{'
    private const val DELIMITER_STR = "{}"
    private const val ESCAPE_CHAR = '\\'


    const val NUMBERS = "0123456789"
    const val NUMBERS_WITHOUT_ZERO = "123456789"

    const val LETTERS_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz"
    const val LETTERS_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LETTERS = LETTERS_LOWER_CASE + LETTERS_UPPER_CASE

    const val NUMBERS_AND_LETTERS = NUMBERS + LETTERS
    const val NUMBERS_AND_LETTERS_LOWER_CASE = NUMBERS + LETTERS_LOWER_CASE
    const val NUMBERS_AND_LETTERS_UPPER_CASE = NUMBERS + LETTERS_UPPER_CASE

    /**
     * 如果第一个参数为null，返回default。否则返回第一个参数
     * @param obj Any? 任意对象
     * @param default String 当第一个参数为null时返回的值
     * @return String 第一个参数或第二个参数
     */
    @JvmStatic
    fun value(obj: Any?, default: String): String = obj?.toString() ?: default

    /**
     * 如果字符串为empty，返回null
     * @param source String? 任意字符串
     * @return String? 如果字符串为empty，返回null，否则原样返回
     */
    @JvmStatic
    fun emptyToNull(source: String?): String? = if (isNullOrEmpty(source)) null else source

    /**
     * 如果字符串为blank，返回null
     * @param source String? 任意字符串
     * @return String? 如果字符串为blank，返回null，否则原样返回
     */
    @JvmStatic
    fun blankToNull(source: String?): String? = if (isNullOrBlank(source)) null else source

    /**
     * 判断字符串是否为null或empty
     * @param source String? 任意字符串
     * @return Boolean 是null或empty
     */
    @JvmStatic
    fun isNullOrEmpty(source: String?) = source.isNullOrEmpty()

    /**
     * 判断字符串是否为null或blank
     * @param source String? 任意字符串
     * @return Boolean 是null或blank
     */
    @JvmStatic
    fun isNullOrBlank(source: String?) = source.isNullOrBlank()

    /**
     * 连接多个字符串，null值将被忽略
     * ```
     * // return "123";
     * StringHelper.concat("1", "", "2", null, "3");
     * ```
     * @param parts Array<out String> 字符串数组
     * @return String 连接后的字符串
     */
    @JvmStatic
    fun concat(vararg parts: String): String = CollectionHelper.removeNull(parts.toList()).joinToString("")

    /**
     * 拼接多个字符串
     * ```
     * List strings = new ArrayList();
     * strings.add("1");
     * strings.add("");
     * strings.add("2");
     * strings.add(null);
     * strings.add("3");
     * // return "123";
     * StringHelper.concat(strings);
     * ```
     * @param parts Iterable<String> 字符串数组
     * @return String 连接后的字符串
     */
    @JvmStatic
    fun concat(parts: Iterable<String>): String = CollectionHelper.removeNull(parts).joinToString("")

    /**
     * 拼接多个字符串，之间用separator间隔，null值将被忽略
     * ```
     * // return "1::2:3";
     * StringHelper.concat(":", "1", "", "2", null, "3");
     * ```
     * @param separator String 分隔符
     * @param parts Array<out String> 字符串数组
     * @return String 连接后的字符串
     */
    @JvmStatic
    fun join(separator: String, vararg parts: String): String =
        if (isNullOrEmpty(separator)) concat(*parts)
        else CollectionHelper.removeNull(parts.toList()).joinToString(separator)

    /**
     * 拼接多个字符串，之间用separator间隔，null值将被忽略
     * ```
     * List strings = new ArrayList();
     * strings.add("1");
     * strings.add("");
     * strings.add("2");
     * strings.add(null);
     * strings.add("3");
     * // return "1::2:3";
     * StringHelper.join(":", strings);
     * ```
     * @param separator String 分隔符
     * @param parts Iterable<String> 字符串数组
     * @return String 连接后的字符串
     */
    @JvmStatic
    fun join(separator: String, parts: Iterable<String>): String =
        if (isNullOrEmpty(separator)) concat(parts)
        else CollectionHelper.removeNull(parts).joinToString(separator)

    /**
     * 获取指定下标的单个字母字符串，index为负数时，从后向前找，index从0开始计数，0返回左侧第一个字符
     * ```
     * // return "4";
     * StringHelper.at("123456789", 3);
     * // return "6";
     * StringHelper.at("123456789", -4);
     * ```
     * @param source String 源字符串
     * @param index Int 下标
     * @return String 长度为1的字符串，未找到返回空
     * @throws DiggerStringException
     */
    @JvmStatic
    fun at(source: String, index: Int): String {
      val size = source.length
      val i = if (index < 0) size + index else index
      return if (i in 0..(size - 1)) source[i].toString() else throw DiggerStringException("对应的下标不存在：[index={}]", index)
    }

    /**
     * 截取从指定下标开始之前/之后的子字符串。index为截取的开始下标，为负数时，下标从源字符串最后向前确定。
     * length决定子字符串的长度，当length为负数时，代表从后向前截取
     * ```
     * // return "345"
     * StringHelper.sub("0123456789", 3, 3);
     * // return "123"
     * StringHelper.sub("0123456789", 3, -3);
     * // return "789"
     * StringHelper.sub("0123456789", -3, 3);
     * // return "567"
     * StringHelper.sub("0123456789", -3, -3);
     *
     * // return "56789"
     * StringHelper.sub("0123456789", 5, 8);
     * // return "012345"
     * StringHelper.sub("0123456789", -5, -8);
     * ```
     * @param source String 源字符串
     * @param start Int 开始下标
     * @param length Int 截取长度及方向
     * @return String 子字符串
     * @throws DiggerStringException
     */
    @JvmStatic
    fun sub(source: String, start: Int, length: Int): String {
      val size = source.length
      if (length == 0 || size == 0) {
        return ""
      }
      (Math.abs(length) > size).throwRuntimeIf { DiggerStringException("截取子字符串长度大于源字符串") }

      val forward = length < 0
      val po1 = if (start < 0) size + start else start
      val po2 = po1 + length

      (forward && po2 < 0).throwRuntimeIf { DiggerStringException("字符串截取开始下标越界：[size={}, start={}, end={}]", size, po2, po1) }
      (!forward && po2 > size).throwRuntimeIf { DiggerStringException("字符串截取结束下标越界：[size={}, start={}, end={}]", size, po1, po2) }

      return if (forward) source.substring(po2 + 1, po1 + 1) else source.substring(po1, po2)
    }

    /**
     * 格式化字符串，使用可变数量参数代替掉字符串中出现的{}
     * ```
     * // return "a=1,b=2,a+b=3"
     * StringHelper.format("a={},b={},a+b={}", "1", "2", "3")
     * // return "a=1,b=2,a+b=\\{3}"
     * StringHelper.format("a={},b={},a+b=\\{{}}", "1", "2", "3")
     * // return "a=1,b=2,a+b={}3"
     * StringHelper.format("a={},b={},a+b=\\{}{}", "1", "2", "3")
     * // return "a=1,b=2,a+b=\\3"
     * StringHelper.format("a={},b={},a+b=\\\\{}", "1", "2", "3")
     * // return "a={},b={},a+b={}"
     * StringHelper.format("a={},b={},a+b={}")
     * // return "a=1,b=2,a+b=3"
     * StringHelper.format("a={},b={},a+b={}", "1", "2", "3", "4")
     * // return "a=1,b=2,a+b={}"
     * StringHelper.format("a={},b={},a+b={}", "1", "2")
     * ```
     * @param pattern String 格式化字符串
     * @param arguments Array<out Any> 参数
     * @return String 格式化后的字符串
     */
    @JvmStatic
    fun format(pattern: String, arguments: Iterable<Any>): String {
      var start = 0
      var cur: Int
      var ai = 0
      val buf = StringBuilder()

      val args = arguments.toList()

      while (ai < args.size) {
        val arg = args[ai]
        cur = pattern.indexOf(DELIMITER_STR, start)

        if (cur == -1) break

        var escaped = false
        if (cur > 0) {
          val c = at(pattern, cur - 1)
          escaped = c[0] == ESCAPE_CHAR
        }
        if (escaped) {
          val c = at(pattern, cur - 2)
          start = if (c[0] == ESCAPE_CHAR) {
            buf.append(pattern, start, cur - 1)
            formatArg(buf, arg)
            cur + 2
          } else {
            ai--
            buf.append(pattern, start, cur - 1)
            buf.append(DELIMITER_START)
            cur + 1
          }
        } else {
          buf.append(pattern, start, cur)
          formatArg(buf, arg)
          start = cur + 2
        }

        ai++
      }

      buf.append(pattern, start, pattern.length)
      return buf.toString()
    }

    /**
     * 格式化字符串，使用可变数量参数代替掉字符串中出现的{}
     * ```
     * // return "a=1,b=2,a+b=3"
     * StringHelper.format("a={},b={},a+b={}", "1", "2", "3")
     * // return "a=1,b=2,a+b=\\{3}"
     * StringHelper.format("a={},b={},a+b=\\{{}}", "1", "2", "3")
     * // return "a=1,b=2,a+b={}3"
     * StringHelper.format("a={},b={},a+b=\\{}{}", "1", "2", "3")
     * // return "a=1,b=2,a+b=\\3"
     * StringHelper.format("a={},b={},a+b=\\\\{}", "1", "2", "3")
     * // return "a={},b={},a+b={}"
     * StringHelper.format("a={},b={},a+b={}")
     * // return "a=1,b=2,a+b=3"
     * StringHelper.format("a={},b={},a+b={}", "1", "2", "3", "4")
     * // return "a=1,b=2,a+b={}"
     * StringHelper.format("a={},b={},a+b={}", "1", "2")
     * // return "a=1,b=[null],a+b={}"
     * StringHelper.format("a={},b={},a+b={}", "1", null)
     * ```
     * @param pattern String 格式化字符串
     * @param arguments Array<out Any> 参数
     * @return String 格式化后的字符串
     */
    @JvmStatic
    fun format(pattern: String, vararg arguments: Any): String = format(pattern, arguments.asList())

    /**
     * 统计looking4子字符串在source字符串中出现的次数。从source中查找子字符串出现的开始位置，
     * 是在上一次找到的位置下标 + 子字符串的长度。也即是说查找"aaa"中"aa"出现的次数，这里是1次，而不是2次
     * ```
     * // return 0
     * StringHelper.times("", "");
     * // return 0
     * StringHelper.times("abc", "1");
     * // return 1
     * StringHelper.times("aaa", "aa");
     * // return 1
     * StringHelper.times("abc123xyz", "123");
     * // return 2
     * StringHelper.times("xyz&abc&123", "&");
     * // return 2
     * StringHelper.times("Abc&abc&ABC&abC", "ab");
     * // return 4
     * StringHelper.times("Abc&abc&ABC&abC", "ab", false);
     * ```
     * @param source String 字符串
     * @param looking4 String 要统计的子字符串
     * @param caseSensitive Boolean 区分大小写，默认true
     * @return Int 在source中出现的次数
     */
    @JvmStatic
    @JvmOverloads
    fun times(source: String, looking4: String, caseSensitive: Boolean = true): Int {
      if (looking4 === "") return 0

      var times = 0
      var position = 0
      while (true) {
        position = source.indexOf(looking4, position, !caseSensitive)
        if (position == -1) {
          break
        }
        position += looking4.length
        times++
      }

      return times
    }

    /**
     * 对source修剪指定的字符串redundant
     * ```
     * // return "123"
     * StringHelper.trim("===123===", "=", 0);
     * // return "123==="
     * StringHelper.trim("===123===", "=", 1);
     * // return "===123"
     * StringHelper.trim("===123===", "=", -1);
     * ```
     * @param source String 字符串
     * @param redundant String 需被修剪的字符串
     * @param place Int 修剪方向，0修剪两侧，负数修剪右侧，正数修剪左侧，默认0
     * @return String
     */
    @JvmStatic
    @JvmOverloads
    fun trim(source: String, redundant: String, place: Int = 0): String {
      if (redundant === "") return source

      val length = source.length
      val step = redundant.length
      var start = 0
      var end = length

      if (place >= 0) {
        while (start < end && source.indexOf(redundant, start) == start) {
          start += step
        }
      }

      if (place <= 0) {
        while (start < end && source.lastIndexOf(redundant, end - 1) == (end - step)) {
          end -= step
        }
      }

      return if (start > 0 || end < length)
        source.substring(start, end)
      else
        source
    }

    /**
     * 生成随机字符串
     * ```
     * StringHelper.random(10);
     * StringHelper.random(10, StringHelper.NUMBERS);
     * ```
     * 预置候选字符：
     * - [StringHelper.NUMBERS]
     * - [StringHelper.NUMBERS_WITHOUT_ZERO]
     * - [StringHelper.LETTERS_LOWER_CASE]
     * - [StringHelper.LETTERS_UPPER_CASE]
     * - [StringHelper.LETTERS]
     * - [StringHelper.NUMBERS_AND_LETTERS]
     * - [StringHelper.NUMBERS_AND_LETTERS_LOWER_CASE]
     * - [StringHelper.NUMBERS_AND_LETTERS_UPPER_CASE]
     * @param length Int 生成字符串长度
     * @param chars String 随机字符串的候选字符，默认[StringHelper.NUMBERS_AND_LETTERS]
     * @return String 随机字符串
     */
    @JvmStatic
    @JvmOverloads
    fun random(length: Int, chars: String = NUMBERS_AND_LETTERS): String {
      if (length <= 0 || chars.isEmpty()) return ""

      val cs = CharArray(length)
      val size = chars.length
      for (i in 0 until length) {
        cs[i] = chars[RANDOM.nextInt(size)]
      }

      return String(cs)
    }

    /**
     * 获取指定的字符区间的内容
     * ```
     * // return Lists.newArrayList("abc", "def")
     * StringHelper.between("[abc] xyz [def]", "[", "]");
     * ```
     * @param source String 字符串
     * @param start String 开始字符串，不能与end相同
     * @param end String 结束字符串，不能与start相同
     * @return List<String>
     */
    @JvmStatic
    fun between(source: String, start: String, end: String): List<String> {
      val ret = mutableListOf<String>()
      val parts = source.split(end)
      for (part in parts) {
        var startAt = part.indexOf(start)
        if (startAt > -1) {
          startAt += start.length
          ret.add(part.substring(startAt))
        }
      }
      return ret
    }

    /**
     * 小写指定字符
     * ```
     * // return "AbC"
     * StringHelper.toLowerCase("ABC", 1);
     * // return "ABC"
     * StringHelper.toLowerCase("ABC", 1, 0);
     * // return "Abc"
     * StringHelper.toLowerCase("ABC", 1, 2);
    ```
     * @param source String 字符串
     * @param start Int 需要小写的字符（开始）下标
     * @param length Int 需要处理的长度
     * @return String 部分字符小写后的字符串
     * @throws DiggerStringException
     */
    @JvmStatic
    @JvmOverloads
    fun toLowerCase(source: String, start: Int, length: Int = 1): String {
      val size = source.length
      if (length <= 0 || size == 0) return source

      (Math.abs(length) > size).throwRuntimeIf { DiggerStringException("需要处理的长度大于源字符串") }

      val po1 = if (start < 0) size + start else start
      val po2 = po1 + length

      (po2 > size).throwRuntimeIf { DiggerStringException("转换小写处理结束下标越界：[size={}, start={}, end={}]", size, po1, po2) }

      val chars = source.toCharArray()
      for (i: Int in po1 until po2) {
        chars[i] = Ascii.toLowerCase(chars[i])
      }
      return String(chars)
    }

    /**
     * 大写指定字符
     * ```
     * // return "aBc"
     * StringHelper.toUpperCase("abc", 1);
     * // return "abc"
     * StringHelper.toUpperCase("abc", 1, 0);
     * // return "aBC"
     * StringHelper.toUpperCase("abc", 1, 2);
     * ```
     * @param source String 字符串
     * @param start Int 需要大写的字符（开始）下标
     * @param length Int 需要处理的长度
     * @return String 部分字符大写后的字符串
     */
    @JvmStatic
    @JvmOverloads
    fun toUpperCase(source: String, start: Int, length: Int = 1): String {
      val size = source.length
      if (length <= 0 || size == 0) return source

      (Math.abs(length) > size).throwRuntimeIf { DiggerStringException("需要处理的长度大于源字符串") }

      val po1 = if (start < 0) size + start else start
      val po2 = po1 + length

      (po2 > size).throwRuntimeIf { DiggerStringException("转换大写处理结束下标越界：[size={}, start={}, end={}]", size, po1, po2) }

      val chars = source.toCharArray()
      for (i: Int in po1 until po2) {
        chars[i] = Ascii.toUpperCase(chars[i])
      }
      return String(chars)
    }

    /**
     * 清除字符串中单词间多余的空格（单词间只保留一个空格），以及两侧的空格
     * ```
     * // return "sample english sentence."
     * StringHelper.simplify("  sample   english   sentence.   ")
     * ```
     * @param source String
     * @return String
     */
    @JvmStatic
    fun simplify(source: String): String = source.replace(Regex("\\s+"), " ").trim()

    /**
     * 转换成驼峰式，首字母大写
     * ```
     * // return "ThereIsAWord"
     * StringHelper.studlyCase("  there is a word"));
     * // return "ThereIsAWord"
     * StringHelper.studlyCase("there_is_a_word  "));
     * // return "ThereIsAWord"
     * StringHelper.studlyCase(" there-is-a-word "));
     * // return ""
     * StringHelper.studlyCase("   "));
     * ```
     * @param source String 字符串
     * @return String 大写驼峰命名
     */
    @JvmStatic
    fun studlyCase(source: String): String = source.run {
      simplify(this)
          .split(Regex("[_\\- ]"))
          .joinToString("") { w -> toUpperCase(w, 0) }
    }

    /**
     * 转换成驼峰式，首字母小写
     * ```
     * // return "thereIsAWord"
     * StringHelper.studlyCase("  there is a word"));
     * // return "thereIsAWord"
     * StringHelper.studlyCase("there_is_a_word  "));
     * // return "thereIsAWord"
     * StringHelper.studlyCase(" there-is-a-word "));
     * // return ""
     * StringHelper.studlyCase("   "));
     * ```
     * @param source String 字符串
     * @return String 小写驼峰命名
     */
    @JvmStatic
    fun camelCase(source: String): String = toLowerCase(studlyCase(source), 0)

    /**
     * 转换成Snake Case，以下划线间隔
     * ```
     * //"there_is_a_word"
     * StringHelper.snakeCase("  there is a word");
     * //"there_is_a_word"
     * StringHelper.snakeCase("there_is_a_word  ");
     * //"there_is_a_word"
     * StringHelper.snakeCase(" there-is-a-word ");
     * //""
     * StringHelper.snakeCase("   ");
     * ```
     * @param source String 字符串
     * @return String 蛇形命名
     */
    @JvmStatic
    fun snakeCase(source: String): String = source.run {
      simplify(this)
          .split(Regex("[_\\- ]"))
          .joinToString("_") { w -> w.toLowerCase() }
    }

    /**
     * 转换成Kebab Case，以短横线间隔
     * ```
     * // return "there-is-a-word"
     * StringHelper.kebabCase("  there is a word");
     * // return "there-is-a-word"
     * StringHelper.kebabCase("there_is_a_word  ");
     * // return "there-is-a-word"
     * StringHelper.kebabCase(" there-is-a-word ");
     * // return ""
     * StringHelper.kebabCase("   ");
     * ```
     * @param source String 字符串
     * @return String 短横线命名
     */
    @JvmStatic
    fun kebabCase(source: String): String = source.run {
      simplify(this)
          .split(Regex("[_\\- ]"))
          .joinToString("-") { w -> w.toLowerCase() }
    }

    /**
     * 重复给定的字符串直到字符串达到或超过给定长度
     * ```
     * // return "00000"
     * StringHelper.repeatUntil("0", 5);
     * // return "01010"
     * StringHelper.repeatUntil("01", 5);
     * // return "01201"
     * StringHelper.repeatUntil("012", 5);
     * ```
     * @param source String 字符串
     * @param length Int 最终长度
     * @return String 处理后的字符串
     * @throws DiggerStringException
     */
    @JvmStatic
    fun repeatUntil(source: String, length: Int): String {
      (length < 0).throwRuntimeIf { DiggerStringException("重复的长度不能为负数") }

      if (length == 0 || source.isEmpty()) return ""

      val size = source.length
      val array = CharArray(length)
      source.toCharArray(array, 0, 0, size)

      var n = size
      while (n < length - n) {
        System.arraycopy(array, 0, array, n, n)
        n = n shl 1
      }
      System.arraycopy(array, 0, array, n, length - n)

      return String(array)
    }

    /**
     * 补充字符串达到给定长度， length如果为负数，将字符串填充至尾部。类似Guava的Strings.padStart()或Strings.padEnd()
     * ```
     * // return "binary numbers look like ..."
     * StringHelper.fill("binary numbers look like ", ".", -28);
     * // return "binary numbers look like 01010"
     * StringHelper.fill("binary numbers look like ", "01", -30);
     * // return "!@#!@#!binary numbers look like "
     * StringHelper.fill("binary numbers look like ", "!@#", 32);
     * ```
     * @param source String 字符串
     * @param filler String 补充的字符串
     * @param length Int 最终长度
     * @return String 处理后的字符串
     */
    @JvmStatic
    fun fill(source: String, filler: String, length: Int): String {
      val len = Math.abs(length)
      if (len <= source.length || filler.isEmpty()) return source

      val tail = length < 0
      val pad = repeatUntil(filler, len - source.length)
      return if (tail) source + pad else pad + source
    }

    /**
     * 检查字符串中是否包含全部指定的子字符串
     * ```
     * // return true
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "cD", "001"));
     * // return true
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cD"));
     * // return true
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cd"), false, true);
     * // return true
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "cd", "ef"), false, false);
     *
     * // return false
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "cd", "001"));
     * // return false
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cd"), true, true);
     * // return false
     * StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cD"), true, false);
     * ```
     * @param source String 字符串
     * @param looking4 Collection<String> 预检查的子字符串
     * @param caseSensitive Boolean 区分大小写，默认true
     * @param overlap Boolean 允许重叠，默认true。例如source="abc"，parts=["ab","bc"]，则overlap=true时，返回true（b字符重叠）。overlap=false时，返回false
     * @return Boolean looking4中的字符串全部包含在source中
     */
    @JvmStatic
    @JvmOverloads
    fun haveAll(source: String, looking4: Collection<String>, caseSensitive: Boolean = true, overlap: Boolean = true): Boolean {
      if (source.isEmpty() || looking4.isEmpty()) return true

      return if (overlap) {
        looking4.all { source.contains(it, !caseSensitive) }
      } else {
        var copy = source

        for (s in looking4) {
          val h = copy.contains(s, !caseSensitive)
          if (!h) return false

          copy = remove(copy, listOf(s), caseSensitive = caseSensitive)
        }
        return true
      }
    }

    /**
     * 检查字符串中是否包含任意一个指定的子字符串
     * ```
     * // return true
     * StringHelper.haveAny("abcDEF001", Lists.newArrayList("aba", "cD", "002"));
     * // return true
     * StringHelper.haveAny("abcDEF001", Lists.newArrayList("aba", "cd", "002"), false);
     *
     * // return false
     * StringHelper.haveAny("abcDEF001", Lists.newArrayList("aba", "cdc", "002"));
     * ```
     * @param source String 字符串
     * @param looking4 List<String> 预检查的子字符串
     * @param caseSensitive Boolean 区分大小写，默认true
     * @return Boolean looking4中的字符串，最少有一个包含在source中
     */
    @JvmStatic
    @JvmOverloads
    fun haveAny(source: String, looking4: List<String>, caseSensitive: Boolean = true): Boolean {
      if (looking4.isEmpty()) return true
      return looking4.any { source.contains(it, !caseSensitive) }
    }

    /**
     * 插入子字符串到指定下标位置
     * ```
     * // return "xyz123456"
     * StringHelper.insert("123456", 0,"xyz");
     * // return "123xyz456"
     * StringHelper.insert("123456", 3,"xyz");
     * // return "123456xyz"
     * StringHelper.insert("123456", 6,"xyz");
     *
     * // return "1234xyz56"
     * StringHelper.insert("123456", -2,"xyz");
     * // return "12xyz3456"
     * StringHelper.insert("123456", -4,"xyz");
     * ```
     * @param source String 字符串
     * @param index Int 插入下标，为负数时，从字符串尾部定位
     * @param part String 需插入字符串
     * @return String 插入后的字符串
     */
    @JvmStatic
    fun insert(source: String, index: Int, part: String): String {
      if (part.isEmpty()) return source
      (Math.abs(index) > source.length).throwRuntimeIf { DiggerStringException("插入字符串下标越界：[size={}, index={}]", source.length, index) }

      val i = if (index < 0) source.length + index else index

      val cs = source.toCharArray()
      val cp = part.toCharArray()
      val cr = CharArray(source.length + part.length)

      System.arraycopy(cs, 0, cr, 0, i)
      System.arraycopy(cp, 0, cr, i, part.length)
      System.arraycopy(cs, i, cr, i + part.length, source.length - i)

      return String(cr)
    }

    /**
     * 删除指定开始和结束下标范围的字符
     * ```
     * //return "123456"
     * StringHelper.remove("123xyz456", 3, 6);
     * //return "123xyz"
     * StringHelper.remove("123xyz456", 6, 9);
     * ```
     * @param source String 字符串
     * @param start Int 开始删除字符的下标
     * @param end Int 结束删除字符的下标
     * @return String 删除指定字符后的字符串
     */
    @JvmStatic
    fun remove(source: String, start: Int, end: Int): String {
      if (source.isEmpty() || start == end) return source
      (start < 0 || end > source.length).throwRuntimeIf { DiggerStringException("删除字符下标越界：[size={}, start={}, end={}]", source.length, start, end) }
      (start > end).throwRuntimeIf { DiggerStringException("删除范围下标start不能大于end") }

      return source.removeRange(start, end)
    }

    /**
     * 删除字符串中出现的指定子字符串，可指定删除次数
     * ```
     * // return "xyz"
     * StringHelper.remove("123xyz456", Lists.newArrayList("123", "456"));
     * // return ""
     * StringHelper.remove("123xyz456", Lists.newArrayList("123", "456", "xyz"));
     * // return "_abc_123xyz456"
     * StringHelper.remove("123xyz456_abc_123xyz456", Lists.newArrayList("123", "456", "xyz"), 1);
     * // return "123xyz456_abc_"
     * StringHelper.remove("123xyz456_abc_123xyz456", Lists.newArrayList("123", "456", "xyz"), -1);
     * // return "123xyz456_abc_"
     * StringHelper.remove("123xyz456_abc_123xyz456", Lists.newArrayList("123", "456", "XYZ"), -1, false);
     * ```
     * @param source String 字符串
     * @param parts Collection<String> 需要从字符串中删除的子字符串
     * @param counts Int 删除次数，默认0。 0 删除全部。 大于0(N) 从下标0开始，查找N次出现的子字符串并删除，如果N大于字符串中实际出现的次数，效果等于0。 小于0 与大于0作用相同，但从字符串的尾部开始查找删除
     * @param caseSensitive Boolean 区分大小写，默认true
     * @return String 删除后的字符串
     */
    @JvmStatic
    @JvmOverloads
    fun remove(source: String, parts: Collection<String>, counts: Int = 0, caseSensitive: Boolean = true): String {
      if (source.isEmpty() || parts.isEmpty()) return source

      var copy = source
      if (counts == 0) {
        parts.forEach { copy = copy.replace(it, "", !caseSensitive) }
      } else {
        val forward = counts < 0

        for (i in 0 until Math.abs(counts)) {
          parts.forEach {
            val ind = if (forward)
              copy.lastIndexOf(it, ignoreCase = !caseSensitive)
            else
              copy.indexOf(it, ignoreCase = !caseSensitive)
            if (ind >= 0) {
              copy = remove(copy, ind, ind + it.length)
            }
          }
        }
      }
      return copy
    }

    /**
     * 确保字符串以给定的前缀开始
     * ```
     * // return "abcxyz"
     * StringHelper.insureStartsWith("xyz", "abc", true);
     * // return "abcABCxyz"
     * StringHelper.insureStartsWith("ABCxyz", "abc", true);
     * // return "ABCxyz"
     * StringHelper.insureStartsWith("ABCxyz", "abc", false);
     * ```
     * @param source String 字符串
     * @param prefix String 前缀
     * @param caseSensitive Boolean 区分大小写，默认true
     * @return String 带有指定前缀的字符串
     */
    @JvmStatic
    @JvmOverloads
    fun insureStartsWith(source: String, prefix: String, caseSensitive: Boolean = true): String =
        if (source.startsWith(prefix, !caseSensitive))
          source
        else prefix + source

    /**
     * 确保字符串以给定的后缀结束
     * ```
     * // return "abcxyz"
     * StringHelper.insureEndsWith("abc", "xyz", true);
     * // return "abcXYZxyz"
     * StringHelper.insureEndsWith("abcXYZ", "xyz", true);
     * // return "abcXYZ"
     * StringHelper.insureEndsWith("abcXYZ", "xyz", false);
     * ```
     * @param source String 字符串
     * @param suffix String 后缀
     * @param caseSensitive Boolean 区分大小写，默认true
     * @return String 带有指定后缀的字符串
     */
    @JvmStatic
    @JvmOverloads
    fun insureEndsWith(source: String, suffix: String, caseSensitive: Boolean = true): String =
        if (source.endsWith(suffix, !caseSensitive))
          source
        else
          source + suffix

    /**
     * 获取字符串指定长度的缩略，尾部跟填充物
     * ```
     * // return "Install the plugi...";
     * TextHelper.brief("Install the plugin; Restart Eclipse and go to Window", 20, "...");
     * // return "默认逻辑是当表单验证失败时,把按钮...";
     * TextHelper.brief("默认逻辑是当表单验证失败时,把按钮给变灰色", 20, "...");
     * ```
     * @param source String 字符串
     * @param length Int 处理后字符串长度
     * @param filler String 填充物
     */
    @JvmStatic
    fun brief(source: String, length: Int, filler: String): String =
        if (filler.isEmpty())
          source.substring(0, length)
        else
          source.substring(0, length - filler.length) + filler

    /**
     * 使用给定的前缀与后缀包裹指定的子字符串
     * ```
     * // return "abc[123]xyz[123]"
     * StringHelper.wrap("abc123xyz123", "123", "[", "]");
     * ```
     * @param source String 字符串
     * @param looking4 String 需要处理的子字符串
     * @param prefix String 前缀
     * @param suffix String 后缀
     * @return String 处理后的字符串
     */
    @JvmStatic
    fun wrap(source: String, looking4: String, prefix: String, suffix: String): String {
      if (source.isEmpty()) return ""

      return source.replace(looking4, concat(prefix, looking4, suffix))
    }

  }
}

private fun formatArg(buf: StringBuilder, o: Any?) {
  if (o == null) {
    buf.append("[null]")
    return
  }

  try {
    val oAsString = o.toString()
    buf.append(oAsString)
  } catch (t: Throwable) {
    buf.append("[failed ${o.javaClass.name}.toString()]")
  }
}