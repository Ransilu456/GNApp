package com.emarketing_paradice.gnsrilanka.data.model

data class MovementLog(
        val logId: String,
        val citizenId: String,
        val movementType: String,
        val description: String,
        val date: String,
        val officerNotes: String
)
