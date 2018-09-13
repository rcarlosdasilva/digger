package io.github.rcarlosdasilva.digger.upms.basic.model

import io.github.rcarlosdasilva.digger.upms.basic.PrivilegeType
import java.util.*

/**
 * 特权表，标识除角色之外的额外附加或削减权限
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Privilege {
  /**
   * 特权指向的权限
   */
  lateinit var permission: Permission
  /**
   * 赋予特权的原因
   */
  var reason: String? = null
  /**
   * 特权类型
   */
  lateinit var type: PrivilegeType
  /**
   * 特权过期时间，空代表不过期
   */
  var expiredAt: Date? = null
}