package com.emarketing_paradice.gnsrilanka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarketing_paradice.gnsrilanka.data.model.Citizen360
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GlobalSearchViewModel(private val repository: FileRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Citizen360>>(emptyList())
    val searchResults: StateFlow<List<Citizen360>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isSearching.value = true
            try {
                val citizens = repository.getCitizens()
                val welfarePrograms = repository.getWelfarePrograms()
                val permits = repository.getPermits()
                val dailyLogs = repository.getDailyLogs()
                val pensions = repository.getPensions()
                val elderlyIds = repository.getElderlyIds()
                val voluntaryOrgs = repository.getVoluntaryOrgs()

                val lowerQuery = query.lowercase().trim()

                // Filter citizens by Name or NIC
                val matchedCitizens =
                        citizens.filter {
                            it.fullName.lowercase().contains(lowerQuery) ||
                                    it.nic.lowercase().contains(lowerQuery)
                        }

                // Aggregate data for each matched citizen
                val results =
                        matchedCitizens.map { citizen ->
                            Citizen360(
                                    citizen = citizen,
                                    welfarePrograms =
                                            welfarePrograms.filter {
                                                it.nic.equals(citizen.nic, ignoreCase = true)
                                            },
                                    permits =
                                            permits.filter {
                                                it.nic.equals(citizen.nic, ignoreCase = true)
                                            },
                                    // Log matching is trickier as it might be by name.
                                    // For now, simple name match or if log visitor name contains
                                    // the search query directly if it wasn't a citizen match?
                                    // The requirement says "search a name or nic".
                                    // Usage: We match logs to the CITIZEN found.
                                    dailyLogs =
                                            dailyLogs.filter { log ->
                                                log.visitorName.equals(
                                                        citizen.fullName,
                                                        ignoreCase = true
                                                ) ||
                                                        log.visitorName
                                                                .lowercase()
                                                                .contains(
                                                                        citizen.fullName.lowercase()
                                                                )
                                            },
                                    pensions =
                                            pensions.filter {
                                                it.nic.equals(citizen.nic, ignoreCase = true)
                                            },
                                    elderlyIds =
                                            elderlyIds.filter {
                                                it.nic.equals(citizen.nic, ignoreCase = true)
                                            },
                                    voluntaryOrgs =
                                            voluntaryOrgs.filter { org ->
                                                org.presidentName.equals(
                                                        citizen.fullName,
                                                        ignoreCase = true
                                                ) ||
                                                        org.secretaryName.equals(
                                                                citizen.fullName,
                                                                ignoreCase = true
                                                        ) ||
                                                        org.treasurerName.equals(
                                                                citizen.fullName,
                                                                ignoreCase = true
                                                        )
                                            }
                            )
                        }

                _searchResults.value = results
            } catch (e: Exception) {
                // Handle error
                _searchResults.value = emptyList()
            } finally {
                _isSearching.value = false
            }
        }
    }

    fun clearSearch() {
        _searchResults.value = emptyList()
    }
}
