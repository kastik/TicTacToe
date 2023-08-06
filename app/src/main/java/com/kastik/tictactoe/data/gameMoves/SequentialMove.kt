package com.kastik.tictactoe.data.gameMoves

class SequentialMove() : GameMoves() {
    override fun getMove(board: MutableList<MutableList<String?>>): List<Int> {
        for (i in 0..board.size-1) {
            for (j in 0..board[0].size-1)
            if (board[i][j] == null) {
                return mutableListOf(i,j)
            }
        }
        return mutableListOf(0,0)
    }
}