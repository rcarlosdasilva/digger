package io.github.rcarlosdasilva.digger.repository.support.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import java.util.*

/**
 * 业务相关Model，非系统数据表结构可全部继承此Model
 *
 * 除继承自[SystemModel]的属性之外，另提供以下几个基本属性：
 * - isDeleted: 可明确标识业务记录是否被逻辑删除
 * - createdBy: 审计字段-记录创建者
 * - createdAt: 审计字段-记录创建时间
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class BusinessModel<M : BusinessModel<M>> : SystemModel<M>() {

  @TableLogic
  @TableField(value = "is_deleted", strategy = FieldStrategy.IGNORED)
  var isDeleted = false
  @TableField(value = "created_by", fill = FieldFill.INSERT)
  var createdBy: Long? = null
  @TableField(value = "created_at", fill = FieldFill.INSERT)
  var createdAt: Date? = null

}