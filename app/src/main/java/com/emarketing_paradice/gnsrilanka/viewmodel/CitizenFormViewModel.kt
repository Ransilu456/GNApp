package com.emarketing_paradice.gnsrilanka.viewmodel

data class CitizenFormState(
    val nic: String = "",
    val nicError: String? = null,
    val fullName: String = "",
    val nameError: String? = null,
    val dob: String = "",
    val dobError: String? = null,
    val gender: String = "",
    val occupation: String = "",
    val householdId: String = "",
    val householdIdError: String? = null,
    val isFormValid: Boolean = false
)

sealed class CitizenFormEvent {
    data class NicChanged(val nic: String) : CitizenFormEvent()
    data class NameChanged(val name: String) : CitizenFormEvent()
    data class DobChanged(val dob: String) : CitizenFormEvent()
    data class GenderChanged(val gender: String) : CitizenFormEvent()
    data class OccupationChanged(val occupation: String) : CitizenFormEvent()
    data class HouseholdIdChanged(val householdId: String) : CitizenFormEvent()
    object Submit : CitizenFormEvent()
}
