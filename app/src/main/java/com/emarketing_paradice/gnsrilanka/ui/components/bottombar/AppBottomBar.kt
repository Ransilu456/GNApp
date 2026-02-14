package com.emarketing_paradice.gnsrilanka.ui.components.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen

@Composable
fun AppBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
        val items =
                listOf(
                        Screen.Home,
                        Screen.CitizenList,
                        Screen.HouseholdList,
                        Screen.RequestList,
                        Screen.Profile
                )

        Surface(
                modifier =
                        Modifier.fillMaxWidth()
                                .shadow(
                                        elevation = 12.dp,
                                        shape =
                                                RoundedCornerShape(
                                                        topStart = 24.dp,
                                                        topEnd = 24.dp
                                                ),
                                        ambientColor = Color.Black.copy(alpha = 0.1f),
                                        spotColor = Color(0xFF0014A8).copy(alpha = 0.1f)
                                )
                                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                color = Color.White,
                tonalElevation = 0.dp
        ) {
                NavigationBar(
                        containerColor = Color.Transparent,
                        modifier = Modifier.height(80.dp),
                        tonalElevation = 0.dp
                ) {
                        items.forEach { screen ->
                                val isSelected = currentRoute == screen.route
                                screen.icon?.let { icon ->
                                        NavigationBarItem(
                                                selected = isSelected,
                                                onClick = { onNavigate(screen.route) },
                                                icon = {
                                                        Icon(
                                                                painter =
                                                                        painterResource(id = icon),
                                                                contentDescription = screen.title,
                                                        )
                                                },
                                                label = {
                                                        Text(
                                                                text = screen.title,
                                                                fontSize = 11.sp,
                                                                fontWeight =
                                                                        if (isSelected)
                                                                                FontWeight.Bold
                                                                        else FontWeight.Medium,
                                                                letterSpacing = 0.5.sp
                                                        )
                                                },
                                                colors =
                                                        NavigationBarItemDefaults.colors(
                                                                selectedIconColor =
                                                                        Color(0xFF0014A8),
                                                                selectedTextColor =
                                                                        Color(0xFF0014A8),
                                                                indicatorColor =
                                                                        Color(0xFF0014A8)
                                                                                .copy(alpha = 0.1f),
                                                                unselectedIconColor =
                                                                        Color(0xFF94A3B8),
                                                                unselectedTextColor =
                                                                        Color(0xFF94A3B8)
                                                        )
                                        )
                                }
                        }
                }
        }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
        com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme {
                AppBottomBar(currentRoute = Screen.Home.route, onNavigate = {})
        }
}
