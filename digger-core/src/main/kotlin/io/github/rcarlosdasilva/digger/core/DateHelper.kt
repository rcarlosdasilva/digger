package io.github.rcarlosdasilva.digger.core

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


/**
 * Date Helper
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class DateHelper private constructor() {

  companion object {

    private val SYSTEM_TIME_ZONE = ZoneId.systemDefault()

    /**
     * 将[Date]转换为[LocalDateTime]
     * @param date Date
     * @return LocalDateTime
     */
    @JvmStatic
    fun toLocalDateTime(date: Date) = LocalDateTime.ofInstant(date.toInstant(), SYSTEM_TIME_ZONE)!!

    /**
     * 将[Date]转换为[LocalDate]
     * @param date Date
     * @return LocalDate
     */
    @JvmStatic
    fun toLocalDate(date: Date) = toLocalDateTime(date).toLocalDate()!!

    /**
     * 将[Date]转换为[LocalTime]
     * @param date Date
     * @return LocalTime
     */
    @JvmStatic
    fun toLocalTime(date: Date) = toLocalDateTime(date).toLocalTime()!!

    /**
     * 将[LocalDateTime]转换为[Date]
     * @param ldt LocalDateTime
     * @return Date
     */
    @JvmStatic
    fun toDate(ldt: LocalDateTime) = Date.from(ldt.atZone(SYSTEM_TIME_ZONE).toInstant())!!

    /**
     * 将[LocalDate] / [LocalTime]转换为[Date]
     * @param ld LocalDate
     * @param lt LocalTime?
     * @return Date
     */
    @JvmStatic
    @JvmOverloads
    fun toDate(ld: LocalDate, lt: LocalTime? = null) =
        lt?.let { Date.from(LocalDateTime.of(ld, lt).atZone(SYSTEM_TIME_ZONE).toInstant())!! }
            ?: Date.from(ld.atStartOfDay().atZone(SYSTEM_TIME_ZONE).toInstant())!!

  }
}