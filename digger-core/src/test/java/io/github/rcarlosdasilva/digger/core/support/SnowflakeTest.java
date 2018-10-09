package io.github.rcarlosdasilva.digger.core.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SnowflakeTest {

   @Test
  void test() {
     Snowflake sf = new Snowflake(1, 5);
     Long id = sf.id();

     System.out.println(id);
     Assertions.assertNotNull(id);
   }
}
