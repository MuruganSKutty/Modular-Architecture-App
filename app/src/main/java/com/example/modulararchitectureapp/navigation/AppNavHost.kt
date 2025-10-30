package com.example.modulararchitectureapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.features.user.ui.UserListScreens
import com.features.user_details.ui.DetailsScreen
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = AppScreens.USER_LIST) {
        // user list screen
        composable(AppScreens.UserList.route) {
            UserListScreens { userId ->
                navController.navigate("user_details/$userId")
            }
        }

        composable(AppScreens.UserDetails.route,
            arguments = listOf(
                navArgument(AppScreens.ARG_USER_ID) { type = NavType.StringType }
            )) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(AppScreens.ARG_USER_ID)
            userId?.let {
                DetailsScreen(userId = userId)
            }
        }
    }
}