package com.example.passwordmanagerassignment.utils

//import kotlin.random.Random

object PasswordGenerator {
    private const val DIGITS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+<>?"

    fun generatePassword(length: Int = 10): String {
        return (1..length)
            .map { DIGITS.random() }
            .joinToString("")
    }
}

