package com.example.myapplication.data.housing

import com.example.myapplication.models.Housing

interface IHousingRepository {
    fun getHousing(callback: HousingCallback)
    fun getHousingOfUser(callback: HousingCallback, userID : String)
    fun createHousing(newHousingItem : Housing)
}
