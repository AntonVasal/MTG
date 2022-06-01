package com.example.mtg.apodKotlin.data.network

import com.example.mtg.apodKotlin.model.ApodsModel
import retrofit2.Response
import retrofit2.http.GET

interface API{
    @GET("apod?api_key=DEMO_KEY&count=10")
    suspend fun getApods() : Response<ArrayList<ApodsModel>>
}