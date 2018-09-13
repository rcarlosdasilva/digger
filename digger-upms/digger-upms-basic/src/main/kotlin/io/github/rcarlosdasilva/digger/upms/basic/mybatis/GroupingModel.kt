package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.starter.support.BusinessModel

@TableName("upms_grouping")
class GroupingModel : BusinessModel<GroupingModel>() {
  lateinit var name: String
  var memo: String? = null
  var parent: Long? = null
}