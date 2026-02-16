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
import com.emarketing_paradice.gnsrilanka.data.model.PermitType
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermitAddScreen(viewModel: GNRegistryViewModel, onPermitAdded: () -> Unit) {
        val draft by viewModel.permitDraft.collectAsState()
        var expanded by remember { mutableStateOf(false) }

        LaunchedEffect(draft.formStatus) {
                if (draft.formStatus == FormStatus.Success) {
                        onPermitAdded()
                }
        }

        Column(
                modifier =
                        Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                OutlinedTextField(
                        value = draft.applicantName,
                        onValueChange = { viewModel.onPermitApplicantNameChanged(it) },
                        label = { Text("Applicant Name") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.nic,
                        onValueChange = { viewModel.onPermitNicChanged(it) },
                        label = { Text("NIC") },
                        modifier = Modifier.fillMaxWidth()
                )

                // Dropdown for Permit Type
                ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                ) {
                        OutlinedTextField(
                                value = draft.type.displayName(),
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Permit Type") },
                                trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                        )
                                },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                        ) {
                                com.emarketing_paradice.gnsrilanka.data.model.PermitType.values()
                                        .forEach { type ->
                                                DropdownMenuItem(
                                                        text = { Text(type.displayName()) },
                                                        onClick = {
                                                                viewModel.onPermitTypeChanged(type)
                                                                expanded = false
                                                        }
                                                )
                                        }
                        }
                }

                if (draft.type == com.emarketing_paradice.gnsrilanka.data.model.PermitType.BUSINESS
                ) {
                        OutlinedTextField(
                                value = draft.businessName,
                                onValueChange = { viewModel.onPermitBusinessNameChanged(it) },
                                label = { Text("Business Name") },
                                modifier = Modifier.fillMaxWidth()
                        )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (draft.formStatus is FormStatus.Error) {
                        Text(
                                text = (draft.formStatus as FormStatus.Error).message,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )
                }

                Button(
                        onClick = { viewModel.savePermit() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled =
                                draft.applicantName.isNotBlank() &&
                                        draft.nic.isNotBlank() &&
                                        draft.formStatus != FormStatus.Loading
                ) {
                        if (draft.formStatus == FormStatus.Loading) {
                                CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White
                                )
                        } else {
                                Text("Save Permit")
                        }
                }
        }
}
