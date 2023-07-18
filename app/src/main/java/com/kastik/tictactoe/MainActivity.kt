package com.kastik.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kastik.tictactoe.ui.theme.TicTacToeTheme
import com.kastik.tictactoe.screens.PlayScreen
import com.kastik.tictactoe.screens.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Start()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Start(){
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
            modifier = Modifier.padding(paddingValues)) {
            composable(Screens.HomeScreen.name) {
                HomeScreen(navController)
            }
            composable(Screens.PlayScreen.name) {
                PlayScreen()
            }
            composable(Screens.SettingsScreen.name){
                SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController){
    Column(){
        Button(onClick = { navController.navigate(Screens.PlayScreen.name) }) {
            Text(text = "Single Player")
        }
        Button(onClick = { navController.navigate(Screens.PlayScreen.name) }) {
            Text(text = "On Device MultiPlayer")
        }
        Button(onClick = { navController.navigate(Screens.PlayScreen.name) }) {
            Text(text = "Online MultiPlayer")
        }
    }
}





@Composable
fun muButton(text: () -> String?, onClick: () -> Unit) {

        TextButton(
            onClick = onClick,
            border = BorderStroke(5.dp, Color.LightGray),
            shape = MaterialTheme.shapes.medium,
            enabled = text()==null
        ) {
            Text(text = text().orEmpty())

        }


}

@Composable
fun SettingsScreen(){
    Text("Hello")
}