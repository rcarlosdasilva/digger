package io.github.rcarlosdasilva.digger.upms.basic.model

/**
 * 用户分组，可用作用户部门、机构等概念
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Grouping {
  /**
   * 名称
   */
  lateinit var name: String
  /**
   * 备注说明
   */
  var memo: String? = null
  /**
   * 父分组
   */
  var parent: Grouping? = null
  /**
   * 子分组
   */
  var children: List<Grouping>? = null
  /**
   * 组下具有的角色（可以将角色限制在组内）
   */
  var roles: List<Role>? = null
  /**
   * 组下的所有账户（用户基数大时，慎用）
   */
  var accounts: List<Account>? = null
}