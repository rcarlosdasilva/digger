package io.github.rcarlosdasilva.digger.config


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
open class Bootstrap4Config

fun main(args: Array<String>) {
  runApplication<Bootstrap4Config>(*args)
}