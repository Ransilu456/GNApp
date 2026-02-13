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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.Household
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.AppBackground
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdListScreen(
        householdViewModel: HouseholdViewModel,
        userMessage: String?,
        onAddHousehold: () -> Unit,
        onEditHousehold: (Household) -> Unit,
        clearUserMessage: () -> Unit
) {
    val households by householdViewModel.households.collectAsState()
    
    HouseholdListScreenContent(
        households = households,
        userMessage = userMessage,
        onAddHousehold = onAddHousehold,
        onEditHousehold = onEditHousehold,
        clearUserMessage = clearUserMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseholdListScreenContent(
    households: List<Household>,
    userMessage: String?,
    onAddHousehold: () -> Unit,
    onEditHousehold: (Household) -> Unit,
    clearUserMessage: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
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

    Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                FloatingActionButton(
                        onClick = onAddHousehold,
                        containerColor = BlueGradientStart,
                        contentColor = Color.White,
                        shape = CircleShape
                ) { Icon(Icons.Default.Add, contentDescription = "Add Household") }
            }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().background(AppBackground)) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text("Search by address or Head NIC") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .padding(top = innerPadding.calculateTopPadding()),
                    shape = RoundedCornerShape(20.dp),
                    colors = SearchBarDefaults.colors(containerColor = AppBackground),
                    tonalElevation = 0.dp
                ) {}
            }

            if (filteredHouseholds.isEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    EmptyContent("No households found.", Icons.Default.Home)
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
                                onItemClick = { onEditHousehold(household) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HouseholdListItem(household: Household, onItemClick: () -> Unit) {
    Card(
            modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
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
                        Icons.Default.Home,
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
                        color = Color.Gray
                )
            }

            Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HouseholdListScreenPreview() {
    val sampleHouseholds = listOf(
        Household("H001", "123 Main St", "Colombo 01", "123456789V"),
        Household("H002", "456 Oak Ave", "Colombo 01", "987654321V")
    )
    GNAppTheme {
        HouseholdListScreenContent(
            households = sampleHouseholds,
            userMessage = null,
            onAddHousehold = {},
            onEditHousehold = {},
            clearUserMessage = {}
        )
    }
}
