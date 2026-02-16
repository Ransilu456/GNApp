package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelfareAddScreen(viewModel: GNRegistryViewModel, onWelfareAdded: () -> Unit) {
        val draft by viewModel.welfareDraft.collectAsState()

        LaunchedEffect(draft.formStatus) {
                if (draft.formStatus == FormStatus.Success) {
                        onWelfareAdded()
                }
        }

        Column(
                modifier =
                        Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                OutlinedTextField(
                        value = draft.programName,
                        onValueChange = { viewModel.onWelfareProgramNameChanged(it) },
                        label = { Text("Program Name") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.beneficiaryName,
                        onValueChange = { viewModel.onWelfareBeneficiaryNameChanged(it) },
                        label = { Text("Beneficiary Name") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.nic,
                        onValueChange = { viewModel.onWelfareNicChanged(it) },
                        label = { Text("NIC") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.amount,
                        onValueChange = { viewModel.onWelfareAmountChanged(it) },
                        label = { Text("Amount (LKR)") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.address,
                        onValueChange = { viewModel.onWelfareAddressChanged(it) },
                        label = { Text("Address") },
                        modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (draft.formStatus is FormStatus.Error) {
                        Text(
                                text = (draft.formStatus as FormStatus.Error).message,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )
                }

                Button(
                        onClick = { viewModel.saveWelfareProgram() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled =
                                draft.programName.isNotBlank() &&
                                        draft.nic.isNotBlank() &&
                                        draft.formStatus != FormStatus.Loading
                ) {
                        if (draft.formStatus == FormStatus.Loading) {
                                CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White
                                )
                        } else {
                                Text("Save Welfare Program")
                        }
                }
        }
}
