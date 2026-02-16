package com.emarketing_paradice.gnsrilanka.data.model

data class ElderlyId(
        val id: String,
        val nic: String,
        val name: String,
        val dateOfBirth: String,
        val address: String,
        val issueDate: String,
        val issuedBy: String, // GN / Divisional Secretariat
        val remarks: String
)
