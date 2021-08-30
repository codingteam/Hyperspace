package ru.org.codingteam.hyperspace.web.features

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import ru.org.codingteam.hyperspace.web.configureLogging
import ru.org.codingteam.hyperspace.web.configureSerialization
import ru.org.codingteam.hyperspace.web.jsonSerializer
import kotlin.test.Test
import kotlin.test.assertEquals

class GamesTests {
    private fun withTestApplication(test: TestApplicationEngine.() -> Unit) {
        withTestApplication({
            configureLogging()
            configureSerialization()
            configureGameApi()
        }, test)
    }

    private inline fun <reified T> TestApplicationCall.receive(): T {
        val text = response.content!!
        return jsonSerializer.readValue(text, T::class.java)
    }

    private fun TestApplicationEngine.sendRequest(url: String, handler: TestApplicationCall.() -> Unit) {
        handleRequest(HttpMethod.Get, url) {
            addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }.apply(handler)
    }

    @Test
    fun testGameList() {
        withTestApplication {
            sendRequest("/api/game/") {
                receive<List<GameDefinition>>()
                assertEquals(HttpStatusCode.OK, response.status())
                response.content
                val list = runBlocking { receive<List<GameDefinition>>() }
                assertEquals(list, emptyList())
            }
        }
    }

    @Test
    fun testGameNotFound() {
        withTestApplication {
            handleRequest(HttpMethod.Get, "/api/game/123") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.contentType)
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }
}
