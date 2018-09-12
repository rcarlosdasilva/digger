package io.github.rcarlosdasilva.digger.repository.mybatis

import com.baomidou.mybatisplus.core.MybatisConfiguration
import org.apache.ibatis.session.ExecutorType
import java.util.*

enum class DBType {
  MYSQL, ORACLE, SQL_SERVER
}

/**
 * 支持的连接池类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class ConnectionPoolType {
  /**
   * 不使用连接池
   */
  NONE,
  /**
   * 阿里的Druid，推荐
   */
  DRUID,
  /**
   * C3P0
   */
  C3P0,
  /**
   * Apache Commons DBCP
   */
  DBCP,
  /**
   * HikariCP
   */
  HIKARI;
}