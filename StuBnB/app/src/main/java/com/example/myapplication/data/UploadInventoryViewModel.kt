package com.example.myapplication.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.validation.Validator
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

class UploadInventoryViewModel: ViewModel() {
    var uploadState = mutableStateOf(UploadInventoryState())
    var validationPassed = mutableStateOf(true)

    fun onEvent(event: UploadInventoryEvent) {
        when(event) {
            is UploadInventoryEvent.NameChange -> {
                uploadState.value = uploadState.value.copy(
                    name = event.name
                )
                validateData()
            }
            is UploadInventoryEvent.DescriptionChange -> {
                uploadState.value = uploadState.value.copy(
                    description = event.description
                )
                validateData()
            }
            is UploadInventoryEvent.PriceChange -> {
                uploadState.value = uploadState.value.copy(
                    price = event.price
                )
                validateData()
            }
            is UploadInventoryEvent.SubjectChange -> {
                uploadState.value = uploadState.value.copy(
                    subject = event.subject
                )
                validateData()
            }
            is UploadInventoryEvent.CategoryChange -> {
                uploadState.value = uploadState.value.copy(
                    category = event.category
                )
                validateData()
            }
            is UploadInventoryEvent.ConditionChange -> {
                uploadState.value = uploadState.value.copy(
                    condition = event.condition
                )
                validateData()
            }
            is UploadInventoryEvent.ButtonClicked -> {
                validateData()
                uploadListing()
            }
        }
    }

    fun uploadListing() {
        val invRep = InventoryRepository()
        val inventoryTemp = Inventory(
            uploadState.value.name, uploadState.value.email, uploadState.value.description, uploadState.value.imageLinks,
            uploadState.value.price.toDouble(), uploadState.value.subject, uploadState.value.category, uploadState.value.condition
        )
        invRep.createInventory(inventoryTemp)
        Navigator.navigate(Screen.Profile)
    }

    fun setEmailAndImage(email: String, imageLinks: MutableList<String>) {
        uploadState.value = uploadState.value.copy(
            email = email
        )
        uploadState.value = uploadState.value.copy(
            imageLinks = imageLinks
        )
    }

    private fun validateData() {

        val nameResult = Validator.validateName(
            name = uploadState.value.name
        )

        val descriptionResult = Validator.validateName(
            name = uploadState.value.description
        )

        val priceResult = Validator.validatePrice(
            price = uploadState.value.price
        )

        val subjectResult = Validator.validateName(
            name = uploadState.value.subject
        )

        val categoryResult = Validator.validateName(
            name = uploadState.value.category
        )

        val conditionResult = Validator.validateName(
            name = uploadState.value.condition
        )

        uploadState.value = uploadState.value.copy(
            nameError = nameResult.status,
            descriptionError = descriptionResult.status,
            priceError = priceResult.status,
            subjectError = subjectResult.status,
            categoryError = categoryResult.status,
            conditionError = conditionResult.status
        )
        validationPassed.value = nameResult.status && descriptionResult.status && priceResult.status && subjectResult.status && categoryResult.status && conditionResult.status
    }
}