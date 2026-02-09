package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.User
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: FileRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        // Check if a user is already logged in
        viewModelScope.launch {
            // In a real app, you might load the current user from a session cache or token
        }
    }

    fun login(nic: String, password: String) {
        if (!validateInput(nic, password)) return

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val users = repository.getUsers()
            val user = users.find { it.nic == nic && it.password == password }
            if (user != null) {
                _currentUser.value = user
                _uiState.value = AuthUiState.Success
            } else {
                _uiState.value = AuthUiState.Error("Invalid NIC or Password")
            }
        }
    }

    fun register(nic: String, password: String, confirmPassword: String) {
        if (!validateInput(nic, password, confirmPassword)) return

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val users = repository.getUsers().toMutableList()
            if (users.any { it.nic == nic }) {
                _uiState.value = AuthUiState.Error("A user with this NIC already exists.")
                return@launch
            }

            val newUser = User(nic, password)
            users.add(newUser)
            repository.saveUsers(users)
            _currentUser.value = newUser
            _uiState.value = AuthUiState.Success
        }
    }

    private fun validateInput(nic: String, password: String): Boolean {
        if (nic.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("NIC and Password cannot be empty.")
            return false
        }
        // Basic NIC validation (can be improved)
        if (nic.length < 10) {
             _uiState.value = AuthUiState.Error("Invalid NIC format.")
            return false
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState.Error("Password must be at least 6 characters long.")
            return false
        }
        return true
    }
    
    private fun validateInput(nic: String, password: String, confirm: String): Boolean {
        if(!validateInput(nic,password)){
            return false
        }
        if (password != confirm) {
            _uiState.value = AuthUiState.Error("Passwords do not match.")
            return false
        }
        return true
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }

    fun logout() {
        _currentUser.value = null
        _uiState.value = AuthUiState.Idle
    }
}

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
