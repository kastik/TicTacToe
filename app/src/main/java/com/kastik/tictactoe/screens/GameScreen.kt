package com.kastik.tictactoe.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kastik.tictactoe.data.GameDataViewModel
import com.kastik.tictactoe.data.GameTypes


@Composable
fun PlayScreen(mode: String?){
    val viewModel = GameDataViewModel(mode)
    Column(modifier=Modifier.fillMaxSize()) {
        Row(modifier=Modifier.fillMaxWidth()) {
            TicTacButton(
                text =  {(viewModel::getBoardData)(0)},
                onClick = {(viewModel::updateBoard)(0)},
                continuePlaying = viewModel::continuePlaying
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(1)},
                onClick = {(viewModel::updateBoard)(1)},
                continuePlaying = viewModel::continuePlaying
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(2)},
                onClick = {(viewModel::updateBoard)(2)},
                continuePlaying = viewModel::continuePlaying
            )
        }
        Row(modifier=Modifier.fillMaxWidth()) {
            TicTacButton(
                text =  {(viewModel::getBoardData)(3)},
                onClick = {(viewModel::updateBoard)(3)},
                continuePlaying = viewModel::continuePlaying
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(4)},
                onClick = {(viewModel::updateBoard)(4)},
                continuePlaying = viewModel::continuePlaying
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(5)},
                onClick = {(viewModel::updateBoard)(5)},
                continuePlaying = viewModel::continuePlaying
            )
        }
        Row(modifier=Modifier.fillMaxWidth()) {
            TicTacButton(
                text =  {(viewModel::getBoardData)(6)},
                onClick = {(viewModel::updateBoard)(6)},
                continuePlaying = viewModel::continuePlaying
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(7)},
                onClick = {(viewModel::updateBoard)(7)},
                continuePlaying = viewModel::continuePlaying
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(8)},
                onClick = {(viewModel::updateBoard)(8)},
                continuePlaying = viewModel::continuePlaying
        )
        }
    }

}


@Composable
fun TicTacButton(text: () -> String?, onClick: () -> Unit, continuePlaying: ()-> Boolean) {

    TextButton(
        onClick = onClick,
        border = BorderStroke(5.dp, Color.LightGray),
        shape = MaterialTheme.shapes.extraLarge,
        enabled = (text()==null && continuePlaying())
    ) {
        AnimatedVisibility(visible = text()!=null) {
            Text(text = text().orEmpty())
        }


    }


}

@Preview
@Composable
fun MyPreview(){
    PlayScreen(GameTypes.SinglePlayer.name)
}