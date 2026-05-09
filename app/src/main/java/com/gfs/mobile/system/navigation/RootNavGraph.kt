package com.gfs.mobile.system.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gfs.mobile.feature.auth.ui.screen.auth.AuthenticationScreen
import com.gfs.mobile.feature.dashboard.ui.screen.welcome.WelcomeScreen
import com.gfs.mobile.core.navigation.Graph

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


