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

class MybatisProperties {

  /**
   * XML文件位置
   */
  var configLocation: String? = null
  /**
   * Mapper位置
   */
  var mapperLocations: Array<String>? = null
  /**
   * Packages to search type aliases. (Package delimiters are ",; \t\n")
   */
  var typeAliasesPackage: String? = null

  var typeEnumsPackage: String? = null
  /**
   * Packages to search for type handlers. (Package delimiters are ",; \t\n")
   */
  var typeHandlersPackage: String? = null
  /**
   * Execution mode for [org.mybatis.spring.SqlSessionTemplate].
   */
  var executorType: ExecutorType? = null
  /**
   * Externalized properties for MyBatis configuration.
   */
  var configurationProperties: Properties? = null
  /**
   * A Configuration object for customize default settings. If [.configLocation]
   * is specified, this property is not used.
   */
  var configuration: MybatisConfiguration? = null

}