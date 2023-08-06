package com.kastik.tictactoe.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.type.DateTime
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


@OptIn(ExperimentalTime::class)
class GameDataViewModel(private val playType: String?, private val datastore: DatastoreRepo): ViewModel() {

    private var mainPlayerSymbol: String
    private var aiPlayerSymbol: String

    private lateinit var currentPlayer: String
    private var previousPlayerFinished = mutableStateOf(true)


    private var gameDifficulty: String

    private var gameEnded = mutableStateOf(false)
    private var winner:String?  = null

    private var playFirst: Boolean
    private var playAsX: Boolean

    private var ticTacToeLogic : TicTacToeLogic

    private var rows: Int
    private var colums: Int


    init {
        val dur: Duration = measureTime {
            runBlocking {

                playAsX = datastore.playAsXFlow().first()
                playFirst = datastore.playFirstFlow().first()
                gameDifficulty = datastore.gameDifficultyFlow().first()
                rows = datastore.rowsFlow().first()
                colums = datastore.columnsFlow().first()
            }
        }
        Log.d("MyLog", "RunBlocking took: "+dur.inWholeMilliseconds)
        mainPlayerSymbol = if (playAsX) { "X" } else { "O" }
        aiPlayerSymbol = if (playAsX) { "O" } else { "X" }
        ticTacToeLogic = TicTacToeLogic(gameDifficulty,mainPlayerSymbol,aiPlayerSymbol,rows,colums)
        setDataAndPlay()
    }


    fun getPreviousPlayerFinished(): MutableState<Boolean>{
        return previousPlayerFinished
    }

    fun getBoardData(position: List<Int>): String?{
        return ticTacToeLogic.getBoardData(position)
    }


    fun getWinner(): String?{
        return winner
    }
    fun getGameEnded(): MutableState<Boolean>{
        return gameEnded
    }


    fun makeAMove(position: List<Int>){
        if(!gameEnded.value) {

            if (playType == GameTypes.SinglePlayer.name) {
                if (previousPlayerFinished.value) {
                    previousPlayerFinished.value = false
                    ticTacToeLogic.setBoardData(position,currentPlayer)
                    gameEnded.value = ticTacToeLogic.hasWon(position)
                    if(gameEnded.value || ticTacToeLogic.checkForDraw()) {
                        winner = if(ticTacToeLogic.checkForDraw()){null} else{currentPlayer}
                    }
                    currentPlayer = if (currentPlayer == "X") { "O" } else { "X" }
                    if(ticTacToeLogic.checkForDraw()) {
                        gameEnded.value = true
                    }else{
                        makeAMove(ticTacToeLogic.getMove())
                    }
                }
                else {
                    ticTacToeLogic.setBoardData(position,currentPlayer)
                    gameEnded.value = ticTacToeLogic.hasWon(position) || ticTacToeLogic.checkForDraw()
                    if(gameEnded.value) {
                        winner = if (ticTacToeLogic.checkForDraw()){null} else{currentPlayer}
                    }
                    currentPlayer = if (currentPlayer == "X") { "O" } else { "X" }
                    previousPlayerFinished.value = true
                }

            }else if (playType == GameTypes.MultiPlayer.name) {
                ticTacToeLogic.setBoardData(position,currentPlayer)
                gameEnded.value = ticTacToeLogic.hasWon(position)
                if(gameEnded.value) {
                    winner = if (ticTacToeLogic.checkForDraw()){null}else{currentPlayer}
                }
                currentPlayer = if (currentPlayer == "X") { "O" } else { "X" }
            }else{ //Online TODO

            }
        }else{
            Log.d("MyLog","Check gm ended $gameEnded position: $position" )
        }
    }

    fun clearGame(){
        ticTacToeLogic.clearBoard()
        gameEnded.value = false
        winner=null
        setDataAndPlay()

    }

    private fun setDataAndPlay(){
        currentPlayer = if (playFirst){mainPlayerSymbol}else{aiPlayerSymbol}
        previousPlayerFinished.value = playFirst
        if (!playFirst && playType== GameTypes.SinglePlayer.name) {
            makeAMove(listOf(Random.nextInt(0, colums), Random.nextInt(0,rows)))
        }
    }



}