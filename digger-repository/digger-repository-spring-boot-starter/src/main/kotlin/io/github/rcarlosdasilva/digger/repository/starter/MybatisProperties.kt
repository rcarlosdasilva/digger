package io.github.rcarlosdasilva.digger.repository.starter

import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.baomidou.mybatisplus.core.config.GlobalConfig
import org.apache.ibatis.session.ExecutorType
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import java.util.*

/**
 * replace/rewrite mybatis-plus properties class
 */
@ConfigurationProperties("digger.repository")
class MybatisProperties {

  /**
   * Mybatis XML配置文件位置
   */
  var configLocation: String? = null
  /**
   * MyBatis Mapper  接口包扫描路径
   *
   * 如果不指定（推荐在这里设置），需要使用@MapperScan注解指定接口位置
   */
  var mapperInterfacePackages: Array<String>? = null
  /**
   * MyBatis Mapper 接口所对应的 XML 文件位置
   */
  var mapperLocations: Array<String>? = null
  /**
   * MyBaits Entity(type aliases)包扫描路径，多个路径使用英文逗号(,)分隔
   */
  var typeAliasesPackages: Array<String>? = null
  /**
   * MyBatis Entity(type aliases)父类
   *
   * 如果不指定, MyBatis从typeAliasesPackages路径下扫描所有类
   */
  var typeAliasesSuperType: Class<*>? = null
  /**
   * 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。
   */
  var executorType: ExecutorType? = null
  /**
   * TypeHandler 扫描路径，多个路径使用英文逗号(,)分隔
   */
  var typeHandlersPackage: String? = null
  /**
   * 枚举类 扫描路径
   */
  var typeEnumsPackage: String? = null
  /**
   * Externalized properties for MyBatis configuration.
   */
  var configurationProperties: Properties? = null
  /**
   * MyBatis的默认官方配置项
   *
   * 如果配置了configLocation，请忽略本配置项
   */
  @NestedConfigurationProperty
  var configuration: MybatisConfiguration? = null
  /**
   * MyBatis-Plus全局配置
   */
  @NestedConfigurationProperty
  var mpConfig = GlobalConfig().apply { dbConfig = GlobalConfig.DbConfig() }

}
