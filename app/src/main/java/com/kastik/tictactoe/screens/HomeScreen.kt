package com.kastik.tictactoe.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kastik.tictactoe.data.DatastoreRepo
import com.kastik.tictactoe.data.GameTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val firstLaunch = DatastoreRepo(LocalContext.current).firstLaunchFlow().collectAsState(initial = false)
    val showDialog = remember { mutableStateOf(firstLaunch.value) }
    if (false){
        AlertDialog(onDismissRequest = { showDialog.value=false }) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Consider donating to support app development and go ad-free!",
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.End) {
                        TextButton(
                            onClick = {
                                showDialog.value = false
                            },
                        ) {
                            Text("Confirm")
                        }
                        TextButton(
                            onClick = {
                                showDialog.value = false
                            },
                        ) {
                            Text("Don't ask again")
                        }

                    }

                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.08f),
            onClick = {
            navController.navigate("${AvailableScreens.GameScreen}/${GameTypes.SinglePlayer.name}"){
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
            Text(text = "Single Player" ,style=MaterialTheme.typography.bodyLarge)
        }
        Button(
            shape= RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.08f),
            onClick = {
            navController.navigate("${AvailableScreens.GameScreen}/${GameTypes.MultiPlayer.name}"){
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
            Text(text = "On Device MultiPlayer",style=MaterialTheme.typography.bodyLarge)
        }
        Button(
            shape= RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.08f),
            onClick = {
            navController.navigate("${AvailableScreens.GameScreen}/${GameTypes.OnlineMultiPlayer.name}"){
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
            Text(text = "Online MultiPlayer",style=MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview
@Composable
fun HomePreview(){
    HomeScreen(navController = rememberNavController())
}
@Preview
@Composable
fun HomePreviewLand(){
    HomeScreen(navController = rememberNavController())
}