package com.example.workshopapp.network


import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.util.NetworkUtils.Companion.WORKSHOP_API
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET(WORKSHOP_API)
    suspend fun getAllWorkShop(): Response<List<WorkShopModel>>
}