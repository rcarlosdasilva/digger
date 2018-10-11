package io.github.rcarlosdasilva.digger.core.support;

import io.github.rcarlosdasilva.digger.core.support.Caller.CallerDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class CallerTest {

  private static MockCaller mock;

  @BeforeAll
  static void beforeAll() {
    mock = new MockCaller();
  }

  @Test
  void testGet() {
    CallerDetail self = mock.call0();
    Assertions.assertNotNull(self);
    Assertions.assertEquals(MockCaller.class, self.getClassType());
    Assertions.assertEquals("call0", self.getMethodName());

    CallerDetail caller = mock.call1();
    Assertions.assertNotNull(caller);
    Assertions.assertEquals(CallerTest.class, caller.getClassType());
    Assertions.assertEquals("testGet", caller.getMethodName());
  }

  @Test
  void testIs() {
    Assertions.assertTrue(mock.callIs0());
    Assertions.assertTrue(mock.callIs1());
    Assertions.assertFalse(mock.callIsBad());
  }

  @Test
  void testFrom() {
    Assertions.assertTrue(mock.callFrom0());
    Assertions.assertTrue(mock.callFrom1());
    Assertions.assertFalse(mock.callFromBad());
  }
}

class MockCaller {

  CallerDetail call0() {
    // 返回的是当前方法
    return Caller.INSTANCE.get(0);
  }

  CallerDetail call1() {
    // 返回的是调用者的方法
    return Caller.INSTANCE.get();
  }

  boolean callIs0() {
    return Caller.INSTANCE.is(CallerTest.class);
  }

  boolean callIs1() {
    return Caller.INSTANCE.is(CallerTest.class, "testIs");
  }

  boolean callIsBad() {
    return Caller.INSTANCE.is(Object.class);
  }

  boolean callFrom0() {
    return Caller.INSTANCE.from(Method.class);
  }

  boolean callFrom1() {
    return callFrom11();
  }

  private boolean callFrom11() {
    return Caller.INSTANCE.from(CallerTest.class, "testFrom");
  }

  boolean callFromBad() {
    return Caller.INSTANCE.from(Object.class);
  }
}
