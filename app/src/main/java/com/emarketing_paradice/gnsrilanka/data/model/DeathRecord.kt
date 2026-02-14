package com.emarketing_paradice.gnsrilanka.data.model

data class DeathRecord(
        val recordId: String,
        val deceasedName: String,
        val age: Int,
        val dateOfDeath: String,
        val causeOfDeath: String,
        val remarks: String
)
