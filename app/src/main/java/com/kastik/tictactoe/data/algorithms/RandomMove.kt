package com.kastik.tictactoe.data.algorithms

import kotlin.random.Random

class RandomMove() : Algorithms() {
    override fun getMove(board: List<String?>): Int {
        val emptyPositions = arrayListOf<Int>()
        for (i in 0..8){
            if(board[i]==null){
                emptyPositions.add(i)
            }
        }
        return(emptyPositions[Random.nextInt(0, emptyPositions.size)])
    }
}