package com.example.workshopapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.utils.Resource
import com.example.workshopapp.view.WorkShopList

class MainActivity : AppCompatActivity(), WorkShopList.EventListener {

    private lateinit var viewModel: MainViewModel
    val mFragmentManager = WSFragmentManager(supportFragmentManager)
    companion object {
        const val TAG = "MA_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelProviderFactory = MainViewModel.Companion.WorkShopViewModelProviderFactory(this)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        viewModel.getScreenTitle().observe(this, { title ->
            supportActionBar?.title = title
        })
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