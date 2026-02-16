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
import kotlinx.coroutines.launch

class GNRegistryViewModel(private val repository: FileRepository) : ViewModel() {

    private val _dailyLogs = MutableStateFlow<List<DailyLog>>(emptyList())
    val dailyLogs: StateFlow<List<DailyLog>> = _dailyLogs.asStateFlow()

    private val _welfarePrograms = MutableStateFlow<List<WelfareProgram>>(emptyList())
    val welfarePrograms: StateFlow<List<WelfareProgram>> = _welfarePrograms.asStateFlow()

    private val _permits = MutableStateFlow<List<Permit>>(emptyList())
    val permits: StateFlow<List<Permit>> = _permits.asStateFlow()

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

    fun addDailyLog(log: DailyLog) {
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
