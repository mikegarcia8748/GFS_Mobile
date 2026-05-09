package com.gfs.mobile.system.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gfs.mobile.feature.auth.ui.screen.auth.AuthenticationScreen
import com.gfs.mobile.core.navigation.AuthScreen
import com.gfs.mobile.core.navigation.Graph

fun NavGraphBuilder.authNavGraph(navController: NavHostController, startDestination: String) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = startDestination
    ) {

        composable(route = AuthScreen.EnterPIN.route) {
            AuthenticationScreen(navController = navController)
        }
    }
}


