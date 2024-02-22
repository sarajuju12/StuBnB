package com.example.myapplication.models

data class Date (
    val year: Int,
    val month: Int,
    val day: Int,

) {
    override fun toString(): String{
        return "$year/$month/$day"
    }

    constructor() : this(2018, 1, 28)
}
