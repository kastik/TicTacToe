package com.kastik.tictactoe.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import com.kastik.tictactoe.data.GameDataViewModel


@Composable
fun GameScreen(gameMode: String?) {
    val viewModel = GameDataViewModel(gameMode)
    Column {
        NewTicTacBoard(viewModel = viewModel)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.NewTextButton(
    text: () -> String?,
    onClick: () -> Unit,
    continuePlaying: () -> Boolean
){
    TextButton(
        onClick = onClick,
        shape = RectangleShape,
        enabled = (text() == null && continuePlaying()),
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    )
    {
        AnimatedVisibility(visible = text() != null,enter = scaleIn()) {
            Text(
                text = text().orEmpty(), style = MaterialTheme.typography.displayLarge,
                color =
                if (text().equals("X"))
                    Color.Blue
                else
                    Color.Red
            )
        }
    }
}

@Composable
fun NewTicTacBoard(viewModel: GameDataViewModel){
    Column(
        Modifier
            .fillMaxSize(1f)
            .aspectRatio(1f)) {
        NewTicTacRow(viewModel, intArrayOf(0,1,2))
        NewTicTacRow(viewModel, intArrayOf(3,4,5))
        NewTicTacRow(viewModel, intArrayOf(6,7,8))
    }
}


@Composable
fun ColumnScope.NewTicTacRow(
    viewModel: GameDataViewModel,
    rows: IntArray
){
    Row(
        Modifier
            .weight(1f)
            .fillMaxSize()) {
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[0]) },
            onClick = { (viewModel::updateBoard)(rows[0]) },
            continuePlaying = viewModel::continuePlaying,
        )
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[1]) },
            onClick = { (viewModel::updateBoard)(rows[1]) },
            continuePlaying = viewModel::continuePlaying,
        )
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[2]) },
            onClick = { (viewModel::updateBoard)(rows[2]) },
            continuePlaying = viewModel::continuePlaying,
        )
    }
}

@Preview
@Composable
fun test(){
    MaterialTheme {
        NewTicTacBoard(GameDataViewModel(""))
    }
}