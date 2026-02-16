package com.emarketing_paradice.gnsrilanka.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.emarketing_paradice.gnsrilanka.ui.screens.citizen.*
import com.emarketing_paradice.gnsrilanka.ui.screens.home.HomeScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.household.*
import com.emarketing_paradice.gnsrilanka.ui.screens.notifications.NotificationsScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.profile.OfficerProfileEditScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.profile.ProfileScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.request.*
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.GlobalSearchViewModel // Added
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel

fun NavGraphBuilder.mainNavGraph(
        navController: NavHostController,
        authViewModel: AuthViewModel,
        citizenViewModel: CitizenViewModel,
        householdViewModel: HouseholdViewModel,
        requestViewModel: RequestViewModel,
        registryViewModel: GNRegistryViewModel,
        globalSearchViewModel: GlobalSearchViewModel, // Added
        snackbarHostState: androidx.compose.material3.SnackbarHostState,
        onOpenDrawer: () -> Unit
) {
    navigation(startDestination = Screen.Home.route, route = "main_flow") {
        composable(Screen.Home.route) {
            HomeScreen(
                    citizenViewModel = citizenViewModel,
                    householdViewModel = householdViewModel,
                    requestViewModel = requestViewModel,
                    authViewModel = authViewModel,
                    globalSearchViewModel = globalSearchViewModel,
                    onNavigateToCitizens = { navController.navigate(Screen.CitizenList.route) },
                    onNavigateToHouseholds = { navController.navigate(Screen.HouseholdList.route) },
                    onNavigateToRequests = { navController.navigate(Screen.RequestList.route) },
                    onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                    onNavigateToGlobalSearch = {
                        navController.navigate(Screen.GlobalSearch.route)
                    },
                    onNavigateToLogs = { navController.navigate(Screen.DailyLogList.route) },
                    onNavigateToPermits = { navController.navigate(Screen.PermitList.route) },
                    onNavigateToWelfare = { navController.navigate(Screen.WelfareList.route) },
                    onOpenDrawer = onOpenDrawer
            )
        }

        composable(Screen.Profile.route) { backStackEntry ->
            ProfileScreen(
                    padding = PaddingValues(0.dp), // Managed by global scaffold
                    authViewModel = authViewModel,
                    citizenViewModel = citizenViewModel,
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo("main_flow") { inclusive = true }
                        }
                    },
                    onNavigateToEditProfile = {
                        navController.navigate(Screen.OfficerProfileEdit.route)
                    }
            )
        }

        // Citizen Flow
        composable(Screen.CitizenList.route) { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("userMessage")
            CitizenListScreen(
                    citizenViewModel = citizenViewModel,
                    userMessage = message,
                    snackbarHostState = snackbarHostState,
                    onAddCitizen = { navController.navigate(Screen.CitizenAdd.route) },
                    onEditCitizen = { citizen ->
                        citizenViewModel.loadCitizenForEdit(citizen)
                        navController.navigate(Screen.CitizenEdit.route)
                    },
                    clearUserMessage = {
                        backStackEntry.savedStateHandle.remove<String>("userMessage")
                    }
            )
        }
        composable(Screen.CitizenAdd.route) {
            CitizenAddScreen(
                    citizenViewModel = citizenViewModel,
                    onCitizenAdded = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "userMessage",
                                "Citizen added successfully"
                        )
                        navController.popBackStack()
                    }
            )
        }
        composable(Screen.CitizenEdit.route) {
            CitizenEditScreen(
                    citizenViewModel = citizenViewModel,
                    onCitizenUpdated = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "userMessage",
                                "Citizen updated successfully"
                        )
                        navController.popBackStack()
                    }
            )
        }

        // Household Flow
        composable(Screen.HouseholdList.route) { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("userMessage")
            HouseholdListScreen(
                    householdViewModel = householdViewModel,
                    userMessage = message,
                    snackbarHostState = snackbarHostState,
                    onAddHousehold = { navController.navigate(Screen.HouseholdAdd.route) },
                    onEditHousehold = { household ->
                        householdViewModel.loadHouseholdForEdit(household)
                        navController.navigate(Screen.HouseholdEdit.route)
                    },
                    clearUserMessage = {
                        backStackEntry.savedStateHandle.remove<String>("userMessage")
                    }
            )
        }
        composable(Screen.HouseholdAdd.route) {
            HouseholdAddScreen(
                    householdViewModel = householdViewModel,
                    onHouseholdAdded = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "userMessage",
                                "Household added successfully"
                        )
                        navController.popBackStack()
                    }
            )
        }
        composable(Screen.HouseholdEdit.route) {
            HouseholdEditScreen(
                    householdViewModel = householdViewModel,
                    onHouseholdUpdated = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "userMessage",
                                "Household updated successfully"
                        )
                        navController.popBackStack()
                    }
            )
        }

        // Request Flow
        composable(Screen.RequestList.route) { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("userMessage")
            RequestListScreen(
                    requestViewModel = requestViewModel,
                    userMessage = message,
                    snackbarHostState = snackbarHostState,
                    onAddRequest = { navController.navigate(Screen.RequestAdd.route) },
                    onEditRequest = { request ->
                        requestViewModel.loadRequestForEdit(request)
                        navController.navigate(Screen.RequestEdit.route)
                    },
                    clearUserMessage = {
                        backStackEntry.savedStateHandle.remove<String>("userMessage")
                    }
            )
        }
        composable(Screen.RequestAdd.route) {
            RequestAddScreen(
                    requestViewModel = requestViewModel,
                    onRequestAdded = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "userMessage",
                                "Request added successfully"
                        )
                        navController.popBackStack()
                    }
            )
        }
        composable(Screen.RequestEdit.route) {
            RequestEditScreen(
                    requestViewModel = requestViewModel,
                    onRequestUpdated = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                "userMessage",
                                "Request updated successfully"
                        )
                        navController.popBackStack()
                    }
            )
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(padding = PaddingValues(0.dp))
        }

        composable(Screen.GlobalSearch.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.search.GlobalSearchResultScreen(
                    viewModel = globalSearchViewModel,
                    onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.OfficerProfileEdit.route) {
            OfficerProfileEditScreen(
                    padding = PaddingValues(0.dp),
                    authViewModel = authViewModel,
                    onProfileUpdated = { navController.popBackStack() }
            )
        }

        // Registry Screens
        composable(Screen.DailyLogList.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.registry.DailyLogListScreen(
                    viewModel = registryViewModel,
                    onAddLog = { navController.navigate(Screen.DailyLogAdd.route) }
            )
        }
        composable(Screen.DailyLogAdd.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.registry.DailyLogAddScreen(
                    viewModel = registryViewModel,
                    onLogAdded = { navController.popBackStack() }
            )
        }

        composable(Screen.WelfareList.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.registry.WelfareListScreen(
                    viewModel = registryViewModel,
                    onAddWelfare = { navController.navigate(Screen.WelfareAdd.route) }
            )
        }
        composable(Screen.WelfareAdd.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.registry.WelfareAddScreen(
                    viewModel = registryViewModel,
                    onWelfareAdded = { navController.popBackStack() }
            )
        }

        composable(Screen.PermitList.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.registry.PermitListScreen(
                    viewModel = registryViewModel,
                    onAddPermit = { navController.navigate(Screen.PermitAdd.route) }
            )
        }
        composable(Screen.PermitAdd.route) {
            com.emarketing_paradice.gnsrilanka.ui.screens.registry.PermitAddScreen(
                    viewModel = registryViewModel,
                    onPermitAdded = { navController.popBackStack() }
            )
        }
    }
}
