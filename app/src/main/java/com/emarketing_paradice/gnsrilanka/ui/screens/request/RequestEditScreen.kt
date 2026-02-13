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
fun RequestEditScreen(
        requestViewModel: RequestViewModel,
        onRequestUpdated: () -> Unit
) {
    val uiState by requestViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.formStatus) {
        if (uiState.formStatus is FormStatus.Success) {
            onRequestUpdated()
        }
    }

    RequestEditScreenContent(
        uiState = uiState,
        onCitizenNicChanged = requestViewModel::onCitizenNicChanged,
        onCertificateTypeChanged = requestViewModel::onCertificateTypeChanged,
        onPurposeChanged = requestViewModel::onPurposeChanged,
        onSaveRequest = requestViewModel::saveRequest
    )
}

@Composable
fun RequestEditScreenContent(
    uiState: RequestUiState,
    onCitizenNicChanged: (String) -> Unit,
    onCertificateTypeChanged: (String) -> Unit,
    onPurposeChanged: (String) -> Unit,
    onSaveRequest: () -> Unit
) {
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
                value = uiState.requestId,
                onValueChange = { /* ID is not editable */},
                label = { Text("Request ID") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                shape = RoundedCornerShape(12.dp)
        )

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
                onClick = onSaveRequest,
                enabled = uiState.formStatus !is FormStatus.Loading,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueGradientStart)
        ) {
            if (uiState.formStatus is FormStatus.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                        "Save Changes",
                        style =
                                MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestEditScreenPreview() {
    GNAppTheme {
        RequestEditScreenContent(
            uiState = RequestUiState(
                requestId = "REQ-001",
                citizenNic = "123456789V",
                certificateType = "Character Certificate",
                purpose = "Employment"
            ),
            onCitizenNicChanged = {},
            onCertificateTypeChanged = {},
            onPurposeChanged = {},
            onSaveRequest = {}
        )
    }
}
