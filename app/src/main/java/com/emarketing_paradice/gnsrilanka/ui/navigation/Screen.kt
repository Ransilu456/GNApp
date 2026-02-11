package com.emarketing_paradice.gnsrilanka.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {

    // Auth
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Register")

    // Main (Bottom bar)
    object Home : Screen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object CitizenList : Screen(
        route = "citizen_list",
        title = "Citizens",
        icon = Icons.Default.Person
    )

    object HouseholdList : Screen(
        route = "household_list",
        title = "Houses",
        icon = Icons.Default.Domain
    )

    object RequestList : Screen(
        route = "request_list",
        title = "Requests",
        icon = Icons.Default.Description
    )

    object Profile : Screen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )

    // Detail / Form screens (NO bottom bar)
    object CitizenAdd : Screen("citizen_add", "Add Citizen")
    object CitizenEdit : Screen("citizen_edit", "Edit Citizen")
    object RequestAdd : Screen("request_add", "Add Request")
    object RequestEdit : Screen("request_edit", "Edit Request")
    // Detail / Form screens (no bottom bar)
    object HouseholdAdd : Screen("household_add", "Add Household")
    object HouseholdEdit : Screen("household_edit", "Edit Household")

}
