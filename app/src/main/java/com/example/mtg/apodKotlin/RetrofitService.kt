package com.example.mtg.apodKotlin

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

     fun retrofitInstance(baseUrl:String,context:Context):Retrofit{
        val client = OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor.Builder(context).build())
                .build()
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
    }

}