package com.gfs.mobile.system.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {

        authNavGraph(navController = navController, startDestination = startDestination)
        dashboardNavGraph(navController = navController)
    }
}

object Graph {
    const val ROOT = "root_nav_graph"
    const val AUTHENTICATION = "auth_graph"
}
