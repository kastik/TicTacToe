package com.kastik.tictactoe.data.gameMoves

abstract class GameMoves {
    abstract fun getMove(board: List<String?>): Int
}