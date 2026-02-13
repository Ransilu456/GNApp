package com.emarketing_paradice.gnsrilanka.data.model

data class Request(
    val id: String,
    val citizenNic: String,
    val citizenName: String = "",
    val certificateType: String,
    val purpose: String,
    val issuedDate: Long,
    val submissionDate: String = "",
    val issuedByGn: String,
    val description: String,
    val status: RequestStatus = RequestStatus.Pending
)
