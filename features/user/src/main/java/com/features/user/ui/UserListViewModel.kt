package com.features.user.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.network.model.User
import com.core.network.utils.DispatcherProvider
import com.core.network.utils.ScreenState
import com.features.user.data.UserRepository
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
                _uiState.value = ScreenState.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }

    companion object {
        private val TAG = UserListViewModel::class.java.simpleName
    }

}