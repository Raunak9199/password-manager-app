package com.example.passwordmanagerassignment.DB.helper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanagerassignment.data.PasswordEntry
import com.example.passwordmanagerassignment.data.PasswordEntryDao

@Database(entities = [PasswordEntry::class], version = 1, exportSchema = false)
abstract class PasswordManagerDatabase : RoomDatabase() {
    abstract fun passwordEntryDao(): PasswordEntryDao
}
