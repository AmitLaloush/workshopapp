package com.example.workshopapp.model

data class WorkShopFetchingError(val error:String): Throwable(error)