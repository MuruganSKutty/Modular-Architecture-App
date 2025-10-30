package com.core.network

import com.core.network.model.User
import retrofit2.http.GET

// interface for API calls
interface ApiInterface {
    @GET("users")
    suspend fun getUsers(): List<User>
}