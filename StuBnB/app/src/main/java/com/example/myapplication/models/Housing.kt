package com.example.myapplication.models

data class Housing(
    val name: String,
    val description: String,
    val imageLinks: MutableList<String>,

    // new stuff
    val seller: String,
    val price: Int,
    val startDate: Date,
    val endDate: Date
)
