package com.example.myapplication.models

data class Inventory(
    val name: String,
    val description: String,
    val imageLinks: MutableList<String>
)

//data class Person(val name: String, var age: Int)
