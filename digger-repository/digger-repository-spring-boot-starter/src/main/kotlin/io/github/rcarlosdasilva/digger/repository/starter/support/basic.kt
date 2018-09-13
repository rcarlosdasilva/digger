package io.github.rcarlosdasilva.digger.repository.starter.support

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.extension.activerecord.Model
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.google.common.base.MoreObjects
import com.google.common.base.Objects
import java.io.Serializable
import java.util.*

/**
 * 基础Model，开发中基本可忽略（可用于关联表）
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class BasicModel<E : Model<*>> : Model<E>() {

  @JsonSerialize(using = ToStringSerializer::class)
  var id: Long? = null

  override fun hashCode(): Int = Objects.hashCode(id, javaClass.name)

  override fun equals(other: Any?): Boolean = other != null && Objects.equal(hashCode(), other.hashCode())

  override fun toString(): String = MoreObjects.toStringHelper(this).add("id", id).toString()

  override fun pkVal(): Serializable? = id

}

/**
 * 系统Model，非业务类数据可继承次Model
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class SystemModel<E : Model<*>> : BasicModel<E>() {

  @TableField("is_disabled")
  var isDisabled = false
  @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
  var updatedBy: Long? = null
  @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
  var updatedAt: Date? = null

}

/**
 * 业务相关Model，非系统数据表全部继承此Model
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class BusinessModel<E : Model<*>> : SystemModel<E>() {

  @TableLogic
  @TableField(value = "is_deleted", strategy = FieldStrategy.IGNORED)
  var isDeleted = false
  @TableField(value = "created_by", fill = FieldFill.INSERT)
  var createdBy: Long? = null
  @TableField(value = "created_at", fill = FieldFill.INSERT)
  var createdAt: Date? = null

  companion object {
    const val serialVersionUID = 5776344955989370955L
  }

}