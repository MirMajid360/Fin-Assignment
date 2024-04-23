package com.majid.mvvmtemplate.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.majid.mvvmtemplate.db.DataStoreKeys
import com.majid.mvvmtemplate.db.DataStoreUtils
import com.majid.mvvmtemplate.db.PreferencesModel
import com.majid.mvvmtemplate.di.DataStorePreferences
import com.majid.mvvmtemplate.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    @DataStorePreferences private val dataStore: DataStore<Preferences>,
) {

    companion object {
        // Define keys for preferences

        // Add more keys as needed
    }

    val preferencesFlow: Flow<PreferencesModel> = dataStore.data
        .map { preferences ->
            PreferencesModel(
                userName = preferences[DataStoreKeys.USER_NAME_KEY] ?: "",
                isUserLoggedIn = preferences[DataStoreKeys.IS_USER_LOGGED_IN_KEY] ?: false,
                userAge = preferences[DataStoreKeys.USER_AGE_KEY] ?: 0
                // Map more preferences as needed
            )
        }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.USER_NAME_KEY] = userName
        }
    }

    suspend fun saveUserLoggedInStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.IS_USER_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    suspend fun getUserLoggedInStatus(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[DataStoreKeys.IS_USER_LOGGED_IN_KEY] ?: false // Return false if the key is not found
        }.first()
    }

    suspend fun saveUserAge(userAge: Int) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.USER_AGE_KEY] = userAge
        }
    }


    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.USER_KEY] = DataStoreUtils.serializeUser(user)
        }
    }

  suspend  fun getUser(): Unit = runBlocking {
        dataStore.data.map { preferences ->
            val serializedUser = preferences[DataStoreKeys.USER_KEY]
            serializedUser?.let { DataStoreUtils.deserializeUser(it) }
        }.first()
    }

    // Add more functions to save other preferences as needed
}
