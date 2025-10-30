package com.core.network.di

import com.core.network.ApiClient
import com.core.network.ApiInterface
import com.core.network.utils.DefaultDispatcherProvider
import com.core.network.utils.DispatcherProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider }
    single<ApiInterface> {
        val service: ApiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        service
    }
}