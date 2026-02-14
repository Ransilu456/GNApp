package com.emarketing_paradice.gnsrilanka.ui.components.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.Household
import com.emarketing_paradice.gnsrilanka.data.model.Request
import com.emarketing_paradice.gnsrilanka.data.model.RequestStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenFormEvent
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCitizenModal(
        state: CitizenFormState,
        onEvent: (CitizenFormEvent) -> Unit,
        onDismiss: () -> Unit
) {
        ModalBottomSheet(onDismissRequest = onDismiss) {
                Column(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        Text(
                                "Add New Citizen",
                                style =
                                        MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = FontWeight.Bold
                                        )
                        )

                        OutlinedTextField(
                                value = state.nic,
                                onValueChange = { onEvent(CitizenFormEvent.NicChanged(it)) },
                                label = { Text("NIC Number") },
                                isError = state.nicError != null,
                                supportingText = { state.nicError?.let { Text(it) } },
                                modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                                value = state.fullName,
                                onValueChange = { onEvent(CitizenFormEvent.NameChanged(it)) },
                                label = { Text("Full Name") },
                                isError = state.nameError != null,
                                supportingText = { state.nameError?.let { Text(it) } },
                                modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                                value = state.dob,
                                onValueChange = { onEvent(CitizenFormEvent.DobChanged(it)) },
                                label = { Text("Date of Birth (YYYY-MM-DD)") },
                                isError = state.dobError != null,
                                supportingText = { state.dobError?.let { Text(it) } },
                                modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                                value = state.gender,
                                onValueChange = { onEvent(CitizenFormEvent.GenderChanged(it)) },
                                label = { Text("Gender") },
                                modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                                value = state.occupation,
                                onValueChange = { onEvent(CitizenFormEvent.OccupationChanged(it)) },
                                label = { Text("Occupation") },
                                modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                                value = state.householdId,
                                onValueChange = {
                                        onEvent(CitizenFormEvent.HouseholdIdChanged(it))
                                },
                                label = { Text("Household ID") },
                                isError = state.householdIdError != null,
                                supportingText = { state.householdIdError?.let { Text(it) } },
                                modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                                onClick = {
                                        onEvent(CitizenFormEvent.Submit)
                                        if (state.isFormValid) onDismiss()
                                },
                                enabled = state.isFormValid,
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                                Text(
                                        "Save Citizen",
                                        style =
                                                MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Bold
                                                )
                                )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHouseholdModal(onDismiss: () -> Unit, onSave: (Household) -> Unit) {
        var id by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var gnDivision by remember { mutableStateOf("") }
        var headNic by remember { mutableStateOf("") }

        ModalBottomSheet(onDismissRequest = onDismiss) {
                Column(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        Text(
                                "Add New Household",
                                style =
                                        MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = FontWeight.Bold
                                        )
                        )

                        OutlinedTextField(
                                value = id,
                                onValueChange = { id = it },
                                label = { Text("Household ID") },
                                modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("Address") },
                                modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                                value = gnDivision,
                                onValueChange = { gnDivision = it },
                                label = { Text("GN Division") },
                                modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                                value = headNic,
                                onValueChange = { headNic = it },
                                label = { Text("Head of Household NIC") },
                                modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                                onClick = {
                                        onSave(Household(id, address, gnDivision, headNic))
                                        onDismiss()
                                },
                                enabled = id.isNotBlank() && address.isNotBlank(),
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                                Text(
                                        "Save Household",
                                        style =
                                                MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Bold
                                                )
                                )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRequestModal(onDismiss: () -> Unit, onSave: (Request) -> Unit) {
        var nic by remember { mutableStateOf("") }
        var type by remember { mutableStateOf("") }
        var purpose by remember { mutableStateOf("") }

        ModalBottomSheet(onDismissRequest = onDismiss) {
                Column(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        Text(
                                "New Service Request",
                                style =
                                        MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = FontWeight.Bold
                                        )
                        )

                        OutlinedTextField(
                                value = nic,
                                onValueChange = { nic = it },
                                label = { Text("Citizen NIC") },
                                modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                                value = type,
                                onValueChange = { type = it },
                                label = { Text("Certificate Type") },
                                modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                                value = purpose,
                                onValueChange = { purpose = it },
                                label = { Text("Purpose") },
                                modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                                onClick = {
                                        onSave(
                                                Request(
                                                        id = System.currentTimeMillis().toString(),
                                                        citizenNic = nic,
                                                        requestType = type,
                                                        certificateType = type,
                                                        purpose = purpose,
                                                        issuedDate = System.currentTimeMillis(),
                                                        issuedByGn = "Admin",
                                                        description = "",
                                                        status = RequestStatus.Pending
                                                )
                                        )
                                        onDismiss()
                                },
                                enabled = nic.isNotBlank() && type.isNotBlank(),
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                                Text(
                                        "Submit Request",
                                        style =
                                                MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Bold
                                                )
                                )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                }
        }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddCitizenModalPreview() {
        com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme {
                AddCitizenModal(
                        state = CitizenFormState(),
                        onEvent = {},
                        onDismiss = {}
                )
        }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddHouseholdModalPreview() {
        com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme {
                AddHouseholdModal(onDismiss = {}, onSave = {})
        }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddRequestModalPreview() {
        com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme {
                AddRequestModal(onDismiss = {}, onSave = {})
        }
}
