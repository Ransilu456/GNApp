package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.WelfareProgram
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelfareListScreen(viewModel: GNRegistryViewModel, onAddWelfare: () -> Unit) {
        val welfarePrograms by viewModel.welfarePrograms.collectAsState()
        var searchQuery by remember { mutableStateOf("") }
        var selectedStatus by remember { mutableStateOf<String?>(null) } // Filter by Status
        val statuses = listOf("Active", "Pending", "Discontinued")

        val filteredPrograms =
                welfarePrograms.filter { program ->
                        val matchesSearch =
                                program.beneficiaryName.contains(searchQuery, ignoreCase = true) ||
                                        program.nic.contains(searchQuery, ignoreCase = true) ||
                                        program.programName.contains(searchQuery, ignoreCase = true)
                        val matchesStatus =
                                selectedStatus == null || program.status == selectedStatus
                        matchesSearch && matchesStatus
                }

        Scaffold(
                floatingActionButton = {
                        FloatingActionButton(
                                onClick = onAddWelfare,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                        ) { Icon(Icons.Default.Add, contentDescription = "Add Welfare") }
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
                                                "Search welfare...",
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

                        // Filter Chips
                        LazyRow(
                                modifier = Modifier.padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                                item {
                                        FilterChip(
                                                selected = selectedStatus == null,
                                                onClick = { selectedStatus = null },
                                                label = { Text("All") },
                                                colors =
                                                        FilterChipDefaults.filterChipColors(
                                                                selectedContainerColor =
                                                                        BlueGradientStart,
                                                                selectedLabelColor =
                                                                        MaterialTheme.colorScheme
                                                                                .onPrimary,
                                                                containerColor = Color.Transparent,
                                                                labelColor =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant
                                                        ),
                                                border =
                                                        FilterChipDefaults.filterChipBorder(
                                                                enabled = true,
                                                                selected = selectedStatus == null,
                                                                borderColor =
                                                                        MaterialTheme.colorScheme
                                                                                .outline.copy(
                                                                                alpha = 0.3f
                                                                        ),
                                                                selectedBorderColor =
                                                                        Color.Transparent
                                                        )
                                        )
                                }
                                items(statuses) { status ->
                                        FilterChip(
                                                selected = selectedStatus == status,
                                                onClick = {
                                                        selectedStatus =
                                                                if (selectedStatus == status) null
                                                                else status
                                                },
                                                label = { Text(status) },
                                                colors =
                                                        FilterChipDefaults.filterChipColors(
                                                                selectedContainerColor =
                                                                        BlueGradientStart,
                                                                selectedLabelColor =
                                                                        MaterialTheme.colorScheme
                                                                                .onPrimary,
                                                                containerColor = Color.Transparent,
                                                                labelColor =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant
                                                        ),
                                                border =
                                                        FilterChipDefaults.filterChipBorder(
                                                                enabled = true,
                                                                selected = selectedStatus == status,
                                                                borderColor =
                                                                        MaterialTheme.colorScheme
                                                                                .outline.copy(
                                                                                alpha = 0.3f
                                                                        ),
                                                                selectedBorderColor =
                                                                        Color.Transparent
                                                        )
                                        )
                                }
                        }

                        if (filteredPrograms.isEmpty()) {
                                Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                ) {
                                        EmptyContent(
                                                if (searchQuery.isEmpty() && selectedStatus == null)
                                                        "No welfare programs found"
                                                else "No programs match your filters",
                                                Icons.AutoMirrored.Filled.Assignment
                                        )
                                }
                        } else {
                                LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        contentPadding = PaddingValues(bottom = 80.dp)
                                ) {
                                        items(filteredPrograms) { program ->
                                                WelfareItemCard(program)
                                        }
                                }
                        }
                }
        }
}

@Composable
fun WelfareItemCard(program: WelfareProgram) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
                Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                                program.programName,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                                "Beneficiary: ${program.beneficiaryName}",
                                                style = MaterialTheme.typography.bodySmall
                                        )
                                }
                                Text(
                                        "LKR ${program.benefitAmount}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                )
                        }

                        Spacer(Modifier.height(8.dp))
                        Text(
                                "NIC: ${program.nic}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                                "Address: ${program.householdAddress}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.height(8.dp))
                        Box(
                                modifier =
                                        Modifier.background(
                                                        when (program.status) {
                                                                "Active" -> Color(0xFFE8F5E9)
                                                                "Pending" -> Color(0xFFFFF3E0)
                                                                else -> Color(0xFFFFEBEE)
                                                        },
                                                        RoundedCornerShape(8.dp)
                                                )
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                                Text(
                                        program.status,
                                        style = MaterialTheme.typography.labelSmall,
                                        color =
                                                when (program.status) {
                                                        "Active" -> Color(0xFF2E7D32)
                                                        "Pending" -> Color(0xFFEF6C00)
                                                        else -> Color(0xFFC62828)
                                                }
                                )
                        }
                }
        }
}
