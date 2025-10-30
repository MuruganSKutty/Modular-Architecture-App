package com.features.user.di

import com.core.network.ApiInterface
import com.core.network.utils.DefaultDispatcherProvider
import com.core.network.utils.DispatcherProvider
import com.features.user.data.UserRepository
import com.features.user.data.UserRepositoryImpl
import com.features.user.ui.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// define the object here which are needed for user module
val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get<ApiInterface>(), get()) }
    viewModel {UserListViewModel(get<UserRepository>(), get()) }
}