package com.example.workshopapp

import android.app.SearchManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.view.WorkShopList
import com.example.workshopapp.view.WorkShopViewModelProviderFactory

class MainActivity : AppCompatActivity(), WorkShopList.EventListener {

    private lateinit var viewModel: MainViewModel
    val mFragmentManager = WSFragmentManager(supportFragmentManager)
    companion object {
        const val TAG = "MA_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelProviderFactory = WorkShopViewModelProviderFactory(this)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        openWorkShopList()
    }

    fun getWorkshopViewModel() = viewModel

    private fun openWorkShopDetails(workShop: WorkShopModel){
        mFragmentManager.displayWorkShopDetails(workShop)
    }

    private fun openWorkShopList(){
        mFragmentManager.displayWorkShopList()
    }

    override fun onWorkshopClicked(workShopModel: WorkShopModel) {
        openWorkShopDetails(workShopModel)
    }
}