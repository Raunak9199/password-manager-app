package com.example.passwordmanagerassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerassignment.data.PasswordEntry
import com.example.passwordmanagerassignment.repository.PasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PasswordRepository = PasswordRepository(application)

    private val _passwords = MutableLiveData<List<PasswordEntry>>()
    val passwords: LiveData<List<PasswordEntry>> = _passwords

    init {
        getAllPasswords()
    }

    fun getAllPasswords() {
        viewModelScope.launch(Dispatchers.IO) {
            val passwords = repository.getAllPasswords()
            _passwords.postValue(passwords)
        }
    }

    fun insertPassword(passwordEntry: PasswordEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPassword(passwordEntry)
            getAllPasswords()
        }
    }

    fun updatePassword(passwordEntry: PasswordEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePassword(passwordEntry)
            getAllPasswords()
        }
    }
    fun deletePassword(passwordEntry: PasswordEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePassword(passwordEntry.id)
            getAllPasswords()
        }
    }
}

