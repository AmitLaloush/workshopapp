package com.example.countryapp.network

//import org.greenrobot.eventbus.EventBus

class Repository {

    companion object {
        const val TAG = "REPOSITORY"
    }

    suspend fun getAllCountries()  = RestClient.service.getAllWorkShop()


}