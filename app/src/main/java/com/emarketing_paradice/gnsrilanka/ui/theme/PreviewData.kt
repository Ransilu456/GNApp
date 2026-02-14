package com.emarketing_paradice.gnsrilanka.ui.theme

import com.emarketing_paradice.gnsrilanka.data.model.*

object PreviewData {
    val sampleUser = User(nic = "123456789V", password = "password123")

    val sampleCitizen = Citizen(
        nic = "123456789V",
        fullName = "John Doe",
        dateOfBirth = "1990-05-15",
        gender = "Male",
        occupation = "Software Engineer",
        householdId = "H001",
        address = "No 123, Main Street, Colombo",
        contactNumber = "0771234567",
        notes = "Sample note"
    )

    val sampleCitizens = listOf(sampleCitizen)

    val sampleHousehold = Household(
        id = "H001",
        address = "No 123, Main Street, Colombo",
        gnDivision = "Colombo Central",
        headNic = "123456789V",
        membersCount = 2,
        remarks = "Sample remark"
    )

    val sampleHouseholds = listOf(sampleHousehold)

    val sampleRequest = Request(
        id = "REQ001",
        citizenNic = "123456789V",
        citizenName = "John Doe",
        requestType = "Character Certificate",
        certificateType = "Character Certificate",
        purpose = "Job Application",
        issuedDate = 0L,
        submissionDate = "2023-10-27",
        issuedByGn = "GN001",
        description = "Character certificate for employment",
        status = RequestStatus.Pending,
        approvalNotes = "Pending review",
        documentPath = "/docs/cert.pdf"
    )

    val sampleRequests = listOf(sampleRequest)
}
