package com.emarketing_paradice.gnsrilanka.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.data.model.User
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.ui.theme.PreviewData
import com.emarketing_paradice.gnsrilanka.viewmodel.*

@Composable
fun HomeScreen(
        citizenViewModel: CitizenViewModel,
        householdViewModel: HouseholdViewModel,
        requestViewModel: RequestViewModel,
        authViewModel: AuthViewModel,
        globalSearchViewModel: GlobalSearchViewModel,
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
        val officerProfile by authViewModel.officerProfile.collectAsState()

        HomeContent(
                citizenState = citizenState,
                householdState = householdState,
                requestState = requestState,
                currentUser = currentUser,
                officerProfile = officerProfile,
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

        val scrollState = rememberScrollState()
        val headerHeight = 280.dp
        val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }

        // Calculate header visibility/collapse based on scroll
        val headerAlpha = (1f - (scrollState.value / (headerHeightPx * 0.6f))).coerceIn(0f, 1f)
        val headerOffset = -(scrollState.value * 0.5f).coerceAtMost(headerHeightPx)

        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                // --- Collapsing Header Background ---
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(headerHeight)
                                        .offset(
                                                y =
                                                        with(LocalDensity.current) {
                                                                headerOffset.toDp()
                                                        }
                                        )
                ) {
                        Image(
                                painter = painterResource(id = R.drawable.bg_home_patterns),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier =
                                        Modifier.fillMaxSize()
                                                .clip(
                                                        RoundedCornerShape(
                                                                bottomStart = 32.dp,
                                                                bottomEnd = 32.dp
                                                        )
                                                )
                        )

                        // Gradient Overlay for readability
                        Box(
                                modifier =
                                        Modifier.fillMaxSize()
                                                .background(
                                                        Brush.verticalGradient(
                                                                colors =
                                                                        listOf(
                                                                                Color.Black.copy(
                                                                                        alpha = 0.4f
                                                                                ),
                                                                                Color.Transparent
                                                                        )
                                                        )
                                                )
                        )

                        Column(
                                modifier =
                                        Modifier.fillMaxSize()
                                                .statusBarsPadding()
                                                .padding(horizontal = 24.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                        ) {
                                // Top Bar Content
                                Row(
                                        modifier =
                                                Modifier.fillMaxWidth().graphicsLayer {
                                                        alpha = headerAlpha
                                                },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                        modifier =
                                                                Modifier.size(52.dp)
                                                                        .clip(
                                                                                RoundedCornerShape(
                                                                                        16.dp
                                                                                )
                                                                        )
                                                                        .background(
                                                                                Color.White.copy(
                                                                                        alpha = 0.2f
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
                                                                modifier = Modifier.size(30.dp)
                                                        )
                                                }
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column {
                                                        Text(
                                                                text = officerProfile?.gnDivision
                                                                                ?: "Grama Niladhari",
                                                                color =
                                                                        Color.White.copy(
                                                                                alpha = 0.8f
                                                                        ),
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.Medium
                                                        )
                                                        Text(
                                                                "Welcome, $userName",
                                                                color = Color.White,
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Bold
                                                        )
                                                }
                                        }
                                        IconButton(
                                                onClick = onOpenDrawer,
                                                modifier =
                                                        Modifier.size(44.dp)
                                                                .clip(RoundedCornerShape(12.dp))
                                                                .background(
                                                                        Color.White.copy(
                                                                                alpha = 0.15f
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
                                                        contentDescription = "Notifications",
                                                        tint = Color.White,
                                                        modifier = Modifier.size(22.dp)
                                                )
                                        }
                                }

                                // Search Bar - Anchored near bottom of header
                                OutlinedTextField(
                                        value = searchText,
                                        onValueChange = { searchText = it },
                                        placeholder = {
                                                Text(
                                                        "Search Name or NIC...",
                                                        color =
                                                                MaterialTheme.colorScheme.onSurface
                                                                        .copy(0.6f),
                                                        style = MaterialTheme.typography.bodyLarge
                                                )
                                        },
                                        modifier =
                                                Modifier.fillMaxWidth()
                                                        .padding(bottom = 8.dp)
                                                        .shadow(
                                                                12.dp,
                                                                RoundedCornerShape(16.dp),
                                                                spotColor = Color.Black.copy(0.3f)
                                                        ),
                                        shape = RoundedCornerShape(16.dp),
                                        colors =
                                                OutlinedTextFieldDefaults.colors(
                                                        focusedContainerColor =
                                                                MaterialTheme.colorScheme.surface
                                                                        .copy(0.95f),
                                                        unfocusedContainerColor =
                                                                MaterialTheme.colorScheme.surface
                                                                        .copy(0.9f),
                                                        focusedTextColor =
                                                                MaterialTheme.colorScheme.onSurface,
                                                        unfocusedTextColor =
                                                                MaterialTheme.colorScheme.onSurface,
                                                        focusedBorderColor =
                                                                MaterialTheme.colorScheme.primary,
                                                        unfocusedBorderColor = Color.Transparent,
                                                        cursorColor =
                                                                MaterialTheme.colorScheme.primary
                                                ),
                                        trailingIcon = {
                                                IconButton(
                                                        onClick = {
                                                                if (searchText.isNotBlank()) {
                                                                        onSearch(searchText)
                                                                        onNavigateToGlobalSearch()
                                                                }
                                                        }
                                                ) {
                                                        Icon(
                                                                painter =
                                                                        painterResource(
                                                                                id =
                                                                                        R.drawable
                                                                                                .ic_solar_magnifer
                                                                        ),
                                                                contentDescription = "Search",
                                                                tint =
                                                                        MaterialTheme.colorScheme
                                                                                .primary
                                                        )
                                                }
                                        },
                                        keyboardOptions =
                                                KeyboardOptions(imeAction = ImeAction.Search),
                                        keyboardActions =
                                                KeyboardActions(
                                                        onSearch = {
                                                                if (searchText.isNotBlank()) {
                                                                        onSearch(searchText)
                                                                        onNavigateToGlobalSearch()
                                                                }
                                                        }
                                                ),
                                        singleLine = true
                                )
                        }
                }

                // --- Scrollable Content ---
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .verticalScroll(scrollState)
                                        .padding(
                                                top = headerHeight - 16.dp
                                        ) // Slight overlap for rounded look
                                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {
                        Text(
                                text = "Overview",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
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
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                )
                                StatCard(
                                        title = "Households",
                                        count = householdState.households.size.toString(),
                                        icon = R.drawable.ic_solar_home_smile,
                                        modifier = Modifier.weight(1f),
                                        color =
                                                MaterialTheme.colorScheme.secondary.copy(
                                                        alpha = 0.1f
                                                )
                                )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        StatCard(
                                title = "Pending Service Requests",
                                count =
                                        requestState
                                                .requests
                                                .count {
                                                        it.status ==
                                                                com.emarketing_paradice.gnsrilanka
                                                                        .data.model.RequestStatus
                                                                        .Pending
                                                }
                                                .toString(),
                                icon = R.drawable.ic_solar_documents,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        SectionHeader("Official Registries", onViewAll = null)
                        Spacer(modifier = Modifier.height(16.dp))

                        LazyRow(
                                modifier = Modifier.fillMaxWidth(),
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

                        SectionHeader("Management", onViewAll = null)
                        Spacer(modifier = Modifier.height(16.dp))

                        ActionGrid(
                                onNavigateToCitizens = onNavigateToCitizens,
                                onNavigateToHouseholds = onNavigateToHouseholds,
                                onNavigateToRequests = onNavigateToRequests,
                                onNavigateToProfile = onNavigateToProfile
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        SectionHeader("Recent Activity", onViewAll = onNavigateToRequests)
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors =
                                        CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                        val recentRequests = requestState.requests.take(3)
                                        if (recentRequests.isEmpty()) {
                                                EmptyStateItem("No recent requests available")
                                        } else {
                                                recentRequests.forEachIndexed { index, request ->
                                                        RecentRequestItem(
                                                                request = request,
                                                                onClick = onNavigateToRequests
                                                        )
                                                        if (index < recentRequests.size - 1) {
                                                                HorizontalDivider(
                                                                        modifier =
                                                                                Modifier.padding(
                                                                                        horizontal =
                                                                                                12.dp
                                                                                ),
                                                                        thickness = 0.5.dp,
                                                                        color =
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .outlineVariant
                                                                )
                                                        }
                                                }
                                        }
                                }
                        }
                        Spacer(modifier = Modifier.height(100.dp)) // Extra space for bottom bar
                }
        }
}

@Composable
fun SectionHeader(title: String, onViewAll: (() -> Unit)? = null) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
                Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                )
                if (onViewAll != null) {
                        TextButton(onClick = onViewAll) {
                                Text(
                                        "View All",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                )
                        }
                }
        }
}

@Composable
fun EmptyStateItem(message: String) {
        Box(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                contentAlignment = Alignment.Center
        ) {
                Text(
                        text = message,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                )
        }
}

@Composable
fun RegistryCard(label: String, icon: Int, onClick: () -> Unit) {
        Card(
                modifier =
                        Modifier.width(140.dp)
                                .height(110.dp)
                                .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        spotColor = Color(0x1A000000)
                                )
                                .clickable { onClick() },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
                Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Box(
                                modifier =
                                        Modifier.size(40.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(
                                                        MaterialTheme.colorScheme.surfaceVariant
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(22.dp)
                                )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                                text = label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                        )
                }
        }
}

@Composable
fun StatCard(title: String, count: String, icon: Int, color: Color, modifier: Modifier = Modifier) {
        Card(
                modifier =
                        modifier.shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                spotColor = Color(0x1A000000)
                        ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                        text = count,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                        text = title,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                                "Households",
                                R.drawable.ic_solar_home_smile,
                                Modifier.weight(1f),
                                onNavigateToHouseholds
                        )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        ActionCard(
                                "Service Requests",
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
                                        spotColor = Color(0x0D000000)
                                )
                                .clickable { onClick() },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                                                .background(
                                                        MaterialTheme.colorScheme.surfaceVariant
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                                text = label,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                        )
                }
        }
}

@Composable
fun RecentRequestItem(
        request: com.emarketing_paradice.gnsrilanka.data.model.Request,
        onClick: () -> Unit
) {
        Row(
                modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
                Box(
                        modifier =
                                Modifier.size(40.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                ) {
                        Icon(
                                painter = painterResource(id = R.drawable.ic_solar_documents),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                        )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                        Text(
                                text = request.certificateType,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                                text = request.status.name,
                                fontSize = 12.sp,
                                color =
                                        when (request.status) {
                                                com.emarketing_paradice.gnsrilanka.data.model
                                                        .RequestStatus.Approved -> Color(0xFF10B981)
                                                com.emarketing_paradice.gnsrilanka.data.model
                                                        .RequestStatus.Rejected -> Color(0xFFEF4444)
                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                        )
                }
                Icon(
                        painter = painterResource(id = R.drawable.ic_solar_alt_arrow_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                )
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
