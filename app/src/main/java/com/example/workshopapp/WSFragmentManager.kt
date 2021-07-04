package com.example.workshopapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.view.WorkShopDetails
import com.example.workshopapp.view.WorkShopList

class WSFragmentManager(private val supportManager: FragmentManager) {


    fun displayWorkShopDetails(workShop: WorkShopModel) = replaceFragmentAndAddToBackStack(
            WorkShopDetails.newInstance(workShop)
    )

    fun displayWorkShopList() = replaceFragmentAndAddToBackStack(
            WorkShopList()
    )

    private fun replaceFragmentAndAddToBackStack(fragment: Fragment) {
        val transaction = supportManager.beginTransaction()
        transaction.apply {
            addToBackStack(null)
            replace(R.id.main_frame, fragment)
            commit()
        }
    }
}