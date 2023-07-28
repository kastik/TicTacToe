package com.kastik.tictactoe.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.kastik.tictactoe.R
import com.kastik.tictactoe.data.DatastoreRepo
import com.kastik.tictactoe.data.GameDataViewModel
import com.kastik.tictactoe.data.GameModes
import com.kastik.tictactoe.data.GameTypes
import com.kastik.tictactoe.ui.theme.TicTacToeTheme
import java.util.Arrays


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(gameMode: String?) {
    val dataStore = DatastoreRepo(LocalContext.current)
    val viewModel = remember { GameDataViewModel(gameMode, dataStore) }

    Row() {
        NewTicTacBoard(viewModel = viewModel)
        val alpha: Float by animateFloatAsState(if (!viewModel.gameEnded().value) 0f else 1f)
        Box(modifier = Modifier.size(alpha.dp))
            Dialog(onDismissRequest = { viewModel.clearGame() }) {
                Column(modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = if (viewModel.getWinner().value != null) {
                                "${viewModel.getWinner().value} Has Won!"
                            } else {
                                "Draw!"
                            },
                            style = MaterialTheme.typography.displaySmall,
                        )
                    }
                    Row(horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = viewModel::clearGame) {
                            Text(text = "Reset Game")
                        }
                    }
                }
            }
        //AdvertView()
    }
    }




@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.NewTextButton(
    text: () -> String?,
    onClick: () -> Unit,
    gamedEnded: () -> MutableState<Boolean>,
    previousPlayerFinished: () -> MutableState<Boolean>
){
    TextButton(
        onClick = onClick,
        shape = RectangleShape,
        enabled = (text() == null && !gamedEnded().value && previousPlayerFinished().value ),
        border = BorderStroke(5.dp, Color.LightGray),
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    )
    {
        AnimatedVisibility(visible = text() != null,enter = scaleIn()) {
            Text(
                text = text().orEmpty(), style = MaterialTheme.typography.displayLarge,
                color =
                if (text().equals("X"))
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun NewTicTacBoard(viewModel: GameDataViewModel){
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(.88f)
            .aspectRatio(1f)) {
        NewTicTacRow(viewModel, intArrayOf(0,1,2))
        NewTicTacRow(viewModel, intArrayOf(3,4,5))
        NewTicTacRow(viewModel, intArrayOf(6,7,8))


    }
}


@Composable
fun ColumnScope.NewTicTacRow(
    viewModel: GameDataViewModel,
    rows: IntArray
){
    Row(
        Modifier
            .weight(1f)
            .fillMaxSize()) {
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[0]) },
            onClick = { (viewModel::updateBoard2)(rows[0]) },
            gamedEnded = viewModel::gameEnded,
            previousPlayerFinished = viewModel::previousPlayerFinished
        )
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[1]) },
            onClick = { (viewModel::updateBoard2)(rows[1]) },
            gamedEnded = viewModel::gameEnded,
            previousPlayerFinished = viewModel::previousPlayerFinished
        )
        NewTextButton(
            text = { (viewModel::getBoardData)(rows[2]) },
            onClick = { (viewModel::updateBoard2)(rows[2]) },
            gamedEnded = viewModel::gameEnded,
            previousPlayerFinished = viewModel::previousPlayerFinished
        )
    }
}

@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = "Advert Here",
        )
    } else {
        AndroidView(
            modifier = modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = context.getString(R.string.ad_id_banner)
                    MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("61D250C7A86D0BD85B1A53F63D45A496")).build())
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}





@Preview
@Composable
fun test(){
    TicTacToeTheme {
            GameScreen(GameTypes.SinglePlayer.name)
    }
}