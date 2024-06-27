package com.example.passwordmanagerassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.passwordmanagerassignment.data.PasswordEntry

class PasswordSharedViewModel : ViewModel() {

    private val _passwordEntry = MutableLiveData<PasswordEntry?>()
    val passwordEntry: LiveData<PasswordEntry?> = _passwordEntry

    fun setPasswordEntry(entry: PasswordEntry) {
        _passwordEntry.value = entry
    }

    fun clearPasswordEntry() {
        _passwordEntry.value = null
    }
}
