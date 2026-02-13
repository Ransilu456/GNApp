package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Request
import com.emarketing_paradice.gnsrilanka.data.model.RequestStatus
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RequestUiState(
        val requests: List<Request> = emptyList(),
        val requestId: String = "",
        val citizenNic: String = "",
        val certificateType: String = "",
        val purpose: String = "",
        val description: String = "",
        val citizenNicError: String? = null,
        val certificateTypeError: String? = null,
        val purposeError: String? = null,
        val formStatus: FormStatus = FormStatus.Idle
)

class RequestViewModel(private val repository: FileRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    val requests: StateFlow<List<Request>> =
            _uiState
                    .map { it.requests }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadRequests()
    }

    private fun loadRequests() {
        viewModelScope.launch {
            val requests = repository.getRequests()
            _uiState.update { it.copy(requests = requests) }
        }
    }

    fun loadRequestForEdit(request: Request) {
        // Functionality to load request for editing
        // For now just logging or simple state update if needed
    }

    fun onCitizenNicChanged(value: String) {
        _uiState.update { it.copy(citizenNic = value, citizenNicError = null) }
    }

    fun onCertificateTypeChanged(value: String) {
        _uiState.update { it.copy(certificateType = value, certificateTypeError = null) }
    }

    fun onPurposeChanged(value: String) {
        _uiState.update { it.copy(purpose = value, purposeError = null) }
    }

    fun onDescriptionChanged(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun saveRequest(gnId: String = "admin") {
        if (!validateForm()) return

        _uiState.update { it.copy(formStatus = FormStatus.Loading) }
        viewModelScope.launch {
            try {
                val citizen = repository.getCitizens().find { it.nic == _uiState.value.citizenNic }
                val request =
                        Request(
                                id = UUID.randomUUID().toString(),
                                citizenNic = _uiState.value.citizenNic,
                                citizenName = citizen?.fullName ?: "Unknown",
                                certificateType = _uiState.value.certificateType,
                                purpose = _uiState.value.purpose,
                                issuedDate = 0L,
                                submissionDate = System.currentTimeMillis().toString(),
                                issuedByGn = gnId,
                                description = _uiState.value.description,
                                status = RequestStatus.Pending
                        )
                repository.saveRequest(request)
                loadRequests()
                _uiState.update { it.copy(formStatus = FormStatus.Success) }
                resetForm()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(formStatus = FormStatus.Error(e.message ?: "Failed to submit request"))
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        if (_uiState.value.citizenNic.isBlank()) {
            _uiState.update { it.copy(citizenNicError = "Citizen NIC is required") }
            isValid = false
        }
        if (_uiState.value.certificateType.isBlank()) {
            _uiState.update { it.copy(certificateTypeError = "Certificate type is required") }
            isValid = false
        }
        if (_uiState.value.purpose.isBlank()) {
            _uiState.update { it.copy(purposeError = "Purpose is required") }
            isValid = false
        }
        return isValid
    }

    fun resetForm() {
        _uiState.update {
            it.copy(
                    citizenNic = "",
                    certificateType = "",
                    purpose = "",
                    description = "",
                    citizenNicError = null,
                    certificateTypeError = null,
                    purposeError = null,
                    formStatus = FormStatus.Idle
            )
        }
    }

    fun updateRequestStatus(requestId: String, status: RequestStatus) {
        viewModelScope.launch {
            val requests = repository.getRequests().toMutableList()
            val index = requests.indexOfFirst { it.id == requestId }
            if (index != -1) {
                val updatedRequest =
                        requests[index].copy(
                                status = status,
                                issuedDate =
                                        if (status == RequestStatus.Approved)
                                                System.currentTimeMillis()
                                        else 0L
                        )
                requests[index] = updatedRequest
                repository.saveRequests(requests)
                loadRequests()
            }
        }
    }
}
