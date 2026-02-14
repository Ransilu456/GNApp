package com.emarketing_paradice.gnsrilanka.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel

// --- Theme Colors from premium design ---
val GnPrimary = Color(0xFF0014A8)
val GnBackground = Color(0xFFF4F7FE)
val GnSurface = Color.White
val GnTextPrimary = Color(0xFF1E293B)
val GnTextSecondary = Color(0xFF94A3B8)

@Composable
fun ProfileScreen(
        authViewModel: AuthViewModel,
        citizenViewModel: CitizenViewModel,
        onLogout: () -> Unit,
        onNavigateToEditProfile: () -> Unit
) {
        val currentUser by authViewModel.currentUser.collectAsState()
        val citizenState by citizenViewModel.uiState.collectAsState()

        // Find detailed citizen info for the logged-in user
        val citizenInfo =
                remember(currentUser, citizenState.citizens) {
                        citizenState.citizens.find { it.nic == currentUser?.nic }
                }

        ProfileScreenContent(
                fullName = citizenInfo?.fullName ?: "Officer",
                division = "North Division - Colombo", // Placeholder or fetch if available
                onLogout = onLogout,
                onNavigateToEditProfile = onNavigateToEditProfile
        )
}

@Composable
fun ProfileScreenContent(
        fullName: String,
        division: String,
        onLogout: () -> Unit,
        onNavigateToEditProfile: () -> Unit
) {
        Scaffold(containerColor = GnBackground) { padding ->
                Column(
                        modifier =
                                Modifier.padding(padding)
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                ) {
                        // Header Section with Cleaner Professional Style
                        Box(
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .height(220.dp)
                                                .background(
                                                        Brush.verticalGradient(
                                                                colors =
                                                                        listOf(
                                                                                GnPrimary,
                                                                                Color(0xFF000B5E)
                                                                        )
                                                        )
                                                )
                        ) {
                                Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                ) {
                                        // Profile Image Placeholder
                                        Surface(
                                                modifier = Modifier.size(100.dp),
                                                shape = RoundedCornerShape(32.dp),
                                                color = Color.White.copy(alpha = 0.2f),
                                                border =
                                                        BorderStroke(
                                                                1.dp,
                                                                Color.White.copy(alpha = 0.3f)
                                                        )
                                        ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                        Icon(
                                                                painter =
                                                                        painterResource(
                                                                                id =
                                                                                        R.drawable
                                                                                                .ic_solar_user_circle
                                                                        ),
                                                                contentDescription = null,
                                                                modifier = Modifier.size(48.dp),
                                                                tint = Color.White
                                                        )
                                                }
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                                text = fullName,
                                                color = Color.White,
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                                text = "Grama Niladhari Officer",
                                                color = Color.White.copy(alpha = 0.7f),
                                                fontSize = 14.sp
                                        )
                                }
                        }

                        // Info Section
                        Column(
                                modifier = Modifier.padding(horizontal = 24.dp).offset(y = (-40).dp)
                        ) {
                                ProfileDetailCard(
                                        items =
                                                listOf(
                                                        ProfileItem("Full Name", fullName),
                                                        ProfileItem("Division", division)
                                                )
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                        "Account Settings",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GnTextPrimary
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                SettingsActionItem(
                                        title = "Edit Profile Info",
                                        subtitle = "Update your personal details",
                                        icon =
                                                R.drawable
                                                        .ic_solar_user_id, // matching user identity
                                        onClick = onNavigateToEditProfile
                                )

                                SettingsActionItem(
                                        title = "Security",
                                        subtitle = "Manage password and authentication",
                                        icon = R.drawable.ic_solar_shield_check,
                                        onClick = {}
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Button(
                                        onClick = onLogout,
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        colors =
                                                ButtonDefaults.buttonColors(
                                                        containerColor = Color(0xFFFFF1F1)
                                                ),
                                        shape = RoundedCornerShape(16.dp),
                                        elevation = ButtonDefaults.buttonElevation(0.dp)
                                ) {
                                        Icon(
                                                painter =
                                                        painterResource(
                                                                id = R.drawable.ic_solar_logout
                                                        ),
                                                contentDescription = null,
                                                tint = Color(0xFFEF4444)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                                "Logout from System",
                                                color = Color(0xFFEF4444),
                                                fontWeight = FontWeight.Bold
                                        )
                                }

                                Spacer(modifier = Modifier.height(40.dp))
                        }
                }
        }
}

@Composable
fun ProfileDetailCard(items: List<ProfileItem>) {
        Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = GnSurface,
                shadowElevation = 8.dp
        ) {
                Column(modifier = Modifier.padding(24.dp)) {
                        items.forEachIndexed { index, item ->
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Column {
                                                Text(
                                                        item.label,
                                                        color = GnTextSecondary,
                                                        fontSize = 12.sp
                                                )
                                                Text(
                                                        item.value,
                                                        color = GnTextPrimary,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.Medium
                                                )
                                        }
                                }
                                if (index < items.size - 1) {
                                        HorizontalDivider(
                                                modifier = Modifier.padding(vertical = 12.dp),
                                                color = Color(0xFFF1F5F9)
                                        )
                                }
                        }
                }
        }
}

data class ProfileItem(val label: String, val value: String)

@Composable
fun SettingsActionItem(title: String, subtitle: String, icon: Int, onClick: () -> Unit) {
        Surface(
                onClick = onClick,
                modifier = Modifier.padding(bottom = 12.dp),
                shape = RoundedCornerShape(20.dp),
                color = GnSurface,
                // Using border/shadow might be nice here too, but consistent with design
                ) {
                Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Box(
                                modifier =
                                        Modifier.size(48.dp)
                                                .background(
                                                        Color(0xFFF1F5F9),
                                                        RoundedCornerShape(14.dp)
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = null,
                                        tint = GnPrimary,
                                        modifier = Modifier.size(24.dp)
                                )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                                Text(title, fontWeight = FontWeight.Bold, color = GnTextPrimary)
                                Text(subtitle, fontSize = 12.sp, color = GnTextSecondary)
                        }

                        Icon(
                                painter = painterResource(id = R.drawable.ic_solar_alt_arrow_right),
                                contentDescription = null,
                                tint = GnTextSecondary,
                                modifier = Modifier.size(20.dp)
                        )
                }
        }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
        GNAppTheme {
                ProfileScreenContent(
                        fullName = "John Doe-Officer",
                        division = "North Division - Colombo",
                        onLogout = {},
                        onNavigateToEditProfile = {}
                )
        }
}
