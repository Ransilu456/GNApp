package com.emarketing_paradice.gnsrilanka.data.model

data class WelfareProgram(
        val id: String,
        val programName: String, // e.g., Aswesuma, Samurdhi, Senior Citizen Allowance
        val beneficiaryName: String,
        val nic: String,
        val householdAddress: String,
        val status: String, // Active, Pending, Discontinued
        val benefitAmount: Double,
        val registrationDate: String,
        val remarks: String
)
