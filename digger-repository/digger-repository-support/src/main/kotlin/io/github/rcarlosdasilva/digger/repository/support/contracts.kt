package io.github.rcarlosdasilva.digger.repository.support

/**
 * 持久层框架
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class RepositoryFrameworks {
  /**
   * 基于MyBatis（MyBatis Plus）
   */
  MYBATIS,
  /**
   * 基于Hibernate（暂时不支持）
   */
  HIBERNATE
}

/**
 * 支持的数据库类型
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class DBType {
  MYSQL, ORACLE, SQL_SERVER, POSTGRESQL
}


/**
 * 支持的连接池类型
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class ConnectionPools {
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
  HIKARI
}