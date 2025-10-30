package com.example.modulararchitectureapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.features.user.ui.UserListScreens

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "user_list") {
        // user list screen
        composable("user_list") {
            UserListScreens {

            }
        }
    }
}