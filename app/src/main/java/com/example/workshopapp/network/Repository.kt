package com.example.workshopapp.network

class Repository {

    companion object {
        const val TAG = "REPOSITORY"
    }

    suspend fun getAllCountries() = RestClient.service.getAllWorkShop()


}