package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.starter.support.BusinessModel
import io.github.rcarlosdasilva.digger.upms.basic.AccountType
import io.github.rcarlosdasilva.digger.upms.basic.Sex
import io.github.rcarlosdasilva.digger.upms.basic.model.Privilege
import java.util.*

@TableName("upms_account")
class AccountModel : BusinessModel<AccountModel>() {
  lateinit var code: String
  lateinit var username: String
  var password: String? = null
  var salt: String? = null
  lateinit var type: AccountType
  var contactId: Long? = null
  var nickname: String? = null
  var sex: Sex? = null
  var avatar: String? = null
  var rememberMeKey: String? = null
  var rememberMeToken: String? = null
  var rememberMeSince: Date? = null
  var signUpTime: Date? = null
  var latestLoginTime: Date? = null
  var countOfLoginTimes = 0
  var isOnline = false
  var isEffective = true
}