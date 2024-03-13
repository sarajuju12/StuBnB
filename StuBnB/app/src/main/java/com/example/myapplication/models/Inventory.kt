package com.example.myapplication.models

data class Inventory(
    val name: String,
    val userId: String,
    val description: String,
    val imageLinks: List<String>,
    val price: Double,
    val subject: String,
    val category: String,
    val condition: String,
    var favourite: Boolean
){
    constructor() : this("", "", "", mutableListOf(), 0.0, "", "", "", false)
}

//data class Person(val name: String, var age: Int)
