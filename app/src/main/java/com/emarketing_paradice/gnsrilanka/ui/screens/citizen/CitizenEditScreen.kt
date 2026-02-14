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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.theme.AppBackground
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.ui.theme.PreviewData
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
                onContactNumberChanged = citizenViewModel::onContactNumberChanged,
                onNotesChanged = citizenViewModel::onNotesChanged,
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
        onContactNumberChanged: (String) -> Unit,
        onNotesChanged: (String) -> Unit,
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
                        text = stringResource(R.string.edit_citizen),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                        value = uiState.nic ?: "",
                        onValueChange = onNicChanged,
                        label = { Text(stringResource(R.string.nic)) },
                        isError = uiState.nicError != null,
                        supportingText = { uiState.nicError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                        value = uiState.fullName ?: "",
                        onValueChange = onFullNameChanged,
                        label = { Text(stringResource(R.string.full_name)) },
                        isError = uiState.fullNameError != null,
                        supportingText = { uiState.fullNameError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                        value = uiState.dateOfBirth ?: "",
                        onValueChange = onDobChanged,
                        label = { Text(stringResource(R.string.dob)) },
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
                                label = { Text(stringResource(R.string.gender)) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                                value = uiState.householdId ?: "",
                                onValueChange = onHouseholdIdChanged,
                                label = { Text(stringResource(R.string.household_id)) },
                                isError = uiState.householdIdError != null,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp)
                        )
                }

                OutlinedTextField(
                        value = uiState.occupation ?: "",
                        onValueChange = onOccupationChanged,
                        label = { Text(stringResource(R.string.occupation)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                        value = uiState.address ?: "",
                        onValueChange = onAddressChanged,
                        label = { Text(stringResource(R.string.address)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        minLines = 3
                )

                OutlinedTextField(
                        value = uiState.contactNumber ?: "",
                        onValueChange = onContactNumberChanged,
                        label = { Text(stringResource(R.string.contact_number)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                        value = uiState.notes ?: "",
                        onValueChange = onNotesChanged,
                        label = { Text(stringResource(R.string.notes)) },
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
                                CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                )
                        } else {
                                Text(
                                        text = stringResource(R.string.save),
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
                                        nic = PreviewData.sampleCitizen.nic,
                                        fullName = PreviewData.sampleCitizen.fullName,
                                        dateOfBirth = PreviewData.sampleCitizen.dateOfBirth,
                                        gender = PreviewData.sampleCitizen.gender,
                                        occupation = PreviewData.sampleCitizen.occupation,
                                        householdId = PreviewData.sampleCitizen.householdId,
                                        address = PreviewData.sampleCitizen.address,
                                        contactNumber = PreviewData.sampleCitizen.contactNumber,
                                        notes = PreviewData.sampleCitizen.notes
                                ),
                        onNicChanged = {},
                        onFullNameChanged = {},
                        onDobChanged = {},
                        onGenderChanged = {},
                        onOccupationChanged = {},
                        onHouseholdIdChanged = {},
                        onAddressChanged = {},
                        onContactNumberChanged = {},
                        onNotesChanged = {},
                        onSaveCitizen = {}
                )
        }
}
