package io.github.rcarlosdasilva.digger.repository.starter

import io.github.rcarlosdasilva.digger.repository.support.ConnectionPools
import io.github.rcarlosdasilva.digger.repository.support.DBType
import io.github.rcarlosdasilva.digger.repository.support.RepositoryFrameworks
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import java.util.*

@ConfigurationProperties("digger.repository")
class DiggerRepositoryProperties {

  /**
   * 持久层框架，暂时只实现MyBatis
   */
  var framework = RepositoryFrameworks.MYBATIS
  /**
   * 使用什么数据库，暂时只实现MySQL
   */
  var db = DBType.MYSQL

  /**
   * 使用的连接池库 - [NONE, DRUID, C3P0, DBCP, HIKARI]
   */
  var pool = ConnectionPools.DRUID
  /**
   * MyBatis (MyBatis Plus)配置
   */
  @NestedConfigurationProperty
  var mybatis = MybatisProperties()

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