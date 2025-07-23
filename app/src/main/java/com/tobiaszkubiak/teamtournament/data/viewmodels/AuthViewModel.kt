package com.tobiaszkubiak.teamtournament.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tobiaszkubiak.teamtournament.data.User
import com.tobiaszkubiak.teamtournament.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registrationState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registrationState: StateFlow<AuthState> = _registrationState

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun registerUser(
        firstName: String, lastName: String, phone: String, email: String, password: String
    ) {
        viewModelScope.launch {
            _registrationState.value = AuthState.Loading
            try {
                userRepository.registerAndSaveUser(firstName, lastName, phone, email, password)
                _registrationState.value = AuthState.Success
            } catch (e: Exception) {
                _registrationState.value = AuthState.Error(e.message ?: "Nieznany błąd")
            }
        }
    }
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                userRepository.loginUser(email, password)
                loadCurrentUser()
                _loginState.value = AuthState.Success
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.message ?: "Błąd logowania")
            }
        }
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = userRepository.getUserData()
        }
    }

    fun logout() {
        userRepository.logout()
        _currentUser.value = null
        _loginState.value = AuthState.Idle
    }
}

class AuthViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}