package com.emarketing_paradice.gnsrilanka.ui.screens.household

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.ui.components.common.SplitScreen
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        SplitScreen(
            title = "Edit Household",
            subtitle = "Update the details of the household below.",
            formContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.householdId,
                        onValueChange = { householdViewModel.onHouseholdIdChanged(it) },
                        label = { Text("Household ID") },
                        isError = uiState.householdIdError != null,
                        supportingText = { uiState.householdIdError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false // ID should not be editable
                    )

                    OutlinedTextField(
                        value = uiState.address,
                        onValueChange = { householdViewModel.onAddressChanged(it) },
                        label = { Text("Address") },
                        isError = uiState.addressError != null,
                        supportingText = { uiState.addressError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uiState.gnDivision,
                        onValueChange = { householdViewModel.onGnDivisionChanged(it) },
                        label = { Text("GN Division") },
                        isError = uiState.gnDivisionError != null,
                        supportingText = { uiState.gnDivisionError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uiState.headNic,
                        onValueChange = { householdViewModel.onHeadNicChanged(it) },
                        label = { Text("Head of Household NIC") },
                        isError = uiState.headNicError != null,
                        supportingText = { uiState.headNicError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { householdViewModel.saveHousehold() },
                        enabled = uiState.formStatus !is FormStatus.Loading,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        if (uiState.formStatus is FormStatus.Loading) {
                            CircularProgressIndicator()
                        } else {
                            Text("Save Changes", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        )
    }
}
