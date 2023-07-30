package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


class GameDataViewModel(private val playType: String?, private val datastore: DatastoreRepo): ViewModel() {


    private lateinit var mainPlayerSymbol: MutableState<String>
    private lateinit var aiPlayerSymbol: MutableState<String>

    private lateinit var currentPlayer: MutableState<String>
    private val previousPlayerFinished: MutableState<Boolean> = mutableStateOf(true)

    private lateinit var gameDifficulty:MutableState<String>

    private val gameEnded = mutableStateOf(false)
    private val winner = mutableStateOf<String?>(null)

    private lateinit var playFirst: MutableState<Boolean>
    private lateinit var playAsX: MutableState<Boolean>

    private lateinit var ticTacToeLogic : TicTacToeLogic


    init {
        runBlocking{
        //val getDataJob = viewModelScope.launch(Dispatchers.Main) {
            playAsX = mutableStateOf( datastore.playAsXFlow().first())
            playFirst = mutableStateOf( datastore.playFirstFlow().first())
            gameDifficulty = mutableStateOf( datastore.gameDifficultyFlow().first())
            mainPlayerSymbol = mutableStateOf(if (playAsX.value) { "X" } else { "O" })
            aiPlayerSymbol = mutableStateOf(if (playAsX.value) { "O" } else { "X" })
            currentPlayer = mutableStateOf(if (playFirst.value){mainPlayerSymbol.value}else{aiPlayerSymbol.value})
            previousPlayerFinished.value = playFirst.value
            ticTacToeLogic = TicTacToeLogic(gameDifficulty.value,mainPlayerSymbol.value,aiPlayerSymbol.value)
        //}
        //viewModelScope.launch {
            //getDataJob.join()
                if (!playFirst.value && playType==GameTypes.SinglePlayer.name) {
                    makeAMove(Random.nextInt(0, 8))
                }
        //}
        }
    }


    fun getPreviousPlayerFinished(): MutableState<Boolean>{
        return previousPlayerFinished
    }

    fun getBoardData(position: Int): String?{
        return ticTacToeLogic.getBoardData(position)
    }


    fun getWinner(): MutableState<String?>{
        return winner
    }
    fun getGameEnded(): MutableState<Boolean>{
        return gameEnded
    }


    fun makeAMove(position: Int){
        if(!gameEnded.value) {

            if (playType == GameTypes.SinglePlayer.name) {
                if (previousPlayerFinished.value) {
                    previousPlayerFinished.value = false
                    ticTacToeLogic.setBoardData(position,currentPlayer.value)
                    gameEnded.value = ticTacToeLogic.hasWon(position)
                    if(gameEnded.value) {
                        winner.value = if(ticTacToeLogic.checkForDraw()){null} else{currentPlayer.value}

                    }
                    currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
                        //CoroutineScope(Dispatchers.Default).launch {
                    makeAMove(ticTacToeLogic.getMove())
                    //}
                }
                else {
                    ticTacToeLogic.setBoardData(position,currentPlayer.value)
                    gameEnded.value = ticTacToeLogic.hasWon(position)
                    if(gameEnded.value) {
                        winner.value = if (ticTacToeLogic.checkForDraw()){null} else{currentPlayer.value}
                    }
                    currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
                    previousPlayerFinished.value = true
                }

            }else if (playType == GameTypes.MultiPlayer.name) {
                ticTacToeLogic.setBoardData(position,currentPlayer.value)
                gameEnded.value = ticTacToeLogic.hasWon(position)
                if(gameEnded.value) {
                    winner.value = if (ticTacToeLogic.checkForDraw()){null}else{currentPlayer.value}
                }
                currentPlayer.value = if (currentPlayer.value == "X") { "O" } else { "X" }
            }else{ //Online TODO

            }
        }else{
            Log.d("MyLog","Check gm ended $gameEnded position: $position" )
        }
    }

    fun clearGame(){
        ticTacToeLogic.clearBoard()
        gameEnded.value = false
        currentPlayer.value = if (playFirst.value){mainPlayerSymbol.value}else{ aiPlayerSymbol.value }
        previousPlayerFinished.value = playFirst.value
        winner.value=null
        if (!playFirst.value && playType==GameTypes.SinglePlayer.name) {
            makeAMove(Random.nextInt(0, 8))
        }
    }



}