package ru.org.codingteam.hyperspace.web.features

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class GameDefinition(val width: Int, val height: Int)

fun Application.configureGameApi() {
    val mutex = Mutex()
    val storage = mutableMapOf<Int, GameDefinition>()
    routing {
        route("/api/game") {
            get("/") {
                val state = mutex.withLock { storage.values.toList() }
                call.respond(state)
            }
            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                    "Invalid id",
                    status = HttpStatusCode.BadRequest
                )
                val game = mutex.withLock { storage[id] } ?: return@get call.respondText(
                    "Game not found",
                    status = HttpStatusCode.NotFound
                )

                call.respond(game)
            }
        }
    }
}
