package com.kastik.tictactoe.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameDataViewModel(mode: String?): ViewModel() {
    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null,null)
    private var currentPlayer = "X"
    private var playType = mode
    private var winner: String? = null


    fun continuePlaying(): Boolean{
        return winner==null
    }

    fun getWinner(): String?{
        return winner
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
        printArray()
        if (currentPlayer=="X"){
            board[position] = "X"
            findWinner(position)
            currentPlayer="O"
            if (playType== GameTypes.SinglePlayer.name && winner==null){
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {  aiPlay() }
            }


        }else{
            board[position] = "O"
            currentPlayer="X"
            findWinner(position)
        }


    }

    private suspend fun aiPlay(){
        for (i in 0..8){
            if (getBoardData(i)==null){
                delay(400)
                updateBoard(i)
                break
            }
        }
    }

    private fun findWinner(playedPosition: Int){
        var won :String? =  null

        when(playedPosition){
            0 -> if (board[0]==board[1] && board[1]==board[2] || board[0]==board[3] && board[3]==board[6] || board[0]==board[4] && board[4]==board[8]){ won=board[0] }
            1 -> if (board[0]==board[1] && board[1]==board[2] || board[1]==board[4] && board[4]==board[7] ){ won=board[1] }
            2 -> if (board[2]==board[5] && board[5]==board[8] || board[2]==board[1] && board[1]==board[0] || board[2]==board[4] && board[4]==board[6] ){ won=board[2] }
            3 -> if(board[3]==board[4] && board[4]==board[5] || board[0]==board[3] && board[3]==board[6] ){won=board[3]}
            4 -> if(board[1]==board[4] && board[4]==board[7] || board[2]==board[4] && board[4]==board[6]  || board[0]==board[4] && board[4]==board[8]  || board[3]==board[4] && board[4]==board[5] ){won=board[4]}
            5 -> if(board[2]==board[5] && board[5]==board[8] || board[5]==board[4] && board[4]==board[3] ){won=board[5]}
            6 -> if (board[0]==board[3] && board[3]==board[6] || board[6]==board[7] && board[7]==board[8] || board[6]==board[4] && board[4]==board[2] ){ won=board[6] }
            7 -> if(board[7]==board[4] && board[4]==board[1] || board[6]==board[7] && board[7]==board[8] ){won=board[7]}
            8 -> if (board[8]==board[5] && board[5]==board[2] || board[8]==board[7] && board[7]==board[6] || board[8]==board[4] && board[4]==board[0]){ won=board[8] }
            else -> won=null
        }
        winner=won
        }

}