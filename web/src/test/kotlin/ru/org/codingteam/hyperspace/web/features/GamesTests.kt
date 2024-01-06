package ru.org.codingteam.hyperspace.web.features

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import ru.org.codingteam.hyperspace.server.DataStorage
import ru.org.codingteam.hyperspace.server.FieldSize
import ru.org.codingteam.hyperspace.server.Game
import ru.org.codingteam.hyperspace.web.configureLogging
import ru.org.codingteam.hyperspace.web.configureSerialization
import ru.org.codingteam.hyperspace.web.jsonSerializer
import kotlin.test.Test
import kotlin.test.assertEquals

class GamesTests {
    private val storage = DataStorage()
    private fun withTestApplication(test: TestApplicationEngine.() -> Unit) {
        withTestApplication({
            configureLogging()
            configureSerialization()
            configureGameApi(storage)
        }, test)
    }

    private inline fun <reified T> TestApplicationCall.receive(): T {
        val text = response.content!!
        return jsonSerializer.readValue(text, T::class.java)
    }

    private fun <T> TestApplicationEngine.sendRequest(
        url: String,
        method: HttpMethod = HttpMethod.Get,
        body: T? = null,
        handler: TestApplicationCall.() -> Unit = {}
    ) {
        handleRequest(method, url) {
            val jsonContentType = ContentType.Application.Json.toString()
            addHeader(HttpHeaders.ContentType, jsonContentType)
            addHeader(HttpHeaders.Accept, jsonContentType)
            body?.let { setBody(jsonSerializer.writeValueAsString(body)) }
        }.apply(handler)
    }

    private fun TestApplicationEngine.sendRequest(
        url: String,
        method: HttpMethod = HttpMethod.Get,
        handler: TestApplicationCall.() -> Unit
    ) = sendRequest(url, method, null, handler)

    @Test
    fun testGameList() {
        withTestApplication {
            sendRequest("/api/game/") {
                receive<List<Game>>()
                assertEquals(HttpStatusCode.OK, response.status())
                response.content
                val list = runBlocking { receive<List<Game>>() }
                assertEquals(list, emptyList())
            }
        }
    }

    @Test
    fun testGameNotFound() {
        withTestApplication {
            sendRequest("/api/game/123") {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun newGameShouldBeAdded() {
        withTestApplication {
            val size = FieldSize(480, 640)
            sendRequest("/api/game/", HttpMethod.Post, size) {
                val id = receive<Int>()
                sendRequest("/api/game/$id") {
                    val createdGame = receive<Game>()
                    assertEquals(size, createdGame.size)
                }
            }
        }
    }

    @Test
    fun newGameIdShouldBeGenerated() {
        withTestApplication {
            val size = FieldSize(480, 640)
            sendRequest("/api/game/", HttpMethod.Post, size) {
                assertEquals(1, receive())
            }
            sendRequest("/api/game/", HttpMethod.Post, size) {
                assertEquals(2, receive())
            }
        }
    }
}
