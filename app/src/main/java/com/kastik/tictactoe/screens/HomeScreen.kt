package com.kastik.tictactoe.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kastik.tictactoe.data.GameTypes

@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = {
            navController.navigate("${AvailableScreens.PlayScreen}/${GameTypes.SinglePlayer.name}")
            }) {
            Text(text = "Single Player")
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = {
            navController.navigate("${AvailableScreens.PlayScreen}/${GameTypes.MultiPlayer.name}")
        }) {
            Text(text = "On Device MultiPlayer")
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = {
            navController.navigate("${AvailableScreens.PlayScreen}/${GameTypes.OnlineMultiPlayer.name}")
        }) {
            Text(text = "Online MultiPlayer")
        }
    }
}


@Preview
@Composable
fun HomePreview(){
    HomeScreen(navController = rememberNavController())
}