package com.kastik.tictactoe.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.08f),
            onClick = {
            navController.navigate("${AvailableScreens.GameScreen}/${GameTypes.SinglePlayer.name}")
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
            navController.navigate("${AvailableScreens.GameScreen}/${GameTypes.MultiPlayer.name}")
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
            navController.navigate("${AvailableScreens.GameScreen}/${GameTypes.OnlineMultiPlayer.name}")
        }) {
            Text(text = "Online MultiPlayer",style=MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview(device = "id:pixel_2_xl")
@Composable
fun HomePreview(){
    HomeScreen(navController = rememberNavController())
}
@Preview(device = "spec:width=2880dp,height=1440dp,dpi=460")
@Composable
fun HomePreviewLand(){
    HomeScreen(navController = rememberNavController())
}