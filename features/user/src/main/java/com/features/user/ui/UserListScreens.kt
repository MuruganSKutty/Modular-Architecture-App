package com.features.user.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.network.model.User
import com.core.network.utils.ScreenState
import com.features.user.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserListScreens(viewModel: UserListViewModel = koinViewModel(), onItemClick: (String) -> Unit) {
    val uiState by viewModel.uiState
    when(uiState) {
        is ScreenState.Loading -> {
            Loader()
        }
        is ScreenState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                (uiState as? ScreenState.Success<List<User>>)?.data?.forEach { user ->
                    item {
                        UserItem(user, onItemClick)
                    }
                }
            }
        }
        is ScreenState.Error -> {
            val message = (uiState as ScreenState.Error).message
            ErrorMessage(message, viewModel)
        }
    }
}

@Composable
private fun Loader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(message: String, viewModel: UserListViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.error) +" $message")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.fetchUsers() }) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}