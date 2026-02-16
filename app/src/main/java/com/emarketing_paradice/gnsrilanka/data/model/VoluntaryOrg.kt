package com.emarketing_paradice.gnsrilanka.data.model

data class VoluntaryOrg(
        val id: String,
        val name: String,
        val type: String, // e.g., "Death Benevolent", "Sports", "Women's", "Youth"
        val registrationNumber: String,
        val registrationDate: String,
        val presidentName: String,
        val secretaryName: String,
        val treasurerName: String,
        val memberCount: Int,
        val status: String,
        val remarks: String
)
