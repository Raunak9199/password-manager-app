package com.example.passwordmanagerassignment.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "passwords")
data class PasswordEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountName: String,
    val usernameEmail: String,
    val encryptedPassword: String
) : Parcelable




//import android.os.Parcel
//import android.os.Parcelable
//
//data class PasswordEntry(
//    val id: Int = -1,
//    val accountName: String,
//    val usernameEmail: String,
//    val encryptedPassword: String
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: ""
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(id)
//        parcel.writeString(accountName)
//        parcel.writeString(usernameEmail)
//        parcel.writeString(encryptedPassword)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<PasswordEntry> {
//        override fun createFromParcel(parcel: Parcel): PasswordEntry {
//            return PasswordEntry(parcel)
//        }
//
//        override fun newArray(size: Int): Array<PasswordEntry?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
