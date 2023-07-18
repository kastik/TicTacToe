package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class GameDataViewModel(mode: String?): ViewModel() {
    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null,null)
    private var currentPlayer = "X"
    private var playType = mode
    private var winner: String? = null


    fun continuePlaying(): Boolean{
        return winner==null
    }


    fun getBoardData(position: Int): String?{
        return board[position]
    }

    private fun printArray(){
        for(i in 0..8){
            Log.d("MyLog",board[i].toString())
        }
    }

    fun updateBoard(position: Int){
        if (currentPlayer=="X"){
            board[position] = "X"
            currentPlayer="Y"
            if (playType== GameTypes.SinglePlayer.name){
                aiPlay()
            }

        }else{
            board[position] = "Y"
            currentPlayer="X"
        }
        findWinner(position)

    }

    private fun aiPlay(){
        for (i in 0..8){
            if (getBoardData(i)==null){
                updateBoard(i)
                break
            }
        }
    }
    fun setMode(type: GameTypes){
        playType= GameTypes.SinglePlayer.name
    }


    fun findWinner(playedPossition: Int){
        var won :String? =  null

        when(playedPossition){
            0 -> if (board[0]==board[1] && board[1]==board[2] || board[0]==board[3] && board[3]==board[6] || board[0]==board[4] && board[4]==board[8]){ won=board[0] }
            1 -> if (board[0]==board[1] && board[1]==board[2] || board[1]==board[4] && board[4]==board[7] ){ won=board[1] }
            2, -> if (board[2]==board[5] && board[5]==board[8] || board[2]==board[1] && board[1]==board[0] || board[2]==board[4] && board[4]==board[6] ){ won=board[2] }
            3 -> if(board[3]==board[4] && board[4]==board[5] || board[0]==board[3] && board[3]==board[6] ){won=board[3]}
            4 -> if(board[1]==board[4] && board[4]==board[7] || board[2]==board[4] && board[4]==board[6]  || board[0]==board[4] && board[4]==board[8]  || board[3]==board[4] && board[4]==board[5] ){won=board[4]}
            5 -> if(board[2]==board[5] && board[5]==board[8] || board[5]==board[4] && board[4]==board[3] ){won=board[5]}
            6, -> if (board[0]==board[3] && board[3]==board[6] || board[6]==board[7] && board[7]==board[8] || board[6]==board[4] && board[4]==board[2] ){ won=board[6] }
            7 -> if(board[7]==board[4] && board[4]==board[1] || board[6]==board[7] && board[7]==board[8] ){won=board[7]}
            8 -> if (board[8]==board[5] && board[5]==board[2] || board[8]==board[7] && board[7]==board[6] || board[8]==board[4] && board[4]==board[0]){ won=board[8] }
        }
        winner=won
        }

}