package com.kastik.tictactoe.data

import androidx.compose.runtime.mutableStateListOf
import com.kastik.tictactoe.data.gameMoves.MinMaxMove
import com.kastik.tictactoe.data.gameMoves.GameMoves
import com.kastik.tictactoe.data.gameMoves.RandomMove
import com.kastik.tictactoe.data.gameMoves.SequentialMove

class TicTacToeLogic(gameDifficulty: String,mainPlayerSymbol: String,AiPlayerSymbol: String) {
    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null)
    private lateinit var gameMove: GameMoves
    init {
        when(gameDifficulty){
            GameDifficulties.Easy.name -> gameMove = SequentialMove()
            GameDifficulties.Medium.name -> gameMove = RandomMove()
            GameDifficulties.Hard.name -> gameMove = MinMaxMove(mainPlayerSymbol,AiPlayerSymbol)

        }
    }
    fun checkForDraw(): Boolean{
        for (i in 0..8){
            if (board[i]==null){
                return false
            }
        }
        return true

    }

    fun getBoardData(position: Int): String?{
        return board[position]
    }
    fun setBoardData(position: Int,player: String?){
        board[position] = player
    }

    fun clearBoard(){
        for (i in 0..8){
            board[i] = null
        }
    }

    fun hasWon(playedPosition: Int):Boolean{
        when(playedPosition){
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

    fun getMove(): Int{
        return gameMove.getMove(board)
    }
}