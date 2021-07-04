package com.example.workshopapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.network.Repository
import com.example.workshopapp.util.NetworkUtils.Companion.isInternetAvailable
import com.example.workshopapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(val context: Context) : ViewModel() {
    private val workShopLiveData: MutableLiveData<Resource<List<WorkShopModel>>> = MutableLiveData()
    private val appTitleLiveData: MutableLiveData<String> = MutableLiveData()
    private val workshopList = mutableListOf<WorkShopModel>()

    companion object {
        const val TAG = "MainViewModel"

        class WorkShopViewModelProviderFactory(val context: Context) : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(context) as T
            }
        }
    }

    fun getWorkShopListObserver(): MutableLiveData<Resource<List<WorkShopModel>>> = workShopLiveData

    fun setAppTitle(title: String) = appTitleLiveData.postValue(title)

    fun getScreenTitle(): MutableLiveData<String> = appTitleLiveData

    fun getWorkShopData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (workshopList.isNullOrEmpty()) {
                if (isInternetAvailable(context)) {
                    workShopLiveData.postValue(Resource.Loading())
                    val response = Repository().getAllCountries()
                    handleNetworkResponse(response)
                } else {
                    workShopLiveData.postValue(Resource.Error("Failed fetching data", null))
                }

            } else {
                displayCurrentList()
            }
        }
    }

    fun displayCurrentList(query: String = "") {
        workShopLiveData.postValue(Resource.Update(workshopList.filter { it.description!!.contains(query, ignoreCase = true) }.sortedBy { it.name }))
    }

    private fun handleNetworkResponse(response: Response<List<WorkShopModel>>) {
        Log.d(TAG, "handleResponse: ")
        if (!response.isSuccessful || response.body() == null) {
            workShopLiveData.postValue(Resource.Error("Failed fetching data", null))
            return
        }
        response.body().apply {
            workshopList.addAll(this ?: mutableListOf())

            workshopList.sortBy { item -> item.name }
            workShopLiveData.postValue(Resource.Success(workshopList))
        }

    }


}
