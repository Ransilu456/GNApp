package com.emarketing_paradice.gnsrilanka.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.R

import com.emarketing_paradice.gnsrilanka.data.model.Citizen
import com.emarketing_paradice.gnsrilanka.data.model.User
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.*

// --- Theme Colors ---
val GnPrimary = Color(0xFF0014A8)
val GnBackground = Color(0xFFF4F7FE)
val GnTextPrimary = Color(0xFF1E293B)
val GnTextSecondary = Color(0xFF94A3B8)

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

    HomeContent(
            citizenState = citizenState,
            householdState = householdState,
            requestState = requestState,
            currentUser = currentUser,
            onNavigateToCitizens = onNavigateToCitizens,
            onNavigateToHouseholds = onNavigateToHouseholds,
            onNavigateToRequests = onNavigateToRequests,
            onNavigateToProfile = onNavigateToProfile,
            onOpenDrawer = onOpenDrawer
    )
}

@Composable
fun HomeContent(
        citizenState: CitizenUiState,
        householdState: HouseholdUiState,
        requestState: RequestUiState,
        currentUser: User?,
        onNavigateToCitizens: () -> Unit,
        onNavigateToHouseholds: () -> Unit,
        onNavigateToRequests: () -> Unit,
        onNavigateToProfile: () -> Unit,
        onOpenDrawer: () -> Unit
) {
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
                                        .height(140.dp)
                                        .background(
                                                Brush.verticalGradient(
                                                        colors =
                                                                listOf(GnPrimary, Color(0xFF000B5E))
                                                )
                                        )
                ) {
                    Row(
                            modifier =
                                    Modifier.fillMaxSize()
                                            .padding(horizontal = 24.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                    "Welcome back,",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                            )
                            Text(
                                    userName,
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(
                                onClick = onOpenDrawer,
                                modifier =
                                        Modifier.size(44.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color.White.copy(alpha = 0.15f))
                        ) {
                            Icon(
                                    painter = painterResource(id = R.drawable.ic_solar_bell),
                                    contentDescription = "Notifications",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                            )
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
                        icon = R.drawable.ic_solar_users_group,
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFEEF2FF)
                )
                StatCard(
                        title = "Houses",
                        count = householdState.households.size.toString(),
                        icon = R.drawable.ic_solar_home_smile,
                        modifier = Modifier.weight(1f),
                        color = Color(0xFFF0FDF4)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            StatCard(
                    title = "Pending Service Requests",
                    count = requestState.requests.size.toString(),
                    icon = R.drawable.ic_solar_documents,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFF7ED),
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
        icon: Int,
        color: Color,
        modifier: Modifier = Modifier,
        isFullWidth: Boolean = false
) {
    Card(
            modifier =
                    modifier.shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = Color(0x1A000000),
                            ambientColor = Color(0x1A000000)
                    ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Handled by shadow
    ) {
        Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
        ) {
            Box(
                    modifier =
                            Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)).background(color),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = GnPrimary,
                        modifier = Modifier.size(24.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                        text = count,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = GnTextPrimary
                )
                Text(
                        text = title,
                        fontSize = 14.sp,
                        color = GnTextSecondary,
                        fontWeight = FontWeight.Medium
                )
            }
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
            ActionCard(
                    "Citizens",
                    R.drawable.ic_solar_users_group,
                    Modifier.weight(1f),
                    onNavigateToCitizens
            )
            ActionCard(
                    "Houses",
                    R.drawable.ic_solar_home_smile,
                    Modifier.weight(1f),
                    onNavigateToHouseholds
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionCard(
                    "Requests",
                    R.drawable.ic_solar_documents,
                    Modifier.weight(1f),
                    onNavigateToRequests
            )
            ActionCard(
                    "My Profile",
                    R.drawable.ic_solar_settings,
                    Modifier.weight(1f),
                    onNavigateToProfile
            )
        }
    }
}

@Composable
fun ActionCard(label: String, icon: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
            modifier =
                    modifier.shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    spotColor = Color(0x0D000000),
                                    ambientColor = Color(0x0D000000)
                            )
                            .clickable { onClick() },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
        ) {
            Box(
                    modifier =
                            Modifier.size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(GnBackground),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = GnPrimary,
                        modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                    text = label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GnTextPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GNAppTheme {
        HomeContent(
                citizenState =
                        CitizenUiState(
                                citizens =
                                        listOf(
                                                Citizen(
                                                        "123456789V",
                                                        "John Doe",
                                                        "1990-01-01",
                                                        "Male",
                                                        "Engineer",
                                                        "H001",
                                                        "No 1, Main St"
                                                )
                                        )
                        ),
                householdState = HouseholdUiState(households = listOf()),
                requestState = RequestUiState(requests = listOf()),
                currentUser = User("123456789V", "password"),
                onNavigateToCitizens = {},
                onNavigateToHouseholds = {},
                onNavigateToRequests = {},
                onNavigateToProfile = {},
                onOpenDrawer = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun StatCardsPreview() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard(
                    title = "Citizens",
                    count = "1,234",
                    icon = R.drawable.ic_solar_users_group,
                    color = Color(0xFFEEF2FF),
                    modifier = Modifier.weight(1f)
            )
            StatCard(
                    title = "Houses",
                    count = "450",
                    icon = R.drawable.ic_solar_home_smile,
                    color = Color(0xFFF0FDF4),
                    modifier = Modifier.weight(1f)
            )
        }
        StatCard(
                title = "Requests",
                count = "12",
                icon = R.drawable.ic_solar_documents,
                color = Color(0xFFFFF7ED),
                modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun ActionGridPreview() {
    Box(modifier = Modifier.padding(16.dp)) { ActionGrid({}, {}, {}, {}) }
}
