package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.starter.support.BasicModel
import kotlin.properties.Delegates

@TableName("upms_association_account_grouping")
class AssocicationAccountGrouping : BasicModel<AssocicationAccountGrouping>() {
  var accountId: Long by Delegates.notNull()
  var groupingId: Long by Delegates.notNull()
}