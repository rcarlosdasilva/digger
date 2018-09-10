package io.github.rcarlosdasilva.digger.repository.starter

import com.alibaba.druid.pool.DruidDataSource
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import com.mchange.v2.c3p0.ComboPooledDataSource
import com.zaxxer.hikari.HikariDataSource
import io.github.rcarlosdasilva.digger.repository.mybatis.ConnectionPoolType
import mu.KotlinLogging
import org.apache.commons.dbcp2.BasicDataSource
import org.apache.ibatis.io.VFS
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.IOException
import java.net.URI
import java.net.URL
import javax.sql.DataSource

/**
 * 自动配置持久层连接池
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@Configuration
@ConditionalOnClass(value = [DataSource::class, EmbeddedDatabaseType::class])
@AutoConfigureBefore(value = [DataSourceAutoConfiguration::class])
@EnableTransactionManagement
@EnableConfigurationProperties(value = [DataSourceProperties::class, DiggerRepositoryProperties::class])
open class DiggerRepositoryAutoConfiguration @Autowired constructor(
    private val defaultResourceLoader: DefaultResourceLoader,
    private val dataSourceProperties: DataSourceProperties,
    private val diggerRepositoryProperties: DiggerRepositoryProperties
) {

  private val logger = KotlinLogging.logger {}

  @Bean
  open fun dataSource(): DataSource {
    val finalUrl = handleSpecial(dataSourceProperties.url)

    val builder = DataSourceBuilder.create().apply {
      this.url(finalUrl)
      this.driverClassName(dataSourceProperties.driverClassName)
      this.username(dataSourceProperties.username)
      this.password(dataSourceProperties.password)
    }

    logger.info { "[${diggerRepositoryProperties.db}] - 正在配置${diggerRepositoryProperties.pool}连接池" }

    return builder.type(poolDrivers[diggerRepositoryProperties.pool]).build().apply {
      configurationDataSource(this)
    }
  }

  @Bean
  open fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory = if (diggerRepositoryProperties.framework == Framework.MYBATIS) {
    mybatisSessionFactory()
  } else {
    hibernateSessionFactory()
  }

  @Bean
  open fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate =
      diggerRepositoryProperties.mybatis.executorType?.let { SqlSessionTemplate(sqlSessionFactory, it) }
          ?: run { SqlSessionTemplate(sqlSessionFactory) }

  private fun mybatisSessionFactory(): SqlSessionFactory =
      MybatisSqlSessionFactoryBean().run {
        return this.`object`!!
      }


  private fun hibernateSessionFactory(): SqlSessionFactory {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun configurationDataSource(ds: DataSource) {

  }

  /**
   * 处理各种数据库的特殊连接字符串参数
   */
  private fun handleSpecial(url: String): String = url


  internal class SpringBootVFS : VFS() {

    private val resourceResolver: ResourcePatternResolver

    init {
      resourceResolver = PathMatchingResourcePatternResolver(javaClass.classLoader)
    }

    private fun preserveSubpackageName(uri: URI, rootPath: String): String {
      val uriStr = uri.toString()
      return uriStr.substring(uriStr.indexOf(rootPath))
    }

    override fun isValid() = true

    @Throws(IOException::class)
    override fun list(url: URL, path: String): List<String> {
      val resources = resourceResolver.getResources("classpath*:$path/**/*.class")
      return resources.map { preserveSubpackageName(it.uri, path) }
    }
  }

  companion object {
    val poolDrivers = mapOf(
        Pair(ConnectionPoolType.NONE, DriverManagerDataSource::class.java),
        Pair(ConnectionPoolType.DRUID, DruidDataSource::class.java),
        Pair(ConnectionPoolType.C3P0, ComboPooledDataSource::class.java),
        Pair(ConnectionPoolType.DBCP, BasicDataSource::class.java),
        Pair(ConnectionPoolType.HIKARI, HikariDataSource::class.java)
    )
  }

}