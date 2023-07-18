package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class MyViewModel: ViewModel() {
    private val board = mutableStateListOf<String?>(null,"X","O",null,null,null,"X",null,"O",null)

    init {
    }

    fun getBoard(): List<String?>{
        return board
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
        board[position] = "X"
        Log.d("MyLog","Updated possion $position")
        printArray()
    }

}