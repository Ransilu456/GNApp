package com.emarketing_paradice.gnsrilanka.ui.screens.request

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestAddScreen(
    requestViewModel: RequestViewModel,
    onRequestAdded: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by requestViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onRequestAdded()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Request") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.citizenNic,
                onValueChange = { requestViewModel.onCitizenNicChanged(it) },
                label = { Text("Citizen NIC") },
                isError = uiState.citizenNicError != null,
                supportingText = { uiState.citizenNicError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.certificateType,
                onValueChange = { requestViewModel.onCertificateTypeChanged(it) },
                label = { Text("Certificate Type") },
                isError = uiState.certificateTypeError != null,
                supportingText = { uiState.certificateTypeError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.purpose,
                onValueChange = { requestViewModel.onPurposeChanged(it) },
                label = { Text("Purpose") },
                isError = uiState.purposeError != null,
                supportingText = { uiState.purposeError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { requestViewModel.saveRequest() },
                enabled = uiState.formStatus !is FormStatus.Loading,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                if (uiState.formStatus is FormStatus.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text("Submit Request", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}
