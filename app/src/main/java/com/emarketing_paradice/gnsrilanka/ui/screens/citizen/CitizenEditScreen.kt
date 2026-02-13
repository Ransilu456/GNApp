package com.emarketing_paradice.gnsrilanka.ui.screens.citizen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus

@Composable
fun CitizenEditScreen(citizenViewModel: CitizenViewModel, onCitizenUpdated: () -> Unit) {
    val uiState by citizenViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onCitizenUpdated()
        }
    }

    CitizenEditScreenContent(
            uiState = uiState,
            onNicChanged = citizenViewModel::onNicChanged,
            onFullNameChanged = citizenViewModel::onFullNameChanged,
            onDobChanged = citizenViewModel::onDobChanged,
            onGenderChanged = citizenViewModel::onGenderChanged,
            onOccupationChanged = citizenViewModel::onOccupationChanged,
            onHouseholdIdChanged = citizenViewModel::onHouseholdIdChanged,
            onAddressChanged = citizenViewModel::onAddressChanged,
            onSaveCitizen = citizenViewModel::saveCitizen
    )
}

@Composable
fun CitizenEditScreenContent(
        uiState: CitizenUiState,
        onNicChanged: (String) -> Unit,
        onFullNameChanged: (String) -> Unit,
        onDobChanged: (String) -> Unit,
        onGenderChanged: (String) -> Unit,
        onOccupationChanged: (String) -> Unit,
        onHouseholdIdChanged: (String) -> Unit,
        onAddressChanged: (String) -> Unit,
        onSaveCitizen: () -> Unit
) {
    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(AppBackground)
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
                text = "Modify Citizen Record",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
                value = uiState.nic ?: "",
                onValueChange = onNicChanged,
                label = { Text("NIC Number") },
                isError = uiState.nicError != null,
                supportingText = { uiState.nicError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                shape = RoundedCornerShape(16.dp)
        )

        OutlinedTextField(
                value = uiState.fullName ?: "",
                onValueChange = onFullNameChanged,
                label = { Text("Full Name") },
                isError = uiState.fullNameError != null,
                supportingText = { uiState.fullNameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
        )

        OutlinedTextField(
                value = uiState.dateOfBirth ?: "",
                onValueChange = onDobChanged,
                label = { Text("Date of Birth (YYYY-MM-DD)") },
                isError = uiState.dobError != null,
                supportingText = { uiState.dobError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
        )

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                    value = uiState.gender ?: "",
                    onValueChange = onGenderChanged,
                    label = { Text("Gender") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                    value = uiState.householdId ?: "",
                    onValueChange = onHouseholdIdChanged,
                    label = { Text("Household ID") },
                    isError = uiState.householdIdError != null,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
            )
        }

        OutlinedTextField(
                value = uiState.occupation ?: "",
                onValueChange = onOccupationChanged,
                label = { Text("Occupation") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
        )

        OutlinedTextField(
                value = uiState.address ?: "",
                onValueChange = onAddressChanged,
                label = { Text("Permanent Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                minLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
                onClick = onSaveCitizen,
                enabled = uiState.formStatus !is FormStatus.Loading,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0014A8))
        ) {
            if (uiState.formStatus is FormStatus.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                        text = "Update Record",
                        style =
                                MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                )
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CitizenEditScreenPreview() {
    GNAppTheme {
        CitizenEditScreenContent(
                uiState =
                        CitizenUiState(
                                nic = "123456789V",
                                fullName = "John Doe",
                                dateOfBirth = "1990-01-01",
                                gender = "Male",
                                occupation = "Engineer",
                                householdId = "H001",
                                address = "123 Main St"
                        ),
                onNicChanged = {},
                onFullNameChanged = {},
                onDobChanged = {},
                onGenderChanged = {},
                onOccupationChanged = {},
                onHouseholdIdChanged = {},
                onAddressChanged = {},
                onSaveCitizen = {}
        )
    }
}
