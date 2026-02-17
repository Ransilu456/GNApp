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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.Request
import com.emarketing_paradice.gnsrilanka.data.model.RequestStatus
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.*
import com.emarketing_paradice.gnsrilanka.ui.theme.PreviewData
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
        requestViewModel: RequestViewModel,
        userMessage: String?,
        snackbarHostState: SnackbarHostState,
        onAddRequest: () -> Unit,
        onEditRequest: (Request) -> Unit,
        clearUserMessage: () -> Unit
) {
        val requests by requestViewModel.requests.collectAsState()

        RequestListScreenContent(
                requests = requests,
                userMessage = userMessage,
                snackbarHostState = snackbarHostState,
                onAddRequest = onAddRequest,
                onEditRequest = onEditRequest,
                onDeleteRequest = { requestViewModel.deleteRequest(it.id) },
                clearUserMessage = clearUserMessage
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreenContent(
        requests: List<Request>,
        userMessage: String?,
        snackbarHostState: SnackbarHostState,
        onAddRequest: () -> Unit,
        onEditRequest: (Request) -> Unit,
        onDeleteRequest: (Request) -> Unit,
        clearUserMessage: () -> Unit
) {
        val scope = rememberCoroutineScope()
        var searchQuery by remember { mutableStateOf("") }

        val filteredRequests =
                requests.filter {
                        (it.citizenName ?: "").contains(searchQuery, ignoreCase = true) ||
                                (it.certificateType ?: "").contains(
                                        searchQuery,
                                        ignoreCase = true
                                ) ||
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

        Column(modifier = Modifier.fillMaxSize()) {
                Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                ) {
                        Column(modifier = Modifier.padding(top = 0.dp)) {
                                SearchBar(
                                        query = searchQuery,
                                        onQueryChange = { searchQuery = it },
                                        onSearch = {},
                                        active = false,
                                        onActiveChange = {},
                                        placeholder = {
                                                Text(
                                                        stringResource(R.string.search_placeholder),
                                                        color =
                                                                MaterialTheme.colorScheme
                                                                        .onSurfaceVariant
                                                )
                                        },
                                        leadingIcon = {
                                                Icon(
                                                        painter =
                                                                painterResource(
                                                                        id =
                                                                                R.drawable
                                                                                        .ic_solar_magnifer
                                                                ),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(20.dp),
                                                        tint =
                                                                MaterialTheme.colorScheme
                                                                        .onSurfaceVariant
                                                )
                                        },
                                        modifier =
                                                Modifier.fillMaxWidth()
                                                        .padding(
                                                                horizontal = 16.dp,
                                                                vertical = 8.dp
                                                        ),
                                        shape = RoundedCornerShape(16.dp),
                                        colors =
                                                SearchBarDefaults.colors(
                                                        containerColor =
                                                                MaterialTheme.colorScheme.surface
                                                ),
                                        tonalElevation = 1.dp
                                ) {}
                                Spacer(modifier = Modifier.height(8.dp))
                        }
                }

                if (filteredRequests.isEmpty()) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                EmptyContent(
                                        stringResource(R.string.no_data_found),
                                        Icons.Default.Description
                                )
                        }
                } else {
                        LazyColumn(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(16.dp)
                        ) {
                                items(filteredRequests) { request ->
                                        RequestListItem(
                                                request = request,
                                                onItemClick = { onEditRequest(request) },
                                                onDeleteClick = { onDeleteRequest(request) }
                                        )
                                }
                        }
                }
        }
}

@Composable
fun RequestListItem(request: Request, onItemClick: () -> Unit, onDeleteClick: () -> Unit) {
        val statusColor =
                when (request.status) {
                        RequestStatus.Approved -> StatusGreen
                        RequestStatus.Pending -> StatusYellow
                        RequestStatus.Rejected -> StatusRed
                }

        Card(
                modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
                shape = RoundedCornerShape(16.dp),
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                }

                                Box(
                                        modifier =
                                                Modifier.background(
                                                                statusColor.copy(alpha = 0.1f),
                                                                RoundedCornerShape(8.dp)
                                                        )
                                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                        Text(
                                                text = request.status.name,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = statusColor
                                        )
                                }

                                IconButton(
                                        onClick = onDeleteClick,
                                        modifier = Modifier.size(24.dp)
                                ) {
                                        Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint =
                                                        MaterialTheme.colorScheme.error.copy(
                                                                alpha = 0.6f
                                                        ),
                                                modifier = Modifier.size(18.dp)
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant,
                                thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Icon(
                                        Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                        text = "Submitted: ${request.submissionDate ?: "N/A"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                        text = stringResource(R.string.details),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = BlueGradientStart,
                                        fontWeight = FontWeight.Bold
                                )
                                Icon(
                                        painter =
                                                painterResource(
                                                        id = R.drawable.ic_solar_alt_arrow_right
                                                ),
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
        GNAppTheme {
                RequestListScreenContent(
                        requests = PreviewData.sampleRequests,
                        userMessage = null,
                        snackbarHostState = remember { SnackbarHostState() },
                        onAddRequest = {},
                        onEditRequest = {},
                        onDeleteRequest = {},
                        clearUserMessage = {}
                )
        }
}
