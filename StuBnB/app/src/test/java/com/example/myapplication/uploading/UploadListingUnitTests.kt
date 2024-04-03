package com.example.myapplication.uploading

import com.example.myapplication.data.housing.UploadHousingEvent
import com.example.myapplication.data.housing.UploadHousingViewModel
import com.example.myapplication.data.inventory.UploadInventoryEvent
import com.example.myapplication.data.inventory.UploadInventoryViewModel
import junit.framework.TestCase
import org.junit.Test

class UploadListingUnitTests {

    private val uploadHousingViewModel = UploadHousingViewModel()
    private val uploadInventoryViewModel = UploadInventoryViewModel()

    @Test
    fun uploadHousingValidateData() {
        // Test invalid upload housing information
        uploadHousingViewModel.onEvent(UploadHousingEvent.NameChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.DescriptionChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.PriceChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.StartDateChange("77/77/7777"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.EndDateChange("01/03/2002"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.AddressChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.PropertyTypeChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.GenderRestrictionChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfGuestsChange("-2"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfBathroomsChange("1.1"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfBedroomsChange("0"))
        TestCase.assertFalse(uploadHousingViewModel.validationPassed.value)

        // Test valid upload housing information
        uploadHousingViewModel.onEvent(UploadHousingEvent.NameChange("test@example.com"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.DescriptionChange("description"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.PriceChange("100.00"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.StartDateChange("11/11/2025"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.EndDateChange("03/03/2026"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.AddressChange("123 rainbow road"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.PropertyTypeChange("apartment"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.GenderRestrictionChange("males only"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfGuestsChange("4"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfBathroomsChange("4"))
        uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfBedroomsChange("4"))
        TestCase.assertTrue(uploadHousingViewModel.validationPassed.value)
    }

    @Test
    fun uploadInventoryValidateData() {
        // Test invalid upload inventory information
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.NameChange(""))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.DescriptionChange("test@example.com"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.PriceChange("12..1"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.SubjectChange("test@example.com"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.CategoryChange("test@example.com"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.ConditionChange("test@example.com"))
        TestCase.assertFalse(uploadInventoryViewModel.validationPassed.value)

        // Test valid upload inventory information
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.NameChange("pencil"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.DescriptionChange("pencil description"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.PriceChange("1.21"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.SubjectChange("subject"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.CategoryChange("category"))
        uploadInventoryViewModel.onEvent(UploadInventoryEvent.ConditionChange("brand new"))
        TestCase.assertTrue(uploadInventoryViewModel.validationPassed.value)
    }
}