package com.features.user.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.DispatcherProvider
import com.core.common.ScreenState
import com.core.common.model.User
import com.core.data.UserRepository
import kotlinx.coroutines.launch

class UserListViewModel(val userRepository: UserRepository, val dispatcherProvider: DispatcherProvider) : ViewModel() {
    private val _uiState = mutableStateOf<ScreenState<List<User>>>(ScreenState.Loading)
    val uiState get() = _uiState

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.value = ScreenState.Loading
            try {
                val users = userRepository.getUsers()
                _uiState.value = ScreenState.Success(users)
            } catch (e: Exception) {
                Log.e(TAG, "Get users api failed with error: ${e.message}")
                _uiState.value = ScreenState.Error("An error occurred, please try again")
            }
        }
    }

    companion object {
        private val TAG = UserListViewModel::class.java.simpleName
    }

}