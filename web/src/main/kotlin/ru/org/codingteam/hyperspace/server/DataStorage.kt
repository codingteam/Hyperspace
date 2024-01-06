package ru.org.codingteam.hyperspace.server

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DataStorage {
    private val mutex = Mutex()
    private val storage = mutableMapOf<Int, Game>()
    private var lastId = 0

    suspend fun getAllGames(): List<Game> = mutex.withLock { storage.values.toList() }
    suspend fun getGame(id: Int): Game? = mutex.withLock { storage[id] }

    suspend fun createGame(fieldSize: FieldSize): Int = mutex.withLock {
        val game = Game(fieldSize)
        val nextId = ++lastId
        storage[nextId] = game
        nextId
    }
}
