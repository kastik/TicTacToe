package com.kastik.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kastik.tictactoe.screens.HomeScreen
import com.kastik.tictactoe.ui.theme.TicTacToeTheme
import com.kastik.tictactoe.screens.PlayScreen
import com.kastik.tictactoe.screens.AvailableScreens

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
                    IconButton(onClick = {navController.navigate(AvailableScreens.SettingsScreen.name)}){
                        Icon(Icons.Default.Settings,
                            contentDescription = null,
                        )
                    }
                }
            )}
    ){
        paddingValues ->
        NavHost(navController = navController,
            startDestination = AvailableScreens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            composable(AvailableScreens.HomeScreen.name) {
                HomeScreen(navController)
            }
            composable("${AvailableScreens.PlayScreen.name}/{GameType}") { NavBackStackEntry ->
                PlayScreen(NavBackStackEntry.arguments?.getString("GameType"))
            }
            composable(AvailableScreens.SettingsScreen.name){
                SettingsScreen()
            }
        }
    }
}









@Composable
fun SettingsScreen(){
    Text("Hello")
}