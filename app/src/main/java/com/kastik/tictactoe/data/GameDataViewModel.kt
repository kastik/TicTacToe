package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


class GameDataViewModel(private val playType: String?, private val datastore: DatastoreRepo): ViewModel() {

    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null,null)


    private lateinit var mainPlayerSymbol: MutableState<String>
    private lateinit var AiPlayerSymbol: MutableState<String>

    private lateinit var currentPlayer: MutableState<String>
    private lateinit var previousPlayerFinished: MutableState<Boolean>

    private lateinit var gameDifficulty:MutableState<String>

    private val gameEnded = mutableStateOf(false)
    private val winner = mutableStateOf<String?>(null)

    private lateinit var playFirst: MutableState<Boolean>
    private lateinit var playAsX: MutableState<Boolean>








    init {
        Log.d("MyLog","Init viewModel")
        val getDataJob = viewModelScope.launch(Dispatchers.Main) {
            Log.d("MyLog","Init coroutine")


            playAsX = mutableStateOf( datastore.playAsXFlow().first())
            playFirst = mutableStateOf( datastore.playFirstFlow().first())
            gameDifficulty = mutableStateOf( datastore.gameDifficultyFlow().first())

            mainPlayerSymbol = mutableStateOf(if (playAsX.value) { "X" } else { "O" })
            AiPlayerSymbol = mutableStateOf(if (playAsX.value) { "O" } else { "X" })



            currentPlayer = mutableStateOf(if (playFirst.value){mainPlayerSymbol.value}else{AiPlayerSymbol.value})
            previousPlayerFinished = mutableStateOf(playFirst.value)


            Log.d("MyLog","Init playAsXSetting ${playAsX.value}")
            Log.d("MyLog","Init playFirstSetting ${playFirst.value}")
            Log.d("MyLog","Init gameDifficultySetting ${gameDifficulty.value}")

                /*
                { playAsXSetting ->
                    mainPlayerSymbol = mutableStateOf(if (playAsXSetting) { "X" } else { "O" })
                    AiPlayerSymbol = mutableStateOf(if (playAsXSetting) { "O" } else { "X" })
                    playAsX = mutableStateOf(playAsXSetting)
                    Log.d("MyLog","Init playAsXSetting $playAsXSetting")
                }*/
        }
        viewModelScope.launch {
            Log.d("MyLog", "Got in launch")
            getDataJob.join()
            Log.d("MyLog", "Job finished playFirst value = ${playFirst.value}")
                if (!playFirst.value && playType==GameTypes.SinglePlayer.name) {
                    Log.d("MyLog", "Got in 3")
                    updateBoard2(Random.nextInt(0, 8))

                }
        }


    }


    fun checkGameEnd(){
        for (i in 0..8){
            if (board[i]==null){
                gameEnded.value = false
            }
        }
        gameEnded.value = true
    }


    fun getWinner(): MutableState<String?>{
        return winner
    }

    fun gameEnded(): MutableState<Boolean>{
        return gameEnded
    }


    fun getBoardData(position: Int): String?{
        return board[position]
    }

    private fun printArray(){
        //for(i in 0..8){
            //Log.d("MyLog",board[i].toString())
        //}
    }



    fun updateBoard2(position: Int){
        if(!gameEnded.value) {
            if (playType == GameTypes.SinglePlayer.name) {
                if (previousPlayerFinished.value) {
                    previousPlayerFinished.value = false
                    board[position] = currentPlayer.value
                    findWinner(position)
                    currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
                    CoroutineScope(Dispatchers.Default).launch {
                        when (gameDifficulty.value) {
                            GameModes.Easy.name -> sequentialMove()
                            GameModes.Medium.name -> randomMove()
                            GameModes.Hard.name -> randomMove() //TODO Some Algorithm
                        }
                    }
                }
                else {
                    board[position] = currentPlayer.value
                    findWinner(position)
                    currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
                    previousPlayerFinished.value = true
                }

            }else{ //Multi Player
                board[position] = currentPlayer.value
                findWinner(position)
                currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
            }
        }
    }
















/*
    fun updateBoard(position: Int){
        if (mainPlayerSymbol.value=="X"){
            //playerFinished = false
            board[position] = "X"
            findWinner(position)
            mainPlayerSymbol.value="O"
            //playerFinished = true
            if (playType== GameTypes.SinglePlayer.name && winner.value==null){
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    if(gameDifficulty.value=="Easy") {
                        sequentialMove()
                    }else if (gameDifficulty.value=="Medium"){
                        randomMove()
                    }else{ //Hard
                        //TODO Some kind of algorithm
                    }

                }
            }


        }else{
            board[position] = "O"
            mainPlayerSymbol.value="X"
            findWinner(position)
        }


    }


 */

    private fun findWinner(playedPosition: Int){
        when(playedPosition){
            0 -> if (board[0]==board[1] && board[1]==board[2] || board[0]==board[3] && board[3]==board[6] || board[0]==board[4] && board[4]==board[8]){ winner.value=board[0]; gameEnded.value =true }
            1 -> if (board[0]==board[1] && board[1]==board[2] || board[1]==board[4] && board[4]==board[7] ){ winner.value=board[1]; gameEnded.value =true }
            2 -> if (board[2]==board[5] && board[5]==board[8] || board[2]==board[1] && board[1]==board[0] || board[2]==board[4] && board[4]==board[6] ){ winner.value=board[2]; gameEnded.value =true }
            3 -> if(board[3]==board[4] && board[4]==board[5] || board[0]==board[3] && board[3]==board[6] ){winner.value=board[3]; gameEnded.value =true}
            4 -> if(board[1]==board[4] && board[4]==board[7] || board[2]==board[4] && board[4]==board[6]  || board[0]==board[4] && board[4]==board[8]  || board[3]==board[4] && board[4]==board[5] ){winner.value=board[4]; gameEnded.value =true}
            5 -> if(board[2]==board[5] && board[5]==board[8] || board[5]==board[4] && board[4]==board[3] ){winner.value=board[5]; gameEnded.value =true}
            6 -> if (board[0]==board[3] && board[3]==board[6] || board[6]==board[7] && board[7]==board[8] || board[6]==board[4] && board[4]==board[2] ){ winner.value=board[6]; gameEnded.value =true }
            7 -> if(board[7]==board[4] && board[4]==board[1] || board[6]==board[7] && board[7]==board[8] ){winner.value=board[7]; gameEnded.value =true}
            8 -> if (board[8]==board[5] && board[5]==board[2] || board[8]==board[7] && board[7]==board[6] || board[8]==board[4] && board[4]==board[0]){ winner.value=board[8]; gameEnded.value =true }
            else -> winner.value=null
        }
    }


    fun clearGame(){
        for (i in 0..8){
            board[i] = null
        }
        winner.value=null
        mainPlayerSymbol.value = if (playAsX.value){
            "X"
        }else{
            "O"
        }

    }

    private fun randomMove(){
        val emptyPositions = arrayListOf<Int>()
        for (i in 0..8){
            if(getBoardData(i)==null){
                emptyPositions.add(i)
            }
        }
        try {
            updateBoard2(emptyPositions[Random.nextInt(0, emptyPositions.size - 1)])
        }catch (e: IllegalArgumentException){
            Log.d("MyLog", "Exception Caught ${e.localizedMessage}")
        }
    }


    private suspend fun sequentialMove(){
        for (i in 0..8){
            if (getBoardData(i)==null){
                delay(400)
                updateBoard2(i)
                //playerFinished = true
                break
            }
        }
    }

}