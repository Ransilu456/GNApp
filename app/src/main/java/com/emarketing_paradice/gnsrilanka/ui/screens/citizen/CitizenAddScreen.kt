package com.emarketing_paradice.gnsrilanka.ui.screens.citizen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.ui.theme.AppBackground
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus

@Composable
fun CitizenAddScreen(
    citizenViewModel: CitizenViewModel,
    onCitizenAdded: () -> Unit
) {
    val uiState by citizenViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onCitizenAdded()
        }
    }

    CitizenAddScreenContent(
        uiState = uiState,
        onNicChanged = citizenViewModel::onNicChanged,
        onFullNameChanged = citizenViewModel::onFullNameChanged,
        onDobChanged = citizenViewModel::onDobChanged,
        onGenderChanged = citizenViewModel::onGenderChanged,
        onOccupationChanged = citizenViewModel::onOccupationChanged,
        onHouseholdIdChanged = citizenViewModel::onHouseholdIdChanged,
        onSaveCitizen = citizenViewModel::saveCitizen
    )
}

@Composable
fun CitizenAddScreenContent(
    uiState: CitizenUiState,
    onNicChanged: (String) -> Unit,
    onFullNameChanged: (String) -> Unit,
    onDobChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onOccupationChanged: (String) -> Unit,
    onHouseholdIdChanged: (String) -> Unit,
    onSaveCitizen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = uiState.nic,
                    onValueChange = onNicChanged,
                    label = { Text("NIC Number") },
                    isError = uiState.nicError != null,
                    supportingText = { uiState.nicError?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = uiState.fullName,
                    onValueChange = onFullNameChanged,
                    label = { Text("Full Name") },
                    isError = uiState.fullNameError != null,
                    supportingText = { uiState.fullNameError?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = uiState.dateOfBirth,
                    onValueChange = onDobChanged,
                    label = { Text("Date of Birth (YYYY-MM-DD)") },
                    isError = uiState.dobError != null,
                    supportingText = { uiState.dobError?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = uiState.gender,
                    onValueChange = onGenderChanged,
                    label = { Text("Gender") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = uiState.occupation,
                    onValueChange = onOccupationChanged,
                    label = { Text("Occupation") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = uiState.householdId,
                    onValueChange = onHouseholdIdChanged,
                    label = { Text("Household ID") },
                    isError = uiState.householdIdError != null,
                    supportingText = { uiState.householdIdError?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSaveCitizen,
            enabled = uiState.formStatus !is FormStatus.Loading,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlueGradientStart)
        ) {
            if (uiState.formStatus is FormStatus.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Add Citizen", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CitizenAddScreenPreview() {
    GNAppTheme {
        CitizenAddScreenContent(
            uiState = CitizenUiState(),
            onNicChanged = {},
            onFullNameChanged = {},
            onDobChanged = {},
            onGenderChanged = {},
            onOccupationChanged = {},
            onHouseholdIdChanged = {},
            onSaveCitizen = {}
        )
    }
}
