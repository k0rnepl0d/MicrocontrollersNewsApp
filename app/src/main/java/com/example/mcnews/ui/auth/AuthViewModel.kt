// app/src/main/java/com/example/mcnews/ui/auth/AuthViewModel.kt
package com.example.mcnews.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcnews.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun login(login: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = repository.login(login, password)
            callback(success)
        }
    }

    fun register(login: String, password: String, firstName: String?, lastName: String?, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = repository.register(login, password, firstName, lastName)
            callback(success)
        }
    }
}