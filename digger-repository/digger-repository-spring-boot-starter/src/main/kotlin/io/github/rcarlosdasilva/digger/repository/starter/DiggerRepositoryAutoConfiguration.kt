package io.github.rcarlosdasilva.digger.repository.starter

import com.alibaba.druid.pool.DruidDataSource
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS
import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator
import com.baomidou.mybatisplus.core.injector.ISqlInjector
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import com.mchange.v2.c3p0.ComboPooledDataSource
import com.zaxxer.hikari.HikariDataSource
import io.github.rcarlosdasilva.digger.common.exception.DiggerRuntimeException
import io.github.rcarlosdasilva.digger.repository.support.ConnectionPools
import io.github.rcarlosdasilva.digger.repository.support.RepositoryFrameworks
import mu.KotlinLogging
import org.apache.commons.dbcp2.BasicDataSource
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.logging.slf4j.Slf4jImpl
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.annotation.PostConstruct
import javax.sql.DataSource

/**
 * 自动配置持久层连接池
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@Configuration
@ConditionalOnClass(value = [DataSource::class])
//@ConditionalOnBean(value = [DataSourceProperties::class, DiggerRepositoryProperties::class])
@EnableTransactionManagement
@AutoConfigureBefore(value = [DataSourceAutoConfiguration::class])
@EnableConfigurationProperties(value = [DataSourceProperties::class, DiggerRepositoryProperties::class])
open class DiggerRepositoryAutoConfiguration {

  private val logger = KotlinLogging.logger {}

  @Autowired
  lateinit var defaultResourceLoader: DefaultResourceLoader
  @Autowired
  lateinit var dataSourceProperties: DataSourceProperties
  @Autowired
  lateinit var diggerRepositoryProperties: DiggerRepositoryProperties
  @Autowired(required = false)
  var metaObjectHandler: MetaObjectHandler? = null
  @Autowired(required = false)
  var keyGenerator: IKeyGenerator? = null
  @Autowired(required = false)
  var sqlInjector: ISqlInjector? = null

  private val resourceResolver = PathMatchingResourcePatternResolver()
  private lateinit var xx: String

  @PostConstruct
  open fun assert() {
    if (!this::diggerRepositoryProperties.isInitialized) {
      throw DiggerRuntimeException("未找到有效的digger-repository配置属性")
    }
  }

  @Bean
  @ConditionalOnMissingBean
  open fun dataSource(): DataSource {
    val builder = DataSourceBuilder.create().apply {
      this.url(dataSourceProperties.url)
      this.driverClassName(dataSourceProperties.driverClassName!!)
      this.username(dataSourceProperties.username!!)
      this.password(dataSourceProperties.password!!)
    }

    logger.info { "[${diggerRepositoryProperties.db}] - 正在配置${diggerRepositoryProperties.pool}连接池" }

    return builder.type(poolDrivers[diggerRepositoryProperties.pool]).build().apply {
      configureDataSource(this)
    }
  }

  @Bean
  @ConditionalOnMissingBean
  open fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory = if (diggerRepositoryProperties.framework == RepositoryFrameworks.MYBATIS) {
    mybatisSessionFactory(dataSource)
  } else {
    hibernateSessionFactory(dataSource)
  }

  @Bean
  @ConditionalOnMissingBean
  open fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate =
      diggerRepositoryProperties.mybatis.executorType?.let {
        SqlSessionTemplate(sqlSessionFactory, it)
      } ?: SqlSessionTemplate(sqlSessionFactory)

  @Bean
  @ConditionalOnMissingBean
  open fun mapperScannerConfigurer(): MapperScannerConfigurer =
      MapperScannerConfigurer().apply {
        diggerRepositoryProperties.mybatis.mapperInterfacePackages?.let {
          setBasePackage(it.joinToString(","))
        } ?: let {
          logger.info { "[MYSQL] 将默认自动扫描所有被Mapper注解的接口" }
          setAnnotationClass(Mapper::class.java)
        }
        setSqlSessionFactoryBeanName("sqlSessionFactory")
      }

  private fun mybatisSessionFactory(dataSource: DataSource): SqlSessionFactory =
      MybatisSqlSessionFactoryBean().let { factoryBean ->
        factoryBean.setDataSource(dataSource)
        factoryBean.vfs = SpringBootVFS::class.java

        with(diggerRepositoryProperties.mybatis) {
          // Mybatis XML配置文件位置
          configLocation?.let {
            factoryBean.setConfigLocation(defaultResourceLoader.getResource(it))
          }
          // MyBatis Mapper 所对应的 XML 文件位置
          mapperLocations?.let {
            val toList = it.flatMap { ml -> resourceResolver.getResources(ml).toList() }.toTypedArray()
            factoryBean.setMapperLocations(toList)
          } ?: let {
            logger.info { "[MySQL] - 默认自动扫描路径\"resources/storage/mapper/xml\"下的所有Mapper XML文件" }
            factoryBean.setMapperLocations(PathMatchingResourcePatternResolver().getResources("classpath:/storage/mapper/xml/*Mapper.xml"))
          }
          // MyBaits Entity包扫描路径
          typeAliasesPackages?.let {
            factoryBean.setTypeAliasesPackage(it.joinToString(","))
          }
          // MyBatis Entity父类
          typeAliasesSuperType?.let {
            factoryBean.setTypeAliasesSuperType(it)
          }
          if (typeAliasesPackages?.isEmpty() != false && typeAliasesSuperType == null) {
            logger.warn { "[MySQL] - 未指定typeAliasesPackage或typeAliasesSuperType属性，将自动扫描继承自BasicEntity的所有Entity" }
            factoryBean.setTypeAliasesSuperType(Any::class.java)
          }
          // TypeHandler 扫描路径
          typeHandlersPackage?.let {
            factoryBean.setTypeHandlersPackage(it)
          }
          // 枚举类 扫描路径
          typeEnumsPackage?.let {
            factoryBean.setTypeEnumsPackage(it)
          }

          // 扩展属性
          configurationProperties?.let {
            factoryBean.setConfigurationProperties(it)
          }

          // 原生 MyBatis 所支持的配置，如未提供，生成默认配置
          val mc = configuration ?: MybatisConfiguration().apply {
            setDefaultScriptingLanguage(MybatisXMLLanguageDriver::class.java)
            // 默认使用SLF4J
            logImpl = Slf4jImpl::class.java
            defaultExecutorType = ExecutorType.REUSE
          }
          factoryBean.setConfiguration(mc)

          // MyBatis-Plus 全局策略配置
          factoryBean.setGlobalConfig(mpConfig.apply {
            //注入填充器
            metaObjectHandler?.let {
              metaObjectHandler = it
            }
            //注入主键生成器
            keyGenerator?.let {
              dbConfig.keyGenerator = keyGenerator
            }
            //注入sql注入器
            sqlInjector?.let {
              sqlInjector = sqlInjector
            }
          })

          // 配置分页插件
          factoryBean.setPlugins(arrayOf<Interceptor>(PaginationInterceptor()))
        }

        logger.info { "[MySQL] - MyBatis 自动配置完毕" }
        return factoryBean.`object`!!
      }


  private fun hibernateSessionFactory(dataSource: DataSource): SqlSessionFactory {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun configureDataSource(ds: DataSource) {
    when (diggerRepositoryProperties.pool) {
      ConnectionPools.DRUID -> configureDruidDataSource(ds)
      ConnectionPools.C3P0 -> configureC3p0DataSource(ds)
      ConnectionPools.DBCP -> configureDbcpDataSource(ds)
      ConnectionPools.HIKARI -> configureHikariDataSource(ds)
      else -> {
        // do nothing when not use pool
      }
    }
  }

  private fun configureHikariDataSource(ds: DataSource) {
    val config = diggerRepositoryProperties.hikari
    config ?: return

    val dataSource = ds as HikariDataSource
    with(config) {
      get("autoCommit")?.let { dataSource.isAutoCommit = it as Boolean }
      get("connectionTimeout")?.let { dataSource.connectionTimeout = it as Long }
      get("idleTimeout")?.let { dataSource.idleTimeout = it as Long }
      get("maxLifetime")?.let { dataSource.maxLifetime = it as Long }
      get("connectionTestQuery")?.let { dataSource.connectionTestQuery = it as String? }
      get("minimumIdle")?.let { dataSource.minimumIdle = it as Int }
      get("maximumPoolSize")?.let { dataSource.maximumPoolSize = it as Int }
      get("metricRegistry")?.let { dataSource.metricRegistry = it }
      get("healthCheckRegistry")?.let { dataSource.healthCheckRegistry = it }
      get("poolName")?.let { dataSource.poolName = it as String? }
      get("initializationFailTimeout")?.let { dataSource.initializationFailTimeout = it as Long }
      get("isolateInternalQueries")?.let { dataSource.isIsolateInternalQueries = it as Boolean }
      get("allowPoolSuspension")?.let { dataSource.isAllowPoolSuspension = it as Boolean }
      get("readOnly")?.let { dataSource.isReadOnly = it as Boolean }
      get("registerMbeans")?.let { dataSource.isRegisterMbeans = it as Boolean }
      get("catalog")?.let { dataSource.catalog = it as String? }
      get("connectionInitSql")?.let { dataSource.connectionInitSql = it as String? }
      get("transactionIsolation")?.let { dataSource.transactionIsolation = it as String? }
      get("validationTimeout")?.let { dataSource.validationTimeout = it as Long }
      get("leakDetectionThreshold")?.let { dataSource.leakDetectionThreshold = it as Long }
    }
  }

  private fun configureDbcpDataSource(ds: DataSource) {
    val config = diggerRepositoryProperties.dbcp
    config ?: return

    val dataSource = ds as BasicDataSource
    with(config) {
      get("defaultAutoCommit")?.let { dataSource.defaultAutoCommit = it as Boolean? }
      get("defaultReadOnly")?.let { dataSource.defaultReadOnly = it as Boolean? }
      get("defaultTransactionIsolation")?.let { dataSource.defaultTransactionIsolation = it as Int }
      get("defaultQueryTimeout")?.let { dataSource.defaultQueryTimeout = it as Int? }
      get("defaultCatalog")?.let { dataSource.defaultCatalog = it as String? }
      get("cacheState")?.let { dataSource.cacheState = it as Boolean }
      get("lifo")?.let { dataSource.lifo = it as Boolean }
      get("maxTotal")?.let { dataSource.maxTotal = it as Int }
      get("maxIdle")?.let { dataSource.maxIdle = it as Int }
      get("minIdle")?.let { dataSource.minIdle = it as Int }
      get("initialSize")?.let { dataSource.initialSize = it as Int }
      get("maxWaitMillis")?.let { dataSource.maxWaitMillis = it as Long }
      get("poolPreparedStatements")?.let { dataSource.isPoolPreparedStatements = it as Boolean }
      get("maxOpenPreparedStatements")?.let { dataSource.maxOpenPreparedStatements = it as Int }
      get("testOnCreate")?.let { dataSource.testOnCreate = it as Boolean }
      get("testOnBorrow")?.let { dataSource.testOnBorrow = it as Boolean }
      get("testOnReturn")?.let { dataSource.testOnReturn = it as Boolean }
      get("timeBetweenEvictionRunsMillis")?.let { dataSource.timeBetweenEvictionRunsMillis = it as Long }
      get("numTestsPerEvictionRun")?.let { dataSource.numTestsPerEvictionRun = it as Int }
      get("minEvictableIdleTimeMillis")?.let { dataSource.minEvictableIdleTimeMillis = it as Long }
      get("testWhileIdle")?.let { dataSource.testWhileIdle = it as Boolean }
      get("validationQuery")?.let { dataSource.validationQuery = it as String? }
      get("validationQueryTimeout")?.let { dataSource.validationQueryTimeout = it as Int }
      get("accessToUnderlyingConnectionAllowed")?.let { dataSource.isAccessToUnderlyingConnectionAllowed = it as Boolean }
      get("maxConnLifetimeMillis")?.let { dataSource.maxConnLifetimeMillis = it as Long }
      get("logExpiredConnections")?.let { dataSource.logExpiredConnections = it as Boolean }
      get("jmxName")?.let { dataSource.jmxName = it as String? }
      get("enableAutoCommitOnReturn")?.let { dataSource.enableAutoCommitOnReturn = it as Boolean }
      get("rollbackOnReturn")?.let { dataSource.rollbackOnReturn = it as Boolean }
      get("fastFailValidation")?.let { dataSource.fastFailValidation = it as Boolean }

      get("disconnectionSqlCodes")?.let {
        @Suppress("UNCHECKED_CAST")
        dataSource.setDisconnectionSqlCodes(it as MutableCollection<String>?)
      }
      get("connectionInitSqls")?.let {
        @Suppress("UNCHECKED_CAST")
        dataSource.setConnectionInitSqls(it as MutableCollection<String>?)
      }
    }
  }

  private fun configureC3p0DataSource(ds: DataSource) {
    val config = diggerRepositoryProperties.c3p0
    config ?: return

    val dataSource = ds as ComboPooledDataSource
    with(config) {
      get("testConnectionOnCheckout")?.let { dataSource.isTestConnectionOnCheckout = it as Boolean }
      get("testConnectionOnCheckin")?.let { dataSource.isTestConnectionOnCheckin = it as Boolean }
      get("dataSourceName")?.let { dataSource.dataSourceName = it as String? }
      get("minPoolSize")?.let { dataSource.minPoolSize = it as Int }
      get("maxPoolSize")?.let { dataSource.maxPoolSize = it as Int }
      get("checkoutTimeout")?.let { dataSource.checkoutTimeout = it as Int }
      get("idleConnectionTestPeriod")?.let { dataSource.idleConnectionTestPeriod = it as Int }
      get("maxConnectionAge")?.let { dataSource.maxConnectionAge = it as Int }
      get("maxIdleTime")?.let { dataSource.maxIdleTime = it as Int }
      get("maxIdleTimeExcessConnections")?.let { dataSource.maxIdleTimeExcessConnections = it as Int }
      get("propertyCycle")?.let { dataSource.propertyCycle = it as Int }
      get("numHelperThreads")?.let { dataSource.numHelperThreads = it as Int }
      get("unreturnedConnectionTimeout")?.let { dataSource.unreturnedConnectionTimeout = it as Int }
      get("debugUnreturnedConnectionStackTraces")?.let { dataSource.isDebugUnreturnedConnectionStackTraces = it as Boolean }
      get("forceSynchronousCheckins")?.let { dataSource.isForceSynchronousCheckins = it as Boolean }
      get("maxStatements")?.let { dataSource.maxStatements = it as Int }
      get("maxStatementsPerConnection")?.let { dataSource.maxStatementsPerConnection = it as Int }
      get("maxAdministrativeTaskTime")?.let { dataSource.maxAdministrativeTaskTime = it as Int }
      get("preferredTestQuery")?.let { dataSource.preferredTestQuery = it as String? }
      get("statementCacheNumDeferredCloseThreads")?.let { dataSource.statementCacheNumDeferredCloseThreads = it as Int }
      get("usesTraditionalReflectiveProxies")?.let { dataSource.isUsesTraditionalReflectiveProxies = it as Boolean }
      get("automaticTestTable")?.let { dataSource.automaticTestTable = it as String? }
      get("acquireIncrement")?.let { dataSource.acquireIncrement = it as Int }
      get("acquireRetryDelay")?.let { dataSource.acquireRetryDelay = it as Int }
      get("acquireRetryAttempts")?.let { dataSource.acquireRetryAttempts = it as Int }
      get("connectionTesterClassName")?.let { dataSource.connectionTesterClassName = it as String? }
      get("initialPoolSize")?.let { dataSource.initialPoolSize = it as Int }
      get("contextClassLoaderSource")?.let { dataSource.contextClassLoaderSource = it as String? }
      get("privilegeSpawnedThreads")?.let { dataSource.isPrivilegeSpawnedThreads = it as Boolean }
      get("forceUseNamedDriverClass")?.let { dataSource.isForceUseNamedDriverClass = it as Boolean }
    }
  }

  private fun configureDruidDataSource(ds: DataSource) {
    val config = diggerRepositoryProperties.druid
    config ?: return

    val datasource = ds as DruidDataSource
    with(config) {
      get("initialSize")?.let { datasource.initialSize = it as Int }
      get("minIdle")?.let { datasource.minIdle = it as Int }
      get("maxActive")?.let { datasource.maxActive = it as Int }
      get("maxWait")?.let { datasource.maxWait = it as Long }
      get("timeBetweenEvictionRunsMillis")?.let { datasource.timeBetweenEvictionRunsMillis = it as Long }
      get("minEvictableIdleTimeMillis")?.let { datasource.minEvictableIdleTimeMillis = it as Long }
      get("validationQuery")?.let { datasource.validationQuery = it as String? }
      get("testWhileIdle")?.let { datasource.isTestWhileIdle = it as Boolean }
      get("testOnBorrow")?.let { datasource.isTestOnBorrow = it as Boolean }
      get("testOnReturn")?.let { datasource.isTestOnReturn = it as Boolean }
      get("poolPreparedStatements")?.let { datasource.isPoolPreparedStatements = it as Boolean }
      get("maxPoolPreparedStatementPerConnectionSize")?.let { datasource.maxPoolPreparedStatementPerConnectionSize = it as Int }
      get("filters")?.let { datasource.setFilters(it as String?) }
    }
  }

  companion object {
    private val poolDrivers = mapOf(
        Pair(ConnectionPools.NONE, DriverManagerDataSource::class.java),
        Pair(ConnectionPools.DRUID, DruidDataSource::class.java),
        Pair(ConnectionPools.C3P0, ComboPooledDataSource::class.java),
        Pair(ConnectionPools.DBCP, BasicDataSource::class.java),
        Pair(ConnectionPools.HIKARI, HikariDataSource::class.java)
    )
  }

}