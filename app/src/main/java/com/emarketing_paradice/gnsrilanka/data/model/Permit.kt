package com.emarketing_paradice.gnsrilanka.data.model

data class Permit(
        val id: String,
        val type: PermitType,
        val applicantName: String,
        val nic: String,
        val businessName: String? = null, // For business permits
        val address: String,
        val issueDate: String,
        val expiryDate: String,
        val status: String, // Active, Expired, Revoked
        val terms: String,
        val remarks: String
)

enum class PermitType {
    BUSINESS,
    FIREARM,
    RESOURCE_TRANSPORT;

    fun displayName(): String =
            when (this) {
                BUSINESS -> "Business Permit"
                FIREARM -> "Firearm License"
                RESOURCE_TRANSPORT -> "Resource Transport"
            }
}
