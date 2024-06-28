package com.example.passwordmanagerassignment.DB.helper

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var INSTANCE: PasswordManagerDatabase? = null

    fun getDatabase(context: Context): PasswordManagerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PasswordManagerDatabase::class.java,
                "passwords.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}