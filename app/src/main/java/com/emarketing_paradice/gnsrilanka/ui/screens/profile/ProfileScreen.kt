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
        val officerProfile by authViewModel.officerProfile.collectAsState()

        ProfileScreenContent(
                profile = officerProfile,
                onLogout = onLogout,
                onNavigateToEditProfile = onNavigateToEditProfile
        )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
        GNAppTheme {
                ProfileScreenContent(
                        profile =
                                com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile(
                                        officerName = "Demo Officer",
                                        gnDivision = "Colombo Central",
                                        officeAddress = "Regional Office, Colombo",
                                        contactInfo = "011-2345678",
                                        authenticationSettings = "Standard"
                                ),
                        onLogout = {},
                        onNavigateToEditProfile = {}
                )
        }
}

@Composable
fun ProfileScreenContent(
        profile: com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile?,
        onLogout: () -> Unit,
        onNavigateToEditProfile: () -> Unit
) {
        val fullName = profile?.officerName ?: "Officer"
        val division = profile?.gnDivision ?: "Assigned Division"

        Scaffold(containerColor = GnBackground) { padding ->
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(bottom = padding.calculateBottomPadding())
                ) {
                        // Header Section
                        Box(
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .height(260.dp)
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
                                        modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                ) {
                                        // Profile Image Placeholder
                                        Surface(
                                                modifier = Modifier.size(110.dp),
                                                shape = RoundedCornerShape(36.dp),
                                                color = Color.White.copy(alpha = 0.15f),
                                                border =
                                                        BorderStroke(
                                                                2.dp,
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
                                                                modifier = Modifier.size(60.dp),
                                                                tint = Color.White
                                                        )
                                                }
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))

                                        Text(
                                                text = fullName,
                                                color = Color.White,
                                                fontSize = 26.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                letterSpacing = 0.5.sp
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Surface(
                                                color = Color.White.copy(alpha = 0.15f),
                                                shape = RoundedCornerShape(8.dp)
                                        ) {
                                                Text(
                                                        text =
                                                                androidx.compose.ui.res
                                                                        .stringResource(
                                                                                R.string
                                                                                        .officer_title
                                                                        ),
                                                        color = Color.White,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        modifier =
                                                                Modifier.padding(
                                                                        horizontal = 12.dp,
                                                                        vertical = 4.dp
                                                                )
                                                )
                                        }
                                }
                        }

                        // Info Section
                        Column(
                                modifier = Modifier.padding(horizontal = 24.dp).offset(y = (-40).dp)
                        ) {
                                ProfileDetailCard(
                                        items =
                                                listOf(
                                                        ProfileItem(
                                                                androidx.compose.ui.res
                                                                        .stringResource(
                                                                                R.string.full_name
                                                                        ),
                                                                fullName
                                                        ),
                                                        ProfileItem(
                                                                androidx.compose.ui.res
                                                                        .stringResource(
                                                                                R.string.gn_division
                                                                        ),
                                                                division
                                                        ),
                                                        ProfileItem(
                                                                androidx.compose.ui.res
                                                                        .stringResource(
                                                                                R.string
                                                                                        .office_address
                                                                        ),
                                                                profile?.officeAddress ?: "N/A"
                                                        ),
                                                        ProfileItem(
                                                                androidx.compose.ui.res
                                                                        .stringResource(
                                                                                R.string
                                                                                        .contact_number
                                                                        ),
                                                                profile?.contactInfo ?: "N/A"
                                                        )
                                                )
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                        text =
                                                androidx.compose.ui.res.stringResource(
                                                        R.string.account_settings
                                                ),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GnTextPrimary
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                SettingsActionItem(
                                        title =
                                                androidx.compose.ui.res.stringResource(
                                                        R.string.edit_profile_info
                                                ),
                                        subtitle =
                                                androidx.compose.ui.res.stringResource(
                                                        R.string.edit_profile_subtitle
                                                ),
                                        icon = R.drawable.ic_solar_user_id,
                                        onClick = onNavigateToEditProfile
                                )

                                SettingsActionItem(
                                        title =
                                                androidx.compose.ui.res.stringResource(
                                                        R.string.security
                                                ),
                                        subtitle =
                                                androidx.compose.ui.res.stringResource(
                                                        R.string.security_subtitle
                                                ),
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
                                                text =
                                                        androidx.compose.ui.res.stringResource(
                                                                R.string.logout_system
                                                        ),
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
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GnSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        letterSpacing = 0.5.sp
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                        item.value,
                                                        color = GnTextPrimary,
                                                        fontSize = 17.sp,
                                                        fontWeight = FontWeight.Bold
                                                )
                                        }
                                }
                                if (index < items.size - 1) {
                                        HorizontalDivider(
                                                modifier = Modifier.padding(vertical = 16.dp),
                                                color = Color(0xFFF1F5F9),
                                                thickness = 1.dp
                                        )
                                }
                        }
                }
        }
}

data class ProfileItem(val label: String, val value: String)

@Composable
fun SettingsActionItem(title: String, subtitle: String, icon: Int, onClick: () -> Unit) {
        Card(
                onClick = onClick,
                modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GnSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
                Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Box(
                                modifier =
                                        Modifier.size(48.dp)
                                                .background(
                                                        Color(0xFFF5F8FF),
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
                                Text(
                                        title,
                                        fontWeight = FontWeight.Bold,
                                        color = GnTextPrimary,
                                        fontSize = 16.sp
                                )
                                Text(
                                        subtitle,
                                        fontSize = 12.sp,
                                        color = GnTextSecondary,
                                        fontWeight = FontWeight.Medium
                                )
                        }

                        Box(
                                modifier =
                                        Modifier.size(32.dp)
                                                .background(
                                                        Color(0xFFF1F5F9),
                                                        RoundedCornerShape(8.dp)
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter =
                                                painterResource(
                                                        id = R.drawable.ic_solar_alt_arrow_right
                                                ),
                                        contentDescription = null,
                                        tint = GnTextSecondary,
                                        modifier = Modifier.size(16.dp)
                                )
                        }
                }
        }
}
