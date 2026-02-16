package com.emarketing_paradice.gnsrilanka.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.data.model.Citizen360
import com.emarketing_paradice.gnsrilanka.viewmodel.GlobalSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSearchResultScreen(viewModel: GlobalSearchViewModel, onBack: () -> Unit) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (isSearching) {
            CircularProgressIndicator(
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            )
        } else if (searchResults.isEmpty()) {
            Text(
                    text = "No results found.",
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            )
        } else {
            LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) { items(searchResults) { result -> Citizen360Card(result) } }
        }
    }
}

@Composable
fun Citizen360Card(data: Citizen360) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Citizen Bio
            Text(
                    text = data.citizen.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
            )
            Text(
                    text = "NIC: ${data.citizen.nic}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                    text = "Address: ${data.citizen.address}",
                    style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            // Section: Welfare
            if (data.welfarePrograms.isNotEmpty()) {
                Text(
                        "Welfare Programs",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                )
                data.welfarePrograms.forEach {
                    Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("- ${it.programName}", fontSize = 14.sp)
                        Text(
                                it.status,
                                fontSize = 12.sp,
                                color = if (it.status == "Active") Color(0xFF2E7D32) else Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Section: Permits
            if (data.permits.isNotEmpty()) {
                Text(
                        "Permits",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                )
                data.permits.forEach {
                    Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("- ${it.type}", fontSize = 14.sp)
                        Text(it.status, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Section: Logs
            if (data.dailyLogs.isNotEmpty()) {
                Text(
                        "Recent Interactions",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                )
                data.dailyLogs.take(3).forEach { // Show last 3 interactions
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text("${it.date}: ${it.purpose}", fontSize = 14.sp)
                        if (it.actionTaken.isNotBlank()) {
                            Text(
                                    "  Action: ${it.actionTaken}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Section: Pensions
            if (data.pensions.isNotEmpty()) {
                Text(
                        "Pensions",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                )
                data.pensions.forEach {
                    Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("- ${it.type}", fontSize = 14.sp)
                        Text("LKR ${it.amount}", fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Section: Elderly IDs
            if (data.elderlyIds.isNotEmpty()) {
                Text(
                        "Elderly ID",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                )
                data.elderlyIds.forEach {
                    Text("- Issued: ${it.issueDate} by ${it.issuedBy}", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Section: Voluntary Orgs
            if (data.voluntaryOrgs.isNotEmpty()) {
                Text(
                        "Voluntary Organizations",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                )
                data.voluntaryOrgs.forEach { Text("- ${it.name} (${it.type})", fontSize = 14.sp) }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
