package com.emarketing_paradice.gnsrilanka.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.Citizen360
import com.emarketing_paradice.gnsrilanka.viewmodel.GlobalSearchViewModel

@Composable
fun GlobalSearchResultScreen(viewModel: GlobalSearchViewModel, onBack: () -> Unit) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (isSearching) {
            CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
            )
        } else if (searchResults.isEmpty()) {
            Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                        painter = painterResource(id = R.drawable.ic_solar_magnifer),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                        text = "No results found for your search.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge
                )
            }
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
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                        modifier =
                                Modifier.size(52.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        ),
                        contentAlignment = Alignment.Center
                ) {
                    Icon(
                            painter = painterResource(id = R.drawable.ic_solar_user_circle),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                            text = data.citizen.fullName,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                            text = "NIC: ${data.citizen.nic}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(20.dp))

            // Info Grid
            SearchInfoRow("Permanent Address", data.citizen.address, R.drawable.ic_solar_home_smile)

            if (data.welfarePrograms.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                SearchInfoRow(
                        "Welfare Benefits",
                        data.welfarePrograms.joinToString { it.programName },
                        R.drawable.ic_registry_welfare
                )
            }

            if (data.permits.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                SearchInfoRow(
                        "Registered Permits",
                        "${data.permits.size} Active Permits",
                        R.drawable.ic_registry_permit
                )
            }

            if (data.dailyLogs.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                SearchInfoRow(
                        "Recent Visits",
                        "${data.dailyLogs.size} Log Entries Found",
                        R.drawable.ic_registry_log
                )
            }
        }
    }
}

@Composable
fun SearchInfoRow(label: String, value: String, icon: Int) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp).padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                    text = label,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
            )
            Text(
                    text = value,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
            )
        }
    }
}
