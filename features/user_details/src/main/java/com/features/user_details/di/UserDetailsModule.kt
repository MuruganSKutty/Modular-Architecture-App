package com.features.user_details.di

import com.core.data.UserRepository
import com.features.user_details.ui.DetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// define the object here which are needed for user details module
val userDetailsModule = module {
    viewModel { DetailsScreenViewModel(get<UserRepository>(), get()) }
}