package io.github.rcarlosdasilva.digger.upms.basic.model

import io.github.rcarlosdasilva.digger.upms.basic.AccountType
import io.github.rcarlosdasilva.digger.upms.basic.Sex
import java.io.Serializable
import java.util.*

/**
 * 系统账号（包含超管、客户等）
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Account : Serializable {
  /**
   * 账号的唯一资源标识码
   */
  lateinit var code: String
  /**
   * 账号
   */
  lateinit var username: String
  /**
   * 密码（BCrypt）
   */
  var password: String? = null
  /**
   * 账户类型
   */
  lateinit var type: AccountType
  /**
   * 账户具有的角色
   */
  lateinit var roles: List<Role>
  /**
   * 账户具有的特权
   */
  var privileges: List<Privilege>? = null
  /**
   * 账户所属的分组
   */
  var groupings: List<Grouping>? = null
  /**
   * 昵称
   */
  var nickname: String? = null
  /**
   * 性别
   */
  var sex: Sex? = null
  /**
   * 头像
   */
  var avatar: String? = null
  /**
   * Remember Me功能：key
   */
  var rememberMeKey: String? = null
  /**
   * Remember Me功能：token
   */
  var rememberMeToken: String? = null
  /**
   * Remember Me功能：since - 记住时间
   */
  var rememberMeSince: Date? = null
  /**
   * 注册时间
   */
  var signUpTime: Date? = null
  /**
   * 最后一次登录时间
   */
  var latestLoginTime: Date? = null
  /**
   * 登录次数
   */
  var countOfLoginTimes = 0
  /**
   * 是否在线（可能）
   */
  var isOnline = false
  /**
   * 联系方式
   */
  var contacts: Contact? = null
  /**
   * 账户正常有效
   */
  var isEffective = true
}