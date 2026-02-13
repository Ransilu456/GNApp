package com.emarketing_paradice.gnsrilanka.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
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

    val showBottomBar =
            currentRoute in
                    listOf(
                            Screen.Home.route,
                            Screen.CitizenList.route,
                            Screen.HouseholdList.route,
                            Screen.RequestList.route,
                            Screen.Profile.route
                    )

    val showTopBar =
            currentRoute !in listOf(Screen.Login.route, Screen.Register.route, Screen.Home.route)

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
                        CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                            text = currentRoute?.let { findTitleByRoute(it) }
                                                            ?: "GN App",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.SemiBold
                                    )
                                },
                                navigationIcon = {
                                    val rootRoutes =
                                            listOf(
                                                    Screen.Home.route,
                                                    Screen.CitizenList.route,
                                                    Screen.HouseholdList.route,
                                                    Screen.RequestList.route,
                                                    Screen.Profile.route
                                            )

                                    if (currentRoute !in rootRoutes &&
                                                    currentRoute != Screen.Login.route &&
                                                    currentRoute != Screen.Register.route
                                    ) {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                    Icons.AutoMirrored.Filled.ArrowBack,
                                                    contentDescription = "Back"
                                            )
                                        }
                                    } else {
                                        IconButton(
                                                onClick = { scope.launch { drawerState.open() } }
                                        ) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                                    }
                                },
                                actions = {
                                    when (currentRoute) {
                                        Screen.CitizenList.route -> {
                                            IconButton(
                                                    onClick = {
                                                        navController.navigate(
                                                                Screen.CitizenAdd.route
                                                        )
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Add,
                                                        contentDescription = "Add Citizen"
                                                )
                                            }
                                        }
                                        Screen.HouseholdList.route -> {
                                            IconButton(
                                                    onClick = {
                                                        navController.navigate(
                                                                Screen.HouseholdAdd.route
                                                        )
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Add,
                                                        contentDescription = "Add Household"
                                                )
                                            }
                                        }
                                        Screen.RequestList.route -> {
                                            IconButton(
                                                    onClick = {
                                                        navController.navigate(
                                                                Screen.RequestAdd.route
                                                        )
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Add,
                                                        contentDescription = "Add Request"
                                                )
                                            }
                                        }
                                        Screen.CitizenAdd.route, Screen.CitizenEdit.route -> {
                                            IconButton(
                                                    onClick = { citizenViewModel.saveCitizen() }
                                            ) {
                                                Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "Save Citizen"
                                                )
                                            }
                                        }
                                        Screen.HouseholdAdd.route, Screen.HouseholdEdit.route -> {
                                            IconButton(
                                                    onClick = { householdViewModel.saveHousehold() }
                                            ) {
                                                Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "Save Household"
                                                )
                                            }
                                        }
                                        Screen.RequestAdd.route, Screen.RequestEdit.route -> {
                                            IconButton(
                                                    onClick = { requestViewModel.saveRequest() }
                                            ) {
                                                Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "Save Request"
                                                )
                                            }
                                        }
                                    }

                                    // Keep Notifications for list screens
                                    if (currentRoute in
                                                    listOf(
                                                            Screen.CitizenList.route,
                                                            Screen.HouseholdList.route,
                                                            Screen.RequestList.route
                                                    )
                                    ) {
                                        IconButton(onClick = { /* Handle notification click */}) {
                                            BadgedBox(badge = { Badge { Text("8") } }) {
                                                Icon(
                                                        Icons.Default.Notifications,
                                                        contentDescription = "Notifications"
                                                )
                                            }
                                        }
                                    }
                                },
                                colors =
                                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.background,
                                                scrolledContainerColor =
                                                        MaterialTheme.colorScheme.surface,
                                                titleContentColor =
                                                        MaterialTheme.colorScheme.onBackground,
                                                navigationIconContentColor =
                                                        MaterialTheme.colorScheme.onBackground,
                                                actionIconContentColor =
                                                        MaterialTheme.colorScheme.onBackground
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
                    requestViewModel = requestViewModel,
                    onOpenDrawer = { scope.launch { drawerState.open() } }
            )
        }
    }
}

private fun findTitleByRoute(route: String): String {
    return when (route) {
        Screen.Home.route -> ""
        Screen.CitizenList.route -> "Search Citizen"
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
