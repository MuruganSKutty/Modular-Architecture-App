package com.features.user.data

import com.core.network.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}