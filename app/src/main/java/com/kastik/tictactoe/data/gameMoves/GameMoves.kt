package com.kastik.tictactoe.data.gameMoves

abstract class GameMoves {
    abstract fun getMove(board: MutableList<MutableList<String?>>): List<Int>
}