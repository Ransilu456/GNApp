package com.emarketing_paradice.gnsrilanka.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.viewmodel.*

// --- Theme Colors ---
val GnPrimary = Color(0xFF0014A8)
val GnBackground = Color(0xFFF4F7FE)
val GnSurface = Color.White
val GnTextPrimary = Color(0xFF1E293B)
val GnTextSecondary = Color(0xFF94A3B8)
val GnAccent = Color(0xFFFFB300)

@Composable
fun HomeScreen(
        citizenViewModel: CitizenViewModel,
        householdViewModel: HouseholdViewModel,
        requestViewModel: RequestViewModel,
        authViewModel: AuthViewModel,
        onNavigateToCitizens: () -> Unit,
        onNavigateToHouseholds: () -> Unit,
        onNavigateToRequests: () -> Unit,
        onNavigateToProfile: () -> Unit,
        onOpenDrawer: () -> Unit
) {
    val citizenState by citizenViewModel.uiState.collectAsState()
    val householdState by householdViewModel.uiState.collectAsState()
    val requestState by requestViewModel.uiState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    // Find the current user's name from citizen list if available
    val userName =
            remember(currentUser, citizenState.citizens) {
                citizenState
                        .citizens
                        .find { it.nic == currentUser?.nic }
                        ?.fullName
                        ?.split(" ")
                        ?.firstOrNull()
                        ?: "Officer"
            }

    Scaffold(
            containerColor = GnBackground,
            topBar = {
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(180.dp)
                                        .background(
                                                Brush.verticalGradient(
                                                        colors =
                                                                listOf(GnPrimary, Color(0xFF000B5E))
                                                )
                                        )
                ) {
                    Column(
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                        "Welcome back,",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 16.sp
                                )
                                Text(
                                        userName,
                                        color = Color.White,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Black
                                )
                            }
                            IconButton(
                                    onClick = onOpenDrawer,
                                    modifier =
                                            Modifier.clip(RoundedCornerShape(12.dp))
                                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(
                                        Icons.Default.Menu,
                                        contentDescription = "Menu",
                                        tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
    ) { padding ->
        Column(
                modifier =
                        Modifier.padding(padding)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp)
        ) {
            // Stats Row
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                        title = "Citizens",
                        count = citizenState.citizens.size.toString(),
                        icon = Icons.Default.People,
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFE0F2FE)
                )
                StatCard(
                        title = "Houses",
                        count = householdState.households.size.toString(),
                        icon = Icons.Default.Home,
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFFFEDD5)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            StatCard(
                    title = "Pending Service Requests",
                    count = requestState.requests.size.toString(),
                    icon = Icons.Default.Assessment,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFE0E7FF),
                    isFullWidth = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                    "Quick Actions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = GnTextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Grid
            ActionGrid(
                    onNavigateToCitizens = onNavigateToCitizens,
                    onNavigateToHouseholds = onNavigateToHouseholds,
                    onNavigateToRequests = onNavigateToRequests,
                    onNavigateToProfile = onNavigateToProfile
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatCard(
        title: String,
        count: String,
        icon: ImageVector,
        color: Color,
        modifier: Modifier = Modifier,
        isFullWidth: Boolean = false
) {
    Box(
            modifier =
                    modifier.shadow(
                                    elevation = 12.dp,
                                    shape = RoundedCornerShape(24.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.05f)
                            )
                            .background(Color.White, RoundedCornerShape(24.dp))
                            .padding(20.dp)
    ) {
        Column {
            Box(
                    modifier =
                            Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(color),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        icon,
                        contentDescription = null,
                        tint = GnPrimary.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(count, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = GnTextPrimary)
            Text(title, fontSize = 12.sp, color = GnTextSecondary, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ActionGrid(
        onNavigateToCitizens: () -> Unit,
        onNavigateToHouseholds: () -> Unit,
        onNavigateToRequests: () -> Unit,
        onNavigateToProfile: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionCard("Citizens", Icons.Default.Group, Modifier.weight(1f), onNavigateToCitizens)
            ActionCard(
                    "Households",
                    Icons.Default.Domain,
                    Modifier.weight(1f),
                    onNavigateToHouseholds
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionCard(
                    "Service Requests",
                    Icons.Default.Description,
                    Modifier.weight(1f),
                    onNavigateToRequests
            )
            ActionCard(
                    "My Profile",
                    Icons.Default.AccountCircle,
                    Modifier.weight(1f),
                    onNavigateToProfile
            )
        }
    }
}

@Composable
fun ActionCard(
        label: String,
        icon: ImageVector,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
) {
    Box(
            modifier =
                    modifier
                            .clickable { onClick() }
                            .shadow(
                                    4.dp,
                                    RoundedCornerShape(20.dp),
                                    ambientColor = Color.Black.copy(0.02f)
                            )
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .padding(16.dp),
            contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = GnPrimary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = GnTextPrimary)
        }
    }
}
