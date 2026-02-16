package com.emarketing_paradice.gnsrilanka.data.model

data class Pension(
        val id: String,
        val nic: String, // Foreign key to Citizen
        val beneficiaryName: String,
        val type: PensionType,
        val amount: Double,
        val registrationDate: String,
        val status: String, // Active, Deceased, Suspended
        val remarks: String
)

enum class PensionType {
    CIVIL,
    FARMER,
    FISHERMAN,
    WIDOW,
    DISABILITY,
    OTHER
}
