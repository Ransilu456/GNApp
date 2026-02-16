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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.components.bottombar.AppBottomBar
import com.emarketing_paradice.gnsrilanka.ui.components.drawer.AppDrawer
import com.emarketing_paradice.gnsrilanka.ui.navigation.AppNavHost
import com.emarketing_paradice.gnsrilanka.ui.navigation.Screen
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.GNRegistryViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.GlobalSearchViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
        authViewModel: AuthViewModel,
        citizenViewModel: CitizenViewModel,
        householdViewModel: HouseholdViewModel,
        requestViewModel: RequestViewModel,
        registryViewModel: GNRegistryViewModel,
        globalSearchViewModel: GlobalSearchViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

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
                        officerProfile = authViewModel.officerProfile.collectAsState().value,
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
                snackbarHost = { SnackbarHost(snackbarHostState) },
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
                                    IconButton(
                                            onClick = {
                                                navController.navigate(Screen.Notifications.route)
                                            }
                                    ) {
                                        BadgedBox(
                                                badge = {
                                                    Badge(
                                                            containerColor =
                                                                    MaterialTheme.colorScheme.error
                                                    ) { Text("3", color = Color.White) }
                                                }
                                        ) {
                                            Icon(
                                                    painter =
                                                            painterResource(
                                                                    id = R.drawable.ic_solar_bell
                                                            ),
                                                    contentDescription = "Notifications",
                                                    tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
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
                                        Screen.WelfareAdd.route -> {
                                            IconButton(
                                                    onClick = {
                                                        registryViewModel.saveWelfareProgram()
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "Save Welfare"
                                                )
                                            }
                                        }
                                        Screen.PermitAdd.route -> {
                                            IconButton(
                                                    onClick = { registryViewModel.savePermit() }
                                            ) {
                                                Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "Save Permit"
                                                )
                                            }
                                        }
                                        Screen.DailyLogAdd.route -> {
                                            IconButton(
                                                    onClick = { registryViewModel.saveDailyLog() }
                                            ) {
                                                Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "Save Log"
                                                )
                                            }
                                        }
                                        Screen.WelfareList.route -> {
                                            IconButton(
                                                    onClick = {
                                                        navController.navigate(
                                                                Screen.WelfareAdd.route
                                                        )
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Add,
                                                        contentDescription = "Add Welfare Program"
                                                )
                                            }
                                        }
                                        Screen.PermitList.route -> {
                                            IconButton(
                                                    onClick = {
                                                        navController.navigate(
                                                                Screen.PermitAdd.route
                                                        )
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Add,
                                                        contentDescription = "Add Permit"
                                                )
                                            }
                                        }
                                        Screen.DailyLogList.route -> {
                                            IconButton(
                                                    onClick = {
                                                        navController.navigate(
                                                                Screen.DailyLogAdd.route
                                                        )
                                                    }
                                            ) {
                                                Icon(
                                                        Icons.Default.Add,
                                                        contentDescription = "Add Daily Log"
                                                )
                                            }
                                        }
                                        else -> {}
                                    }
                                },
                                colors =
                                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                containerColor = Color.Transparent,
                                                scrolledContainerColor =
                                                        MaterialTheme.colorScheme.surface.copy(
                                                                alpha = 0.95f
                                                        ),
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
                    registryViewModel = registryViewModel,
                    globalSearchViewModel = globalSearchViewModel,
                    snackbarHostState = snackbarHostState,
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
        Screen.WelfareAdd.route -> Screen.WelfareAdd.title
        Screen.PermitAdd.route -> Screen.PermitAdd.title
        Screen.DailyLogAdd.route -> Screen.DailyLogAdd.title
        Screen.Notifications.route -> Screen.Notifications.title
        else -> "GN App"
    }
}
