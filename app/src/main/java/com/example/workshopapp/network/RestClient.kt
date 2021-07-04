package com.example.workshopapp.network

import com.example.workshopapp.util.NetworkUtils.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestClient {

    val service: ApiServices by lazy {
        val retrofit = createRetrofitClient()
        retrofit.create(ApiServices::class.java)
    }

    private fun createRetrofitClient() = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

}