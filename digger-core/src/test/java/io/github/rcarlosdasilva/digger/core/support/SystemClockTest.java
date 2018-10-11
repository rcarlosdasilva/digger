package io.github.rcarlosdasilva.digger.core.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SystemClockTest {

  @Test
  void test() throws InterruptedException {
    long now = SystemClock.INSTANCE.now();
    Thread.sleep(100);
    long after = SystemClock.INSTANCE.now();

    Assertions.assertTrue(after > now);
  }
}
