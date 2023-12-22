package com.example.data.di

import com.example.data.AuthRepositoryImpl
import com.example.data.remote.api.AuthApi
import com.example.domain.repositories.AuthRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class DataModule {
    @Provides
    fun provideHotelDetailsApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create(),
                ),
            )
            .build()
            .create()
    }

    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }
}
