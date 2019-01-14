package io.github.rcarlosdasilva.digger.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.rcarlosdasilva.digger.core.misc.Pair;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class RandomHelperTest {

  @Test
  void testString() {
    Assertions.assertNotNull(RandomHelper.string(10));
    Assertions.assertEquals(10, RandomHelper.string(10, RandomHelper.NUMBERS).length());
    Assertions.assertFalse(RandomHelper.string(10, RandomHelper.LETTERS).contains("0"));
    Assertions.assertFalse(RandomHelper.string(10, RandomHelper.NUMBERS_WITHOUT_ZERO).contains("0"));
  }

  @Test
  void testCollection() {
    ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
    MatcherAssert.assertThat(RandomHelper.element(list), Matchers.isIn(list));
    MatcherAssert.assertThat(RandomHelper.element(list), not(is(6)));

    Map<String, Integer> map = Maps.newHashMap();
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    Pair<String, Integer> pair = RandomHelper.element(map);
    Assertions.assertNotNull(pair);
    Assertions.assertTrue(map.containsKey(pair.getFirst()));
    Assertions.assertTrue(map.containsValue(pair.getSecond()));
    Assertions.assertEquals(pair.getSecond(), map.get(pair.getFirst()));
  }

  @Test
  void testColor() {
    Color color = RandomHelper.color();
    Assertions.assertNotNull(color);

    String colorHex = RandomHelper.colorHex();
    Assertions.assertNotNull(colorHex);
    Assertions.assertEquals(7, colorHex.length());
    Assertions.assertEquals('#', colorHex.charAt(0));
  }

  @Test
  void testDate() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime b = LocalDateTime.parse("2018-05-01 12:00:00", dtf);
    LocalDateTime e = LocalDateTime.parse("2018-10-01 12:00:00", dtf);

    Pair<LocalDateTime, LocalDateTime> pair1 = new Pair<>(b, e);
    Pair<Date, Date> pair2 = new Pair<>(DateHelper.toDate(b), DateHelper.toDate(e));

    LocalDateTime localDateTime = RandomHelper.localDateTime(pair1);
    Date date = RandomHelper.date(pair2);

    Assertions.assertNotNull(localDateTime);
    Assertions.assertNotNull(date);

    Assertions.assertTrue(localDateTime.isAfter(b));
    Assertions.assertTrue(localDateTime.isBefore(e));
  }
}
