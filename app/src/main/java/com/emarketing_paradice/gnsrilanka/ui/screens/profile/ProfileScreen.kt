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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel

// Theme-based colors are used directly from MaterialTheme.colorScheme

@Composable
fun ProfileScreen(
        padding: PaddingValues,
        authViewModel: AuthViewModel,
        citizenViewModel: CitizenViewModel,
        onLogout: () -> Unit,
        onNavigateToEditProfile: () -> Unit
) {
        val officerProfile by authViewModel.officerProfile.collectAsState()

        ProfileScreenContent(
                profile = officerProfile,
                padding = padding,
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
                        padding = PaddingValues(0.dp),
                        onLogout = {},
                        onNavigateToEditProfile = {}
                )
        }
}

@Composable
fun ProfileScreenContent(
        profile: com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile?,
        padding: PaddingValues,
        onLogout: () -> Unit,
        onNavigateToEditProfile: () -> Unit
) {
        val fullName = profile?.officerName ?: "Officer"
        val division = profile?.gnDivision ?: "Assigned Division"

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
                                        .height(280.dp)
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
                ) {
                        Column(
                                modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                        ) {
                                // Profile Image Placeholder
                                Surface(
                                        modifier = Modifier.size(120.dp),
                                        shape = RoundedCornerShape(40.dp),
                                        color =
                                                MaterialTheme.colorScheme.onPrimary.copy(
                                                        alpha = 0.2f
                                                ),
                                        border =
                                                BorderStroke(
                                                        2.dp,
                                                        MaterialTheme.colorScheme.onPrimary.copy(
                                                                alpha = 0.4f
                                                        )
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
                                                        modifier = Modifier.size(70.dp),
                                                        tint = MaterialTheme.colorScheme.onPrimary
                                                )
                                        }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                        text = fullName,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Surface(
                                        color =
                                                MaterialTheme.colorScheme.onPrimary.copy(
                                                        alpha = 0.15f
                                                ),
                                        shape = RoundedCornerShape(12.dp)
                                ) {
                                        Text(
                                                text =
                                                        androidx.compose.ui.res.stringResource(
                                                                R.string.officer_title
                                                        ),
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                style = MaterialTheme.typography.labelMedium,
                                                modifier =
                                                        Modifier.padding(
                                                                horizontal = 16.dp,
                                                                vertical = 6.dp
                                                        )
                                        )
                                }
                        }
                }

                // Info Section
                Column(modifier = Modifier.padding(horizontal = 24.dp).offset(y = (-40).dp)) {
                        ProfileDetailCard(
                                items =
                                        listOf(
                                                ProfileItem(
                                                        androidx.compose.ui.res.stringResource(
                                                                R.string.full_name
                                                        ),
                                                        fullName
                                                ),
                                                ProfileItem(
                                                        androidx.compose.ui.res.stringResource(
                                                                R.string.gn_division
                                                        ),
                                                        division
                                                ),
                                                ProfileItem(
                                                        androidx.compose.ui.res.stringResource(
                                                                R.string.office_address
                                                        ),
                                                        profile?.officeAddress ?: "N/A"
                                                ),
                                                ProfileItem(
                                                        androidx.compose.ui.res.stringResource(
                                                                R.string.contact_number
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
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
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
                                title = androidx.compose.ui.res.stringResource(R.string.security),
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
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.errorContainer
                                                                .copy(alpha = 0.4f),
                                                contentColor = MaterialTheme.colorScheme.error
                                        ),
                                shape = RoundedCornerShape(16.dp),
                                elevation = ButtonDefaults.buttonElevation(0.dp)
                        ) {
                                Icon(
                                        painter = painterResource(id = R.drawable.ic_solar_logout),
                                        contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                        text =
                                                androidx.compose.ui.res.stringResource(
                                                        R.string.logout_system
                                                ),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                )
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                }
        }
}

@Composable
fun ProfileDetailCard(items: List<ProfileItem>) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border =
                        BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
        ) {
                Column(modifier = Modifier.padding(20.dp)) {
                        items.forEachIndexed { index, item ->
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Column {
                                                Text(
                                                        item.label,
                                                        color =
                                                                MaterialTheme.colorScheme
                                                                        .onSurfaceVariant,
                                                        style =
                                                                MaterialTheme.typography
                                                                        .labelMedium,
                                                        fontWeight = FontWeight.SemiBold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                        item.value,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        fontWeight = FontWeight.Bold
                                                )
                                        }
                                }
                                if (index < items.size - 1) {
                                        HorizontalDivider(
                                                modifier = Modifier.padding(vertical = 12.dp),
                                                color =
                                                        MaterialTheme.colorScheme.outlineVariant
                                                                .copy(alpha = 0.5f),
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
                colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border =
                        BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                        )
        ) {
                Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Box(
                                modifier =
                                        Modifier.size(48.dp)
                                                .background(
                                                        MaterialTheme.colorScheme.primary.copy(
                                                                alpha = 0.1f
                                                        ),
                                                        RoundedCornerShape(14.dp)
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                                Text(
                                        title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                        subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        }

                        Box(
                                modifier =
                                        Modifier.size(32.dp)
                                                .background(
                                                        MaterialTheme.colorScheme.outlineVariant
                                                                .copy(alpha = 0.3f),
                                                        RoundedCornerShape(10.dp)
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                Icon(
                                        painter =
                                                painterResource(
                                                        id = R.drawable.ic_solar_alt_arrow_right
                                                ),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                )
                        }
                }
        }
}
