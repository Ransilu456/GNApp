package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.DailyLog
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogListScreen(viewModel: GNRegistryViewModel, onAddLog: () -> Unit) {
    val logs by viewModel.dailyLogs.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredLogs =
            logs.filter {
                searchQuery.isEmpty() ||
                        it.visitorName.contains(searchQuery, ignoreCase = true) ||
                        it.purpose.contains(searchQuery, ignoreCase = true) ||
                        it.date.contains(searchQuery, ignoreCase = true)
            }

    Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                        onClick = onAddLog,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                ) { Icon(Icons.Default.Add, contentDescription = "Add Log") }
            }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Search Bar
            Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.1.dp
            ) {
                OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        placeholder = { Text("Search by name, purpose, or date...") },
                        leadingIcon = {
                            Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = Color(0xFF64748B)
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors =
                                OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                                ),
                        singleLine = true
                )
            }

            if (filteredLogs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyContent(
                            if (searchQuery.isEmpty()) "No visitor logs found"
                            else "No logs match your search",
                            Icons.Default.History
                    )
                }
            } else {
                LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(16.dp)
                ) { items(filteredLogs) { log -> DailyLogItem(log) } }
            }
        }
    }
}

@Composable
fun DailyLogItem(log: DailyLog) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                        modifier =
                                Modifier.size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary.copy(0.1f)),
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
