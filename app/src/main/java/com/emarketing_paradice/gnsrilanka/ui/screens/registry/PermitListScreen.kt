package com.emarketing_paradice.gnsrilanka.ui.screens.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Security
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
import com.emarketing_paradice.gnsrilanka.data.model.Permit
import com.emarketing_paradice.gnsrilanka.data.model.PermitType
import com.emarketing_paradice.gnsrilanka.ui.components.common.EmptyContent
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermitListScreen(viewModel: GNRegistryViewModel, onAddPermit: () -> Unit) {
        val permits by viewModel.permits.collectAsState()
        var searchQuery by remember { mutableStateOf("") }
        var selectedType by remember { mutableStateOf<PermitType?>(null) } // Filter by Type

        val filteredPermits =
                permits.filter { permit ->
                        val matchesSearch =
                                permit.applicantName.contains(searchQuery, ignoreCase = true) ||
                                        permit.type.name.contains(searchQuery, ignoreCase = true)
                        val matchesType = selectedType == null || permit.type == selectedType
                        matchesSearch && matchesType
                }

        Scaffold(
                floatingActionButton = {
                        FloatingActionButton(
                                onClick = onAddPermit,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                        ) { Icon(Icons.Default.Add, contentDescription = "Add Permit") }
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
                                                "Search permits...",
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
                                                selected = selectedType == null,
                                                onClick = { selectedType = null },
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
                                                                selected = selectedType == null,
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
                                items(PermitType.values().toList()) { type ->
                                        FilterChip(
                                                selected = selectedType == type,
                                                onClick = { selectedType = type },
                                                label = {
                                                        Text(
                                                                type.displayName(),
                                                                style =
                                                                        MaterialTheme.typography
                                                                                .labelMedium
                                                        )
                                                },
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
                                                                selected = selectedType == type,
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

                        if (filteredPermits.isEmpty()) {
                                Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                ) {
                                        EmptyContent(
                                                if (searchQuery.isEmpty() && selectedType == null)
                                                        "No permits found"
                                                else "No permits match your filters",
                                                Icons.AutoMirrored.Filled.Assignment
                                        )
                                }
                        } else {
                                LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        contentPadding = PaddingValues(bottom = 80.dp)
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
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                                                                        PermitType.BUSINESS ->
                                                                                Color(0xFFE3F2FD)
                                                                        PermitType.FIREARM ->
                                                                                Color(0xFFFFECB3)
                                                                        PermitType
                                                                                .RESOURCE_TRANSPORT ->
                                                                                Color(0xFFE8F5E9)
                                                                }
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Icon(
                                                when (permit.type) {
                                                        PermitType.BUSINESS ->
                                                                Icons.Default.Business
                                                        PermitType.FIREARM -> Icons.Default.Security
                                                        PermitType.RESOURCE_TRANSPORT ->
                                                                Icons.Default.LocalShipping
                                                },
                                                contentDescription = null,
                                                tint =
                                                        when (permit.type) {
                                                                PermitType.BUSINESS ->
                                                                        Color(0xFF1976D2)
                                                                PermitType.FIREARM ->
                                                                        Color(0xFFF57C00)
                                                                PermitType.RESOURCE_TRANSPORT ->
                                                                        Color(0xFF388E3C)
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
                                                                        PermitType.BUSINESS ->
                                                                                Color(0xFFE3F2FD)
                                                                        PermitType.FIREARM ->
                                                                                Color(0xFFFFECB3)
                                                                        PermitType
                                                                                .RESOURCE_TRANSPORT ->
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
                                                                PermitType.BUSINESS ->
                                                                        Color(0xFF1976D2)
                                                                PermitType.FIREARM ->
                                                                        Color(0xFFF57C00)
                                                                PermitType.RESOURCE_TRANSPORT ->
                                                                        Color(0xFF388E3C)
                                                        }
                                        )
                                }
                        }

                        Spacer(Modifier.height(12.dp))
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                                Text(
                                        "NIC: ${permit.nic}",
                                        style = MaterialTheme.typography.bodySmall
                                )
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
                                        if (permit.status != "Active") FontWeight.Bold
                                        else FontWeight.Normal
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
