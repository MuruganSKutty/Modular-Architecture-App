package com.features.user.data

import com.core.network.ApiInterface
import com.core.network.model.User
import com.core.network.utils.DefaultDispatcherProvider
import com.core.network.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    val apiService: ApiInterface,
    val dispatcherProvider: DispatcherProvider
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return withContext(dispatcherProvider.io) {
            val response = apiService.getUsers()
            return@withContext response
        }
    }
}