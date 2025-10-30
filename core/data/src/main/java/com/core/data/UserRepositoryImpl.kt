package com.core.data

import com.core.common.DispatcherProvider
import com.core.common.model.User
import com.core.network.ApiInterface
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    val apiService: ApiInterface,
    val dispatcherProvider: DispatcherProvider
) : UserRepository {
    private val cache = mutableMapOf<String, User>()
    override suspend fun getUsers(): List<User> {
        return withContext(dispatcherProvider.io) {
            val response = apiService.getUsers()
            response.forEach { cache[it.id] = it }
            return@withContext response
        }
    }

    override suspend fun getUserDetails(userId: String): User {
        return cache[userId] ?: apiService.getUser(userId).also {
            cache[userId] = it
        }
    }
}