package com.kastik.tictactoe.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.kastik.tictactoe.data.gameMoves.MinMaxMove
import com.kastik.tictactoe.data.gameMoves.GameMoves
import com.kastik.tictactoe.data.gameMoves.RandomMove
import com.kastik.tictactoe.data.gameMoves.SequentialMove

class TicTacToeLogic(gameDifficulty: String,mainPlayerSymbol: String,AiPlayerSymbol: String,val rows: Int,val columns: Int) {
    private val _board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null)
    private var board = mutableStateListOf<MutableList<String?>>()
    private lateinit var gameMove: GameMoves
    init {
        for (i in 0..columns){
            board.add(arrayOfNulls<String?>(rows).toMutableList())
        }

        when(gameDifficulty){
            GameDifficulties.Easy.name -> gameMove = SequentialMove()
            GameDifficulties.Medium.name -> gameMove = RandomMove()
            GameDifficulties.Hard.name -> gameMove = MinMaxMove(mainPlayerSymbol,AiPlayerSymbol)

        }
    }
    fun checkForDraw(): Boolean{
        for (i in 0..columns){
            for(j in 0..rows) {
                if (board[i][j] == null) {
                    return false
                }
            }
        }
        return true

    }

    fun getBoardData(possition: List<Int>): String?{
        return board[possition[0]][possition[1]]
    }
    fun setBoardData(possition: List<Int>,player: String?){
        board[possition[0]][possition[1]] = player
    }

    fun clearBoard(){
        for (i in 0..columns){
            for (j in 0..rows) {
                board[i][j] = null
            }
        }
    }

    fun hasWon(possition: List<Int>):Boolean{
        when(0){
            0 -> if (board[0]==board[1] && board[1]==board[2] || board[0]==board[3] && board[3]==board[6] || board[0]==board[4] && board[4]==board[8]){ return true }
            1 -> if (board[0]==board[1] && board[1]==board[2] || board[1]==board[4] && board[4]==board[7] ){ return true }
            2 -> if (board[2]==board[5] && board[5]==board[8] || board[2]==board[1] && board[1]==board[0] || board[2]==board[4] && board[4]==board[6] ){ return true }
            3 -> if(board[3]==board[4] && board[4]==board[5] || board[0]==board[3] && board[3]==board[6] ){return true}
            4 -> if(board[1]==board[4] && board[4]==board[7] || board[2]==board[4] && board[4]==board[6]  || board[0]==board[4] && board[4]==board[8]  || board[3]==board[4] && board[4]==board[5] ){return true}
            5 -> if(board[2]==board[5] && board[5]==board[8] || board[5]==board[4] && board[4]==board[3] ){return true}
            6 -> if (board[0]==board[3] && board[3]==board[6] || board[6]==board[7] && board[7]==board[8] || board[6]==board[4] && board[4]==board[2] ){ return true }
            7 -> if(board[7]==board[4] && board[4]==board[1] || board[6]==board[7] && board[7]==board[8] ){return true}
            8 -> if (board[8]==board[5] && board[5]==board[2] || board[8]==board[7] && board[7]==board[6] || board[8]==board[4] && board[4]==board[0]){ return true }
        }
        return false
    }

    fun getMove(): List<Int>{
        return gameMove.getMove(board.toMutableList())
    }
}