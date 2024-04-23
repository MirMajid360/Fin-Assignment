package com.majid.mvvmtemplate.utils

import android.content.Context
import android.content.Intent
import com.majid.mvvmtemplate.models.User

object Utils {

    fun generateUsers(): List<User> {
        val users = mutableListOf<User>()

        val names = listOf(
            "Majid", "Aksa", "Ali", "Harsh", "Omar","Divya", "Zainab", "Deepika", "Alexa", "Siri", "Mariam",
            "Andy Robin", "Jet Brains", "Naina", "Sumiya", "Hamza", "Nadia", "Hassan", "Amina", "Omar", "Sara",
            "Amir", "Devin", "Cortana", "Jasmine",
        )
        val cities = listOf(
            "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad", "Chennai", "Kolkata", "Surat", "Pune", "Jaipur",
            "Lucknow", "Kanpur", "Nagpur", "Visakhapatnam", "Indore", "Thane", "Bhopal", "Patna", "Vadodara", "Ghaziabad",
            "Ludhiana", "Agra", "Nashik", "Faridabad", "Meerut"
        )

        var id = 0
        repeat(25) {
            val name = names.random()
            val age = (18..65).random()
            val city = cities.random()
            users.add(User(id++,name, age, city))
        }

        return users
    }

    fun isValidPassword(password: String): String {
        val missingCriteria = mutableListOf<String>()

        // Define regex patterns for each validation criterion
        val digitRegex = Regex(".*\\d.*")
        val uppercaseRegex = Regex(".*[A-Z].*")
        val lowercaseRegex = Regex(".*[a-z].*")
        val specialCharRegex = Regex(".*[!@#\$%^&*()-=_+\\[\\]{};:'\"<>,.?/\\\\].*")

        // Check each criterion and collect missing ones
        if (password.length < 7) {
            missingCriteria.add("minimum length of 7 characters")
        }
        if (!digitRegex.matches(password)) {
            missingCriteria.add("at least 1 digit")
        }
        if (!uppercaseRegex.matches(password)) {
            missingCriteria.add("at least 1 uppercase letter")
        }
        if (!lowercaseRegex.matches(password)) {
            missingCriteria.add("at least 1 lowercase letter")
        }
        if (!specialCharRegex.matches(password)) {
            missingCriteria.add("at least 1 special character")
        }

        return if (missingCriteria.isEmpty()) {
            Constants.PASSWORD_VALIDATED
        } else {
            "Password should contain: ${missingCriteria.joinToString(", ")}"
        }
    }

}