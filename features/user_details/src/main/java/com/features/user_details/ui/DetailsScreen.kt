package com.features.user_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.core.common.ScreenState
import com.core.common.model.User
import com.core.ui.components.ErrorMessage
import com.core.ui.components.Loader
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(viewModel: DetailsScreenViewModel = koinViewModel(), userId: String) {
    val uiState by viewModel.uiState
    LaunchedEffect(Unit) {
        viewModel.fetchUserDetails(userId)
    }
    when(uiState) {
        is ScreenState.Loading -> {
            Loader()
        }
        is ScreenState.Success -> {
            val user = (uiState as? ScreenState.Success<User>)?.data
            user?.let {
                DetailsContent(user)
            }
        }
        is ScreenState.Error -> {
            val message = (uiState as ScreenState.Error).message
            ErrorMessage(message) {
                viewModel.fetchUserDetails("")
            }
        }
    }
}

