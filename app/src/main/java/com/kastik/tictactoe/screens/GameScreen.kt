package com.kastik.tictactoe.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
import com.kastik.tictactoe.data.GameTypes
import com.kastik.tictactoe.ui.theme.TicTacToeTheme
import java.util.Arrays


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(gameMode: String?) {
    val dataStore = DatastoreRepo(LocalContext.current)
    val viewModel = remember { GameDataViewModel(gameMode, dataStore) }

    Column() {
        TicTacBoard(viewModel = viewModel)
        if (viewModel.getGameEnded().value) {
            Dialog(
                onDismissRequest = { viewModel.clearGame() }) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = if (viewModel.getWinner() != null) {
                                "${viewModel.getWinner()} Has Won!"
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
        }
        AdvertView()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.TicTacToeButton(
    text: () -> String?,
    onClick: () -> Unit,
    gamedEnded: () -> MutableState<Boolean>,
    previousPlayerFinished: () -> MutableState<Boolean>,
    modifier: Modifier
){
    TextButton(
        onClick = onClick,
        shape = RectangleShape,
        enabled = (text() == null && !gamedEnded().value && previousPlayerFinished().value
                ),
        border = BorderStroke(5.dp, Color.LightGray),
        modifier = Modifier
            .weight(1f)
            .fillMaxSize().then(modifier)
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

/*
@Composable
fun TicTacToeBoard(viewModel: GameDataViewModel){
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(.88f)
            .aspectRatio(1f)) {
        TicTacToeRow(viewModel, intArrayOf(0,1,2))
        TicTacToeRow(viewModel, intArrayOf(3,4,5))
        TicTacToeRow(viewModel, intArrayOf(6,7,8))


    }
}

 */

/*
@Composable
fun ColumnScope.TicTacToeRow(
    viewModel: GameDataViewModel,
    rows: IntArray
){
    Row(
        Modifier
            .weight(1f)
            .fillMaxSize()) {
        TicTacToeButton(
            text = { (viewModel::getBoardData)(rows[0]) },
            onClick = { (viewModel::makeAMove)(rows[0]) },
            gamedEnded = viewModel::getGameEnded,
            previousPlayerFinished = viewModel::getPreviousPlayerFinished
        )
        TicTacToeButton(
            text = { (viewModel::getBoardData)(rows[1]) },
            onClick = { (viewModel::makeAMove)(rows[1]) },
            gamedEnded = viewModel::getGameEnded,
            previousPlayerFinished = viewModel::getPreviousPlayerFinished
        )
        TicTacToeButton(
            text = { (viewModel::getBoardData)(rows[2]) },
            onClick = { (viewModel::makeAMove)(rows[2]) },
            gamedEnded = viewModel::getGameEnded,
            previousPlayerFinished = viewModel::getPreviousPlayerFinished
        )
    }
}
 */

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

@Composable
fun GameScreenPreview(){
    TicTacToeTheme {
            GameScreen(GameTypes.SinglePlayer.name)
    }
}



@Composable
fun TicTacBoard(viewModel: GameDataViewModel){
    val tictactoes: MutableList<MutableList<String>> = mutableListOf(
        mutableListOf("X", "O", "X", "X"),
        mutableListOf("O", "X", "O", "X"),
        mutableListOf("X", "O", "X", "X"),
        mutableListOf("X", "O", "X", "X")
    )

    Column {
        for (i in 0..tictactoes.size - 1) {
            Row {
                for (j in 0..tictactoes[0].size - 1) {
                    if ( i>0 && j > 0 ) { // Ola ektos ton terma aristeron kai terma dexion stilon
                        TicTacToeButton(
                            text = { (viewModel::getBoardData)(listOf(i,j)) },
                            onClick = { (viewModel::makeAMove)(listOf(i,j)) },
                            gamedEnded = viewModel::getGameEnded,
                            previousPlayerFinished = viewModel::getPreviousPlayerFinished,
                            modifier = Modifier.bottomRightBorder(5.dp, Color.LightGray)
                        )
                    } else {
                        if ( i == 0 && j==0) { // To pano aristera mono
                            TicTacToeButton(
                                text = { (viewModel::getBoardData)(listOf(i,j)) },
                                onClick = { (viewModel::makeAMove)(listOf(i,j)) },
                                gamedEnded = viewModel::getGameEnded,
                                previousPlayerFinished = viewModel::getPreviousPlayerFinished,
                                modifier = Modifier.myBorder(5.dp, Color.LightGray)
                            )
                        }
                        else {
                            if (j==0 && i>0) { //Ola ta pano aristera
                                TicTacToeButton(
                                    text = { (viewModel::getBoardData)(listOf(i,j)) },
                                    onClick = { (viewModel::makeAMove)(listOf(i,j)) },
                                    gamedEnded = viewModel::getGameEnded,
                                    previousPlayerFinished = viewModel::getPreviousPlayerFinished,
                                    modifier = Modifier.topRightBottomBorder(5.dp, Color.LightGray)
                                )
                            }
                            else { // Ola ta aristera
                                TicTacToeButton(
                                    text = { (viewModel::getBoardData)(listOf(i,j)) },
                                    onClick = { (viewModel::makeAMove)(listOf(i,j)) },
                                    gamedEnded = viewModel::getGameEnded,
                                    previousPlayerFinished = viewModel::getPreviousPlayerFinished,
                                    modifier = Modifier.leftBottomRightBorder(5.dp, Color.LightGray)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}




fun Modifier.myBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x = width, 0f),
                end = Offset(x = width,y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x =0f,y= 0f),
                end = Offset(width,0f),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x=0f, y=0f),
                end = Offset(x = 0f, y=height),
                strokeWidth = strokeWidthPx
            )
        }

    }
)



fun Modifier.bottomRightBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x = width, 0f),
                end = Offset(x = width,y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)


fun Modifier.topRightBottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x = width, 0f),
                end = Offset(x = width,y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x =0f,y= 0f),
                end = Offset(width,0f),
                strokeWidth = strokeWidthPx
            )
        }
    }
)
fun Modifier.leftBottomRightBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x = width, 0f),
                end = Offset(x = width,y = height),
                strokeWidth = strokeWidthPx
            )
            drawLine(
                color = color,
                start = Offset(x = 0f, 0f),
                end = Offset(x = 0f,y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

