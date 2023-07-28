package com.kastik.tictactoe.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Start(){
    val navController = rememberNavController()
    val showSettingsIcon = remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "TicTacToe") },
                actions = {
                    AnimatedVisibility(visible = showSettingsIcon.value, enter = scaleIn(),exit= scaleOut()) {
                        IconButton(onClick = {
                            navController.navigate(AvailableScreens.SettingsScreen.name) {
                                popUpTo(AvailableScreens.HomeScreen.name) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                            )
                        }
                    }
                }
            )
        }
    ){
            paddingValues ->
        NavHost(navController = navController,
            startDestination = AvailableScreens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            composable(AvailableScreens.HomeScreen.name) {
                showSettingsIcon.value = true
                HomeScreen(navController)
            }
            composable("${AvailableScreens.GameScreen.name}/{GameType}") { navBackStackEntry ->
                showSettingsIcon.value = false
                GameScreen(navBackStackEntry.arguments?.getString("GameType"))
            }
            composable(AvailableScreens.SettingsScreen.name){
                showSettingsIcon.value = false
                SettingsScreen()
            }
        }
    }
}

@Preview
@Composable
fun MainView() {
    Start()
}