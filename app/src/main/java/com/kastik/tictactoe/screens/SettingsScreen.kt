package com.kastik.tictactoe.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.kastik.tictactoe.data.DatastoreRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(){
    val scope = rememberCoroutineScope()
    val con = LocalContext.current
    val repo = DatastoreRepo(con)

    var expanded by remember { mutableStateOf(false) }

    val selectedText by repo.gameDifficultyFlow().collectAsState(initial = "Medium")
    val playFirst by repo.playFirstFlow().collectAsState(initial = true)
    val playAsX by repo.playAsXFlow().collectAsState(initial = true)

    Column(modifier= Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Play First",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(10.dp))
            Switch(checked = playFirst,
                onCheckedChange = {
                    scope.launch { repo.setplayFirst(!playFirst) }},
                modifier = Modifier.padding(10.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Play as X",modifier = Modifier
                .padding(10.dp)
                .weight(1f),style = MaterialTheme.typography.displaySmall)
            Switch(checked = playAsX, onCheckedChange = {
                scope.launch {
                    repo.setplayAsX(!playAsX)
                }},modifier = Modifier.padding(10.dp))
        }
        Row(){
            Text(
                text = "Dificulty",
                modifier = Modifier
                    .padding(10.dp)
                    .weight(3f),
                style = MaterialTheme.typography.displaySmall
            )
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(2f),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    // The `menuAnchor` modifier must be passed to the text field for correctness.
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = selectedText,
                    onValueChange = {},
                    //trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text =  { Text(text = "Easy")},
                        onClick = {
                            expanded=false
                            scope.launch(Dispatchers.IO)  { repo.setGameDifficulty("Easy")}},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    DropdownMenuItem(
                        text = {  Text(text = "Medium") },
                        onClick = {
                            expanded=false
                            scope.launch(Dispatchers.IO) { repo.setGameDifficulty("Medium")}},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    DropdownMenuItem(
                        text = {  Text(text = "Hard") },
                        onClick = {
                            expanded=false
                            scope.launch(Dispatchers.IO)  { repo.setGameDifficulty("Hard")}},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                }
            }

        }
        Row(){
                Text(
                    text = "Visit Privacy Policy",
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                        .clickable {
                            val browserIntent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://tictactoe-66ae1.web.app/privacy-poliy.html")
                                )
                            startActivity(con, browserIntent, null);

                        },
                    style = MaterialTheme.typography.displaySmall
                )

        }

    }
}



@Preview
@Composable
fun SettingsPreview(){
    SettingsScreen()
}