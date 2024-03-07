package com.example.myapplication.data.housing

import com.example.myapplication.models.Housing

interface HousingCallback {
    fun onHousingLoaded(housingList: MutableList<Housing>)
}