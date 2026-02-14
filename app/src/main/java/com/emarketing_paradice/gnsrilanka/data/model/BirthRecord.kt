package com.emarketing_paradice.gnsrilanka.data.model

data class BirthRecord(
        val recordId: String,
        val childName: String,
        val dateOfBirth: String,
        val placeOfBirth: String,
        val fatherName: String,
        val motherName: String,
        val guardianAddress: String,
        val remarks: String
)
