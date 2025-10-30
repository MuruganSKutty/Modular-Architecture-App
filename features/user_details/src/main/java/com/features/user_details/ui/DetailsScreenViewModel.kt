package com.features.user_details.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.UserRepository
import com.core.network.model.User
import com.core.network.utils.DispatcherProvider
import com.core.network.utils.ScreenState
import kotlinx.coroutines.launch

class DetailsScreenViewModel(val repository: UserRepository, val dispatcherProvider: DispatcherProvider) : ViewModel() {
    private val _uiState = mutableStateOf<ScreenState<User>>(ScreenState.Loading)
    val uiState get() = _uiState

    fun fetchUserDetails(userId: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.value = ScreenState.Loading
            try {
                val users = repository.getUserDetails(userId)
                users?.let {
                    _uiState.value = ScreenState.Success(users)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Get users api failed with error: ${e.message}")
                _uiState.value = ScreenState.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }

    companion object {
        private val TAG = DetailsScreenViewModel::class.java.simpleName
    }
}