package io.github.rcarlosdasilva.digger.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

class DateHelperTest {

  @Test
  void test() {
    ZoneOffset offset = OffsetDateTime.now().getOffset();

    Date now = new Date();
    Calendar instance = Calendar.getInstance();
    instance.setTime(now);

    LocalDateTime localDateTime = DateHelper.toLocalDateTime(now);
    Assertions.assertNotNull(localDateTime);
    Assertions.assertEquals(now.getTime(), localDateTime.toInstant(offset).toEpochMilli());


    LocalDate localDate = DateHelper.toLocalDate(now);
    Assertions.assertNotNull(localDate);
    Assertions.assertEquals(instance.get(Calendar.DAY_OF_YEAR), localDate.getDayOfYear());


    LocalTime localTime = DateHelper.toLocalTime(now);
    Assertions.assertNotNull(localTime);
    Assertions.assertEquals(instance.get(Calendar.MINUTE), localTime.getMinute());


    Date date1 = DateHelper.toDate(localDateTime);
    Date date2 = DateHelper.toDate(localDate, localTime);
    Assertions.assertEquals(date1, date2);
  }
}
