package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.DailyLog
import com.emarketing_paradice.gnsrilanka.data.model.Permit
import com.emarketing_paradice.gnsrilanka.data.model.WelfareProgram
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WelfareUiState(
        val programName: String = "",
        val beneficiaryName: String = "",
        val nic: String = "",
        val amount: String = "",
        val address: String = "",
        val status: String = "Active",
        val formStatus: FormStatus = FormStatus.Idle
)

data class PermitUiState(
        val applicantName: String = "",
        val nic: String = "",
        val type: com.emarketing_paradice.gnsrilanka.data.model.PermitType =
                com.emarketing_paradice.gnsrilanka.data.model.PermitType.BUSINESS,
        val businessName: String = "",
        val formStatus: FormStatus = FormStatus.Idle
)

data class DailyLogUiState(
        val visitorName: String = "",
        val purpose: String = "",
        val actionTaken: String = "",
        val formStatus: FormStatus = FormStatus.Idle
)

class GNRegistryViewModel(private val repository: FileRepository) : ViewModel() {

    private val _dailyLogs = MutableStateFlow<List<DailyLog>>(emptyList())
    val dailyLogs: StateFlow<List<DailyLog>> = _dailyLogs.asStateFlow()

    private val _welfarePrograms = MutableStateFlow<List<WelfareProgram>>(emptyList())
    val welfarePrograms: StateFlow<List<WelfareProgram>> = _welfarePrograms.asStateFlow()

    private val _permits = MutableStateFlow<List<Permit>>(emptyList())
    val permits: StateFlow<List<Permit>> = _permits.asStateFlow()

    // Draft States
    private val _welfareDraft = MutableStateFlow(WelfareUiState())
    val welfareDraft = _welfareDraft.asStateFlow()

    private val _permitDraft = MutableStateFlow(PermitUiState())
    val permitDraft = _permitDraft.asStateFlow()

    private val _dailyLogDraft = MutableStateFlow(DailyLogUiState())
    val dailyLogDraft = _dailyLogDraft.asStateFlow()

    init {
        loadRegistries()
    }

    private fun loadRegistries() {
        viewModelScope.launch {
            _dailyLogs.value = repository.getDailyLogs()
            _welfarePrograms.value = repository.getWelfarePrograms()
            _permits.value = repository.getPermits()
        }
    }

    // Welfare Draft updates
    fun onWelfareProgramNameChanged(value: String) {
        _welfareDraft.update { it.copy(programName = value) }
    }
    fun onWelfareBeneficiaryNameChanged(value: String) {
        _welfareDraft.update { it.copy(beneficiaryName = value) }
    }
    fun onWelfareNicChanged(value: String) {
        _welfareDraft.update { it.copy(nic = value) }
    }
    fun onWelfareAmountChanged(value: String) {
        _welfareDraft.update { it.copy(amount = value) }
    }
    fun onWelfareAddressChanged(value: String) {
        _welfareDraft.update { it.copy(address = value) }
    }

    fun saveWelfareProgram() {
        val draft = _welfareDraft.value
        if (draft.programName.isBlank() || draft.nic.isBlank()) return

        _welfareDraft.update { it.copy(formStatus = FormStatus.Loading) }
        viewModelScope.launch {
            try {
                val program =
                        WelfareProgram(
                                id = System.currentTimeMillis().toString(),
                                programName = draft.programName,
                                beneficiaryName = draft.beneficiaryName,
                                nic = draft.nic,
                                benefitAmount = draft.amount.toDoubleOrNull() ?: 0.0,
                                status = draft.status,
                                registrationDate =
                                        java.text.SimpleDateFormat(
                                                        "yyyy-MM-dd",
                                                        java.util.Locale.getDefault()
                                                )
                                                .format(java.util.Date()),
                                householdAddress = draft.address,
                                remarks = "Added from app"
                        )
                repository.saveWelfareProgram(program)
                loadRegistries()
                _welfareDraft.update { it.copy(formStatus = FormStatus.Success) }
                resetWelfareForm()
            } catch (e: Exception) {
                _welfareDraft.update {
                    it.copy(formStatus = FormStatus.Error(e.message ?: "Error"))
                }
            }
        }
    }

    fun resetWelfareForm() {
        _welfareDraft.value = WelfareUiState()
    }

    // Permit Draft updates
    fun onPermitApplicantNameChanged(value: String) {
        _permitDraft.update { it.copy(applicantName = value) }
    }
    fun onPermitNicChanged(value: String) {
        _permitDraft.update { it.copy(nic = value) }
    }
    fun onPermitTypeChanged(value: com.emarketing_paradice.gnsrilanka.data.model.PermitType) {
        _permitDraft.update { it.copy(type = value) }
    }
    fun onPermitBusinessNameChanged(value: String) {
        _permitDraft.update { it.copy(businessName = value) }
    }

    fun savePermit() {
        val draft = _permitDraft.value
        if (draft.applicantName.isBlank() || draft.nic.isBlank()) return

        _permitDraft.update { it.copy(formStatus = FormStatus.Loading) }
        viewModelScope.launch {
            try {
                val permit =
                        Permit(
                                id = System.currentTimeMillis().toString(),
                                type = draft.type,
                                applicantName = draft.applicantName,
                                nic = draft.nic,
                                businessName =
                                        if (draft.type ==
                                                        com.emarketing_paradice.gnsrilanka.data
                                                                .model.PermitType.BUSINESS
                                        )
                                                draft.businessName
                                        else null,
                                address = "",
                                issueDate =
                                        java.text.SimpleDateFormat(
                                                        "yyyy-MM-dd",
                                                        java.util.Locale.getDefault()
                                                )
                                                .format(java.util.Date()),
                                expiryDate = "2025-12-31",
                                status = "Active",
                                terms = "",
                                remarks = "Added from app"
                        )
                repository.savePermit(permit)
                loadRegistries()
                _permitDraft.update { it.copy(formStatus = FormStatus.Success) }
                resetPermitForm()
            } catch (e: Exception) {
                _permitDraft.update { it.copy(formStatus = FormStatus.Error(e.message ?: "Error")) }
            }
        }
    }

    fun resetPermitForm() {
        _permitDraft.value = PermitUiState()
    }

    // Daily Log Draft updates
    fun onDailyLogVisitorNameChanged(value: String) {
        _dailyLogDraft.update { it.copy(visitorName = value) }
    }
    fun onDailyLogPurposeChanged(value: String) {
        _dailyLogDraft.update { it.copy(purpose = value) }
    }
    fun onDailyLogActionTakenChanged(value: String) {
        _dailyLogDraft.update { it.copy(actionTaken = value) }
    }

    fun saveDailyLog() {
        val draft = _dailyLogDraft.value
        if (draft.visitorName.isBlank() || draft.purpose.isBlank()) return

        _dailyLogDraft.update { it.copy(formStatus = FormStatus.Loading) }
        viewModelScope.launch {
            try {
                val log =
                        DailyLog(
                                id = System.currentTimeMillis().toString(),
                                date =
                                        java.text.SimpleDateFormat(
                                                        "yyyy-MM-dd HH:mm",
                                                        java.util.Locale.getDefault()
                                                )
                                                .format(java.util.Date()),
                                serialNumber = "LOG-${System.currentTimeMillis() % 10000}",
                                visitorName = draft.visitorName,
                                purpose = draft.purpose,
                                actionTaken = draft.actionTaken,
                                remarks = "Added from app"
                        )
                repository.saveDailyLog(log)
                loadRegistries()
                _dailyLogDraft.update { it.copy(formStatus = FormStatus.Success) }
                resetDailyLogForm()
            } catch (e: Exception) {
                _dailyLogDraft.update {
                    it.copy(formStatus = FormStatus.Error(e.message ?: "Error"))
                }
            }
        }
    }

    fun resetDailyLogForm() {
        _dailyLogDraft.value = DailyLogUiState()
    }

    fun addDailyLog(
            log: DailyLog
    ) { // Keep for backward compat if needed, but we'll use save functions
        viewModelScope.launch {
            repository.saveDailyLog(log)
            loadRegistries()
        }
    }

    fun addWelfareProgram(program: WelfareProgram) {
        viewModelScope.launch {
            repository.saveWelfareProgram(program)
            loadRegistries()
        }
    }

    fun addPermit(permit: Permit) {
        viewModelScope.launch {
            repository.savePermit(permit)
            loadRegistries()
        }
    }
}
