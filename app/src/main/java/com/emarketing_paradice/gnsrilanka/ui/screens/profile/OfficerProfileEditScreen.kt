package com.emarketing_paradice.gnsrilanka.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficerProfileEditScreen(
        padding: PaddingValues,
        authViewModel: AuthViewModel,
        onProfileUpdated: () -> Unit
) {
    val profile by authViewModel.officerProfile.collectAsState()

    var officerName by remember { mutableStateOf(profile?.officerName ?: "") }
    var gnDivision by remember { mutableStateOf(profile?.gnDivision ?: "") }
    var officeAddress by remember { mutableStateOf(profile?.officeAddress ?: "") }
    var contactInfo by remember { mutableStateOf(profile?.contactInfo ?: "") }
    var authSettings by remember { mutableStateOf(profile?.authenticationSettings ?: "") }

    LaunchedEffect(profile) {
        profile?.let {
            officerName = it.officerName
            gnDivision = it.gnDivision
            officeAddress = it.officeAddress
            contactInfo = it.contactInfo
            authSettings = it.authenticationSettings
        }
    }

    Column(
            modifier =
                    Modifier.padding(padding)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
                value = officerName,
                onValueChange = { officerName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
                value = gnDivision,
                onValueChange = { gnDivision = it },
                label = { Text("GN Division") },
                modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
                value = officeAddress,
                onValueChange = { officeAddress = it },
                label = { Text("Office Address") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
        )

        OutlinedTextField(
                value = contactInfo,
                onValueChange = { contactInfo = it },
                label = { Text("Contact Info") },
                modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
                value = authSettings,
                onValueChange = { authSettings = it },
                label = { Text("Authentication Settings") },
                modifier = Modifier.fillMaxWidth()
        )

        Button(
                onClick = {
                    val updatedProfile =
                            OfficerProfile(
                                    officerName = officerName,
                                    gnDivision = gnDivision,
                                    officeAddress = officeAddress,
                                    contactInfo = contactInfo,
                                    authenticationSettings = authSettings
                            )
                    authViewModel.saveOfficerProfile(updatedProfile)
                    onProfileUpdated()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.Save, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Save Profile")
        }
    }
}
