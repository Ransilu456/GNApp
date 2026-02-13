package com.emarketing_paradice.gnsrilanka.ui.screens.request

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
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel

@Composable
fun RequestAddScreen(
        requestViewModel: RequestViewModel,
        onRequestAdded: () -> Unit
) {
    val uiState by requestViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onRequestAdded()
        }
    }

    RequestAddScreenContent(
        uiState = uiState,
        onCitizenNicChanged = requestViewModel::onCitizenNicChanged,
        onCertificateTypeChanged = requestViewModel::onCertificateTypeChanged,
        onPurposeChanged = requestViewModel::onPurposeChanged,
        onSaveRequest = requestViewModel::saveRequest
    )
}

@Composable
fun RequestAddScreenContent(
    uiState: RequestUiState,
    onCitizenNicChanged: (String) -> Unit,
    onCertificateTypeChanged: (String) -> Unit,
    onPurposeChanged: (String) -> Unit,
    onSaveRequest: () -> Unit
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
                        value = uiState.citizenNic,
                        onValueChange = onCitizenNicChanged,
                        label = { Text("Citizen NIC") },
                        isError = uiState.citizenNicError != null,
                        supportingText = { uiState.citizenNicError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.certificateType,
                        onValueChange = onCertificateTypeChanged,
                        label = { Text("Certificate Type") },
                        isError = uiState.certificateTypeError != null,
                        supportingText = { uiState.certificateTypeError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.purpose,
                        onValueChange = onPurposeChanged,
                        label = { Text("Purpose") },
                        isError = uiState.purposeError != null,
                        supportingText = { uiState.purposeError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
                onClick = onSaveRequest,
                enabled = uiState.formStatus !is FormStatus.Loading,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueGradientStart)
        ) {
            if (uiState.formStatus is FormStatus.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                        "Submit Request",
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
fun RequestAddScreenPreview() {
    GNAppTheme {
        RequestAddScreenContent(
            uiState = RequestUiState(),
            onCitizenNicChanged = {},
            onCertificateTypeChanged = {},
            onPurposeChanged = {},
            onSaveRequest = {}
        )
    }
}
