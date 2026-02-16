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
                Screen(route = "household_list", title = "Houses", icon = R.drawable.ic_solar_home)

        object RequestList :
                Screen(
                        route = "request_list",
                        title = "Requests",
                        icon = R.drawable.ic_solar_documents
                )

        object Profile :
                Screen(route = "profile", title = "System", icon = R.drawable.ic_solar_settings)

        // Registries
        object DailyLogList :
                Screen(
                        route = "daily_log_list",
                        title = "Visitor Log",
                        icon = R.drawable.ic_solar_document_text
                )

        object WelfareList :
                Screen(
                        route = "welfare_list",
                        title = "Welfare",
                        icon = R.drawable.ic_solar_medal_ribbon_star
                )

        object PermitList :
                Screen(
                        route = "permit_list",
                        title = "Permits",
                        icon = R.drawable.ic_baseline_assignment
                )

        // Detail / Form screens (NO bottom bar)
        object CitizenAdd : Screen("citizen_add", "Add Citizen")
        object CitizenEdit : Screen("citizen_edit", "Edit Citizen")
        object HouseholdAdd : Screen("household_add", "Add Household")
        object HouseholdEdit : Screen("household_edit", "Edit Household")
        object RequestAdd : Screen("request_add", "Add Request")
        object RequestEdit : Screen("request_edit", "Edit Request")

        object OfficerProfileEdit : Screen("officer_profile_edit", "Edit Officer Profile")

        object GlobalSearch : Screen("global_search", "Search")

        object WelfareAdd : Screen("welfare_add", "Add Welfare")
        object PermitAdd : Screen("permit_add", "Add Permit")
        object DailyLogAdd : Screen("daily_log_add", "Add Daily Log")

        object Notifications :
                Screen(
                        route = "notifications",
                        title = "Notifications",
                        icon = R.drawable.ic_solar_bell
                )
}
