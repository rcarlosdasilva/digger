package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.starter.support.BasicModel
import kotlin.properties.Delegates

@TableName("upms_association_account_role")
class AssociationAccountRole : BasicModel<AssociationAccountRole>() {
  var accountId: Long by Delegates.notNull()
  var roleId: Long by Delegates.notNull()
}