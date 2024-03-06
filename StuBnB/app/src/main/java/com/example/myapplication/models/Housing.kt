package com.example.myapplication.models

data class Housing(
    val name: String,
    val description: String,
    val imageLinks: List<String>,
    val userId: String,
    val price: Double,
    val startDate: String,
    val endDate: String,

    val address: String,
    val propertyType: String,
    val genderRestriction: String,
    val numOfGuests: kotlin.Int,
    val numOfBedrooms: kotlin.Int,
    val numOfBathrooms: kotlin.Int
){
    constructor() : this (name = "", description = "", imageLinks = mutableListOf(),
                userId = "", price = 0.0, startDate = "", endDate = "", address = "",
                propertyType = "", genderRestriction = "", numOfGuests = 0, numOfBedrooms = 0,
                numOfBathrooms = 0)
}
