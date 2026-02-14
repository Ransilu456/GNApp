package com.emarketing_paradice.gnsrilanka.ui.navigation

import com.emarketing_paradice.gnsrilanka.R

sealed class Screen(val route: String, val title: String, val icon: Int? = null) {

    // Auth
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Register")

    // Main (Bottom bar)
    object Home : Screen(route = "home", title = "Home", icon = R.drawable.ic_solar_home_smile)

    object CitizenList :
            Screen(
                    route = "citizen_list",
                    title = "Citizens",
                    icon = R.drawable.ic_solar_users_group
            )

    object HouseholdList :
            Screen(
                    route = "household_list",
                    title = "Houses",
                    icon = R.drawable.ic_solar_home_smile // Assuming same as home for now or update
                    // it
                    )

    object RequestList :
            Screen(
                    route = "request_list",
                    title = "Requests",
                    icon = R.drawable.ic_solar_documents
            )

    object Profile :
            Screen(route = "profile", title = "System", icon = R.drawable.ic_solar_settings)

    // Detail / Form screens (NO bottom bar)
    object CitizenAdd : Screen("citizen_add", "Add Citizen")
    object CitizenEdit : Screen("citizen_edit", "Edit Citizen")
    object RequestAdd : Screen("request_add", "Add Request")
    object RequestEdit : Screen("request_edit", "Edit Request")
    // Detail / Form screens (no bottom bar)
    object HouseholdAdd : Screen("household_add", "Add Household")
    object HouseholdEdit : Screen("household_edit", "Edit Household")
}
