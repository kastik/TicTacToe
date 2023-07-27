package com.kastik.tictactoe.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kastik.tictactoe.data.DatastoreRepo
import com.kastik.tictactoe.data.GameDataViewModel


@Composable
fun GameScreen(gameMode: String?) {
    val dataStore = DatastoreRepo(LocalContext.current)
    val viewModel = remember { GameDataViewModel(gameMode,dataStore) }
    Column {
        NewTicTacBoard(viewModel = viewModel)
        AnimatedVisibility(visible = viewModel.gameEnded().value) {
            Dialog(onDismissRequest = {} ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxHeight(.35f)
                        .fillMaxWidth(.80F)
                        .background(MaterialTheme.colorScheme.onBackground)){
                    Text(text = if (viewModel.getWinner().value!=null) {"${viewModel.getWinner().value} Has Won!"}else{"Draw!"},
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.align(Alignment.TopCenter))
                    TextButton(onClick = viewModel::clearGame, modifier = Modifier.align(Alignment.BottomEnd)) {
                        Text(text = "Reset Game")

                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.NewTextButton(
    text: () -> String?,
    onClick: () -> Unit,
    gamedEnded: () -> MutableState<Boolean>
){
    TextButton(
        onClick = onClick,
        shape = RectangleShape,
        enabled = (text() == null && !gamedEnded().value),
        border = BorderStroke(5.dp, Color.LightGray),
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
            .fillMaxWidth()
            .fillMaxHeight(.88f)
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
            onClick = { (viewModel::updateBoard2)(rows[0]) },
            gamedEnded = viewModel::gameEnded,
        )
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[1]) },
            onClick = { (viewModel::updateBoard2)(rows[1]) },
            gamedEnded = viewModel::gameEnded,
        )
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[2]) },
            onClick = { (viewModel::updateBoard2)(rows[2]) },
            gamedEnded = viewModel::gameEnded,
        )
    }
}

@Preview
@Composable
fun test(){
    MaterialTheme {
        GameScreen("")
    }
}