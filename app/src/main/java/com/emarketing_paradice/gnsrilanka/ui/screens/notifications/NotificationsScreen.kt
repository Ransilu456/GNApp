package com.emarketing_paradice.gnsrilanka.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    val gnPrimary = Color(0xFF0014A8)
    val gnBackground = Color(0xFFF4F7FE)

    Scaffold(
            containerColor = gnBackground,
            topBar = {
                CenterAlignedTopAppBar(
                        title = {
                            Text(
                                    text = stringResource(R.string.notifications),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                )
                            }
                        },
                        colors =
                                TopAppBarDefaults.centerAlignedTopAppBarColors(
                                        containerColor = Color.White
                                )
                )
            }
    ) { padding ->
        Column(
                modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NotificationItem(
                    title = "System Update",
                    message =
                            "The GN App has been updated to version 2.0 with per-officer profiles.",
                    time = "Just now",
                    icon = R.drawable.ic_solar_shield_check
            )
            NotificationItem(
                    title = "New Service Request",
                    message = "A new household registration request is pending for North Division.",
                    time = "2 hours ago",
                    icon = R.drawable.ic_solar_documents
            )
            NotificationItem(
                    title = "Profile Initialized",
                    message =
                            "Your officer profile (NIC: 1995xxxxxx) has been created successfully.",
                    time = "Yesterday",
                    icon = R.drawable.ic_solar_user_circle
            )
        }
    }
}

@Composable
fun NotificationItem(title: String, message: String, time: String, icon: Int) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                    modifier =
                            Modifier.size(48.dp)
                                    .background(Color(0xFFF0F4FF), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = Color(0xFF0014A8),
                        modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF1E293B)
                )
                Text(
                        text = message,
                        fontSize = 13.sp,
                        color = Color(0xFF64748B),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                        text = time,
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8),
                        fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
