package com.example.myapplication.models

data class User(
    val email: String,
    val name: String,
    val imageLink: String
) {
    constructor() : this("", "", "")
}
