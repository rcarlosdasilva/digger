package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.support.model.SystemModel
import io.github.rcarlosdasilva.digger.upms.basic.PermissionType

@TableName("upms_permission")
class PermissionModel : SystemModel<PermissionModel>() {
  lateinit var name: String
  lateinit var code: String
  var memo: String? = null
  lateinit var type: PermissionType
}