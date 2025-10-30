package com.core.data

import com.core.common.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserDetails(userId: String): User
}