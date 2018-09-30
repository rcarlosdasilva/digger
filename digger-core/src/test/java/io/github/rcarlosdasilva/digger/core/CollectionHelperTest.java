package io.github.rcarlosdasilva.digger.core;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CollectionHelperTest {

  @Test
  void testRemoveNull() {
    List<String> source = Lists.newArrayList("1", "", "2", null, "3");
    List<String> expected = Lists.newArrayList("1", "", "2", "3");
    List<String> target = CollectionHelper.removeNull(source);
    Assertions.assertNotNull(target);
    Assertions.assertIterableEquals(expected, target);
  }

}
