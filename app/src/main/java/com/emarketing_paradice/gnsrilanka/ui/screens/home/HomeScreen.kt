package com.emarketing_paradice.gnsrilanka.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.User
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.ui.theme.PreviewData
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
        globalSearchViewModel: GlobalSearchViewModel, // Added
        onNavigateToCitizens: () -> Unit,
        onNavigateToHouseholds: () -> Unit,
        onNavigateToRequests: () -> Unit,
        onNavigateToProfile: () -> Unit,
        onNavigateToGlobalSearch: () -> Unit,
        onNavigateToLogs: () -> Unit,
        onNavigateToPermits: () -> Unit,
        onNavigateToWelfare: () -> Unit,
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
                officerProfile = authViewModel.officerProfile.collectAsState().value,
                onNavigateToCitizens = onNavigateToCitizens,
                onNavigateToHouseholds = onNavigateToHouseholds,
                onNavigateToRequests = onNavigateToRequests,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToGlobalSearch = onNavigateToGlobalSearch,
                onNavigateToLogs = onNavigateToLogs,
                onNavigateToPermits = onNavigateToPermits,
                onNavigateToWelfare = onNavigateToWelfare,
                onOpenDrawer = onOpenDrawer,
                isSearching = globalSearchViewModel.isSearching.collectAsState().value,
                onSearch = { query -> globalSearchViewModel.search(query) }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
        citizenState: CitizenUiState,
        householdState: HouseholdUiState,
        requestState: RequestUiState,
        currentUser: User?,
        officerProfile: com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile?,
        isSearching: Boolean,
        onSearch: (String) -> Unit,
        onNavigateToCitizens: () -> Unit,
        onNavigateToHouseholds: () -> Unit,
        onNavigateToRequests: () -> Unit,
        onNavigateToProfile: () -> Unit,
        onNavigateToGlobalSearch: () -> Unit,
        onNavigateToLogs: () -> Unit,
        onNavigateToPermits: () -> Unit,
        onNavigateToWelfare: () -> Unit,
        onOpenDrawer: () -> Unit
) {
        val userName = officerProfile?.officerName?.split(" ")?.firstOrNull() ?: "Officer"
        var searchText by remember { mutableStateOf("") }

        Scaffold(
                containerColor = GnBackground,
                topBar = {
                        Box(modifier = Modifier.fillMaxWidth()) {
                                // Header Background using SVG/Vector Drawable
                                Image(
                                        painter = painterResource(id = R.drawable.bg_home_patterns),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier =
                                                Modifier.fillMaxWidth()
                                                        .height(280.dp)
                                                        .clip(
                                                                RoundedCornerShape(
                                                                        bottomStart = 32.dp,
                                                                        bottomEnd = 32.dp
                                                                )
                                                        )
                                )

                                // Content Overlay
                                Column(
                                        modifier =
                                                Modifier.fillMaxSize()
                                                        .padding(
                                                                start = 24.dp,
                                                                end = 24.dp,
                                                                top = 48.dp
                                                        )
                                ) {
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                Row(
                                                        verticalAlignment =
                                                                Alignment.CenterVertically
                                                ) {
                                                        Box(
                                                                modifier =
                                                                        Modifier.size(56.dp)
                                                                                .clip(CircleShape)
                                                                                .background(
                                                                                        Color.White
                                                                                                .copy(
                                                                                                        alpha =
                                                                                                                0.2f
                                                                                                )
                                                                                ),
                                                                contentAlignment = Alignment.Center
                                                        ) {
                                                                Icon(
                                                                        painter =
                                                                                painterResource(
                                                                                        id =
                                                                                                R.drawable
                                                                                                        .ic_solar_user_circle
                                                                                ),
                                                                        contentDescription = null,
                                                                        tint = Color.White,
                                                                        modifier =
                                                                                Modifier.size(32.dp)
                                                                )
                                                        }

                                                        Spacer(modifier = Modifier.width(16.dp))

                                                        Column {
                                                                Text(
                                                                        text = "Welcome Back,",
                                                                        color =
                                                                                Color.White.copy(
                                                                                        alpha = 0.8f
                                                                                ),
                                                                        fontSize = 14.sp,
                                                                        fontWeight =
                                                                                FontWeight.Medium
                                                                )
                                                                Text(
                                                                        userName,
                                                                        color = Color.White,
                                                                        fontSize = 24.sp,
                                                                        fontWeight =
                                                                                FontWeight.Bold,
                                                                        letterSpacing = 0.5.sp
                                                                )
                                                        }
                                                }
                                                IconButton(
                                                        onClick = onOpenDrawer,
                                                        modifier =
                                                                Modifier.size(48.dp)
                                                                        .clip(
                                                                                RoundedCornerShape(
                                                                                        14.dp
                                                                                )
                                                                        )
                                                                        .background(
                                                                                Color.White.copy(
                                                                                        alpha =
                                                                                                0.15f
                                                                                )
                                                                        )
                                                ) {
                                                        Icon(
                                                                painter =
                                                                        painterResource(
                                                                                id =
                                                                                        R.drawable
                                                                                                .ic_solar_bell
                                                                        ),
                                                                contentDescription =
                                                                        "Notifications",
                                                                tint = Color.White,
                                                                modifier = Modifier.size(24.dp)
                                                        )
                                                }
                                        }

                                        Spacer(modifier = Modifier.height(32.dp))

                                        // Search Bar
                                        OutlinedTextField(
                                                value = searchText,
                                                onValueChange = { searchText = it },
                                                placeholder = {
                                                        Text(
                                                                "Search Name or NIC...",
                                                                color = Color.White.copy(0.7f)
                                                        )
                                                },
                                                modifier =
                                                        Modifier.fillMaxWidth()
                                                                .height(56.dp)
                                                                .shadow(
                                                                        8.dp,
                                                                        RoundedCornerShape(28.dp),
                                                                        spotColor =
                                                                                Color.Black.copy(
                                                                                        0.2f
                                                                                )
                                                                ),
                                                shape = RoundedCornerShape(28.dp),
                                                colors =
                                                        OutlinedTextFieldDefaults.colors(
                                                                focusedContainerColor =
                                                                        Color.White.copy(0.15f),
                                                                unfocusedContainerColor =
                                                                        Color.White.copy(0.15f),
                                                                focusedTextColor = Color.White,
                                                                unfocusedTextColor = Color.White,
                                                                focusedBorderColor =
                                                                        Color.White.copy(0.3f),
                                                                unfocusedBorderColor =
                                                                        Color.Transparent,
                                                                cursorColor = Color.White
                                                        ),
                                                trailingIcon = {
                                                        IconButton(
                                                                onClick = {
                                                                        onSearch(searchText)
                                                                        onNavigateToGlobalSearch()
                                                                }
                                                        ) {
                                                                Icon(
                                                                        painter =
                                                                                painterResource(
                                                                                        id =
                                                                                                R.drawable
                                                                                                        .ic_solar_magnifer
                                                                                ),
                                                                        contentDescription =
                                                                                "Search",
                                                                        tint = Color.White
                                                                )
                                                        }
                                                },
                                                singleLine = true
                                        )
                                }
                        }
                }
        ) { padding ->
                Column(
                        modifier =
                                Modifier.padding(paddingValues = padding)
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {
                        // Stats Row
                        Text(
                                text = "Overview",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = GnTextPrimary,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

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
                                        title = "Households",
                                        count = householdState.households.size.toString(),
                                        icon = R.drawable.ic_solar_home_smile,
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFFF0FDF4)
                                )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        StatCard(
                                title = "Pending Requests",
                                count = requestState.requests.size.toString(),
                                icon = R.drawable.ic_solar_documents,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color(0xFFFFF7ED),
                                isFullWidth = true
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                                text = "Official Registries",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = GnTextPrimary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Registry Actions
                        LazyRow(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                                item {
                                        RegistryCard(
                                                "Visitor Log",
                                                R.drawable.ic_registry_log,
                                                onNavigateToLogs
                                        )
                                }
                                item {
                                        RegistryCard(
                                                "Permits",
                                                R.drawable.ic_registry_permit,
                                                onNavigateToPermits
                                        )
                                }
                                item {
                                        RegistryCard(
                                                "Welfare",
                                                R.drawable.ic_registry_welfare,
                                                onNavigateToWelfare
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                                text = "General Management",
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

                        Spacer(modifier = Modifier.height(32.dp))
                }
        }
}

@Composable
fun RegistryCard(label: String, icon: Int, onClick: () -> Unit) {
        Card(
                modifier =
                        Modifier.width(160.dp)
                                .height(120.dp)
                                .shadow(
                                        elevation = 6.dp,
                                        shape = RoundedCornerShape(24.dp),
                                        spotColor = Color(0x1A000000)
                                )
                                .clickable { onClick() },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
                Column(
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Box(
                                modifier =
                                        Modifier.size(44.dp)
                                                .clip(RoundedCornerShape(14.dp))
                                                .background(GnBackground),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = null,
                                        tint = GnPrimary,
                                        modifier = Modifier.size(24.dp)
                                )
                        }
                        Text(
                                text = label,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = GnTextPrimary
                        )
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
                                        Modifier.size(48.dp)
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(color),
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
                                "Profile",
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
                        citizenState = CitizenUiState(citizens = PreviewData.sampleCitizens),
                        householdState =
                                HouseholdUiState(households = PreviewData.sampleHouseholds),
                        requestState = RequestUiState(requests = PreviewData.sampleRequests),
                        currentUser = PreviewData.sampleUser,
                        officerProfile = null,
                        isSearching = false,
                        onSearch = {},
                        onNavigateToCitizens = {},
                        onNavigateToHouseholds = {},
                        onNavigateToRequests = {},
                        onNavigateToProfile = {},
                        onNavigateToGlobalSearch = {},
                        onNavigateToLogs = {},
                        onNavigateToPermits = {},
                        onNavigateToWelfare = {},
                        onOpenDrawer = {}
                )
        }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun StatCardsPreview() {
        Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
