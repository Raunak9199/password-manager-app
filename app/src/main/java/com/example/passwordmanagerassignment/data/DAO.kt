package com.example.passwordmanagerassignment.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordEntryDao {

    @Insert
    suspend fun insertPassword(passwordEntry: PasswordEntry): Long

    @Query("SELECT * FROM passwords")
    suspend fun getAllPasswords(): List<PasswordEntry>

    @Update
    suspend fun updatePassword(passwordEntry: PasswordEntry): Int

    @Query("DELETE FROM passwords WHERE id = :id")
    suspend fun deletePassword(id: Int): Int
}
