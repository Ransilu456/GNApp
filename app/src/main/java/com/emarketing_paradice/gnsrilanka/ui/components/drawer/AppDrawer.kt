package com.emarketing_paradice.gnsrilanka.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen

@Composable
fun AppDrawer(
        currentRoute: String?,
        officerProfile: com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile?,
        onNavigate: (String) -> Unit,
        onLogout: () -> Unit,
        closeDrawer: () -> Unit
) {
        val items =
                listOf(
                        Screen.Home,
                        Screen.CitizenList,
                        Screen.HouseholdList,
                        Screen.RequestList,
                        Screen.DailyLogList,
                        Screen.WelfareList,
                        Screen.PermitList,
                        Screen.Profile
                )

        ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
        ) {
                // Header
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(200.dp)
                                        .background(
                                                Brush.verticalGradient(
                                                        colors =
                                                                listOf(
                                                                        MaterialTheme.colorScheme
                                                                                .primary,
                                                                        MaterialTheme.colorScheme
                                                                                .primaryContainer
                                                                )
                                                )
                                        )
                                        .padding(24.dp)
                ) {
                        Column(modifier = Modifier.align(Alignment.BottomStart)) {
                                Box(
                                        modifier =
                                                Modifier.size(64.dp)
                                                        .clip(RoundedCornerShape(20.dp))
                                                        .background(
                                                                MaterialTheme.colorScheme.onPrimary
                                                                        .copy(alpha = 0.2f)
                                                        ),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Icon(
                                                painter =
                                                        painterResource(
                                                                id = R.drawable.ic_solar_user_circle
                                                        ),
                                                contentDescription = null,
                                                modifier = Modifier.size(32.dp),
                                                tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                }
                                Spacer(Modifier.height(16.dp))
                                Text(
                                        officerProfile?.officerName ?: "Digital Assistant",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                        officerProfile?.gnDivision ?: "Grama Niladhari - Sri Lanka",
                                        style = MaterialTheme.typography.bodySmall,
                                        color =
                                                MaterialTheme.colorScheme.onPrimary.copy(
                                                        alpha = 0.7f
                                                )
                                )
                        }
                }

                Spacer(Modifier.height(24.dp))

                // Navigation Items and Footer
                Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                        items.forEach { screen ->
                                screen.icon?.let { icon ->
                                        val isSelected = currentRoute == screen.route
                                        NavigationDrawerItem(
                                                icon = {
                                                        Icon(
                                                                painter =
                                                                        painterResource(id = icon),
                                                                contentDescription = screen.title,
                                                                tint =
                                                                        if (isSelected)
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .primary
                                                                        else
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onSurfaceVariant
                                                        )
                                                },
                                                label = {
                                                        Text(
                                                                screen.title,
                                                                fontWeight =
                                                                        if (isSelected)
                                                                                FontWeight.Bold
                                                                        else FontWeight.Medium,
                                                                color =
                                                                        if (isSelected)
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .primary
                                                                        else
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onSurface
                                                        )
                                                },
                                                selected = isSelected,
                                                onClick = {
                                                        onNavigate(screen.route)
                                                        closeDrawer()
                                                },
                                                colors =
                                                        NavigationDrawerItemDefaults.colors(
                                                                selectedContainerColor =
                                                                        MaterialTheme.colorScheme
                                                                                .primary.copy(
                                                                                alpha = 0.1f
                                                                        ),
                                                                unselectedContainerColor =
                                                                        Color.Transparent
                                                        ),
                                                modifier =
                                                        Modifier.padding(
                                                                horizontal = 12.dp,
                                                                vertical = 4.dp
                                                        )
                                        )
                                }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Footer with Logout
                        HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                color = MaterialTheme.colorScheme.outlineVariant
                        )
                        NavigationDrawerItem(
                                icon = {
                                        Icon(
                                                painter =
                                                        painterResource(
                                                                id = R.drawable.ic_solar_logout
                                                        ),
                                                contentDescription = "Logout",
                                                tint = MaterialTheme.colorScheme.error
                                        )
                                },
                                label = {
                                        Text(
                                                "Logout",
                                                color = MaterialTheme.colorScheme.error,
                                                fontWeight = FontWeight.Bold
                                        )
                                },
                                selected = false,
                                onClick = {
                                        closeDrawer()
                                        onLogout()
                                },
                                colors =
                                        NavigationDrawerItemDefaults.colors(
                                                unselectedContainerColor = Color.Transparent
                                        ),
                                modifier = Modifier.padding(12.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                }
        }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AppDrawerPreview() {
        com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme {
                AppDrawer(
                        currentRoute = Screen.Home.route,
                        officerProfile = null,
                        onNavigate = {},
                        onLogout = {},
                        closeDrawer = {}
                )
        }
}
