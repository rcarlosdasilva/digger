package io.github.rcarlosdasilva.digger.upms.basic.model

/**
 * 角色
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Role {
  /**
   * 名称
   */
  lateinit var name: String
  /**
   * 备注说明
   */
  var memo: String? = null
  /**
   * 具有的权限
   */
  lateinit var permissions: List<Permission>
  /**
   * 当前角色继承自那个角色（可继承目标角色的所有权限）
   */
  var inheritedFrom: Role? = null
  /**
   * 当前角色从属于那个分组
   */
  var belong: Grouping? = null
}
