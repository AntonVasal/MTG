package com.example.mtg.apodKotlin.data.repositories

import com.example.mtg.apodKotlin.model.ApodsModel
import com.example.mtg.apodKotlin.data.network.API
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApodRepository @Inject constructor(private val api:API) {

    suspend fun getApods() : ArrayList<ApodsModel> = api.getApods().body()!!
}