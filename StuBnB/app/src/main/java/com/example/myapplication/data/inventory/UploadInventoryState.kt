package com.example.myapplication.data.inventory

data class UploadInventoryState(
    var name: String = "",
    var description: String = "",
    var price: String = "",
    var subject: String = "",
    var category: String = "",
    var condition: String = "",
    var email: String = "",
    var imageLinks: List<String> = emptyList(),
    var downloadUrls: List<String> = emptyList(),
    var nameError: Boolean = true,
    var descriptionError: Boolean = true,
    var priceError: Boolean = true,
    var subjectError: Boolean = true,
    var categoryError: Boolean = true,
    var conditionError: Boolean = true,
)