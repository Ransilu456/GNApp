package com.emarketing_paradice.gnsrilanka.ui.screens.household

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.Household
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.ui.theme.PreviewData
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdListScreen(
        householdViewModel: HouseholdViewModel,
        userMessage: String?,
        snackbarHostState: SnackbarHostState,
        onAddHousehold: () -> Unit,
        onEditHousehold: (Household) -> Unit,
        clearUserMessage: () -> Unit
) {
        val households by householdViewModel.households.collectAsState()

        HouseholdListScreenContent(
                households = households,
                userMessage = userMessage,
                snackbarHostState = snackbarHostState,
                onAddHousehold = onAddHousehold,
                onEditHousehold = onEditHousehold,
                onDeleteHousehold = { householdViewModel.deleteHousehold(it.id) },
                clearUserMessage = clearUserMessage
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdListScreenContent(
        households: List<Household>,
        userMessage: String?,
        snackbarHostState: SnackbarHostState,
        onAddHousehold: () -> Unit,
        onEditHousehold: (Household) -> Unit,
        onDeleteHousehold: (Household) -> Unit,
        clearUserMessage: () -> Unit
) {
        val scope = rememberCoroutineScope()
        var searchQuery by remember { mutableStateOf("") }

        val filteredHouseholds =
                households.filter {
                        (it.address ?: "").contains(searchQuery, ignoreCase = true) ||
                                (it.headNic ?: "").contains(searchQuery, ignoreCase = true)
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
                        tonalElevation = 0.dp
                ) {
                        SearchBar(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                onSearch = {},
                                active = false,
                                onActiveChange = {},
                                placeholder = {
                                        Text(
                                                stringResource(R.string.search_placeholder),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                },
                                leadingIcon = {
                                        Icon(
                                                painter =
                                                        painterResource(
                                                                id = R.drawable.ic_solar_magnifer
                                                        ),
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp),
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                },
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors =
                                        SearchBarDefaults.colors(
                                                containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                tonalElevation = 1.dp
                        ) {}
                        Spacer(modifier = Modifier.height(8.dp))
                }

                if (filteredHouseholds.isEmpty()) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                EmptyContent(
                                        stringResource(R.string.no_data_found),
                                        Icons.Default.Home
                                )
                        }
                } else {
                        LazyColumn(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(16.dp)
                        ) {
                                items(filteredHouseholds) { household ->
                                        HouseholdListItem(
                                                household = household,
                                                onItemClick = { onEditHousehold(household) },
                                                onDeleteClick = { onDeleteHousehold(household) }
                                        )
                                }
                        }
                }
        }
}

@Composable
fun HouseholdListItem(household: Household, onItemClick: () -> Unit, onDeleteClick: () -> Unit) {
        Card(
                modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
                shape = RoundedCornerShape(20.dp),
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
                Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Box(
                                modifier =
                                        Modifier.size(50.dp)
                                                .clip(CircleShape)
                                                .background(BlueGradientStart.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter =
                                                painterResource(
                                                        id = R.drawable.ic_solar_home_smile
                                                ),
                                        contentDescription = "Household",
                                        tint = BlueGradientStart,
                                        modifier = Modifier.size(28.dp)
                                )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                                Text(
                                        text = household.address ?: "Unknown Address",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                        text = "Head NIC: ${household.headNic ?: "N/A"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        }

                        IconButton(onClick = onDeleteClick, modifier = Modifier.size(24.dp)) {
                                Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
                                        modifier = Modifier.size(20.dp)
                                )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                                painter = painterResource(id = R.drawable.ic_solar_alt_arrow_right),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                        )
                }
        }
}

@Preview(showBackground = true)
@Composable
fun HouseholdListScreenPreview() {
        GNAppTheme {
                HouseholdListScreenContent(
                        households = PreviewData.sampleHouseholds,
                        userMessage = null,
                        snackbarHostState = remember { SnackbarHostState() },
                        onAddHousehold = {},
                        onEditHousehold = {},
                        onDeleteHousehold = {},
                        clearUserMessage = {}
                )
        }
}
