package io.github.rcarlosdasilva.digger.repository.starter

enum class Framework {

  /**
   * 基于MyBatis
   */
  MYBATIS,
  /**
   * 基于Hibernate
   */
  HIBERNATE

}

enum class DBType {
  MYSQL, ORACLE, SQL_SERVER, POSTGRESQL
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