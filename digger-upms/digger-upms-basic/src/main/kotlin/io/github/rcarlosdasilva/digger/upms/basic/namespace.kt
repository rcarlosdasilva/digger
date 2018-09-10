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