package com.example.mtg.apodKotlin.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mtg.apodKotlin.data.network.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): API = Retrofit.Builder()
            .client(
                    OkHttpClient()
                            .newBuilder()
                            .addInterceptor(ChuckerInterceptor.Builder(context).build())
                            .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nasa.gov/planetary/")
            .build()
            .create(API::class.java)
}