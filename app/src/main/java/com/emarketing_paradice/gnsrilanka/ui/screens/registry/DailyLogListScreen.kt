package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.DailyLog
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogListScreen(viewModel: GNRegistryViewModel, onAddLog: () -> Unit) {
        val logs by viewModel.dailyLogs.collectAsState()
        var searchQuery by remember { mutableStateOf("") }

        Scaffold(
                floatingActionButton = {
                        FloatingActionButton(
                                onClick = onAddLog,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                        ) { Icon(Icons.Default.Add, contentDescription = "Add Log") }
                }
        ) { padding ->
                Column(
                        modifier =
                                Modifier.padding(padding)
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(16.dp)
                ) {
                        // Search Bar
                        OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .padding(bottom = 16.dp)
                                                .shadow(
                                                        4.dp,
                                                        RoundedCornerShape(16.dp),
                                                        spotColor = Color.Black.copy(0.1f)
                                                ),
                                placeholder = {
                                        Text(
                                                "Search logs...",
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                },
                                leadingIcon = {
                                        Icon(
                                                painterResource(id = R.drawable.ic_solar_magnifer),
                                                contentDescription = "Search",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                },
                                colors =
                                        OutlinedTextFieldDefaults.colors(
                                                focusedContainerColor =
                                                        MaterialTheme.colorScheme.surface,
                                                unfocusedContainerColor =
                                                        MaterialTheme.colorScheme.surface,
                                                focusedBorderColor =
                                                        MaterialTheme.colorScheme.primary.copy(
                                                                alpha = 0.5f
                                                        ),
                                                unfocusedBorderColor = Color.Transparent
                                        ),
                                shape = RoundedCornerShape(16.dp),
                                singleLine = true
                        )

                        LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 80.dp)
                        ) {
                                items(
                                        logs.filter {
                                                it.visitorName.contains(
                                                        searchQuery,
                                                        ignoreCase = true
                                                ) ||
                                                        it.purpose.contains(
                                                                searchQuery,
                                                                ignoreCase = true
                                                        )
                                        }
                                ) { log -> LogItemCard(log) }
                        }
                }
        }
}

@Composable
fun LogItemCard(log: DailyLog) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
                Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                        modifier =
                                                Modifier.size(40.dp)
                                                        .clip(CircleShape)
                                                        .background(
                                                                MaterialTheme.colorScheme.primary
                                                                        .copy(0.1f)
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Icon(
                                                Icons.Default.Person,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                        )
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                        Text(
                                                log.visitorName,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                                log.date,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                }
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("Purpose: ${log.purpose}", style = MaterialTheme.typography.bodyMedium)
                        if (log.actionTaken.isNotEmpty()) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                        "Action: ${log.actionTaken}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.secondary
                                )
                        }
                }
        }
}
