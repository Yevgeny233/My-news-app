package com.myselfproject.mynewsapp.di

import com.myselfproject.mynewsapp.BASE_URL
import com.myselfproject.mynewsapp.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)
}