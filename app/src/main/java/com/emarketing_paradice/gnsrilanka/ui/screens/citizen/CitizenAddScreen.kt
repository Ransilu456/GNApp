package com.emarketing_paradice.gnsrilanka.ui.screens.citizen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenAddScreen(
    citizenViewModel: CitizenViewModel,
    onCitizenAdded: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by citizenViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onCitizenAdded()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Citizen") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.nic,
                onValueChange = { citizenViewModel.onNicChanged(it) },
                label = { Text("NIC Number") },
                isError = uiState.nicError != null,
                supportingText = { uiState.nicError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.fullName,
                onValueChange = { citizenViewModel.onFullNameChanged(it) },
                label = { Text("Full Name") },
                isError = uiState.fullNameError != null,
                supportingText = { uiState.fullNameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.dateOfBirth,
                onValueChange = { citizenViewModel.onDobChanged(it) },
                label = { Text("Date of Birth (YYYY-MM-DD)") },
                isError = uiState.dobError != null,
                supportingText = { uiState.dobError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.gender,
                onValueChange = { citizenViewModel.onGenderChanged(it) },
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.occupation,
                onValueChange = { citizenViewModel.onOccupationChanged(it) },
                label = { Text("Occupation") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.householdId,
                onValueChange = { citizenViewModel.onHouseholdIdChanged(it) },
                label = { Text("Household ID") },
                isError = uiState.householdIdError != null,
                supportingText = { uiState.householdIdError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { citizenViewModel.saveCitizen() },
                enabled = uiState.formStatus !is FormStatus.Loading,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                if (uiState.formStatus is FormStatus.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text("Add Citizen", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}
