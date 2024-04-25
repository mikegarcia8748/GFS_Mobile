package com.gfs.mobile.system.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.gfs.mobile.system.navigation.AuthScreen
import com.gfs.mobile.system.navigation.RootNavGraph
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GFSMaterialTheme {

                val navController = rememberNavController()
                val startDestination = AuthScreen.EnterPIN.route

                RootNavGraph(navController = navController, startDestination = startDestination)
            }
        }
    }
}