package com.core.data.di

import com.core.data.UserRepository
import com.core.data.UserRepositoryImpl
import com.core.network.ApiInterface
import org.koin.dsl.module

val coreDataModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}