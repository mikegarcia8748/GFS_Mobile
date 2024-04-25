package com.gfs.mobile.system.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gfs.mobile.system.ui.screen.auth.AuthenticationScreen
import com.gfs.mobile.system.ui.screen.welcome.WelcomeScreen

@Composable
fun RootNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {

        authNavGraph(navController = navController, startDestination = startDestination)
        dashboardNavGraph(navController = navController)

        composable(route = Graph.DASHBOARD) {
            WelcomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_nav_graph"
    const val AUTHENTICATION = "auth_graph"
    const val DASHBOARD = "dashboard_graph"
}
