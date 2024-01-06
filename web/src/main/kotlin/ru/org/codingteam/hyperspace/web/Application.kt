package ru.org.codingteam.hyperspace.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.org.codingteam.hyperspace.server.DataStorage
import ru.org.codingteam.hyperspace.web.features.configureGameApi

fun Application.configureLogging() {
    install(CallLogging)
}

val jsonSerializer = ObjectMapper().apply {
    registerKotlinModule()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(jsonSerializer))
    }
}

fun main() {
    val storage = DataStorage()
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureLogging()
        configureSerialization()
        configureGameApi(storage)
    }.start(wait = true)
}
