package com.kastik.tictactoe.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kastik.tictactoe.data.GameDataViewModel
import com.kastik.tictactoe.data.GameTypes



@Composable
fun GameScreen(gameMode: String?) {
    val viewModel = GameDataViewModel(gameMode)
    val visibility = remember { viewModel.getWinner() != null}
    Column {
        TicTacToeBoard(viewModel = viewModel)
        AnimatedVisibility(visible = visibility) {
            val winner = remember { viewModel.getWinner() }
            Text(text = "The Winner Is $winner")
        }
    }

}

@Composable
fun TicTacButton(
    text: () -> String?,
    onClick: () -> Unit,
    continuePlaying: () -> Boolean,
    modifier: Modifier
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        border = null,
        shape = RectangleShape,
        enabled = (text() == null && continuePlaying())
        ) {
            //AnimatedVisibility(visible = text() != null) {
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


@Composable
fun TicTacToeRow(
    data1: @Composable RowScope.() -> Unit,
    data2: @Composable RowScope.() -> Unit,
    data3: @Composable RowScope.() -> Unit
) {
    Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
        data1()
        data2()
        data3()
    }
}

@Composable
fun TicTacToeColumn(
    data1: @Composable () -> Unit,
    data2: @Composable () -> Unit,
    data3: @Composable () -> Unit
) {
    Column(Modifier.aspectRatio(1f), verticalArrangement = Arrangement.Center) {
        data1()
        data2()
        data3()
    }
}

@Composable
fun TicTacToeBoard(viewModel: GameDataViewModel){
    TicTacToeColumn(
        data1 = {
            TicTacToeRow(
                data1 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(0) },
                        onClick = { (viewModel::updateBoard)(0) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                },
                data2 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(1) },
                        onClick = { (viewModel::updateBoard)(1) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                },
                data3 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(2) },
                        onClick = { (viewModel::updateBoard)(2) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                }
            )
        }, data2 = {
            TicTacToeRow(
                data1 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(3) },
                        onClick = { (viewModel::updateBoard)(3) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                },
                data2 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(4) },
                        onClick = { (viewModel::updateBoard)(4) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                },
                data3 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(5) },
                        onClick = { (viewModel::updateBoard)(5) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                }
            )
        }, data3 = {
            TicTacToeRow(
                data1 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(6) },
                        onClick = { (viewModel::updateBoard)(6) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                },
                data2 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(7) },
                        onClick = { (viewModel::updateBoard)(7) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                },
                data3 = {
                    TicTacButton(
                        text = { (viewModel::getBoardData)(8) },
                        onClick = { (viewModel::updateBoard)(8) },
                        continuePlaying = viewModel::continuePlaying,
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                    )
                }
            )
        })

}


@Preview
@Composable
fun MyPreview() {
    GameScreen(GameTypes.SinglePlayer.name)

}
