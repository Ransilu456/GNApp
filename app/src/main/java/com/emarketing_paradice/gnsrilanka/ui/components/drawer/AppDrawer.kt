package com.emarketing_paradice.gnsrilanka.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen

@Composable
fun AppDrawer(
    currentRoute: String?,
    userName: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
    ) {
        // Drawer Header
        DrawerHeader(userName)

        Spacer(modifier = Modifier.height(12.dp))

        // Drawer Items
        DrawerItem(
            selected = currentRoute == Screen.Home.route,
            icon = Icons.Default.Home,
            label = "Home",
            onClick = { onNavigate(Screen.Home.route) }
        )
        DrawerItem(
            selected = currentRoute == Screen.CitizenList.route,
            icon = Icons.Default.Person,
            label = "Citizens",
            onClick = { onNavigate(Screen.CitizenList.route) }
        )
        DrawerItem(
            selected = currentRoute == Screen.HouseholdList.route,
            icon = Icons.Default.Domain,
            label = "Households",
            onClick = { onNavigate(Screen.HouseholdList.route) }
        )
        DrawerItem(
            selected = currentRoute == Screen.RequestList.route,
            icon = Icons.Default.Description,
            label = "Requests",
            onClick = { onNavigate(Screen.RequestList.route) }
        )
        DrawerItem(
            selected = currentRoute == Screen.Profile.route,
            icon = Icons.Default.AccountCircle,
            label = "Profile",
            onClick = { onNavigate(Screen.Profile.route) }
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
        
        DrawerItem(
            selected = false,
            icon = Icons.Default.ExitToApp,
            label = "Logout",
            onClick = onLogout,
            isLogout = true
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun DrawerHeader(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = userName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.1.sp
            )
            Text(
                text = "Grama Niladhari",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun DrawerItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    val textColor = if (isLogout) MaterialTheme.colorScheme.error else if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    val containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent

    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                )
            )
        },
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = null) },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = containerColor,
            unselectedContainerColor = Color.Transparent,
            selectedIconColor = textColor,
            unselectedIconColor = textColor,
            selectedTextColor = textColor,
            unselectedTextColor = textColor
        )
    )
}
