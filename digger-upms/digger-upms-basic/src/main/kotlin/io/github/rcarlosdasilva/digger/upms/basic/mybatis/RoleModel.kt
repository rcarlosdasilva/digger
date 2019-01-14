package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.support.model.SystemModel

@TableName("upms_role")
class RoleModel : SystemModel<RoleModel>() {
  lateinit var name: String
  var memo: String? = null
  var inheritedFrom: Long? = null
  var belong: Long? = null
}