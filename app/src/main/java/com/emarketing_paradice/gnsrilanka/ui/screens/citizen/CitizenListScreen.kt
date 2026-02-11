package com.emarketing_paradice.gnsrilanka.ui.screens.citizen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.Citizen
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenListScreen(
    citizenViewModel: CitizenViewModel,
    userMessage: String?,
    onAddCitizen: () -> Unit,
    onEditCitizen: (Citizen) -> Unit,
    clearUserMessage: () -> Unit
) {
    val citizens by citizenViewModel.citizens.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }


    val filteredCitizens = citizens.filter {
        it.fullName.contains(searchQuery, ignoreCase = true) || it.nic.contains(searchQuery, ignoreCase = true)
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
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0F4F8))
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Search Citizens") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(30.dp),
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
                tonalElevation = 2.dp
            ) {}

            if (filteredCitizens.isEmpty()) {
                EmptyContent("No citizens found.", Icons.Default.People)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(filteredCitizens) { citizen ->
                        CitizenListItem(
                            citizen = citizen,
                            onItemClick = { onEditCitizen(citizen) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CitizenListItem(
    citizen: Citizen,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Citizen",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = citizen.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "NIC: ${citizen.nic}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            
            if (citizen.nic.hashCode() % 3 == 0) {
                 Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = "Badge",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
