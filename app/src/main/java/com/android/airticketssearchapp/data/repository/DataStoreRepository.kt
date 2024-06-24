package com.android.airticketssearchapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getFrom() = runBlocking { dataStore.data.first()[FROM] }

    suspend fun saveLastFrom(from: String) = dataStore.edit { preferences ->
        preferences[FROM] = from
    }

    companion object {
        private val FROM = stringPreferencesKey("from_tickets")
    }
}