package me.mkweb.slacksvmeal

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SlackSvmealApplication

fun main(args: Array<String>) {
    SpringApplication.run(SlackSvmealApplication::class.java, *args)
}
