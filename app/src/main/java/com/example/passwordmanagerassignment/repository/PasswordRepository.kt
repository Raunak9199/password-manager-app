package com.example.passwordmanagerassignment.repository

import android.content.Context
import com.example.passwordmanagerassignment.DB.helper.DatabaseProvider
import com.example.passwordmanagerassignment.data.PasswordEntry
import com.example.passwordmanagerassignment.data.PasswordEntryDao

//class PasswordRepository(private val context: Context) {
//
//    private val passwordEntryDao: PasswordEntryDao = DatabaseProvider.getDatabase(context).passwordEntryDao()
//
//    suspend fun insertPassword(passwordEntry: PasswordEntry): Long {
//        return passwordEntryDao.insertPassword(passwordEntry)
//    }
//
//    suspend fun getAllPasswords(): List<PasswordEntry> {
//        return passwordEntryDao.getAllPasswords()
//    }
//
//    suspend fun updatePassword(passwordEntry: PasswordEntry): Int {
//        return passwordEntryDao.updatePassword(passwordEntry)
//    }
//
//    suspend fun deletePassword(id: Int): Int {
//        return passwordEntryDao.deletePassword(id)
//    }
//}

class PasswordRepository(context: Context) {
    private val passwordEntryDao: PasswordEntryDao = DatabaseProvider.getDatabase(context).passwordEntryDao()

    suspend fun insertPassword(passwordEntry: PasswordEntry): Long {
        return passwordEntryDao.insertPassword(passwordEntry)
    }

    suspend fun getAllPasswords(): List<PasswordEntry> {
        return passwordEntryDao.getAllPasswords()
    }

    suspend fun updatePassword(passwordEntry: PasswordEntry): Int {
        return passwordEntryDao.updatePassword(passwordEntry)
    }

    suspend fun deletePassword(id: Int): Int {
        return passwordEntryDao.deletePassword(id)
    }
}
