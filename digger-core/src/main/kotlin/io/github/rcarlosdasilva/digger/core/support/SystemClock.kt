package io.github.rcarlosdasilva.digger.core.support

import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

/**
 * 参考 http://git.oschina.net/yu120/sequence，Kotlin重写
 * <p>
 * 高并发场景下System.currentTimeMillis()的性能问题的优化
 * <p>
 * System.currentTimeMillis()的调用比new一个普通对象要耗时的多（具体耗时高出多少我还没测试过，有人说是100倍左右）
 * <p>
 * System.currentTimeMillis()之所以慢是因为去跟系统打了一次交道
 * <p>
 * 后台定时更新时钟，JVM退出时，线程自动回收
 * <p>
 * 10亿：43410,206,210.72815533980582%
 * <p>
 * 1亿：4699,29,162.0344827586207%
 * <p>
 * 1000万：480,12,40.0%
 * <p>
 * 100万：50,10,5.0%
 * <p>
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object SystemClock {

  private const val period: Long = 1
  private val now: AtomicLong = AtomicLong(System.currentTimeMillis())

  init {
    scheduleClockUpdating()
  }

  private fun scheduleClockUpdating() {
    val executor = ScheduledThreadPoolExecutor(1) { r ->
      Thread(r, "System Clock").apply { this.isDaemon = true }
    }

    executor.scheduleAtFixedRate({ now.set(System.currentTimeMillis()) }, period, period, TimeUnit.MILLISECONDS)
  }

  fun now(): Long = now.get()

}