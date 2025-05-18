
package com.example.librarymanager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStore(private val context: Context) {
    private val SORT_TYPE = stringPreferencesKey("sort_type")

    val getSortType: Flow<SortType> = context.dataStore.data
        .map { preferences ->
            try {
                SortType.valueOf(preferences[SORT_TYPE] ?: SortType.BY_DATE.name)
            } catch (e: Exception) {
                SortType.BY_DATE
            }
        }

    suspend fun saveSortType(sortType: SortType) {
        context.dataStore.edit { preferences ->
            preferences[SORT_TYPE] = sortType.name
        }
    }
}
