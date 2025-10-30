package com.example.modulararchitectureapp.navigation

sealed class AppScreens(val route: String) {
    data object UserList : AppScreens(USER_LIST)
    data object UserDetails : AppScreens("$USER_DETAILS/{$ARG_USER_ID}")

    companion object {
        const val USER_LIST = "user_list"
        const val USER_DETAILS = "user_details"
        const val ARG_USER_ID = "user_id"
    }
}