package com.example.myapplication.data

import com.example.myapplication.models.Housing

interface IHousingRepository {
    val temporaryTestingHousingList: MutableList<Housing>
    fun getHousing() : MutableList<Housing>
    fun getHousingOfUser(UserID : Integer): MutableList<Housing>
}
