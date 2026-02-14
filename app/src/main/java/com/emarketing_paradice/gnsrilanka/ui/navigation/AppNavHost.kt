package com.emarketing_paradice.gnsrilanka.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emarketing_paradice.gnsrilanka.ui.screens.auth.LoginScreen
import com.emarketing_paradice.gnsrilanka.ui.screens.auth.RegisterScreen
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel

@Composable
fun AppNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier,
        authViewModel: AuthViewModel,
        citizenViewModel: CitizenViewModel,
        householdViewModel: HouseholdViewModel,
        requestViewModel: RequestViewModel,
        onOpenDrawer: () -> Unit
) {
    val currentUser = authViewModel.currentUser.collectAsState().value

    androidx.compose.runtime.LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("main_flow") { popUpTo(Screen.Login.route) { inclusive = true } }
        }
    }

    NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = modifier
    ) {
        // Authentication Flow
        composable(Screen.Login.route) {
            LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate("main_flow") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                    authViewModel = authViewModel,
                    onRegisterSuccess = {
                        navController.navigate("main_flow") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // Main Application Flow
        mainNavGraph(
                navController = navController,
                authViewModel = authViewModel,
                citizenViewModel = citizenViewModel,
                householdViewModel = householdViewModel,
                requestViewModel = requestViewModel,
                onOpenDrawer = onOpenDrawer
        )
    }
}
