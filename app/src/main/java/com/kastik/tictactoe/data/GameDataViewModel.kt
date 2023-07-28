package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kastik.tictactoe.utils.MinMaxImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameDataViewModel(private val playType: String?, private val datastore: DatastoreRepo): ViewModel() {

    private val board = mutableStateListOf<String?>(null,null,null,null,null,null,null,null,null,null)


    private lateinit var mainPlayerSymbol: MutableState<String>
    private lateinit var AiPlayerSymbol: MutableState<String>

    private lateinit var currentPlayer: MutableState<String>
    private val previousPlayerFinished: MutableState<Boolean> = mutableStateOf(true)

    private lateinit var gameDifficulty:MutableState<String>

    private val gameEnded = mutableStateOf(false)
    private val winner = mutableStateOf<String?>(null)

    private lateinit var playFirst: MutableState<Boolean>
    private lateinit var playAsX: MutableState<Boolean>


    init {
        val getDataJob = viewModelScope.launch(Dispatchers.Main) {
            playAsX = mutableStateOf( datastore.playAsXFlow().first())
            playFirst = mutableStateOf( datastore.playFirstFlow().first())
            gameDifficulty = mutableStateOf( datastore.gameDifficultyFlow().first())
            mainPlayerSymbol = mutableStateOf(if (playAsX.value) { "X" } else { "O" })
            AiPlayerSymbol = mutableStateOf(if (playAsX.value) { "O" } else { "X" })
            currentPlayer = mutableStateOf(if (playFirst.value){mainPlayerSymbol.value}else{AiPlayerSymbol.value})
            previousPlayerFinished.value = playFirst.value
        }
        viewModelScope.launch {
            getDataJob.join()
                if (!playFirst.value && playType==GameTypes.SinglePlayer.name) {
                    updateBoard2(Random.nextInt(0, 8))
                } } }
    fun previousPlayerFinished(): MutableState<Boolean>{
        return previousPlayerFinished
    }
    fun checkGameEnd(){
        var temp = gameEnded.value
        for (i in 0..8){
            if (board[i]==null){
                temp = false
            }
        }
        gameEnded.value = temp
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
    fun updateBoard2(position: Int){
        if(!gameEnded.value) {
            if (playType == GameTypes.SinglePlayer.name) {
                if (previousPlayerFinished.value) {
                    previousPlayerFinished.value = false
                    board[position] = currentPlayer.value
                    findWinner(position)
                    //checkGameEnd()
                    currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
                    CoroutineScope(Dispatchers.Default).launch {
                        when (gameDifficulty.value) {
                            GameModes.Easy.name -> sequentialMove()
                            GameModes.Medium.name -> randomMove()
                            GameModes.Hard.name -> minMax()
                        }
                    }
                }
                else {
                    board[position] = currentPlayer.value
                    findWinner(position)
                    //checkGameEnd()
                    currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
                    previousPlayerFinished.value = true
                }

            }else{ //Multi Player
                board[position] = currentPlayer.value
                findWinner(position)
                //checkGameEnd()
                currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
            }
        }
    }
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
        }
    }
    fun clearGame(){
        for (i in 0..8){ board[i] = null }
        gameEnded.value = false
        currentPlayer = if (playFirst.value){mainPlayerSymbol}else{ AiPlayerSymbol }
        previousPlayerFinished.value = playFirst.value
        winner.value=null
    }
    private suspend fun randomMove(){
        delay(400)
        val emptyPositions = arrayListOf<Int>()
        for (i in 0..8){
            if(getBoardData(i)==null){
                emptyPositions.add(i)
            }
        }
        try {
            updateBoard2(emptyPositions[Random.nextInt(0, emptyPositions.size - 1)])
        }catch (_: IllegalArgumentException){} }
    private suspend fun sequentialMove(){
        delay(400)
        for (i in 0..8){
            if (getBoardData(i)==null){
                updateBoard2(i)
                //playerFinished = true
                break
            }
        }
    }
    private suspend fun minMax(){
        delay(400)
        updateBoard2(MinMaxImplementation(mainPlayerSymbol.value,AiPlayerSymbol.value).findBestMove(board.toList()))
    }
}