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
fun DailyLogAddScreen(viewModel: GNRegistryViewModel, onLogAdded: () -> Unit) {
        val draft by viewModel.dailyLogDraft.collectAsState()

        LaunchedEffect(draft.formStatus) {
                if (draft.formStatus == FormStatus.Success) {
                        onLogAdded()
                }
        }

        Column(
                modifier =
                        Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                OutlinedTextField(
                        value = draft.visitorName,
                        onValueChange = { viewModel.onDailyLogVisitorNameChanged(it) },
                        label = { Text("Visitor Name") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.purpose,
                        onValueChange = { viewModel.onDailyLogPurposeChanged(it) },
                        label = { Text("Purpose") },
                        modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                        value = draft.actionTaken,
                        onValueChange = { viewModel.onDailyLogActionTakenChanged(it) },
                        label = { Text("Action Taken (Optional)") },
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
                        onClick = { viewModel.saveDailyLog() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled =
                                draft.visitorName.isNotBlank() &&
                                        draft.purpose.isNotBlank() &&
                                        draft.formStatus != FormStatus.Loading
                ) {
                        if (draft.formStatus == FormStatus.Loading) {
                                CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White
                                )
                        } else {
                                Text("Save Log")
                        }
                }
        }
}
