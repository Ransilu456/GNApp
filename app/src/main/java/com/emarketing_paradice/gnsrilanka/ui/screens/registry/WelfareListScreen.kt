package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.data.model.WelfareProgram
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelfareListScreen(viewModel: GNRegistryViewModel, onAddWelfare: () -> Unit) {
        val programs by viewModel.welfarePrograms.collectAsState()
        var searchQuery by remember { mutableStateOf("") }
        var selectedStatus by remember { mutableStateOf("All") }

        val filteredPrograms =
                programs.filter {
                        val matchesSearch =
                                searchQuery.isEmpty() ||
                                        it.beneficiaryName.contains(
                                                searchQuery,
                                                ignoreCase = true
                                        ) ||
                                        it.nic.contains(searchQuery, ignoreCase = true) ||
                                        it.programName.contains(searchQuery, ignoreCase = true)

                        val matchesStatus = selectedStatus == "All" || it.status == selectedStatus

                        matchesSearch && matchesStatus
                }

        Column(modifier = Modifier.fillMaxSize()) {
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
                                        placeholder = {
                                                Text("Search by name, NIC, or program...")
                                        },
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

                                // Status Filter Chips
                                LazyRow(
                                        modifier =
                                                Modifier.fillMaxWidth()
                                                        .padding(
                                                                horizontal = 16.dp,
                                                                vertical = 8.dp
                                                        ),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                        val statuses =
                                                listOf("All", "Active", "Pending", "Discontinued")
                                        items(statuses) { status ->
                                                FilterChip(
                                                        selected = selectedStatus == status,
                                                        onClick = { selectedStatus = status },
                                                        label = {
                                                                Text(
                                                                        status,
                                                                        style =
                                                                                MaterialTheme
                                                                                        .typography
                                                                                        .labelMedium
                                                                )
                                                        },
                                                        colors =
                                                                FilterChipDefaults.filterChipColors(
                                                                        selectedContainerColor =
                                                                                BlueGradientStart,
                                                                        selectedLabelColor =
                                                                                Color.White,
                                                                        containerColor =
                                                                                Color.Transparent,
                                                                        labelColor =
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onSurfaceVariant
                                                                ),
                                                        border =
                                                                FilterChipDefaults.filterChipBorder(
                                                                        enabled = true,
                                                                        selected =
                                                                                selectedStatus ==
                                                                                        status,
                                                                        borderColor =
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .outline
                                                                                        .copy(
                                                                                                alpha =
                                                                                                        0.3f
                                                                                        ),
                                                                        selectedBorderColor =
                                                                                Color.Transparent
                                                                )
                                                )
                                        }
                                }
                        }
                }

                if (filteredPrograms.isEmpty()) {
                        Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                        ) {
                                EmptyContent(
                                        if (searchQuery.isEmpty() && selectedStatus == "All")
                                                "No welfare programs found"
                                        else "No programs match your filters",
                                        Icons.Default.CardGiftcard
                                )
                        }
                } else {
                        LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(16.dp)
                        ) { items(filteredPrograms) { program -> WelfareItem(program) } }
                }
        }
}

@Composable
fun WelfareItem(program: WelfareProgram) {
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
                                                                MaterialTheme.colorScheme.secondary
                                                                        .copy(0.1f)
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Icon(
                                                Icons.Default.Star,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.secondary
                                        )
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                        Text(
                                                program.beneficiaryName,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                                program.programName,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.primary
                                        )
                                }
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                                Text(
                                        "NIC: ${program.nic}",
                                        style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                        "Amount: LKR ${program.benefitAmount}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                                "Address: ${program.householdAddress}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.height(8.dp))
                        Box(
                                modifier =
                                        Modifier.background(
                                                        if (program.status == "Active")
                                                                Color(0xFFE8F5E9)
                                                        else Color(0xFFFFF3E0),
                                                        RoundedCornerShape(8.dp)
                                                )
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                                Text(
                                        program.status,
                                        style = MaterialTheme.typography.labelSmall,
                                        color =
                                                if (program.status == "Active") Color(0xFF2E7D32)
                                                else Color(0xFFEF6C00)
                                )
                        }
                }
        }
}
