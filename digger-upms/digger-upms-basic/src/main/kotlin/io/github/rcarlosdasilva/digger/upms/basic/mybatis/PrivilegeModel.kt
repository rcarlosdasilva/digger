package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.starter.support.SystemModel
import io.github.rcarlosdasilva.digger.upms.basic.PrivilegeType
import java.util.*
import kotlin.properties.Delegates

@TableName("upms_privilege")
class PrivilegeModel : SystemModel<PrivilegeModel>() {
  var accountId: Long by Delegates.notNull()
  var permissionId: Long by Delegates.notNull()
  var reason: String? = null
  lateinit var type: PrivilegeType
  var expiredAt: Date? = null
}