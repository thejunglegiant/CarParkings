package com.thejunglegiant.carparkings.di

import com.thejunglegiant.carparkings.data.api.ApiService
import com.thejunglegiant.carparkings.data.api.RetrofitClient
import com.thejunglegiant.carparkings.data.api.RetrofitClient.BASE_URL
import com.thejunglegiant.carparkings.ui.screens.carnumber.CarNumberViewModel
import com.thejunglegiant.carparkings.ui.screens.main.MainViewModel
import com.thejunglegiant.carparkings.ui.screens.map.MapViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    viewModel {
        MainViewModel(
            api = get()
        )
    }
    viewModel {
        CarNumberViewModel(
            api = get(),
        )
    }
    viewModel {
        MapViewModel(
            api = get(),
        )
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
}