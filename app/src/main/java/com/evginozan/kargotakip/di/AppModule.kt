package com.evginozan.kargotakip.di

import android.util.Log
import com.evginozan.kargotakip.data.local.UserPreferences
import com.evginozan.kargotakip.data.remote.KargoTakipApiService
import com.evginozan.kargotakip.data.repository.AuthRepositoryImpl
import com.evginozan.kargotakip.data.repository.BranchRepositoryImpl
import com.evginozan.kargotakip.data.repository.CargoRepositoryImpl
import com.evginozan.kargotakip.domain.repository.AuthRepository
import com.evginozan.kargotakip.domain.repository.BranchRepository
import com.evginozan.kargotakip.domain.repository.CargoRepository
import com.evginozan.kargotakip.domain.usecase.GetCargoByTrackingCodeUseCase
import com.evginozan.kargotakip.domain.usecase.GetCurrentUserUseCase
import com.evginozan.kargotakip.domain.usecase.GetDeliveryCodeUseCase
import com.evginozan.kargotakip.domain.usecase.GetUserCargosUseCase
import com.evginozan.kargotakip.domain.usecase.LoginUseCase
import com.evginozan.kargotakip.domain.usecase.LogoutUseCase
import com.evginozan.kargotakip.domain.usecase.RegisterUseCase
import com.evginozan.kargotakip.presentation.auth.LoginViewModel
import com.evginozan.kargotakip.presentation.cargo.CargoDetailViewModel
import com.evginozan.kargotakip.presentation.cargo.DeliveryCodeViewModel
import com.evginozan.kargotakip.presentation.home.HomeViewModel
import com.evginozan.kargotakip.presentation.profile.ProfileViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModel
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { CargoDetailViewModel(get(), get()) }
    viewModel { DeliveryCodeViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }

    // UseCase
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetCargoByTrackingCodeUseCase(get()) }
    factory { GetUserCargosUseCase(get(), get()) }
    factory { GetDeliveryCodeUseCase(get()) }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<CargoRepository> { CargoRepositoryImpl(get()) }
    single<BranchRepository> { BranchRepositoryImpl(get()) }

    // DataSource
    single { UserPreferences(androidContext()) }

    // Ktor
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = object : Logger
                {
                    override fun log(message: String) {
                        Log.i("KtorClient", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
        }
    }

    // API Service
    single { KargoTakipApiService(get()) }
}