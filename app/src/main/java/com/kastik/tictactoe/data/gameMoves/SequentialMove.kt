package com.kastik.tictactoe.data.gameMoves

class SequentialMove() : GameMoves() {
    override fun getMove(board: List<String?>): Int {
        for (i in 0..8) {
            if (board[i] == null) {
                return i
            }
        }
        return 0
    }
}