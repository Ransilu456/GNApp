package com.emarketing_paradice.gnsrilanka.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
                                .background(MaterialTheme.colorScheme.background)
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors =
                                CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                        Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                                OutlinedTextField(
                                        value = officerName,
                                        onValueChange = { officerName = it },
                                        label = { Text("Full Name") },
                                        placeholder = { Text("Enter your full name") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = gnDivision,
                                        onValueChange = { gnDivision = it },
                                        label = { Text("GN Division") },
                                        placeholder = { Text("Enter your GN Division") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = officeAddress,
                                        onValueChange = { officeAddress = it },
                                        label = { Text("Office Address") },
                                        placeholder = { Text("Enter your office address") },
                                        modifier = Modifier.fillMaxWidth(),
                                        minLines = 2,
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = contactInfo,
                                        onValueChange = { contactInfo = it },
                                        label = { Text("Contact Info") },
                                        placeholder = { Text("Enter your contact information") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = authSettings,
                                        onValueChange = { authSettings = it },
                                        label = { Text("Authentication Settings") },
                                        placeholder = { Text("Enter authentication settings") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )
                        }
                }

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
