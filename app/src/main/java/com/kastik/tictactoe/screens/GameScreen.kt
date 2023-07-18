package com.kastik.tictactoe.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.kastik.tictactoe.data.MyViewModel
import com.kastik.tictactoe.muButton

@Preview
@Composable
fun PlayScreen(){
    val viewModel = MyViewModel()
    Column() {
        Row {
            muButton(
                text =  {(viewModel::getBoardData)(0)},
                onClick = {(viewModel::updateBoard)(0)}
            )
            muButton(
                text =  {(viewModel::getBoardData)(1)},
                onClick = {(viewModel::updateBoard)(1)}
            )
            muButton(
                text =  {(viewModel::getBoardData)(2)},
                onClick = {(viewModel::updateBoard)(2)}
            )
        }
        Row {
            muButton(
                text =  {(viewModel::getBoardData)(3)},
                onClick = {(viewModel::updateBoard)(3)}
            )
            muButton(
                text =  {(viewModel::getBoardData)(4)},
                onClick = {(viewModel::updateBoard)(4)}
            )
            muButton(
                text =  {(viewModel::getBoardData)(5)},
                onClick = {(viewModel::updateBoard)(5)}
            )
        }
        Row {
            muButton(
                text =  {(viewModel::getBoardData)(6)},
                onClick = {(viewModel::updateBoard)(6)}
            )
            muButton(
                    text =  {(viewModel::getBoardData)(7)},
            onClick = {(viewModel::updateBoard)(7)}
            )
            muButton(
            text =  {(viewModel::getBoardData)(8)},
            onClick = {(viewModel::updateBoard)(8)}
        )
        }
    }

}