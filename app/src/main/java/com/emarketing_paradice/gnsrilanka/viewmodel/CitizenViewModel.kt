package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Citizen
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// A more descriptive name for the form state
data class CitizenUiState(
    val nic: String = "",
    val fullName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "Male", // Default value
    val occupation: String = "",
    val householdId: String = "",
    val nicError: String? = null,
    val fullNameError: String? = null,
    val dobError: String? = null,
    val householdIdError: String? = null,
    val formStatus: FormStatus = FormStatus.Idle
)

class CitizenViewModel(private val repository: FileRepository) : ViewModel() {
    private val _citizens = MutableStateFlow<List<Citizen>>(emptyList())
    val citizens: StateFlow<List<Citizen>> = _citizens.asStateFlow()

    private val _uiState = MutableStateFlow(CitizenUiState())
    val uiState: StateFlow<CitizenUiState> = _uiState.asStateFlow()

    init {
        loadCitizens()
    }

    private fun loadCitizens() {
        viewModelScope.launch {
            _citizens.value = repository.getCitizens()
        }
    }

    fun onNicChanged(nic: String) {
        _uiState.update { it.copy(nic = nic, nicError = null, formStatus = FormStatus.Idle) }
    }

    fun onFullNameChanged(name: String) {
        _uiState.update { it.copy(fullName = name, fullNameError = null, formStatus = FormStatus.Idle) }
    }

    fun onDobChanged(dob: String) {
        _uiState.update { it.copy(dateOfBirth = dob, dobError = null, formStatus = FormStatus.Idle) }
    }

    fun onGenderChanged(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun onOccupationChanged(occupation: String) {
        _uiState.update { it.copy(occupation = occupation) }
    }

    fun onHouseholdIdChanged(id: String) {
        _uiState.update { it.copy(householdId = id, householdIdError = null, formStatus = FormStatus.Idle) }
    }

    fun saveCitizen() {
        if (!validateForm()) return

        viewModelScope.launch {
            _uiState.update { it.copy(formStatus = FormStatus.Loading) }

            val state = _uiState.value
            val citizen = Citizen(
                nic = state.nic,
                fullName = state.fullName,
                dateOfBirth = state.dateOfBirth,
                gender = state.gender,
                occupation = state.occupation,
                householdId = state.householdId
            )

            val currentList = _citizens.value.toMutableList()
            // Check for existing citizen and update or add
            val existingIndex = currentList.indexOfFirst { it.nic == citizen.nic }
            if (existingIndex != -1) {
                currentList[existingIndex] = citizen
            } else {
                currentList.add(citizen)
            }

            repository.saveCitizens(currentList)
            _citizens.value = currentList
            _uiState.value = CitizenUiState(formStatus = FormStatus.Success) // Reset form
        }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        val nicError = if (state.nic.isBlank()) "NIC is required" else if (state.nic.length < 10) "Invalid NIC" else null
        val nameError = if (state.fullName.isBlank()) "Full Name is required" else null
        val dobError = if (state.dateOfBirth.isBlank()) "Date of Birth is required" else null
        val householdIdError = if (state.householdId.isBlank()) "Household ID is required" else null

        _uiState.update {
            it.copy(
                nicError = nicError,
                fullNameError = nameError,
                dobError = dobError,
                householdIdError = householdIdError
            )
        }

        return nicError == null && nameError == null && dobError == null && householdIdError == null
    }

    fun deleteCitizen(nic: String) {
        viewModelScope.launch {
             val currentList = _citizens.value.filter { it.nic != nic }
            _citizens.value = currentList
            repository.saveCitizens(currentList)
        }
    }
    
    fun loadCitizenForEdit(citizen: Citizen) {
        _uiState.value = CitizenUiState(
            nic = citizen.nic,
            fullName = citizen.fullName,
            dateOfBirth = citizen.dateOfBirth,
            gender = citizen.gender,
            occupation = citizen.occupation,
            householdId = citizen.householdId
        )
    }

    fun resetForm() {
        _uiState.value = CitizenUiState()
    }
}
