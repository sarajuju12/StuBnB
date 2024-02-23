package com.example.myapplication.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.validation.Validator
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import kotlinx.coroutines.launch

class UploadInventoryViewModel: ViewModel() {
    var uploadState = mutableStateOf(UploadInventoryState())
    var validationPassed = mutableStateOf(true)
    var showAlert = mutableStateOf(false)

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

    private fun uploadListing() {
        if (uploadState.value.imageLinks.isNullOrEmpty()) {
            showAlert.value = true
        } else {
            val invRep = InventoryRepository()
            val inventoryTemp = Inventory(
                uploadState.value.name,
                uploadState.value.email,
                uploadState.value.description,
                uploadState.value.imageLinks,
                uploadState.value.price.toDouble(),
                uploadState.value.subject,
                uploadState.value.category,
                uploadState.value.condition
            )
            invRep.createInventory(inventoryTemp)
            Navigator.navigate(Screen.Profile)
        }
    }

    fun setEmail(email: String) {
        uploadState.value = uploadState.value.copy(
            email = email
        )
    }

    fun updateImageLinks(newImageLinks: MutableList<String>) {
        val updatedImageLinks = uploadState.value.imageLinks.toMutableList()
        viewModelScope.launch {
            updatedImageLinks += newImageLinks
            uploadState.value = uploadState.value.copy(
                imageLinks = updatedImageLinks.distinct()
            )
        }
    }

    fun onImageRemove(index: Int) {
        val updatedImageLinks = uploadState.value.imageLinks.toMutableList()
        viewModelScope.launch {
            updatedImageLinks.removeAt(index)
            uploadState.value = uploadState.value.copy(
                imageLinks = updatedImageLinks.distinct()
            )
        }
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