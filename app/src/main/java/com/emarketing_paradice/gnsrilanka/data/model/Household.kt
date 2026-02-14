package com.emarketing_paradice.gnsrilanka.data.model

data class Household(
        val id: String,
        val address: String,
        val gnDivision: String,
        val headNic: String, // Also serves as headOfFamily reference
        val membersCount: Int = 0,
        val remarks: String = ""
)
