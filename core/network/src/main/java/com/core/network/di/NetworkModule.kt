package com.core.network.di

import com.core.common.DefaultDispatcherProvider
import com.core.common.DispatcherProvider
import com.core.network.ApiClient
import com.core.network.ApiInterface
import org.koin.dsl.module

val networkModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider }
    single<ApiInterface> {
        val service: ApiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        service
    }
}