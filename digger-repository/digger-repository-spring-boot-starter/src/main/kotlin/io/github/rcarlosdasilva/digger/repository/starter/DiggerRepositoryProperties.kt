package io.github.rcarlosdasilva.digger.repository.starter

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(prefix = "digger.repository.rdb")
class DiggerRepositoryProperties {

  /**
   * 持久层框架，暂时只实现MyBatis
   */
  val framework: Framework = Framework.MYBATIS
  /**
   * 使用什么数据库，暂时只实现MySQL
   */
  val db: DBType = DBType.MYSQL

  /**
   * 使用的连接池库 - [NONE, DRUID, C3P0, DBCP, HIKARI]
   */
  lateinit var pool: ConnectionPoolType
  /**
   * 数据库字段为tiny_int时是否映射为Java的Boolean
   */
  var tinyintToBoolean = true
  /**
   * MyBatis (MyBatis Plus)配置
   */
  val mybatis = MybatisProperties()

  /**
   * Druid 相关配置
   */
  var druid: Properties? = null
  /**
   * c3p0 相关配置
   */
  var c3p0: Properties? = null
  /**
   * DBCP 相关配置
   */
  var dbcp: Properties? = null
  /**
   * Hikari 相关配置
   */
  var hikari: Properties? = null

}