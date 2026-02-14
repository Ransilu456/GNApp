package com.emarketing_paradice.gnsrilanka.ui.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(onMenuClick: () -> Unit, title: String) {
    CenterAlignedTopAppBar(
            title = {
                Text(
                        text = title,
                        style =
                                MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                )
                )
            },
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* TODO: Notifications */}) {
                    Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    )
    )
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme {
        AppTopBar(onMenuClick = {}, title = "Home")
    }
}
