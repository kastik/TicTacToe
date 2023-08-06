package com.kastik.tictactoe.data.gameMoves

import kotlin.random.Random

class RandomMove() : GameMoves() {
    override fun getMove(boardList: MutableList<MutableList<String?>>): List<Int> {
        val emptyRows: MutableList<MutableList<Int>> = emptyList<MutableList<Int>>().toMutableList()
        val emptyCells: MutableList<Int> = emptyList<Int>().toMutableList()
        emptyRows.add(emptyCells)

        for (i in 0..boardList.size-1){
            for (j in 0..boardList[0].size-1){
            if(boardList[i][j]==null){
                emptyRows[i][j]
            }
        }}
        return listOf(emptyRows[Random.nextInt(0,emptyRows.size-1)][Random.nextInt(0,emptyCells.size-1)])
    }
}