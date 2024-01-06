package ru.org.codingteam.hyperspace.web.features

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.org.codingteam.hyperspace.server.DataStorage
import ru.org.codingteam.hyperspace.server.FieldSize

fun Application.configureGameApi(storage: DataStorage) {
    fun ApplicationCall.getInt(name: String): Int? = parameters[name]?.toIntOrNull()
    suspend fun ApplicationCall.invalidArg(name: String) = respondText(
        "Invalid parameter: $name",
        status = HttpStatusCode.BadRequest
    )
    suspend fun ApplicationCall.gameNotFound() = respondText(
        "Game not found",
        status = HttpStatusCode.NotFound
    )

    routing {
        route("/api/game") {
            get("/") {
                val games = storage.getAllGames()
                call.respond(games)
            }
            get("{id}") {
                val id = call.getInt("id") ?: return@get call.invalidArg("id")
                val game = storage.getGame(id) ?: return@get call.gameNotFound()
                call.respond(game)
            }
            post("/") {
                val newGameParameters = call.receive<FieldSize>()
                val id = storage.createGame(newGameParameters)
                call.respond(id)
            }
        }
    }
}
