package io.github.rcarlosdasilva.digger.core.misc;

import com.google.common.collect.Maps;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

class MiscTest {

  @Test
  void testPair() {
    Pair<String, Integer> pair = new Pair<>("num", 1);

    MatcherAssert.assertThat(pair.toString(), not(containsString("@")));
  }

  @Test
  void testConvert() {
    Map<String, Boolean> original = Maps.newHashMap();
    original.put("A", true);
    original.put("B", false);

    for (Map.Entry<String, Boolean> entry : original.entrySet()) {
      Pair<String, Boolean> converted = Pair.from(entry);
      Assertions.assertNotNull(converted);

      if ("A".equals(converted.getFirst())) {
        Assertions.assertTrue(converted.getSecond());
      } else {
        Assertions.assertFalse(converted.getSecond());
      }
    }
  }

}
