package io.github.rcarlosdasilva.digger.core;

import com.google.common.collect.Lists;
import io.github.rcarlosdasilva.digger.common.exception.DiggerRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringHelperTest {

  @Test
  void testValue() {
    Assertions.assertEquals("abc", StringHelper.value(null, "abc"));
    Assertions.assertEquals("xyz", StringHelper.value("xyz", "abc"));
  }

  @Test
  void testNullOrBlankOrEmpty() {
    Assertions.assertTrue(StringHelper.isNullOrEmpty(""));
    Assertions.assertTrue(StringHelper.isNullOrEmpty(null));
    Assertions.assertFalse(StringHelper.isNullOrEmpty(" "));
    Assertions.assertFalse(StringHelper.isNullOrEmpty("abc"));

    Assertions.assertTrue(StringHelper.isNullOrBlank(""));
    Assertions.assertTrue(StringHelper.isNullOrBlank(null));
    Assertions.assertTrue(StringHelper.isNullOrBlank(" "));
    Assertions.assertFalse(StringHelper.isNullOrBlank("abc"));

    Assertions.assertNull(StringHelper.emptyToNull(""));
    Assertions.assertNull(StringHelper.emptyToNull(null));
    Assertions.assertNotNull(StringHelper.emptyToNull(" "));
    Assertions.assertNotNull(StringHelper.emptyToNull("abc"));

    Assertions.assertNull(StringHelper.blankToNull(""));
    Assertions.assertNull(StringHelper.blankToNull(null));
    Assertions.assertNull(StringHelper.blankToNull(" "));
    Assertions.assertNotNull(StringHelper.blankToNull("abc"));
  }

  @Test
  void testConcatAndJoin() {
    Assertions.assertEquals("123", StringHelper.concat("1", "", "2", null, "3"));
    Assertions.assertEquals("123", StringHelper.concat(Lists.newArrayList("1", "", "2", null, "3")));
    Assertions.assertEquals("1::2:3", StringHelper.join(":", "1", "", "2", null, "3"));
    Assertions.assertEquals("1::2:3", StringHelper.join(":", Lists.newArrayList("1", "", "2", null, "3")));
  }

  @Test
  void testPosition() {
    Assertions.assertEquals("4", StringHelper.at("123456789", 3));
    Assertions.assertEquals("6", StringHelper.at("123456789", -4));
    Assertions.assertThrows(DiggerRuntimeException.class, () -> StringHelper.at("123456789", -20));

    // ============

    Assertions.assertEquals("", StringHelper.sub("0123456789", 0, 0));
    Assertions.assertEquals("345", StringHelper.sub("0123456789", 3, 3));
    Assertions.assertEquals("123", StringHelper.sub("0123456789", 3, -3));
    Assertions.assertEquals("789", StringHelper.sub("0123456789", -3, 3));
    Assertions.assertEquals("567", StringHelper.sub("0123456789", -3, -3));
    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.sub("0123456789", 5, 8));
    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.sub("0123456789", -5, -8));
  }

  @Test
  void testFormat() {
    Assertions.assertEquals("a=1,b=2,a+b=3", StringHelper.format("a={},b={},a+b={}", Lists.newArrayList("1", "2", "3")));
    Assertions.assertEquals("a=1,b=2,a+b=\\{3}", StringHelper.format("a={},b={},a+b=\\{{}}", Lists.newArrayList("1", "2", "3")));
    Assertions.assertEquals("a=1,b=2,a+b={}3", StringHelper.format("a={},b={},a+b=\\{}{}", Lists.newArrayList("1", "2", "3")));
    Assertions.assertEquals("a=1,b=2,a+b=\\3", StringHelper.format("a={},b={},a+b=\\\\{}", Lists.newArrayList("1", "2", "3")));
    Assertions.assertEquals("a={},b={},a+b={}", StringHelper.format("a={},b={},a+b={}"));
    Assertions.assertEquals("a=1,b=2,a+b=3", StringHelper.format("a={},b={},a+b={}", Lists.newArrayList("1", "2", "3", "4")));
    Assertions.assertEquals("a=1,b=2,a+b={}", StringHelper.format("a={},b={},a+b={}", Lists.newArrayList("1", "2")));

    Assertions.assertEquals("a=1,b=2,a+b=3", StringHelper.format("a={},b={},a+b={}", "1", "2", "3"));
    Assertions.assertEquals("a=1,b=2,a+b=\\{3}", StringHelper.format("a={},b={},a+b=\\{{}}", "1", "2", "3"));
    Assertions.assertEquals("a=1,b=2,a+b={}3", StringHelper.format("a={},b={},a+b=\\{}{}", "1", "2", "3"));
    Assertions.assertEquals("a=1,b=2,a+b=\\3", StringHelper.format("a={},b={},a+b=\\\\{}", "1", "2", "3"));
    Assertions.assertEquals("a={},b={},a+b={}", StringHelper.format("a={},b={},a+b={}"));
    Assertions.assertEquals("a=1,b=2,a+b=3", StringHelper.format("a={},b={},a+b={}", "1", "2", "3", "4"));
    Assertions.assertEquals("a=1,b=2,a+b={}", StringHelper.format("a={},b={},a+b={}", "1", "2"));
    Assertions.assertEquals("a=1,b=[null],a+b={}", StringHelper.format("a={},b={},a+b={}", "1", null));
  }

  @Test
  void testTimes() {
    Assertions.assertEquals(0, StringHelper.times("", ""));
    Assertions.assertEquals(0, StringHelper.times("abc", "1"));
    Assertions.assertEquals(1, StringHelper.times("aaa", "aa"));
    Assertions.assertEquals(1, StringHelper.times("abc123xyz", "123"));
    Assertions.assertEquals(2, StringHelper.times("xyz&abc&123", "&"));
    Assertions.assertEquals(2, StringHelper.times("Abc&abc&ABC&abC", "ab"));
    Assertions.assertEquals(4, StringHelper.times("Abc&abc&ABC&abC", "ab", false));
  }

  @Test
  void testTrim() {
    Assertions.assertEquals("123", StringHelper.trim("===123===", "="));
    Assertions.assertEquals("123===", StringHelper.trim("===123===", "=", 1));
    Assertions.assertEquals("===123", StringHelper.trim("===123===", "=", -1));
  }

  @Test
  void testRandom() {
    Assertions.assertNotNull(StringHelper.random(10));
    Assertions.assertEquals(10, StringHelper.random(10, StringHelper.NUMBERS).length());
    Assertions.assertFalse(StringHelper.random(10, StringHelper.LETTERS).contains("0"));
    Assertions.assertFalse(StringHelper.random(10, StringHelper.NUMBERS_WITHOUT_ZERO).contains("0"));
  }

  @Test
  void testBetween() {
    Assertions.assertIterableEquals(Lists.newArrayList("abc", "def"), StringHelper.between("[abc] xyz [def]", "[", "]"));
  }

  @Test
  void testCase() {
    Assertions.assertEquals("AbC", StringHelper.toLowerCase("ABC", 1));
    Assertions.assertEquals("aBc", StringHelper.toUpperCase("abc", 1));

    Assertions.assertEquals("ABC", StringHelper.toLowerCase("ABC", 1, 0));
    Assertions.assertEquals("abc", StringHelper.toUpperCase("abc", 1, 0));

    Assertions.assertEquals("Abc", StringHelper.toLowerCase("ABC", 1, 2));
    Assertions.assertEquals("aBC", StringHelper.toUpperCase("abc", 1, 2));

    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.toLowerCase("ABC", 1, 3));
    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.toUpperCase("abc", 1, 3));

    // ======================================================

    Assertions.assertEquals("ThereIsAWord", StringHelper.studlyCase("  there is a word"));
    Assertions.assertEquals("ThereIsAWord", StringHelper.studlyCase("there_is_a_word  "));
    Assertions.assertEquals("ThereIsAWord", StringHelper.studlyCase(" there-is-a-word "));
    Assertions.assertEquals("", StringHelper.studlyCase("   "));

    Assertions.assertEquals("thereIsAWord", StringHelper.camelCase("  there is a word"));
    Assertions.assertEquals("thereIsAWord", StringHelper.camelCase("there_is_a_word  "));
    Assertions.assertEquals("thereIsAWord", StringHelper.camelCase(" there-is-a-word "));
    Assertions.assertEquals("", StringHelper.camelCase("   "));

    Assertions.assertEquals("there_is_a_word", StringHelper.snakeCase("  there is a word"));
    Assertions.assertEquals("there_is_a_word", StringHelper.snakeCase("there_is_a_word  "));
    Assertions.assertEquals("there_is_a_word", StringHelper.snakeCase(" there-is-a-word "));
    Assertions.assertEquals("", StringHelper.snakeCase("   "));

    Assertions.assertEquals("there-is-a-word", StringHelper.kebabCase("  there is a word"));
    Assertions.assertEquals("there-is-a-word", StringHelper.kebabCase("there_is_a_word  "));
    Assertions.assertEquals("there-is-a-word", StringHelper.kebabCase(" there-is-a-word "));
    Assertions.assertEquals("", StringHelper.kebabCase("   "));
  }

  @Test
  void testSimplify() {
    Assertions.assertEquals("sample english sentence.", StringHelper.simplify("  sample   english   sentence.   "));
  }

  @Test
  void testPad() {
    Assertions.assertEquals("00000", StringHelper.repeatUntil("0", 5));
    Assertions.assertEquals("01010", StringHelper.repeatUntil("01", 5));
    Assertions.assertEquals("01201", StringHelper.repeatUntil("012", 5));

    String sentence = "binary Numbers look like ";
    Assertions.assertEquals(sentence + "...", StringHelper.fill(sentence, ".", -28));
    Assertions.assertEquals(sentence + "01010", StringHelper.fill(sentence, "01", -30));
    Assertions.assertEquals("!@#!@#!" + sentence, StringHelper.fill(sentence, "!@#", 32));
  }

  @Test
  void testContains() {
    Assertions.assertTrue(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "cD", "001")));
    Assertions.assertTrue(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cD")));
    Assertions.assertTrue(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cd"), false, true));
    Assertions.assertTrue(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "cd", "ef"), false, false));

    Assertions.assertFalse(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "cd", "001")));
    Assertions.assertFalse(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cd"), true, true));
    Assertions.assertFalse(StringHelper.haveAll("abcDEF001", Lists.newArrayList("ab", "bc", "cD"), true, false));


    Assertions.assertTrue(StringHelper.haveAny("abcDEF001", Lists.newArrayList("aba", "cD", "002")));
    Assertions.assertTrue(StringHelper.haveAny("abcDEF001", Lists.newArrayList("aba", "cd", "002"), false));

    Assertions.assertFalse(StringHelper.haveAny("abcDEF001", Lists.newArrayList("aba", "cdc", "002")));
  }

  @Test
  void testInsertAndRemove() {
    Assertions.assertEquals("xyz123456", StringHelper.insert("123456", 0, "xyz"));
    Assertions.assertEquals("123xyz456", StringHelper.insert("123456", 3, "xyz"));
    Assertions.assertEquals("123456xyz", StringHelper.insert("123456", 6, "xyz"));

    Assertions.assertEquals("1234xyz56", StringHelper.insert("123456", -2, "xyz"));
    Assertions.assertEquals("12xyz3456", StringHelper.insert("123456", -4, "xyz"));


    Assertions.assertEquals("123456", StringHelper.remove("123xyz456", 3, 6));
    Assertions.assertEquals("123xyz", StringHelper.remove("123xyz456", 6, 9));
    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.remove("123xyz456", 6, 10), "删除字符下标越界");
    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.remove("123xyz456", -1, 3), "删除字符下标越界");
    Assertions.assertThrows(DiggerStringException.class, () -> StringHelper.remove("123xyz456", 5, 4), "删除范围下标start不能大于end");

    Assertions.assertEquals("xyz", StringHelper.remove("123xyz456", Lists.newArrayList("123", "456")));
    Assertions.assertEquals("", StringHelper.remove("123xyz456", Lists.newArrayList("123", "456", "xyz")));
    Assertions.assertEquals("_abc_123xyz456", StringHelper.remove("123xyz456_abc_123xyz456", Lists.newArrayList("123", "456", "xyz"), 1));
    Assertions.assertEquals("123xyz456_abc_", StringHelper.remove("123xyz456_abc_123xyz456", Lists.newArrayList("123", "456", "xyz"), -1));
    Assertions.assertEquals("123xyz456_abc_", StringHelper.remove("123xyz456_abc_123xyz456", Lists.newArrayList("123", "456", "XYZ"), -1, false));
  }

  @Test
  void testStartsAndEndsWith() {
    Assertions.assertTrue(StringHelper.startsWith("123xyz456", "789", "456", "123"));
    Assertions.assertFalse(StringHelper.startsWith("123xyz456", "abc", "xyz"));
    Assertions.assertTrue(StringHelper.startsWith("abc123xyz", false, "123", "ABC"));

    Assertions.assertTrue(StringHelper.endsWith("123xyz456", "789", "456", "123"));
    Assertions.assertFalse(StringHelper.endsWith("123xyz456", "abc", "xyz"));
    Assertions.assertTrue(StringHelper.endsWith("abc123xyz", false, "123", "XYZ"));

    Assertions.assertTrue(StringHelper.startsWith("123xyz456", Lists.newArrayList("789", "456", "123")));
    Assertions.assertFalse(StringHelper.startsWith("123xyz456", Lists.newArrayList("abc", "xyz")));
    Assertions.assertTrue(StringHelper.startsWith("abc123xyz", false, Lists.newArrayList("123", "ABC")));

    Assertions.assertTrue(StringHelper.endsWith("123xyz456", Lists.newArrayList("789", "456", "123")));
    Assertions.assertFalse(StringHelper.endsWith("123xyz456", Lists.newArrayList("abc", "xyz")));
    Assertions.assertTrue(StringHelper.endsWith("abc123xyz", false, Lists.newArrayList("123", "XYZ")));

    // ====================================================================================

    Assertions.assertEquals("abcxyz", StringHelper.insureStartsWith("xyz", "abc", true));
    Assertions.assertEquals("abcABCxyz", StringHelper.insureStartsWith("ABCxyz", "abc", true));
    Assertions.assertEquals("ABCxyz", StringHelper.insureStartsWith("ABCxyz", "abc", false));

    Assertions.assertEquals("abcxyz", StringHelper.insureEndsWith("abc", "xyz", true));
    Assertions.assertEquals("abcXYZxyz", StringHelper.insureEndsWith("abcXYZ", "xyz", true));
    Assertions.assertEquals("abcXYZ", StringHelper.insureEndsWith("abcXYZ", "xyz", false));
  }

  @Test
  void testBrief() {
    Assertions.assertEquals("Install the plugi...", StringHelper.brief("Install the plugin; Restart Eclipse and go to Window", 20, "..."));
    Assertions.assertEquals("默认逻辑是当表单验证失败时,把按钮...", StringHelper.brief("默认逻辑是当表单验证失败时,把按钮给变灰色", 20, "..."));
  }

  @Test
  void testWrap() {
    Assertions.assertEquals("abc[123]xyz[123]", StringHelper.wrap("abc123xyz123", "123", "[", "]"));
  }

}
