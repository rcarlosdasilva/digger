package io.github.rcarlosdasilva.digger.upms.basic.model

import io.github.rcarlosdasilva.digger.upms.basic.PermissionType

/**
 * 权限
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Permission {
  /**
   * 名称
   */
  lateinit var name: String
  /**
   * 权限编码，唯一，以英文冒号(:)分隔权限层级
   */
  lateinit var code: String
  /**
   * 备注说明
   */
  var memo: String? = null
  /**
   * 权限类型
   */
  lateinit var type: PermissionType
  /**
   * 属于的角色，反查
   */
  var roles: List<Role>? = null
}