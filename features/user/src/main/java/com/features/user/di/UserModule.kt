package com.features.user.di

import com.core.data.UserRepository
import com.core.network.ApiInterface
import com.features.user.ui.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// define the object here which are needed for user module
val userModule = module {
    viewModel {UserListViewModel(get<UserRepository>(), get()) }
}