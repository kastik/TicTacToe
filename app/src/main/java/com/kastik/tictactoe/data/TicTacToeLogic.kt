package com.kastik.tictactoe.data

import androidx.compose.runtime.mutableStateListOf
import com.kastik.tictactoe.utils.MinMaxImplementation
import kotlinx.coroutines.delay
import kotlin.random.Random

class TicTacToeLogic() {
    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null)
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
            else -> return !checkForDraw()
        }
        return false
    }



    fun getMinMaxMove(mainPlayerSymbol: String,AiPlayerSymbol:String): Int{
        //delay(400)
        return MinMaxImplementation(mainPlayerSymbol,AiPlayerSymbol).findBestMove(board.toList())
    }

    fun sequentialMove():Int{
        //delay(400)
        for (i in 0..8){
            if (board[i]==null){
                return i
            }
        }
        return 0
    }


    fun randomMove(): Int{
        //delay(400)
        val emptyPositions = arrayListOf<Int>()
        for (i in 0..8){
            if(board[i]==null){
                emptyPositions.add(i)
            } }
        return(emptyPositions[Random.nextInt(0, emptyPositions.size)])
    }
}