package io.github.rcarlosdasilva.digger.upms.basic.mybatis

import com.baomidou.mybatisplus.annotation.TableName
import io.github.rcarlosdasilva.digger.repository.starter.support.BusinessModel
import io.github.rcarlosdasilva.digger.upms.basic.PeopleCalled

@TableName("upms_contact")
class ContactModel : BusinessModel<ContactModel>() {
  var name: String? = null
  var nameOfForeign: String? = null
  var called: PeopleCalled? = null
  var mobile: List<String>? = null
  var telephone: String? = null
  var fax: String? = null
  var mail: List<String>? = null
  var address: String? = null
}