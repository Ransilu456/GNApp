package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.Permit
import com.emarketing_paradice.gnsrilanka.data.model.PermitType
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermitListScreen(viewModel: GNRegistryViewModel, onAddPermit: (PermitType) -> Unit) {
    val permits by viewModel.permits.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<PermitType?>(null) }

    val filteredPermits =
            permits.filter {
                val matchesSearch =
                        searchQuery.isEmpty() ||
                                it.applicantName.contains(searchQuery, ignoreCase = true) ||
                                it.nic.contains(searchQuery, ignoreCase = true) ||
                                it.businessName?.contains(searchQuery, ignoreCase = true) == true

                val matchesType = selectedType == null || it.type == selectedType

                matchesSearch && matchesType
            }

    Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                        onClick = { onAddPermit(PermitType.BUSINESS) },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                ) { Icon(Icons.Default.Add, contentDescription = "Add Permit") }
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
                Column {
                    OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            placeholder = { Text("Search by name, NIC, or business...") },
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
                                            focusedContainerColor =
                                                    MaterialTheme.colorScheme.surface,
                                            unfocusedContainerColor =
                                                    MaterialTheme.colorScheme.surface,
                                            disabledContainerColor =
                                                    MaterialTheme.colorScheme.surface,
                                    ),
                            singleLine = true
                    )

                    // Type Filter Chips
                    LazyRow(
                            modifier =
                                    Modifier.fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                    selected = selectedType == null,
                                    onClick = { selectedType = null },
                                    label = {
                                        Text("All", style = MaterialTheme.typography.labelMedium)
                                    },
                                    colors =
                                            FilterChipDefaults.filterChipColors(
                                                    selectedContainerColor = BlueGradientStart,
                                                    selectedLabelColor = Color.White,
                                                    containerColor = Color.Transparent,
                                                    labelColor =
                                                            MaterialTheme.colorScheme
                                                                    .onSurfaceVariant
                                            ),
                                    border =
                                            FilterChipDefaults.filterChipBorder(
                                                    enabled = true,
                                                    selected = selectedType == null,
                                                    borderColor =
                                                            MaterialTheme.colorScheme.outline.copy(
                                                                    alpha = 0.3f
                                                            ),
                                                    selectedBorderColor = Color.Transparent
                                            )
                            )
                        }
                        items(PermitType.values().toList()) { type ->
                            FilterChip(
                                    selected = selectedType == type,
                                    onClick = { selectedType = type },
                                    label = {
                                        Text(
                                                type.displayName(),
                                                style = MaterialTheme.typography.labelMedium
                                        )
                                    },
                                    colors =
                                            FilterChipDefaults.filterChipColors(
                                                    selectedContainerColor = BlueGradientStart,
                                                    selectedLabelColor = Color.White,
                                                    containerColor = Color.Transparent,
                                                    labelColor =
                                                            MaterialTheme.colorScheme
                                                                    .onSurfaceVariant
                                            ),
                                    border =
                                            FilterChipDefaults.filterChipBorder(
                                                    enabled = true,
                                                    selected = selectedType == type,
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
            }

            if (filteredPermits.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyContent(
                            if (searchQuery.isEmpty() && selectedType == null) "No permits found"
                            else "No permits match your filters",
                            Icons.Default.Assignment
                    )
                }
            } else {
                LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(16.dp)
                ) { items(filteredPermits) { permit -> PermitItem(permit) } }
            }
        }
    }
}

@Composable
fun PermitItem(permit: Permit) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
            ) {
                // Icon based on type
                Box(
                        modifier =
                                Modifier.size(40.dp)
                                        .clip(CircleShape)
                                        .background(
                                                when (permit.type) {
                                                    PermitType.BUSINESS -> Color(0xFFE3F2FD)
                                                    PermitType.FIREARM -> Color(0xFFFFECB3)
                                                    PermitType.RESOURCE_TRANSPORT ->
                                                            Color(0xFFE8F5E9)
                                                }
                                        ),
                        contentAlignment = Alignment.Center
                ) {
                    Icon(
                            when (permit.type) {
                                PermitType.BUSINESS -> Icons.Default.Business
                                PermitType.FIREARM -> Icons.Default.Security
                                PermitType.RESOURCE_TRANSPORT -> Icons.Default.LocalShipping
                            },
                            contentDescription = null,
                            tint =
                                    when (permit.type) {
                                        PermitType.BUSINESS -> Color(0xFF1976D2)
                                        PermitType.FIREARM -> Color(0xFFF57C00)
                                        PermitType.RESOURCE_TRANSPORT -> Color(0xFF388E3C)
                                    }
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                            permit.applicantName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                    )
                    permit.businessName?.let {
                        Text(
                                it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Type Badge
                Box(
                        modifier =
                                Modifier.background(
                                                when (permit.type) {
                                                    PermitType.BUSINESS -> Color(0xFFE3F2FD)
                                                    PermitType.FIREARM -> Color(0xFFFFECB3)
                                                    PermitType.RESOURCE_TRANSPORT ->
                                                            Color(0xFFE8F5E9)
                                                },
                                                RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                            permit.type.displayName(),
                            style = MaterialTheme.typography.labelSmall,
                            color =
                                    when (permit.type) {
                                        PermitType.BUSINESS -> Color(0xFF1976D2)
                                        PermitType.FIREARM -> Color(0xFFF57C00)
                                        PermitType.RESOURCE_TRANSPORT -> Color(0xFF388E3C)
                                    }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("NIC: ${permit.nic}", style = MaterialTheme.typography.bodySmall)
                Text(
                        "Issued: ${permit.issueDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                    "Expires: ${permit.expiryDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color =
                            if (permit.status == "Active")
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            else Color(0xFFD32F2F),
                    fontWeight =
                            if (permit.status != "Active") FontWeight.Bold else FontWeight.Normal
            )

            Spacer(Modifier.height(8.dp))
            Box(
                    modifier =
                            Modifier.background(
                                            when (permit.status) {
                                                "Active" -> Color(0xFFE8F5E9)
                                                "Expired" -> Color(0xFFFFEBEE)
                                                else -> Color(0xFFFFF3E0)
                                            },
                                            RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                        permit.status,
                        style = MaterialTheme.typography.labelSmall,
                        color =
                                when (permit.status) {
                                    "Active" -> Color(0xFF2E7D32)
                                    "Expired" -> Color(0xFFC62828)
                                    else -> Color(0xFFEF6C00)
                                }
                )
            }
        }
    }
}
