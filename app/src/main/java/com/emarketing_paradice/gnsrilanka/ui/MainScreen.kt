package com.emarketing_paradice.gnsrilanka.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import com.emarketing_paradice.gnsrilanka.ui.components.bottombar.AppBottomBar
import com.emarketing_paradice.gnsrilanka.ui.components.drawer.AppDrawer
import com.emarketing_paradice.gnsrilanka.ui.components.topbar.AppTopBar
import com.emarketing_paradice.gnsrilanka.ui.navigation.AppNavHost
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(repository: FileRepository) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val authViewModel: AuthViewModel = viewModel { AuthViewModel(repository) }
    val citizenViewModel: CitizenViewModel = viewModel { CitizenViewModel(repository) }
    val householdViewModel: HouseholdViewModel = viewModel { HouseholdViewModel(repository) }
    val requestViewModel: RequestViewModel = viewModel { RequestViewModel(repository) }

    val currentUser by authViewModel.currentUser.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topLevelRoutes = setOf(
        Screen.Home.route,
        Screen.CitizenList.route,
        Screen.HouseholdList.route,
        Screen.RequestList.route,
        Screen.Profile.route
    )

    val showBars = currentUser != null && currentRoute in topLevelRoutes

    val topBarTitle = when (currentRoute) {
        Screen.Home.route -> "Dashboard"
        Screen.CitizenList.route -> "Citizens"
        Screen.HouseholdList.route -> "Households"
        Screen.RequestList.route -> "Requests"
        Screen.Profile.route -> "Profile"
        else -> "GN App"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = showBars,
        drawerContent = {
            if (showBars) {
                AppDrawer(
                    currentRoute = currentRoute,
                    userName = currentUser?.nic ?: "Grama Niladhari",
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (showBars) {
                    AppTopBar(
                        title = topBarTitle,
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }
            },
            bottomBar = {
                if (showBars) {
                    AppBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(if (showBars) innerPadding else PaddingValues()),
                citizenViewModel = citizenViewModel,
                householdViewModel = householdViewModel,
                requestViewModel = requestViewModel,
                authViewModel = authViewModel
            )
        }
    }
}
