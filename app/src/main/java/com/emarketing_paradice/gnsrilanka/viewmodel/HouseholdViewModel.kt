package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Household
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HouseholdUiState(
    val householdId: String = "",
    val address: String = "",
    val gnDivision: String = "",
    val headNic: String = "",
    val householdIdError: String? = null,
    val addressError: String? = null,
    val gnDivisionError: String? = null,
    val headNicError: String? = null,
    val formStatus: FormStatus = FormStatus.Idle
)

class HouseholdViewModel(private val repository: FileRepository) : ViewModel() {
    private val _households = MutableStateFlow<List<Household>>(emptyList())
    val households: StateFlow<List<Household>> = _households.asStateFlow()

    private val _uiState = MutableStateFlow(HouseholdUiState())
    val uiState: StateFlow<HouseholdUiState> = _uiState.asStateFlow()

    init {
        loadHouseholds()
    }

    private fun loadHouseholds() {
        viewModelScope.launch {
            _households.value = repository.getHouseholds()
        }
    }

    fun onHouseholdIdChanged(id: String) {
        _uiState.update { it.copy(householdId = id, householdIdError = null, formStatus = FormStatus.Idle) }
    }

    fun onAddressChanged(address: String) {
        _uiState.update { it.copy(address = address, addressError = null, formStatus = FormStatus.Idle) }
    }

    fun onGnDivisionChanged(gnDivision: String) {
        _uiState.update { it.copy(gnDivision = gnDivision, gnDivisionError = null, formStatus = FormStatus.Idle) }
    }

    fun onHeadNicChanged(headId: String) {
        _uiState.update { it.copy(headNic = headId, headNicError = null, formStatus = FormStatus.Idle) }
    }

    fun saveHousehold() {
        if (!validateForm()) return

        viewModelScope.launch {
            _uiState.update { it.copy(formStatus = FormStatus.Loading) }

            val state = _uiState.value
            val household = Household(
                id = state.householdId,
                address = state.address,
                gnDivision = state.gnDivision,
                headNic = state.headNic
            )

            val currentList = _households.value.toMutableList()
            val existingIndex = currentList.indexOfFirst { it.id == household.id }

            if (existingIndex != -1) {
                currentList[existingIndex] = household
            } else {
                if (currentList.any { it.id == household.id }) {
                    _uiState.update { it.copy(formStatus = FormStatus.Error("A household with this ID already exists."), householdIdError = "ID already exists") }
                    return@launch
                }
                currentList.add(household)
            }

            repository.saveHouseholds(currentList)
            _households.value = currentList
            _uiState.value = HouseholdUiState(formStatus = FormStatus.Success)
        }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        val idError = if (state.householdId.isBlank()) "Household ID is required" else null
        val addressError = if (state.address.isBlank()) "Address is required" else null
        val gnDivisionError = if (state.gnDivision.isBlank()) "GN Division is required" else null
        val headNicError = if (state.headNic.isBlank()) "Head NIC is required" else if (state.headNic.length < 10) "Invalid NIC" else null

        _uiState.update {
            it.copy(
                householdIdError = idError,
                addressError = addressError,
                gnDivisionError = gnDivisionError,
                headNicError = headNicError
            )
        }

        return idError == null && addressError == null && gnDivisionError == null && headNicError == null
    }

    fun deleteHousehold(id: String) {
        viewModelScope.launch {
            val currentList = _households.value.filter { it.id != id }
            _households.value = currentList
            repository.saveHouseholds(currentList)
        }
    }

    fun loadHouseholdForEdit(household: Household) {
        _uiState.value = HouseholdUiState(
            householdId = household.id,
            address = household.address,
            gnDivision = household.gnDivision,
            headNic = household.headNic
        )
    }
    
    fun resetForm() {
        _uiState.value = HouseholdUiState()
    }
}
