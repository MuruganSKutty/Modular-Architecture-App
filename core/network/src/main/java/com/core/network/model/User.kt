package com.core.network.model


data class User(
    val id: String,
    val name: String = "",
    val company: String = "",
    val email: String = "",
    val address: String = "",
    val zip: String = "",
    val state: String = "",
    val country: String = "",
    val phone: String = "",
    val photo: String = "",
)
