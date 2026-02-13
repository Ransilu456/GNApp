package com.emarketing_paradice.gnsrilanka.data.model

data class Citizen(
    val nic: String,
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val occupation: String,
    val householdId: String,
    val address: String = "",
    val isAlive: Boolean = true,
    val isActive: Boolean = true
)