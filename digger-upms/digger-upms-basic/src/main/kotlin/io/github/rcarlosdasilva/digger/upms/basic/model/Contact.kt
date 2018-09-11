package io.github.rcarlosdasilva.digger.upms.basic.model

import io.github.rcarlosdasilva.digger.upms.basic.PeopleCalled
import java.io.Serializable

/**
 * 联系人信息
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Contact : Serializable {

  /**
   * 姓名
   */
  var name: String? = null
  /**
   * 外文姓名（一般为英文，也可存储除英文外的非中文名）
   */
  var nameOfForeign: String? = null
  /**
   * 称谓，取值：NON 无， MRG 先生， MSG 女士， MIS 小姐
   */
  var called: PeopleCalled? = null
  /**
   * 手机
   */
  var mobile: List<String>? = null
  /**
   * 座机
   */
  var telephone: String? = null
  /**
   * 传真
   */
  var fax: String? = null
  /**
   * 邮件地址
   */
  var mail: List<String>? = null
  /**
   * 联系地址
   */
  var address: String? = null

}