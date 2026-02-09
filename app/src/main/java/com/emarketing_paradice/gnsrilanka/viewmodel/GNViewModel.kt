package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.GN
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GNViewModel(private val repository: FileRepository) : ViewModel() {
    private val _gnData = MutableStateFlow<GN?>(null)
    val gnData: StateFlow<GN?> = _gnData

    init {
        loadGNData()
    }

    private fun loadGNData() {
        viewModelScope.launch {
            // In a real app, this might load from a specific GN profile file
            // For now, we'll just use the first user or a default
            val users = repository.getUsers()
            if (users.isNotEmpty()) {
                _gnData.value = GN(users[0].nic, "Grama Niladhari")
            }
        }
    }
}
