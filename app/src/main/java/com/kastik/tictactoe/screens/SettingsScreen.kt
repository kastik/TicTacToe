package com.kastik.tictactoe.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(){
    var someVal by remember { mutableStateOf(true)}
    var someVal2 by rememberSaveable { mutableStateOf(false) }
    Column(modifier= Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "PlayFirst",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(10.dp))
            Switch(checked = someVal,
                onCheckedChange = {someVal=!someVal},
                modifier = Modifier.padding(10.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "SomeOther Setting",modifier = Modifier.padding(10.dp).weight(1f),style = MaterialTheme.typography.displaySmall)
            Switch(checked = someVal2, onCheckedChange = {someVal2=!someVal2 },modifier = Modifier.padding(10.dp))
        }
    }
}

@Preview
@Composable
fun SettingsPreview(){
    SettingsScreen()
}