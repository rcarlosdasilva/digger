package io.github.rcarlosdasilva.digger.repository.support.model

import com.baomidou.mybatisplus.extension.activerecord.Model
import com.google.common.base.MoreObjects
import com.google.common.base.Objects
import java.io.Serializable

/**
 * 基础Model，属于最小粒度的Model，可作为任何表结构的Model父类（可用于关联表）
 *
 * 只存在 id 属性。需要注意 id 序列化为json字符串时，Long输出为字符串在js中使用时，精度丢失的问题
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class BasicModel<M : BasicModel<M>> : Model<M>() {

  var id: Long? = null

  override fun hashCode(): Int = Objects.hashCode(id, javaClass.name)

  override fun equals(other: Any?): Boolean = other != null && Objects.equal(hashCode(), other.hashCode())

  override fun toString(): String = MoreObjects.toStringHelper(this).add("id", id).toString()

  override fun pkVal(): Serializable? = id

}