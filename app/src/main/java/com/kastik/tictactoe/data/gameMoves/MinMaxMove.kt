package com.kastik.tictactoe.data.gameMoves

class MinMaxMove(val player: String, val opponent:String) : GameMoves() {


    private fun movesLeft(board: MutableList<MutableList<String?>>): Boolean {
        for (i in 0..board.size-1) {
            for (j in 0..board[0].size-1) {
                if (board[i][j] == null) {
                    return true
                }
            }
        }
        return false
    }

    private fun evaluate(board: MutableList<MutableList<String?>>): Int {

        for (i in 0..board.size-4){
            for(j in 0..board[0].size-4){
                if (board[i][j] == board[i+1][j] && board[i+1][j] == board[i+2][j] && board[i][j]!=null ){return if(board[i][j] == player) {10} else { -10 }} //Ola ta katheta
                if (board[i][j] == board[i][j+1] && board[i][j+1] == board[i][j+2] && board[i][j]!=null ){return if(board[i][j] == player) {10} else { -10 }} //Ola ta vertical
                if (board[i][j] == board[i+1][j+1] && board[i+1][j+1] == board[i+2][j+2] && board[i][j]!=null ){return if(board[i][j] == player) {10} else { -10 }} //Ola ta plagia dexia
                //if (board[i][j] == board[i+1][j+1] && board[i+1][j+1] == board[i+2][j+2] && board[i][j]!=null ){return if(board[i][j] == player) {10} else { -10 }} //Ola ta plagia dexia
            }
        }

        //if (board[0] == board[1] && board[1] == board[2] && board[0]!=null || board[0] == board[3] && board[3] == board[6]  && board[0]!=null|| board[0] == board[4] && board[4] == board[8] && board[0]!=null) { return if (board[0] == player) { 10 } else { -10 } }
        //if (board[0] == board[1] && board[1] == board[2]  && board[1]!=null|| board[1] == board[4] && board[4] == board[7] && board[1]!=null) { return if (board[1] == player) { 10 } else { -10 } }
        //if (board[2] == board[5] && board[5] == board[8]  && board[2]!=null || board[2] == board[1] && board[1] == board[0]  && board[2]!=null || board[2] == board[4] && board[4] == board[6] && board[2]!=null) { return if (board[2] == player) { 10 } else { -10 } }
        //if (board[3] == board[4] && board[4] == board[5]  && board[3]!=null || board[0] == board[3] && board[3] == board[6] && board[3]!=null) { return if (board[3] == player) { 10 } else { -10 } }
        //if (board[1] == board[4] && board[4] == board[7]  && board[4]!=null|| board[2] == board[4] && board[4] == board[6]  && board[4]!=null || board[0] == board[4] && board[4] == board[8]  && board[4]!=null|| board[3] == board[4] && board[4] == board[5]  && board[4]!=null) { return if (board[4] == player) { 10 } else { -10 } }
        //if (board[2] == board[5] && board[5] == board[8]  && board[5]!=null|| board[5] == board[4] && board[4] == board[3] && board[5]!=null) { return if (board[5] == player) { 10 } else { -10 } }
        //if (board[0] == board[3] && board[3] == board[6]  && board[6]!=null|| board[6] == board[7] && board[7] == board[8]  && board[6]!=null|| board[6] == board[4] && board[4] == board[2] && board[6]!=null) { return if (board[6] == player) { 10 } else { -10 } }
        //if (board[7] == board[4] && board[4] == board[1]  && board[7]!=null|| board[6] == board[7] && board[7] == board[8] && board[7]!=null) { return if (board[7] == player) { 10 } else { -10 } }
        //if (board[8] == board[5] && board[5] == board[2]  && board[8]!=null|| board[8] == board[7] && board[7] == board[6]  && board[8]!=null|| board[8] == board[4] && board[4] == board[0] && board[8]!=null) { return if (board[8] == player) { 10 } else { -10 } }

        return 0
    }
    private fun minMax(board: MutableList<MutableList<String?>>, depth: Int, isMaxTurn: Boolean): Int {
        val score = evaluate(board)

        if (score == 10) {
            return score
        }
        if (score == -10) {
            return score
        }
        if (movesLeft(board) == false) {
            return 0
        }
        if (isMaxTurn) {
            var best = -1000
            for (i in 0..board.size-1) {
                for (j in 0..board[0].size-1){
                if (board[i][j] == null) {
                    board[i][j] = player
                    best = Math.max(best, minMax(board, depth + 1, !isMaxTurn))
                    board[i][j] = null
                }}
            }
            return best
        } else {
            var best = 1000 // Change to a large positive value
            for (i in 0..board.size-1) {
                for (j in 0..board[0].size-1){
                if (board[i][j] == null) {
                    board[i][j] = opponent
                    best = Math.min(best, minMax(board, depth + 1, !isMaxTurn))
                    board[i][j] = null
                }
            }}
            return best
        }
    }

    override fun getMove(boardList: MutableList<MutableList<String?>>): List<Int> {
        val board = boardList
        var bestVal = -1000
        var bestMoveRow = -1
        var bestMoveCell = -1
        for (i in 0..board.size-1) {
            for (j in 0..board[0].size - 1) {
                if (board[i][j] == null) {
                    board[i][j] = player
                    val moveVal = minMax(board, 0, false)
                    board[i][j] = null
                    if (moveVal > bestVal) {
                        bestVal = moveVal
                        bestMoveRow = i
                        bestMoveCell = j
                    }
                }
            }
        }
        return mutableListOf(bestMoveRow,bestMoveCell)
    }

}

