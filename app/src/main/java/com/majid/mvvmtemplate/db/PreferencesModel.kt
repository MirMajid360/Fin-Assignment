package com.majid.mvvmtemplate.db

// PreferencesModel.kt
data class PreferencesModel(
    val userName: String = "",
    val isUserLoggedIn: Boolean = false,
    val userAge: Int = 0
    // Add more preferences as needed
)
