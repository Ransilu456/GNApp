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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.theme.AppBackground
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.ui.theme.PreviewData
import com.emarketing_paradice.gnsrilanka.viewmodel.FormStatus
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel

@Composable
fun RequestEditScreen(requestViewModel: RequestViewModel, onRequestUpdated: () -> Unit) {
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
                onRequestTypeChanged = requestViewModel::onRequestTypeChanged,
                onPurposeChanged = requestViewModel::onPurposeChanged,
                onDescriptionChanged = requestViewModel::onDescriptionChanged,
                onApprovalNotesChanged = requestViewModel::onApprovalNotesChanged,
                onDocumentPathChanged = requestViewModel::onDocumentPathChanged,
                onSaveRequest = requestViewModel::saveRequest
        )
}

@Composable
fun RequestEditScreenContent(
        uiState: RequestUiState,
        onCitizenNicChanged: (String) -> Unit,
        onCertificateTypeChanged: (String) -> Unit,
        onRequestTypeChanged: (String) -> Unit,
        onPurposeChanged: (String) -> Unit,
        onDescriptionChanged: (String) -> Unit,
        onApprovalNotesChanged: (String) -> Unit,
        onDocumentPathChanged: (String) -> Unit,
        onSaveRequest: () -> Unit
) {
        Column(
                modifier =
                        Modifier.fillMaxSize()
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
                        label = { Text(stringResource(R.string.nic)) },
                        isError = uiState.citizenNicError != null,
                        supportingText = { uiState.citizenNicError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.certificateType,
                        onValueChange = onCertificateTypeChanged,
                        label = { Text(stringResource(R.string.request_type)) },
                        isError = uiState.certificateTypeError != null,
                        supportingText = { uiState.certificateTypeError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.purpose,
                        onValueChange = onPurposeChanged,
                        label = { Text(stringResource(R.string.purpose)) },
                        isError = uiState.purposeError != null,
                        supportingText = { uiState.purposeError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.description,
                        onValueChange = onDescriptionChanged,
                        label = { Text(stringResource(R.string.description)) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.approvalNotes,
                        onValueChange = onApprovalNotesChanged,
                        label = { Text(stringResource(R.string.approval_notes)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                        value = uiState.documentPath,
                        onValueChange = onDocumentPathChanged,
                        label = { Text(stringResource(R.string.document_path)) },
                        modifier = Modifier.fillMaxWidth(),
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
                                CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                )
                        } else {
                                Text(
                                        stringResource(R.string.save),
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
                        uiState =
                                RequestUiState(
                                        requestId = PreviewData.sampleRequest.id,
                                        citizenNic = PreviewData.sampleRequest.citizenNic,
                                        certificateType = PreviewData.sampleRequest.certificateType,
                                        purpose = PreviewData.sampleRequest.purpose,
                                        description = PreviewData.sampleRequest.description,
                                        approvalNotes = PreviewData.sampleRequest.approvalNotes,
                                        documentPath = PreviewData.sampleRequest.documentPath
                                ),
                        onCitizenNicChanged = {},
                        onCertificateTypeChanged = {},
                        onRequestTypeChanged = {},
                        onPurposeChanged = {},
                        onDescriptionChanged = {},
                        onApprovalNotesChanged = {},
                        onDocumentPathChanged = {},
                        onSaveRequest = {}
                )
        }
}
