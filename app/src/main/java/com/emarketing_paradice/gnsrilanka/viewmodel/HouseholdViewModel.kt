package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Household
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HouseholdUiState(
        val households: List<Household> = emptyList(),
        val id: String = "",
        val address: String = "",
        val gnDivision: String = "",
        val headNic: String = "",
        val idError: String? = null,
        val addressError: String? = null,
        val gnDivisionError: String? = null,
        val headNicError: String? = null,
        val formStatus: FormStatus = FormStatus.Idle
)

class HouseholdViewModel(private val repository: FileRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HouseholdUiState())
    val uiState: StateFlow<HouseholdUiState> = _uiState.asStateFlow()

    val households: StateFlow<List<Household>> =
            _uiState
                    .map { it.households }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadHouseholds()
    }

    private fun loadHouseholds() {
        viewModelScope.launch {
            val households = repository.getHouseholds()
            _uiState.update { it.copy(households = households) }
        }
    }

    fun loadHouseholdForEdit(household: Household) {
        _uiState.update {
            it.copy(
                    id = household.id,
                    address = household.address,
                    gnDivision = household.gnDivision,
                    headNic = household.headNic,
                    formStatus = FormStatus.Idle
            )
        }
    }

    fun onIdChanged(value: String) {
        _uiState.update { it.copy(id = value, idError = null) }
    }

    fun onAddressChanged(value: String) {
        _uiState.update { it.copy(address = value, addressError = null) }
    }

    fun onGnDivisionChanged(value: String) {
        _uiState.update { it.copy(gnDivision = value, gnDivisionError = null) }
    }

    fun onHeadNicChanged(value: String) {
        _uiState.update { it.copy(headNic = value, headNicError = null) }
    }

    fun saveHousehold() {
        if (!validateForm()) return

        _uiState.update { it.copy(formStatus = FormStatus.Loading) }
        viewModelScope.launch {
            try {
                val household =
                        Household(
                                id = _uiState.value.id,
                                address = _uiState.value.address,
                                gnDivision = _uiState.value.gnDivision,
                                headNic = _uiState.value.headNic
                        )
                repository.saveHousehold(household)
                loadHouseholds()
                _uiState.update { it.copy(formStatus = FormStatus.Success) }
                resetForm()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(formStatus = FormStatus.Error(e.message ?: "Failed to save household"))
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        if (_uiState.value.id.isBlank()) {
            _uiState.update { it.copy(idError = "Household ID is required") }
            isValid = false
        }
        if (_uiState.value.address.isBlank()) {
            _uiState.update { it.copy(addressError = "Address is required") }
            isValid = false
        }
        if (_uiState.value.headNic.isBlank()) {
            _uiState.update { it.copy(headNicError = "Head of Household NIC is required") }
            isValid = false
        }
        return isValid
    }

    private fun resetForm() {
        _uiState.update {
            it.copy(
                    id = "",
                    address = "",
                    gnDivision = "",
                    headNic = "",
                    idError = null,
                    addressError = null,
                    gnDivisionError = null,
                    headNicError = null
            )
        }
    }

    fun deleteHousehold(id: String) {
        viewModelScope.launch {
            val households = repository.getHouseholds().filter { it.id != id }
            repository.saveHouseholds(households)
            loadHouseholds()
        }
    }
}
