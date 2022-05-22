package com.example.mtg.apodKotlin

import retrofit2.Response
import retrofit2.http.GET

interface API{
    @GET("apod?api_key=DEMO_KEY&count=80")
    suspend fun getApods() : Response<ArrayList<ApodsModel>>
}