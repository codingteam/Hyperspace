package ru.org.codingteam.hyperspace.server

enum class GameStatus {
    Waiting, Started, Finished
}

data class FieldSize(val width: Int, val height: Int)
data class Game(val size: FieldSize, val playerCount: Int = 0, val status: GameStatus = GameStatus.Waiting)
