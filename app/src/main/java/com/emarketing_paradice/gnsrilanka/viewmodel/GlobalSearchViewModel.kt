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

                                val tokens =
                                        query.lowercase().trim().split("\\s+".toRegex()).filter {
                                                it.isNotBlank()
                                        }

                                // Filter citizens by Name or NIC using tokenized search
                                val matchedCitizens =
                                        citizens.filter { citizen ->
                                                val lowerName = citizen.fullName.lowercase()
                                                val lowerNic = citizen.nic.lowercase()

                                                // Match if all tokens are present in either name or
                                                // NIC
                                                tokens.all { token ->
                                                        lowerName.contains(token) ||
                                                                lowerNic.contains(token)
                                                }
                                        }

                                // Aggregate data for each matched citizen
                                val results =
                                        matchedCitizens.map { citizen ->
                                                Citizen360(
                                                        citizen = citizen,
                                                        welfarePrograms =
                                                                welfarePrograms.filter {
                                                                        it.nic.equals(
                                                                                citizen.nic,
                                                                                ignoreCase = true
                                                                        )
                                                                },
                                                        permits =
                                                                permits.filter {
                                                                        it.nic.equals(
                                                                                citizen.nic,
                                                                                ignoreCase = true
                                                                        )
                                                                },
                                                        dailyLogs =
                                                                dailyLogs.filter { log ->
                                                                        log.visitorName.equals(
                                                                                citizen.fullName,
                                                                                ignoreCase = true
                                                                        ) ||
                                                                                log.visitorName
                                                                                        .lowercase()
                                                                                        .contains(
                                                                                                citizen.fullName
                                                                                                        .lowercase()
                                                                                        ) ||
                                                                                (citizen.nic
                                                                                        .isNotBlank() &&
                                                                                        (log.purpose
                                                                                                .lowercase()
                                                                                                .contains(
                                                                                                        citizen.nic
                                                                                                                .lowercase()
                                                                                                ) ||
                                                                                                log.remarks
                                                                                                        .lowercase()
                                                                                                        .contains(
                                                                                                                citizen.nic
                                                                                                                        .lowercase()
                                                                                                        )))
                                                                },
                                                        pensions =
                                                                pensions.filter {
                                                                        it.nic.equals(
                                                                                citizen.nic,
                                                                                ignoreCase = true
                                                                        )
                                                                },
                                                        elderlyIds =
                                                                elderlyIds.filter {
                                                                        it.nic.equals(
                                                                                citizen.nic,
                                                                                ignoreCase = true
                                                                        )
                                                                },
                                                        voluntaryOrgs =
                                                                voluntaryOrgs.filter { org ->
                                                                        org.presidentName.equals(
                                                                                citizen.fullName,
                                                                                ignoreCase = true
                                                                        ) ||
                                                                                org.secretaryName
                                                                                        .equals(
                                                                                                citizen.fullName,
                                                                                                ignoreCase =
                                                                                                        true
                                                                                        ) ||
                                                                                org.treasurerName
                                                                                        .equals(
                                                                                                citizen.fullName,
                                                                                                ignoreCase =
                                                                                                        true
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
