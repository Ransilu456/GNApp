package com.emarketing_paradice.gnsrilanka.ui.screens.citizen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.Citizen
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.*
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenListScreen(
        citizenViewModel: CitizenViewModel,
        userMessage: String?,
        snackbarHostState: SnackbarHostState,
        onAddCitizen: () -> Unit,
        onEditCitizen: (Citizen) -> Unit,
        clearUserMessage: () -> Unit
) {
        val citizens by citizenViewModel.citizens.collectAsState()

        CitizenListScreenContent(
                citizens = citizens,
                userMessage = userMessage,
                snackbarHostState = snackbarHostState,
                onAddCitizen = onAddCitizen,
                onEditCitizen = onEditCitizen,
                onDeleteCitizen = { citizenViewModel.deleteCitizen(it.nic) },
                clearUserMessage = clearUserMessage
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenListScreenContent(
        citizens: List<Citizen>,
        userMessage: String?,
        snackbarHostState: SnackbarHostState,
        onAddCitizen: () -> Unit,
        onEditCitizen: (Citizen) -> Unit,
        onDeleteCitizen: (Citizen) -> Unit,
        clearUserMessage: () -> Unit
) {
        val scope = rememberCoroutineScope()
        var searchQuery by remember { mutableStateOf("") }
        var selectedFilter by remember { mutableStateOf("All") }

        val filteredCitizens =
                citizens.filter {
                        val fullName = it.fullName ?: ""
                        val nic = it.nic ?: ""
                        (fullName.contains(searchQuery, ignoreCase = true) ||
                                nic.contains(searchQuery, ignoreCase = true)) &&
                                (selectedFilter == "All" || getCitizenStatus(it) == selectedFilter)
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
                        shadowElevation = 0.1.dp
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
                                                        color = Color(0xFF64748B)
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
                                                        tint = Color(0xFF64748B)
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
                                                        containerColor = Color.White,
                                                        dividerColor = Color.Transparent
                                                ),
                                        tonalElevation = 1.dp
                                ) {}

                                FilterSection(
                                        selectedFilter = selectedFilter,
                                        onFilterSelected = { selectedFilter = it }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                        }
                }

                if (filteredCitizens.isEmpty()) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                EmptyContent(
                                        stringResource(R.string.no_data_found),
                                        Icons.Default.People
                                )
                        }
                } else {
                        LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(16.dp)
                        ) {
                                items(filteredCitizens, key = { it.nic ?: it.hashCode() }) { citizen
                                        ->
                                        CitizenListItem(
                                                citizen = citizen,
                                                onItemClick = { onEditCitizen(citizen) },
                                                onDeleteClick = { onDeleteCitizen(citizen) }
                                        )
                                }
                        }
                }
        }
}

@Composable
fun FilterSection(selectedFilter: String, onFilterSelected: (String) -> Unit) {
        val filters = listOf("All", "Approved", "Pending", "Rejected")
        Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                filters.forEach { filter ->
                        FilterChip(
                                selected = selectedFilter == filter,
                                onClick = { onFilterSelected(filter) },
                                label = {
                                        Text(filter, style = MaterialTheme.typography.labelMedium)
                                },
                                colors =
                                        FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = BlueGradientStart,
                                                selectedLabelColor = Color.White,
                                                containerColor = Color.Transparent,
                                                labelColor =
                                                        MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                border =
                                        FilterChipDefaults.filterChipBorder(
                                                enabled = true,
                                                selected = selectedFilter == filter,
                                                borderColor =
                                                        MaterialTheme.colorScheme.outline.copy(
                                                                alpha = 0.3f
                                                        ),
                                                selectedBorderColor = Color.Transparent
                                        )
                        )
                }
        }
}

@Composable
fun CitizenListItem(citizen: Citizen, onItemClick: () -> Unit, onDeleteClick: () -> Unit) {
        val status = getCitizenStatus(citizen)
        val statusColor =
                when (status) {
                        "Approved" -> StatusGreen
                        "Pending" -> StatusYellow
                        else -> StatusRed
                }

        Card(
                modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
                Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Box(
                                modifier =
                                        Modifier.size(48.dp)
                                                .clip(CircleShape)
                                                .background(BlueGradientStart.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter =
                                                painterResource(
                                                        id = R.drawable.ic_solar_user_circle
                                                ),
                                        contentDescription = null,
                                        tint = BlueGradientStart,
                                        modifier = Modifier.size(24.dp)
                                )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                                Text(
                                        text = citizen.fullName ?: "Unknown",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                        text = "NIC: ${citizen.nic ?: "N/A"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (!citizen.address.isNullOrBlank()) {
                                        Text(
                                                text = citizen.address,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                        )
                                }
                        }

                        Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                                Box(
                                        modifier =
                                                Modifier.background(
                                                                statusColor.copy(alpha = 0.1f),
                                                                RoundedCornerShape(8.dp)
                                                        )
                                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                        Text(
                                                text = status,
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
                                                tint = StatusRed.copy(alpha = 0.7f),
                                                modifier = Modifier.size(18.dp)
                                        )
                                }
                        }
                }
        }
}

private fun getCitizenStatus(citizen: Citizen): String {
        val nic = citizen.nic ?: ""
        if (nic.isEmpty()) return "Pending"
        val hashCode = nic.hashCode()
        return when (if (hashCode == Int.MIN_VALUE) 0 else Math.abs(hashCode) % 3) {
                0 -> "Approved"
                1 -> "Pending"
                else -> "Rejected"
        }
}

@Preview(showBackground = true)
@Composable
fun CitizenListScreenPreview() {
        GNAppTheme {
                CitizenListScreenContent(
                        citizens = PreviewData.sampleCitizens,
                        userMessage = null,
                        snackbarHostState = remember { SnackbarHostState() },
                        onAddCitizen = {},
                        onEditCitizen = {},
                        onDeleteCitizen = {},
                        clearUserMessage = {}
                )
        }
}
