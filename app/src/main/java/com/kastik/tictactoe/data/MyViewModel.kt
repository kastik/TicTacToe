package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kastik.tictactoe.screens.GameTypes


class MyViewModel: ViewModel() {
    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null,null)
    private var currentPlayer = "X"
    private var playType = GameTypes.SinglePlayer



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
            if (playType==GameTypes.SinglePlayer){
                aiPlay()
            }

        }else{
            board[position] = "Y"
            currentPlayer="X"
        }

    }

    private fun aiPlay(){
        for (i in 1..8){
            if (getBoardData(i)==null){
                updateBoard(i)
                break
            }
        }
    }
    fun setMode(type: GameTypes){
        playType=type
    }

}