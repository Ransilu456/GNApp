package com.emarketing_paradice.gnsrilanka.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emarketing_paradice.gnsrilanka.ui.screens.auth.LoginScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.auth.RegisterScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.citizen.CitizenAddScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.citizen.CitizenEditScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.citizen.CitizenListScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.home.HomeScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.household.HouseholdAddScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.household.HouseholdEditScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.household.HouseholdListScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.profile.ProfileScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.request.RequestAddScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.request.RequestEditScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.request.RequestListScreen
import com.emarketing_paradice.gnsrilanka.viewmodel.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object CitizenList : Screen("citizen_list")
    object CitizenAdd : Screen("citizen_add")
    object CitizenEdit : Screen("citizen_edit")
    object HouseholdList : Screen("household_list")
    object HouseholdAdd : Screen("household_add")
    object HouseholdEdit : Screen("household_edit")
    object RequestList : Screen("request_list")
    object RequestAdd : Screen("request_add")
    object RequestEdit : Screen("request_edit")
    object Profile : Screen("profile")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    citizenViewModel: CitizenViewModel,
    householdViewModel: HouseholdViewModel,
    requestViewModel: RequestViewModel,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // Auth Flow
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // Main App Flow
        composable(Screen.Home.route) { 
            HomeScreen(
                citizenViewModel = citizenViewModel,
                householdViewModel = householdViewModel,
                requestViewModel = requestViewModel,
                onNavigateToCitizens = { navController.navigate(Screen.CitizenList.route) },
                onNavigateToHouseholds = { navController.navigate(Screen.HouseholdList.route) },
                onNavigateToRequests = { navController.navigate(Screen.RequestList.route) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(authViewModel = authViewModel, onLogout = {
                authViewModel.logout()
                navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
            })
        }

        // Citizen Flow
        composable(Screen.CitizenList.route) { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("userMessage")
            CitizenListScreen(
                citizenViewModel = citizenViewModel,
                userMessage = message,
                onAddCitizen = { 
                    citizenViewModel.resetForm()
                    navController.navigate(Screen.CitizenAdd.route) 
                },
                onEditCitizen = { citizen ->
                    citizenViewModel.loadCitizenForEdit(citizen)
                    navController.navigate(Screen.CitizenEdit.route)
                },
                clearUserMessage = { backStackEntry.savedStateHandle.remove<String>("userMessage") }
            )
        }
        composable(Screen.CitizenAdd.route) { 
            CitizenAddScreen(citizenViewModel = citizenViewModel, onCitizenAdded = {
                navController.previousBackStackEntry?.savedStateHandle?.set("userMessage", "Citizen added successfully")
                navController.popBackStack()
            }) 
        }
        composable(Screen.CitizenEdit.route) { 
            CitizenEditScreen(citizenViewModel = citizenViewModel, onCitizenUpdated = {
                navController.previousBackStackEntry?.savedStateHandle?.set("userMessage", "Citizen updated successfully")
                navController.popBackStack()
            }) 
        }

        // Household Flow
        composable(Screen.HouseholdList.route) { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("userMessage")
            HouseholdListScreen(
                householdViewModel = householdViewModel,
                userMessage = message,
                onAddHousehold = {
                    householdViewModel.resetForm()
                    navController.navigate(Screen.HouseholdAdd.route)
                },
                onEditHousehold = { household ->
                    householdViewModel.loadHouseholdForEdit(household)
                    navController.navigate(Screen.HouseholdEdit.route)
                },
                clearUserMessage = { backStackEntry.savedStateHandle.remove<String>("userMessage") }
            )
        }
        composable(Screen.HouseholdAdd.route) {
            HouseholdAddScreen(householdViewModel = householdViewModel, onHouseholdAdded = {
                navController.previousBackStackEntry?.savedStateHandle?.set("userMessage", "Household added successfully")
                navController.popBackStack()
            })
        }
        composable(Screen.HouseholdEdit.route) {
            HouseholdEditScreen(householdViewModel = householdViewModel, onHouseholdUpdated = {
                navController.previousBackStackEntry?.savedStateHandle?.set("userMessage", "Household updated successfully")
                navController.popBackStack()
            })
        }

        // Request Flow
        composable(Screen.RequestList.route) { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("userMessage")
            RequestListScreen(
                requestViewModel = requestViewModel,
                userMessage = message,
                onAddRequest = {
                    requestViewModel.resetForm()
                    navController.navigate(Screen.RequestAdd.route)
                },
                onEditRequest = { request ->
                    requestViewModel.loadRequestForEdit(request)
                    navController.navigate(Screen.RequestEdit.route)
                },
                clearUserMessage = { backStackEntry.savedStateHandle.remove<String>("userMessage") }
            )
        }
        composable(Screen.RequestAdd.route) {
            RequestAddScreen(requestViewModel = requestViewModel, onRequestAdded = {
                navController.previousBackStackEntry?.savedStateHandle?.set("userMessage", "Request added successfully")
                navController.popBackStack()
            })
        }
        composable(Screen.RequestEdit.route) {
            RequestEditScreen(requestViewModel = requestViewModel, onRequestUpdated = {
                navController.previousBackStackEntry?.savedStateHandle?.set("userMessage", "Request updated successfully")
                navController.popBackStack()
            })
        }
    }
}
