package com.emarketing_paradice.gnsrilanka.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile
import com.emarketing_paradice.gnsrilanka.data.model.User
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: FileRepository, context: Context) : ViewModel() {
    private val sharedPrefs: SharedPreferences =
            context.getSharedPreferences("gn_auth_prefs", Context.MODE_PRIVATE)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _officerProfile = MutableStateFlow<OfficerProfile?>(null)
    val officerProfile: StateFlow<OfficerProfile?> = _officerProfile.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        // Check if a user is already logged in from SharedPreferences
        viewModelScope.launch {
            val savedNic = sharedPrefs.getString("logged_in_nic", null)
            if (savedNic != null) {
                val users = repository.getUsers()
                val user = users.find { it.nic == savedNic }
                if (user != null) {
                    _currentUser.value = user
                    loadOfficerProfile()
                }
            }
        }
    }

    private fun loadOfficerProfile() {
        _currentUser.value?.let { user ->
            viewModelScope.launch {
                val profile = repository.getOfficerProfile(user.nic)
                if (profile == null) {
                    // Initialize default profile for new officer
                    val defaultProfile =
                            OfficerProfile(
                                    officerName = "Officer ${user.nic.takeLast(4)}",
                                    gnDivision = "Assigned Division",
                                    officeAddress = "Regional Office",
                                    contactInfo = "Contact Not Set",
                                    authenticationSettings = "Standard"
                            )
                    repository.saveOfficerProfile(user.nic, defaultProfile)
                    _officerProfile.value = defaultProfile
                } else {
                    _officerProfile.value = profile
                }
            }
        }
    }

    fun saveOfficerProfile(profile: OfficerProfile) {
        _currentUser.value?.let { user ->
            viewModelScope.launch {
                repository.saveOfficerProfile(user.nic, profile)
                _officerProfile.value = profile
            }
        }
    }

    fun login(nic: String, password: String) {
        if (!validateInput(nic, password)) return

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val users = repository.getUsers()
            val user = users.find { it.nic == nic && it.password == password }
            if (user != null) {
                // Save session
                sharedPrefs.edit().putString("logged_in_nic", user.nic).apply()
                _currentUser.value = user
                loadOfficerProfile()
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

            // Save session
            sharedPrefs.edit().putString("logged_in_nic", newUser.nic).apply()

            _currentUser.value = newUser
            loadOfficerProfile()
            _uiState.value = AuthUiState.Success
        }
    }

    private fun validateInput(nic: String, password: String): Boolean {
        if (nic.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("NIC and Password cannot be empty.")
            return false
        }

        // Precise Sri Lankan NIC validation
        val oldNicPattern = Regex("^[0-9]{9}[vVxX]$")
        val newNicPattern = Regex("^[0-9]{12}$")

        if (!oldNicPattern.matches(nic) && !newNicPattern.matches(nic)) {
            _uiState.value =
                    AuthUiState.Error("Invalid NIC format. Use 9 digits + V/X or 12 digits.")
            return false
        }

        if (password.length < 6) {
            _uiState.value = AuthUiState.Error("Password must be at least 6 characters long.")
            return false
        }
        return true
    }

    private fun validateInput(nic: String, password: String, confirm: String): Boolean {
        if (!validateInput(nic, password)) {
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
        sharedPrefs.edit().remove("logged_in_nic").apply()
        _currentUser.value = null
        _officerProfile.value = null
        _uiState.value = AuthUiState.Idle
    }
}

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
