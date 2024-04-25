package com.gfs.mobile.system.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gfs.mobile.system.ui.screen.auth.AuthenticationScreen

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

sealed class AuthScreen(val route: String) {
    data object EnterPIN: AuthScreen(route = "enter_pin")
}