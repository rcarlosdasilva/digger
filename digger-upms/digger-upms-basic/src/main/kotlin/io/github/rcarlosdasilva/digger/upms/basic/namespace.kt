package io.github.rcarlosdasilva.digger.upms.basic

/**
 * 账户类型，基本能涵盖大部分情况
 *
 * @param sn 序号，可用于存入数据库数字类型的字段
 * @param code 编码，可用于存入数据库3位字符的字段
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class AccountType(
    val sn: Int,
    val code: String
) {

  /**
   * 普通用户
   */
  USER(0, "USR"),
  /**
   * 管理员
   */
  ADMIN(1, "ADM"),
  /**
   * 测试账户
   */
  TESTER(2, "TST"),
  /**
   * 游客
   */
  GUEST(3, "GST"),
  /**
   * 客户
   */
  CLIENT(4, "CLT"),
  /**
   * 运营人员
   */
  OPERATOR(5, "OPT"),
  /**
   * 经销商、代理商
   */
  AGENCY(6, "AGC"),
  /**
   * 合作方
   */
  COOPERATOR(7, "CPR"),
  /**
   * 子账户
   */
  CHILD_USER(8, "CUS"),
  /**
   * 临时账户
   */
  DAY_LABORER(9, "DLB"),
  /**
   * 监管账户
   */
  SUPERVISOR(10, "SPV"),
  /**
   * 双用户体系 - 生产者
   */
  MULTI_PRODUCER(11, "MPD"),
  /**
   * 双用户体系 - 消费者
   */
  MULTI_CONSUMER(12, "MCS")
}

/**
 * 权限类型，大致分三种：菜单、功能、页面组件，功能可细分为三级：API、METHOD、CODE，如无需要，可指定通用类型，即无类型区分
 *
 * @param sn 序号，可用于存入数据库数字类型的字段
 * @param code 编码，可用于存入数据库3位字符的字段
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class PermissionType(
    val sn: Int,
    val code: String
) {
  /**
   * 通用，如无需细分权限类型，可全部指定该类型
   */
  GENERAL(0, "GNR"),
  /**
   * 菜单权限
   */
  MENU(1, "MNU"),
  /**
   * 功能权限，可作为[API], [METHOD], [CODE]的统称涵盖
   */
  FEATURE(2, "FTU"),
  /**
   * 接口级权限，作为功能权限[FEATURE]的细分
   */
  API(3, "API"),
  /**
   * 方法级权限，作为功能权限[FEATURE]的细分
   */
  METHOD(4, "MTD"),
  /**
   * 代码级权限，作为功能权限[FEATURE]的细分
   */
  CODE(5, "COD"),
  /**
   * 页面组件权限，为前端使用权限
   */
  PAGE_COMPONENT(6, "PCP")
}

/**
 * 特权类型
 *
 * @param sn 序号，可用于存入数据库数字类型的字段
 * @param code 编码，可用于存入数据库3位字符的字段
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class PrivilegeType(
    val sn: Int,
    val code: String
) {
  /**
   * 附加额外权限
   */
  ADDITIONAL(0, "ADT"),
  /**
   * 削减已有权限
   */
  ABRIDGED(1, "ABD")
}

/**
 * 性别
 *
 * @param sn 序号，可用于存入数据库数字类型的字段
 * @param code 编码，可用于存入数据库3位字符的字段
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class Sex(
    val sn: Int,
    val code: String
) {
  /**
   * 未知、无可奉告
   */
  UNKNOWN(0, "UNW"),
  /**
   * 男
   */
  MALE(1, "MLE"),
  /**
   * 女
   */
  FEMALE(2, "FML"),
  /**
   * 不男不女
   */
  ANDROGYNOUS(3, "ADN"),
  /**
   * 伪娘
   */
  CROSS_DRESSER(4, "CDS"),
  /**
   * 人妖
   */
  LADY_BOY(5, "LDB")
}


/**
 * 称呼
 *
 * @param sn 序号，可用于存入数据库数字类型的字段
 * @param code 编码，可用于存入数据库3位字符的字段
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class PeopleCalled(
    val sn: Int,
    val code: String
) {
  /**
   * 无
   */
  NOTHING(0, "NOT"),
  /**
   * 先生
   */
  MISTER(1, "MST"),
  /**
   * 女士
   */
  MADAM(2, "MDM"),
  /**
   * 小姐
   */
  MISS(3, "MIS")
}