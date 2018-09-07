# DIGGER - just scaffold

## 模块
@模块可以作为单独的jar包，在任何项目中使用，其余的模块只能被DIGGER依赖
- [ ] digger-common：**脚手架公共代码部分**，包括公共常量、枚举，基础类的定义等，没有实际可应用的代码
- [ ] digger-core：**脚手架核心代码**，包含实用的公共底层工具类，抽象类等，没有实际的业务代码
- [ ] digger-config@：**配置中心**，提供不同环境下，各个模块的配置信息
- [ ] digger-registry：**注册中心**，基于Eureka的注册中心
- [ ] digger-repository@：**持久化层自动配置**，提供基于配置，0代码实现的自动化连接池配置
- [ ] digger-message@：**消息中心**，提供统一的MQ/AMQP实现、调用风格，降低使用成本
- [ ] digger-cache@：**缓存中心**，多样的缓存实现，可基于配置实现多级缓存，统一缓存使用接口
- [ ] digger-security@：**安全中心**，齐备的安全防护，自动配置，实现0代码开发
- [ ] digger-log@：**日志中心**，负责收集日志，根据配置处理日志，统一所有模块的日志记录风格
- [ ] digger-job@：**任务中心**，提供动态、静态两种任务模式
- [ ] digger-cogen@：**代码自动生成器**，可根据模板配置自动生成任意层代码
- [ ] digger-cli：**CLI**，针对DIGGER的命令行，带有Maven插件，可以生成代码，启动项目等
- [ ] digger-fds@：**FDS**，有多种FDS的实现，例如常见FastFDS等
- [ ] digger-gateway：**统一网关**，DIGGER的API统一入口，负责安全、限流、负载等
- [ ] digger-monitor@：**监控中心**，扩展Spring的Actuator，实现更全面的项目监控
- [ ] digger-upms@：**通用用户权限模块**，提供基础的用户权限方案
- [ ] digger-pay@：**支付模块**，对常见的支付接口做封装，例如微信支付、支付宝支付等
- [ ] digger-weixin@：**微信开发**，封装微信公众号、开放平台、小程序等官方接口，降低开发难度
- [ ] digger-document@：**项目文档**，通过注解的方式，实现项目API文档的输出
- [ ] digger-admin：**后台管理**，DIGGER的一个完整前端，提供基础管理能力
- [ ] digger-demo：**DEMO**，对各个模块中，比较复杂的功能的使用小样

## 文档
- digger-common
- digger-core
- digger-config
- digger-registry
- digger-repository
- digger-message
- digger-cache
- digger-security
- digger-log
- digger-job
- digger-cogen
- digger-cli
- digger-fds
- digger-gateway
- digger-monitor
- digger-upms
- digger-pay
- digger-weixin
- digger-document
- digger-admin
- digger-demo