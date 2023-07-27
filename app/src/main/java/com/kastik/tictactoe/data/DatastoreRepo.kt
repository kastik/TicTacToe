package com.kastik.tictactoe.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlin.properties.Delegates


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DatastoreRepo(private val context: Context) {


    companion object {
        val gameDifficultyPreference = stringPreferencesKey("gameDifficulty")
        val playFirstPreferece = booleanPreferencesKey("playFirst")
        val playAsXPreference = booleanPreferencesKey("playAsX")
    }


    suspend fun gameDifficulty(): String {
        lateinit var gameDifficulty: String
        context.dataStore.data
            .map { preferences ->
                preferences[gameDifficultyPreference] ?: "Medium"
            }.collect{
                gameDifficulty = it
            }
        return gameDifficulty
    }


    suspend fun playFirst(): Boolean {
        var playFirst:Boolean = false
        context.dataStore.data
            .map { preferences ->
                preferences[playFirstPreferece] ?: false
            }.collect{
                playFirst = it
            }
        return playFirst
    }




    suspend fun playAsX(): Boolean {
        var playAsX:Boolean = true
        context.dataStore.data
            .map { preferences ->
                preferences[playAsXPreference] ?: true
            }.collect{
                playAsX = it
            }
        return playAsX
    }




      fun gameDifficultyFlow(): Flow<String> = context.dataStore.data
            .map { preferences ->
                preferences[gameDifficultyPreference] ?: "Medium"
            }



    fun playFirstFlow(): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[playFirstPreferece]?: false
        }




    fun playAsXFlow(): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[playAsXPreference]?: true
        }





    suspend fun setplayAsX(playAsX: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[playAsXPreference] = playAsX
        }
    }
    suspend fun setplayFirst(playFirst: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[playFirstPreferece] = playFirst
        }
    }

    suspend fun setGameDifficulty(gameDifficulty: String) {
        context.dataStore.edit { preferences ->
            preferences[gameDifficultyPreference] = gameDifficulty
        }
    }



}

data class myPrefs(val gameDifficulty: String,val playFirst: Boolean,val playAsX: Boolean)