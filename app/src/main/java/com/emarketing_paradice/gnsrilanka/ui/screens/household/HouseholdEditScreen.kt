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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.ui.theme.AppBackground
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel

@Composable
fun HouseholdEditScreen(
        householdViewModel: HouseholdViewModel,
        onHouseholdUpdated: () -> Unit
) {
    val uiState by householdViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onHouseholdUpdated()
        }
    }

    HouseholdEditScreenContent(
        uiState = uiState,
        onIdChanged = householdViewModel::onIdChanged,
        onAddressChanged = householdViewModel::onAddressChanged,
        onGnDivisionChanged = householdViewModel::onGnDivisionChanged,
        onHeadNicChanged = householdViewModel::onHeadNicChanged,
        onSaveHousehold = householdViewModel::saveHousehold
    )
}

@Composable
fun HouseholdEditScreenContent(
    uiState: HouseholdUiState,
    onIdChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onGnDivisionChanged: (String) -> Unit,
    onHeadNicChanged: (String) -> Unit,
    onSaveHousehold: () -> Unit
) {
    Column(
            modifier =
                    Modifier.fillMaxSize()
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
            Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                        value = uiState.id,
                        onValueChange = onIdChanged,
                        label = { Text("Household ID") },
                        isError = uiState.idError != null,
                        supportingText = {
                            uiState.idError?.let { error ->
                                Text(text = error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false, // ID should not be editable
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.address,
                        onValueChange = onAddressChanged,
                        label = { Text("Address") },
                        isError = uiState.addressError != null,
                        supportingText = {
                            uiState.addressError?.let { error ->
                                Text(text = error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.gnDivision,
                        onValueChange = onGnDivisionChanged,
                        label = { Text("GN Division") },
                        isError = uiState.gnDivisionError != null,
                        supportingText = {
                            uiState.gnDivisionError?.let { error ->
                                Text(text = error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.headNic,
                        onValueChange = onHeadNicChanged,
                        label = { Text("Head of Household NIC") },
                        isError = uiState.headNicError != null,
                        supportingText = {
                            uiState.headNicError?.let { error ->
                                Text(text = error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
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
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                        "Save Changes",
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
fun HouseholdEditScreenPreview() {
    GNAppTheme {
        HouseholdEditScreenContent(
            uiState = HouseholdUiState(
                id = "H001",
                address = "123 Main St",
                gnDivision = "Colombo 01",
                headNic = "123456789V"
            ),
            onIdChanged = {},
            onAddressChanged = {},
            onGnDivisionChanged = {},
            onHeadNicChanged = {},
            onSaveHousehold = {}
        )
    }
}
