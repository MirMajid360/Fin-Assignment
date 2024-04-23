package com.majid.mvvmtemplate.db

import com.google.gson.Gson
import com.majid.mvvmtemplate.models.User

object DataStoreUtils {
    private val gson = Gson()

    fun serializeUser(user: User): String {
        return gson.toJson(user)
    }

    fun deserializeUser(serializedUser: String): User {
        return gson.fromJson(serializedUser, User::class.java)
    }
}