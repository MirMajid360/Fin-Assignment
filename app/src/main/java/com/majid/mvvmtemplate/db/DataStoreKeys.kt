package com.majid.mvvmtemplate.db

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
     val USER_KEY = stringPreferencesKey("user")

     val USER_NAME_KEY = stringPreferencesKey("user_name")
     val IS_USER_LOGGED_IN_KEY = booleanPreferencesKey("is_user_logged_in")
     val USER_AGE_KEY = intPreferencesKey("user_age")
}