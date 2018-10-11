package io.github.rcarlosdasilva.digger.core.misc;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

class MiscTest {

  @Test
  void testPair() {
    Pair<String, Integer> pair = new Pair<>("num", 1);

    MatcherAssert.assertThat(pair.toString(), not(containsString("@")));
  }

}
