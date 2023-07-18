package com.kastik.tictactoe.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kastik.tictactoe.data.MyViewModel


@Composable
fun PlayScreen(viewModel: MyViewModel){
    Column() {
        Row() {
            TicTacButton(
                text =  {(viewModel::getBoardData)(0)},
                onClick = {(viewModel::updateBoard)(0)}
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(1)},
                onClick = {(viewModel::updateBoard)(1)}
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(2)},
                onClick = {(viewModel::updateBoard)(2)}
            )
        }
        Row() {
            TicTacButton(
                text =  {(viewModel::getBoardData)(3)},
                onClick = {(viewModel::updateBoard)(3)}
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(4)},
                onClick = {(viewModel::updateBoard)(4)}
            )
            TicTacButton(
                text =  {(viewModel::getBoardData)(5)},
                onClick = {(viewModel::updateBoard)(5)}
            )
        }
        Row() {
            TicTacButton(
                text =  {(viewModel::getBoardData)(6)},
                onClick = {(viewModel::updateBoard)(6)}
            )
            TicTacButton(
                    text =  {(viewModel::getBoardData)(7)},
            onClick = {(viewModel::updateBoard)(7)}
            )
            TicTacButton(
            text =  {(viewModel::getBoardData)(8)},
            onClick = {(viewModel::updateBoard)(8)}
        )
        }
    }

}


@Composable
fun TicTacButton(text: () -> String?, onClick: () -> Unit) {

    TextButton(
        onClick = onClick,
        border = BorderStroke(5.dp, Color.LightGray),
        shape = MaterialTheme.shapes.extraLarge,
        enabled = text()==null
    ) {
        Text(text = text().orEmpty())

    }


}

@Preview
@Composable
fun MyPreview(){
    PlayScreen(viewModel = MyViewModel())
}