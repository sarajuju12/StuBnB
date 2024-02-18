package com.example.myapplication.models

data class Inventory(
    val name: String,
    val userId: String,
    val description: String,
    val imageLinks: MutableList<String>,
    val price: Double,
    val subject: String,
    val category: String,
    val condition: String
){
    constructor() : this("", "", "", mutableListOf(), 0.0, "", "", "")
}

//data class Person(val name: String, var age: Int)
