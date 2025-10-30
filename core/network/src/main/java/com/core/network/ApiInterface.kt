package com.core.network

import com.core.common.model.User
import retrofit2.http.GET
import retrofit2.http.Path

// interface for API calls
interface ApiInterface {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): User
}