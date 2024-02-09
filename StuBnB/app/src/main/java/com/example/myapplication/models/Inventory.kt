package com.example.myapplication.models

data class Inventory(
    val name: String,
    val description: String,
    val imageLinks: MutableList<String>,
    val price: Int,
    val subject: String,
    val category: String,
    val condition: String
)

//data class Person(val name: String, var age: Int)
