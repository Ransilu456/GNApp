package com.emarketing_paradice.gnsrilanka.ui.screens.request

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.Request
import com.emarketing_paradice.gnsrilanka.data.model.RequestStatus
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.*
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
        requestViewModel: RequestViewModel,
        userMessage: String?,
        onAddRequest: () -> Unit,
        onEditRequest: (Request) -> Unit,
        clearUserMessage: () -> Unit
) {
    val requests by requestViewModel.requests.collectAsState()
    
    RequestListScreenContent(
        requests = requests,
        userMessage = userMessage,
        onAddRequest = onAddRequest,
        onEditRequest = onEditRequest,
        clearUserMessage = clearUserMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreenContent(
    requests: List<Request>,
    userMessage: String?,
    onAddRequest: () -> Unit,
    onEditRequest: (Request) -> Unit,
    clearUserMessage: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    val filteredRequests =
            requests.filter {
                (it.citizenName ?: "").contains(searchQuery, ignoreCase = true) ||
                        (it.certificateType ?: "").contains(searchQuery, ignoreCase = true) ||
                        (it.citizenNic ?: "").contains(searchQuery, ignoreCase = true)
            }

    LaunchedEffect(userMessage) {
        userMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                clearUserMessage()
            }
        }
    }

    Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = AppBackground
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    SearchBar(
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                onSearch = { },
                                expanded = false,
                                onExpandedChange = { },
                                placeholder = { Text("Search by type or NIC") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                            )
                        },
                        expanded = false,
                        onExpandedChange = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = SearchBarDefaults.colors(containerColor = AppBackground),
                        content = { }
                    )
                }
            }

            if (filteredRequests.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    EmptyContent("No requests found.", Icons.Default.Description)
                }
            } else {
                LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(16.dp)
                ) {
                    items(filteredRequests) { request ->
                        RequestListItem(request = request, onItemClick = { onEditRequest(request) })
                    }
                }
            }
        }
    }
}

@Composable
fun RequestListItem(request: Request, onItemClick: () -> Unit) {
    val statusColor =
            when (request.status) {
                RequestStatus.Approved -> StatusGreen
                RequestStatus.Pending -> StatusYellow
                RequestStatus.Rejected -> StatusRed
            }

    Card(
            modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                            text = request.certificateType ?: "Unknown",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                    )
                    Text(
                            text = "NIC: ${request.citizenNic ?: "N/A"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                            text = request.status.name,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = AppBackground, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                        text = "Submitted: ${request.submissionDate ?: "N/A"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                        text = "Details",
                        style = MaterialTheme.typography.labelLarge,
                        color = BlueGradientStart,
                        fontWeight = FontWeight.Bold
                )
                Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = BlueGradientStart,
                        modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestListScreenPreview() {
    val sampleRequests = listOf(
        Request(
            id = "R001",
            citizenNic = "123456789V",
            citizenName = "John Doe",
            certificateType = "Character Certificate",
            purpose = "Employment",
            issuedDate = 0L,
            submissionDate = "2023-10-27",
            issuedByGn = "GN01",
            description = "Character certificate for job",
            status = RequestStatus.Pending
        ),
        Request(
            id = "R002",
            citizenNic = "987654321V",
            citizenName = "Jane Smith",
            certificateType = "Residency Certificate",
            purpose = "School Admission",
            issuedDate = 123456789L,
            submissionDate = "2023-10-26",
            issuedByGn = "GN01",
            description = "Residency certificate for school",
            status = RequestStatus.Approved
        )
    )
    GNAppTheme {
        RequestListScreenContent(
            requests = sampleRequests,
            userMessage = null,
            onAddRequest = {},
            onEditRequest = {},
            clearUserMessage = {}
        )
    }
}
