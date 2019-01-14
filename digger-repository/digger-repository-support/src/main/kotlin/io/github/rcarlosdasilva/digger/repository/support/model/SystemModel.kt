package io.github.rcarlosdasilva.digger.repository.support.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.*

/**
 * 系统Model，非业务类表结构可继承此Model，一般可用于字典表、日志表等
 *
 * 存在三个有属性：
 * - isDisabled: 标识记录是否已失效，一定程度上可以替代，表示记录已过期、被锁定、非法操作引起的失效，或被删除
 * - updateBy: 审计字段-记录更新者
 * - updateAt: 审计字段-记录更新时间
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class SystemModel<M : SystemModel<M>> : BasicModel<M>() {

  @TableField("is_disabled")
  var isDisabled = false
  @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
  var updatedBy: Long? = null
  @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
  var updatedAt: Date? = null

}