package com.emarketing_paradice.gnsrilanka.ui.screens.household

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel

@Composable
fun HouseholdAddScreen(householdViewModel: HouseholdViewModel, onHouseholdAdded: () -> Unit) {
        val uiState by householdViewModel.uiState.collectAsState()

        LaunchedEffect(uiState.formStatus) {
                if (uiState.formStatus is FormStatus.Success) {
                        onHouseholdAdded()
                }
        }

        HouseholdAddScreenContent(
                uiState = uiState,
                onIdChanged = householdViewModel::onIdChanged,
                onAddressChanged = householdViewModel::onAddressChanged,
                onGnDivisionChanged = householdViewModel::onGnDivisionChanged,
                onHeadNicChanged = householdViewModel::onHeadNicChanged,
                onMembersCountChanged = householdViewModel::onMembersCountChanged,
                onRemarksChanged = householdViewModel::onRemarksChanged,
                onSaveHousehold = householdViewModel::saveHousehold
        )
}

@Composable
fun HouseholdAddScreenContent(
        uiState: HouseholdUiState,
        onIdChanged: (String) -> Unit,
        onAddressChanged: (String) -> Unit,
        onGnDivisionChanged: (String) -> Unit,
        onHeadNicChanged: (String) -> Unit,
        onMembersCountChanged: (String) -> Unit,
        onRemarksChanged: (String) -> Unit,
        onSaveHousehold: () -> Unit
) {
        Column(
                modifier =
                        Modifier.fillMaxSize()
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
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                                OutlinedTextField(
                                        value = uiState.id,
                                        onValueChange = onIdChanged,
                                        label = { Text(stringResource(R.string.household_id)) },
                                        placeholder = { Text("e.g. HH-001") },
                                        isError = uiState.idError != null,
                                        supportingText = { uiState.idError?.let { Text(it) } },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = uiState.address,
                                        onValueChange = onAddressChanged,
                                        label = { Text(stringResource(R.string.address)) },
                                        placeholder = { Text("Enter residential address") },
                                        isError = uiState.addressError != null,
                                        supportingText = { uiState.addressError?.let { Text(it) } },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = uiState.gnDivision,
                                        onValueChange = onGnDivisionChanged,
                                        label = { Text("GN Division") },
                                        placeholder = { Text("Enter GN division name") },
                                        isError = uiState.gnDivisionError != null,
                                        supportingText = {
                                                uiState.gnDivisionError?.let { Text(it) }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = uiState.headNic,
                                        onValueChange = onHeadNicChanged,
                                        label = { Text(stringResource(R.string.nic) + " (Head)") },
                                        placeholder = { Text("NIC of the head of household") },
                                        isError = uiState.headNicError != null,
                                        supportingText = { uiState.headNicError?.let { Text(it) } },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = uiState.membersCount,
                                        onValueChange = onMembersCountChanged,
                                        label = { Text(stringResource(R.string.members_count)) },
                                        placeholder = { Text("e.g. 4") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                        value = uiState.remarks,
                                        onValueChange = onRemarksChanged,
                                        label = { Text(stringResource(R.string.remarks)) },
                                        placeholder = { Text("Any additional notes...") },
                                        modifier = Modifier.fillMaxWidth(),
                                        minLines = 3,
                                        shape = RoundedCornerShape(12.dp)
                                )
                        }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                        onClick = onSaveHousehold,
                        enabled = uiState.formStatus !is FormStatus.Loading,
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BlueGradientStart)
                ) {
                        if (uiState.formStatus is FormStatus.Loading) {
                                CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                )
                        } else {
                                Text(
                                        stringResource(R.string.save),
                                        style =
                                                MaterialTheme.typography.titleMedium.copy(
                                                        fontWeight = FontWeight.Bold
                                                )
                                )
                        }
                }
        }
}

@Preview(showBackground = true)
@Composable
fun HouseholdAddScreenPreview() {
        GNAppTheme {
                HouseholdAddScreenContent(
                        uiState = HouseholdUiState(),
                        onIdChanged = {},
                        onAddressChanged = {},
                        onGnDivisionChanged = {},
                        onHeadNicChanged = {},
                        onMembersCountChanged = {},
                        onRemarksChanged = {},
                        onSaveHousehold = {}
                )
        }
}
