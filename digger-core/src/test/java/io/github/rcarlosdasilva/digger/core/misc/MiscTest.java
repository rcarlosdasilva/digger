package io.github.rcarlosdasilva.digger.core.misc;

import com.google.common.collect.Lists;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

class MiscTest {

  @Test
  void testPair() {
    Pair<String, Integer> pair = new Pair<>("num", 1);

    System.out.println(pair.toString());
    MatcherAssert.assertThat(pair.toString(), not(containsString("@")));
  }

  @Test
  void testPair2List() {
    Pair<String, Integer> p1 = new Pair<>("key", 1);
    Pair<String, Integer> p2 = new Pair<>("key", 2);
    Pair<String, Integer> p3 = new Pair<>("key", 3);
    Pair<String, Integer> p4 = new Pair<>("key", 4);
    Pair<String, Integer> p5 = new Pair<>("key", 5);

    List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, p3, null, p4, p5);
    List<Integer> list = PairHelper.toList(pairs);
    MatcherAssert.assertThat(list, hasSize(5));
    MatcherAssert.assertThat(list, contains(1, 2, 3, 4, 5));
  }

  @Test
  void testPair2Map() {
    Pair<String, Integer> p1 = new Pair<>("a", 1);
    Pair<String, Integer> p2 = new Pair<>("b", 2);
    Pair<String, Integer> p3 = new Pair<>("c", 3);
    Pair<String, Integer> p4 = new Pair<>("d", 4);
    Pair<String, Integer> p5 = new Pair<>("e", 5);

    List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, p3, null, p4, p5);
    Map<String, Integer> map = PairHelper.toMap(pairs);
    MatcherAssert.assertThat(map, hasKey("e"));
    MatcherAssert.assertThat(map, hasValue(1));
    MatcherAssert.assertThat(map.values(), hasSize(5));
  }

  @Test
  void testAssembly() {
    Pair<String, Integer> p1 = new Pair<>("a", 1);
    Pair<String, Integer> p2 = new Pair<>("b", 2);
    Pair<String, Integer> p3 = new Pair<>("c", 3);

    List<Pair<String, Integer>> pairs = Lists.newArrayList(p1, p2, null, p3);
    String custom = PairHelper.assembly(pairs, "->", ",");
    String parameters = PairHelper.toParameters(pairs);

    Assertions.assertEquals("a->1,b->2,c->3", custom);
    Assertions.assertEquals("a=1&b=2&c=3", parameters);
  }
}
