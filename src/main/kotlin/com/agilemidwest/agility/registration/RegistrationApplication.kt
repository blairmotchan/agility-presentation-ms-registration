package com.agilemidwest.agility.registration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class RegistrationApplication

@Throws(InterruptedException::class)
fun main(args: Array<String>) {
    runApplication<RegistrationApplication>(*args)
}
