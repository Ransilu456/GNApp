package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Request
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class RequestUiState(
    val requestId: String = "",
    val citizenNic: String = "",
    val certificateType: String = "",
    val purpose: String = "",
    val citizenNicError: String? = null,
    val certificateTypeError: String? = null,
    val purposeError: String? = null,
    val formStatus: FormStatus = FormStatus.Idle
)

class RequestViewModel(private val repository: FileRepository) : ViewModel() {
    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests: StateFlow<List<Request>> = _requests.asStateFlow()

    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    init {
        loadRequests()
    }

    private fun loadRequests() {
        viewModelScope.launch {
            _requests.value = repository.getRequests()
        }
    }

    fun onCitizenNicChanged(nic: String) {
        _uiState.update { it.copy(citizenNic = nic, citizenNicError = null, formStatus = FormStatus.Idle) }
    }

    fun onCertificateTypeChanged(certificateType: String) {
        _uiState.update { it.copy(certificateType = certificateType, certificateTypeError = null, formStatus = FormStatus.Idle) }
    }

    fun onPurposeChanged(purpose: String) {
        _uiState.update { it.copy(purpose = purpose, purposeError = null, formStatus = FormStatus.Idle) }
    }

    fun saveRequest() {
        if (!validateForm()) return

        viewModelScope.launch {
            _uiState.update { it.copy(formStatus = FormStatus.Loading) }
            val state = _uiState.value
            
            val request = if (state.requestId.isNotBlank()) {
                // Update existing request
                _requests.value.find { it.id == state.requestId }?.copy(
                    citizenNic = state.citizenNic,
                    certificateType = state.certificateType,
                    purpose = state.purpose
                )
            } else {
                // Create new request
                Request(
                    id = UUID.randomUUID().toString(),
                    citizenNic = state.citizenNic,
                    certificateType = state.certificateType,
                    purpose = state.purpose,
                    issuedDate = System.currentTimeMillis(),
                    issuedByGn = "Admin",
                    description = "",
                    status = "Pending"
                )
            }

            if (request == null) {
                _uiState.update { it.copy(formStatus = FormStatus.Error("Could not find request to update.")) }
                return@launch
            }

            val currentList = _requests.value.toMutableList()
            val existingIndex = currentList.indexOfFirst { it.id == request.id }

            if (existingIndex != -1) {
                currentList[existingIndex] = request
            } else {
                currentList.add(request)
            }

            repository.saveRequests(currentList)
            _requests.value = currentList
            _uiState.value = RequestUiState(formStatus = FormStatus.Success)
        }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        val nicError = if (state.citizenNic.isBlank()) "Citizen NIC is required" else if (state.citizenNic.length < 10) "Invalid NIC" else null
        val certificateTypeError = if (state.certificateType.isBlank()) "Certificate Type is required" else null
        val purposeError = if (state.purpose.isBlank()) "Purpose is required" else null
        
        _uiState.update {
            it.copy(
                citizenNicError = nicError,
                certificateTypeError = certificateTypeError,
                purposeError = purposeError
            )
        }

        return nicError == null && certificateTypeError == null && purposeError == null
    }

    fun deleteRequest(id: String) {
        viewModelScope.launch {
            val currentList = _requests.value.filter { it.id != id }
            _requests.value = currentList
            repository.saveRequests(currentList)
        }
    }

    fun loadRequestForEdit(request: Request) {
        _uiState.value = RequestUiState(
            requestId = request.id,
            citizenNic = request.citizenNic,
            certificateType = request.certificateType,
            purpose = request.purpose
        )
    }

    fun updateRequestStatus(id: String, newStatus: String) {
        viewModelScope.launch {
            val currentList = _requests.value.toMutableList()
            val requestIndex = currentList.indexOfFirst { it.id == id }
            if (requestIndex != -1) {
                val updatedRequest = currentList[requestIndex].copy(status = newStatus)
                currentList[requestIndex] = updatedRequest
                repository.saveRequests(currentList)
                _requests.value = currentList
            }
        }
    }
    
    fun resetForm() {
        _uiState.value = RequestUiState()
    }
}
