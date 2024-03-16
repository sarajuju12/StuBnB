package com.example.myapplication.data.inventory

import com.example.myapplication.data.housing.UploadHousingEvent
import com.example.myapplication.models.Inventory

sealed class UploadInventoryEvent {
    data class NameChange(val name: String): UploadInventoryEvent()
    data class DescriptionChange(val description: String): UploadInventoryEvent()
    data class PriceChange(val price: String): UploadInventoryEvent()
    data class SubjectChange(val subject: String): UploadInventoryEvent()
    data class CategoryChange(val category: String): UploadInventoryEvent()
    data class ConditionChange(val condition: String): UploadInventoryEvent()
    object ButtonClicked: UploadInventoryEvent()
}