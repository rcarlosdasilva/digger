package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.support.model.BasicModel
import kotlin.properties.Delegates

@TableName("upms_association_role_permission")
class AssociationRolePermission : BasicModel<AssociationRolePermission>() {
  var roleId: Long by Delegates.notNull()
  var permissionId: Long by Delegates.notNull()
}