package io.github.rcarlosdasilva.digger.registry


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
open class Bootstrap

fun main(args: Array<String>) {
  runApplication<Bootstrap>(*args)
}