package com.emarketing_paradice.gnsrilanka.data.model

data class DailyLog(
        val id: String,
        val date: String,
        val serialNumber: String,
        val visitorName: String,
        val purpose: String,
        val actionTaken: String,
        val remarks: String
)
