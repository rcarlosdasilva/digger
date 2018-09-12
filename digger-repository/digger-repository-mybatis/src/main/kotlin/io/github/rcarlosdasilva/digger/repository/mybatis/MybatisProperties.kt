package io.github.rcarlosdasilva.digger.repository.mybatis

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties

class MybatisProperties : MybatisPlusProperties() {

  /**
   * Mapper 接口包
   */
  var mapperInterfacePackages: List<String>? = null

}
