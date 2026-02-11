package com.emarketing_paradice.gnsrilanka.ui.components.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Screen.Home,
        Screen.CitizenList,
        Screen.HouseholdList,
        Screen.RequestList,
        Screen.Profile
    )

    NavigationBar {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            screen.icon?.let {
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigate(screen.route) },
                    icon = { Icon(it, contentDescription = screen.title) },
                    label = { Text(screen.title) },
                )
            }
        }
    }
}
