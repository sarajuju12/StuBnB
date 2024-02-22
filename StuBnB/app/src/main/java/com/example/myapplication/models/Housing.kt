package com.example.myapplication.models

data class Housing(
    val userId: String, // data integrity
    val name: String,
    val description: String,
    val imagelink: MutableList<String>,
    val seller: String,
    val price: Double,
    val startDate: Date,
    val endDate: Date,
    // can extend more later
){
    constructor() : this(userId = "", name = "", description = "", imagelink = mutableListOf(),
                seller= "", price = 0.0, startDate = Date(), endDate = Date())
}
