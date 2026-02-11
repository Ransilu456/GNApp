package com.emarketing_paradice.gnsrilanka.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emarketing_paradice.gnsrilanka.ui.components.bottombar.AppBottomBar
import com.emarketing_paradice.gnsrilanka.ui.components.drawer.AppDrawer
import com.emarketing_paradice.gnsrilanka.ui.navigation.AppNavHost
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    citizenViewModel: CitizenViewModel,
    householdViewModel: HouseholdViewModel,
    requestViewModel: RequestViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.CitizenList.route,
        Screen.HouseholdList.route,
        Screen.RequestList.route,
        Screen.Profile.route
    )

    val showTopBar = currentRoute !in listOf(Screen.Login.route, Screen.Register.route)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                if (showTopBar) {
                    TopAppBar(
                        title = { Text(text = currentRoute?.let { findTitleByRoute(it) } ?: "") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            if (currentRoute == Screen.CitizenList.route || currentRoute == Screen.HouseholdList.route || currentRoute == Screen.RequestList.route) {

                                IconButton(onClick = { /* Handle notification click */ }) {
                                    BadgedBox(badge = { Badge { Text("8") } }) {
                                        Icon(
                                            Icons.Default.Notifications,
                                            contentDescription = "Notifications"
                                        )
                                    }
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = if (currentRoute == Screen.Home.route) MaterialTheme.colorScheme.primary else Color.Transparent,
                            titleContentColor = if (currentRoute == Screen.Home.route) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                            navigationIconContentColor = if (currentRoute == Screen.Home.route) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                            actionIconContentColor = if (currentRoute == Screen.Home.route) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                        ),
                        scrollBehavior = scrollBehavior
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    AppBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { padding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
                authViewModel = authViewModel,
                citizenViewModel = citizenViewModel,
                householdViewModel = householdViewModel,
                requestViewModel = requestViewModel
            )
        }
    }
}

private fun findTitleByRoute(route: String): String {
    return when (route) {
        Screen.Home.route -> ""
        Screen.CitizenList.route -> "Search Mechanic"
        Screen.HouseholdList.route -> Screen.HouseholdList.title
        Screen.RequestList.route -> Screen.RequestList.title
        Screen.Profile.route -> Screen.Profile.title
        Screen.CitizenAdd.route -> Screen.CitizenAdd.title
        Screen.CitizenEdit.route -> Screen.CitizenEdit.title
        Screen.HouseholdAdd.route -> Screen.HouseholdAdd.title
        Screen.HouseholdEdit.route -> Screen.HouseholdEdit.title
        Screen.RequestAdd.route -> Screen.RequestAdd.title
        Screen.RequestEdit.route -> Screen.RequestEdit.title
        else -> "GN App"
    }
}
