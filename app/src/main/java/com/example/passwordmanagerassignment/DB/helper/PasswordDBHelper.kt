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


//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.example.passwordmanagerassignment.data.PasswordEntry
//
//class PasswordDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    override fun onCreate(db: SQLiteDatabase) {
//        val createTable = "CREATE TABLE $TABLE_NAME (" +
//                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "$COLUMN_ACCOUNT_NAME TEXT," +
//                "$COLUMN_USERNAME_EMAIL TEXT," +
//                "$COLUMN_ENCRYPTED_PASSWORD TEXT)"
//        db.execSQL(createTable)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        onCreate(db)
//    }
//
//    fun insertPassword(passwordEntry: PasswordEntry): Long {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_ACCOUNT_NAME, passwordEntry.accountName)
//            put(COLUMN_USERNAME_EMAIL, passwordEntry.usernameEmail)
//            put(COLUMN_ENCRYPTED_PASSWORD, passwordEntry.encryptedPassword)
//        }
//        return db.insert(TABLE_NAME, null, values)
//    }
//
//    fun getAllPasswords(): List<PasswordEntry> {
//        val passwords = mutableListOf<PasswordEntry>()
//        val db = readableDatabase
//        val cursor = db.query(
//            TABLE_NAME,
//            null,
//            null,
//            null,
//            null,
//            null,
//            null
//        )
//        with(cursor) {
//            while (moveToNext()) {
//                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
//                val accountName = getString(getColumnIndexOrThrow(COLUMN_ACCOUNT_NAME))
//                val usernameEmail = getString(getColumnIndexOrThrow(COLUMN_USERNAME_EMAIL))
//                val encryptedPassword = getString(getColumnIndexOrThrow(COLUMN_ENCRYPTED_PASSWORD))
//                val passwordEntry = PasswordEntry(id, accountName, usernameEmail, encryptedPassword)
//                passwords.add(passwordEntry)
//            }
//        }
//        cursor.close()
//        return passwords
//    }
//
//    fun updatePassword(passwordEntry: PasswordEntry): Int {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_ACCOUNT_NAME, passwordEntry.accountName)
//            put(COLUMN_USERNAME_EMAIL, passwordEntry.usernameEmail)
//            put(COLUMN_ENCRYPTED_PASSWORD, passwordEntry.encryptedPassword)
//        }
//        return db.update(
//            TABLE_NAME,
//            values,
//            "$COLUMN_ID = ?",
//            arrayOf(passwordEntry.id.toString())
//        )
//    }
//
//    fun deletePassword(id: Int): Int {
//        val db = writableDatabase
//        return db.delete(
//            TABLE_NAME,
//            "$COLUMN_ID = ?",
//            arrayOf(id.toString())
//        )
//    }
//
//    companion object {
//        private const val DATABASE_NAME = "passwords.db"
//        private const val DATABASE_VERSION = 1
//        private const val TABLE_NAME = "passwords"
//        private const val COLUMN_ID = "id"
//        private const val COLUMN_ACCOUNT_NAME = "accountName"
//        private const val COLUMN_USERNAME_EMAIL = "usernameEmail"
//        private const val COLUMN_ENCRYPTED_PASSWORD = "encryptedPassword"
//    }
//}
