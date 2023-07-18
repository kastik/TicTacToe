package com.kastik.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kastik.tictactoe.data.MyViewModel
import com.kastik.tictactoe.screens.GameTypes
import com.kastik.tictactoe.ui.theme.TicTacToeTheme
import com.kastik.tictactoe.screens.PlayScreen
import com.kastik.tictactoe.screens.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MyViewModel()
        setContent {
            TicTacToeTheme {
                Start(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Start(viewModel: MyViewModel){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(text = "TicTacToe") },
                actions = {
                    IconButton(onClick = {navController.navigate(Screens.SettingsScreen.name)}){
                        Icon(Icons.Default.Settings,
                            contentDescription = null,
                        )
                    }
                }
            )}
    ){
        paddingValues ->
        NavHost(navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            composable(Screens.HomeScreen.name) {
                HomeScreen(navController,viewModel)
            }
            composable(Screens.PlayScreen.name) {
                PlayScreen(viewModel)
            }
            composable(Screens.SettingsScreen.name){
                SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController,viewModel: MyViewModel){
    Column(){
        Button(onClick = {
            viewModel.setMode(GameTypes.SinglePlayer)
            navController.navigate(Screens.PlayScreen.name)
        }) {
            Text(text = "Single Player")
        }
        Button(onClick = {
            viewModel.setMode(GameTypes.MultiPlayer)
            navController.navigate(Screens.PlayScreen.name)
        }) {
            Text(text = "On Device MultiPlayer")
        }
        Button(onClick = { navController.navigate(Screens.PlayScreen.name) }) {
            Text(text = "Online MultiPlayer")
        }
    }
}







@Composable
fun SettingsScreen(){
    Text("Hello")
}