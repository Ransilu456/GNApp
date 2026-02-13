package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Citizen
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CitizenUiState(
        val citizens: List<Citizen> = emptyList(),
        val nic: String = "",
        val fullName: String = "",
        val dateOfBirth: String = "",
        val gender: String = "",
        val occupation: String = "",
        val householdId: String = "",
        val address: String = "",
        val nicError: String? = null,
        val fullNameError: String? = null,
        val dobError: String? = null,
        val householdIdError: String? = null,
        val formStatus: FormStatus = FormStatus.Idle
)

class CitizenViewModel(private val repository: FileRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CitizenUiState())
    val uiState: StateFlow<CitizenUiState> = _uiState.asStateFlow()

    val citizens: StateFlow<List<Citizen>> =
            _uiState
                    .map { it.citizens }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadCitizens()
    }

    private fun loadCitizens() {
        viewModelScope.launch {
            val citizens = repository.getCitizens()
            _uiState.update { it.copy(citizens = citizens) }
        }
    }

    fun loadCitizenForEdit(citizen: Citizen) {
        setCitizenForEdit(citizen)
    }

    fun onNicChanged(value: String) {
        _uiState.update { it.copy(nic = value, nicError = null) }
    }

    fun onFullNameChanged(value: String) {
        _uiState.update { it.copy(fullName = value, fullNameError = null) }
    }

    fun onDobChanged(value: String) {
        _uiState.update { it.copy(dateOfBirth = value, dobError = null) }
    }

    fun onGenderChanged(value: String) {
        _uiState.update { it.copy(gender = value) }
    }

    fun onOccupationChanged(value: String) {
        _uiState.update { it.copy(occupation = value) }
    }

    fun onHouseholdIdChanged(value: String) {
        _uiState.update { it.copy(householdId = value, householdIdError = null) }
    }

    fun onAddressChanged(value: String) {
        _uiState.update { it.copy(address = value) }
    }

    fun saveCitizen() {
        if (!validateForm()) return

        _uiState.update { it.copy(formStatus = FormStatus.Loading) }
        viewModelScope.launch {
            try {
                val citizen =
                        Citizen(
                                nic = _uiState.value.nic,
                                fullName = _uiState.value.fullName,
                                dateOfBirth = _uiState.value.dateOfBirth,
                                gender = _uiState.value.gender,
                                occupation = _uiState.value.occupation,
                                householdId = _uiState.value.householdId,
                                address = _uiState.value.address
                        )
                repository.saveCitizen(citizen)
                loadCitizens()
                _uiState.update { it.copy(formStatus = FormStatus.Success) }
                resetForm()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(formStatus = FormStatus.Error(e.message ?: "Failed to save citizen"))
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        if (_uiState.value.nic.isBlank()) {
            _uiState.update { it.copy(nicError = "NIC is required") }
            isValid = false
        }
        if (_uiState.value.fullName.isBlank()) {
            _uiState.update { it.copy(fullNameError = "Full Name is required") }
            isValid = false
        }
        if (_uiState.value.dateOfBirth.isBlank()) {
            _uiState.update { it.copy(dobError = "Date of Birth is required") }
            isValid = false
        }
        if (_uiState.value.householdId.isBlank()) {
            _uiState.update { it.copy(householdIdError = "Household ID is required") }
            isValid = false
        }
        return isValid
    }

    fun resetForm() {
        _uiState.update {
            it.copy(
                    nic = "",
                    fullName = "",
                    dateOfBirth = "",
                    gender = "",
                    occupation = "",
                    householdId = "",
                    address = "",
                    nicError = null,
                    fullNameError = null,
                    dobError = null,
                    householdIdError = null,
                    formStatus = FormStatus.Idle
            )
        }
    }

    fun deleteCitizen(nic: String) {
        viewModelScope.launch {
            val citizens = repository.getCitizens().filter { it.nic != nic }
            repository.saveCitizens(citizens)
            loadCitizens()
        }
    }

    private fun setCitizenForEdit(citizen: Citizen) {
        _uiState.update {
            it.copy(
                    nic = citizen.nic,
                    fullName = citizen.fullName,
                    dateOfBirth = citizen.dateOfBirth,
                    gender = citizen.gender,
                    occupation = citizen.occupation,
                    householdId = citizen.householdId,
                    address = citizen.address,
                    formStatus = FormStatus.Idle
            )
        }
    }
}
