package com.example.myapplication.data.housing

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.validation.Validator
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch


class UploadHousingViewModel : ViewModel() {
    var uploadState = mutableStateOf(UploadHousingState())
    var validationPassed = mutableStateOf(true)
    var showAlert = mutableStateOf(false)
    var uploadProgress = mutableStateOf(false)

    fun onEvent(event: UploadHousingEvent) {
        when(event) {
            is UploadHousingEvent.NameChange -> {
                uploadState.value = uploadState.value.copy(
                    name = event.name
                )
                validateData()
            }
            is UploadHousingEvent.DescriptionChange -> {
                uploadState.value = uploadState.value.copy(
                    description = event.description
                )
                validateData()
            }
            is UploadHousingEvent.PriceChange -> {
                uploadState.value = uploadState.value.copy(
                    price = event.price
                )
                validateData()
            }
            is UploadHousingEvent.StartDateChange -> {
                uploadState.value = uploadState.value.copy(
                    startDate = event.startDate
                )
                validateData()
            }
            is UploadHousingEvent.EndDateChange -> {
                uploadState.value = uploadState.value.copy(
                    endDate = event.endDate
                )
                validateData()
            }
            is UploadHousingEvent.AddressChange -> {
                uploadState.value = uploadState.value.copy(
                    address = event.address
                )
                validateData()
            }
            is UploadHousingEvent.PropertyTypeChange -> {
                uploadState.value = uploadState.value.copy(
                    propertyType = event.propertyType
                )
                validateData()
            }
            is UploadHousingEvent.GenderRestrictionChange -> {
                uploadState.value = uploadState.value.copy(
                    genderRestriction = event.genderRestriction
                )
                validateData()
            }
            is UploadHousingEvent.NumOfGuestsChange -> {
                uploadState.value = uploadState.value.copy(
                    numOfGuests = event.numOfGuests
                )
                validateData()
            }
            is UploadHousingEvent.NumOfBedroomsChange -> {
                uploadState.value = uploadState.value.copy(
                    numOfBedrooms = event.numOfBedrooms
                )
                validateData()
            }
            is UploadHousingEvent.NumOfBathroomsChange -> {
                uploadState.value = uploadState.value.copy(
                    numOfBathrooms = event.numOfBathrooms
                )
                validateData()
            }
            is UploadHousingEvent.ButtonClicked -> {
                validateData()
                uploadListing()
            }
        }
    }

    private fun uploadListing() {
        if (uploadState.value.imageLinks.isNullOrEmpty()) {
            showAlert.value = true
        } else {
            uploadImagesToStorage(uploadState.value.imageLinks.map(Uri::parse))
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

    private fun uploadImagesToStorage(imageUriList: List<Uri>) {
        uploadProgress.value = true
        val storageReference = FirebaseStorage.getInstance().reference
        val myUrlList = mutableListOf<String>()
        val expectedSize = imageUriList.size
        for (i in imageUriList.indices) {
            val fileReference = storageReference.child("${System.currentTimeMillis()}_${(imageUriList[i]).lastPathSegment}")
            val uploadTask = fileReference.putFile(imageUriList[i])
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: Exception("Unknown error")
                }
                fileReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fileReference.downloadUrl.addOnSuccessListener { uri ->
                        val myUrl = uri.toString()
                        Log.d(TAG, myUrl)
                        myUrlList.add(myUrl)
                        if (myUrlList.size == expectedSize) {
                            Log.d(TAG, myUrlList.toString())
                            uploadState.value = uploadState.value.copy(
                                imageLinks = myUrlList
                            )
                            val invRep = HousingRepository()
                            val housingTemp = Housing(
                                uploadState.value.name,
                                uploadState.value.description,
                                uploadState.value.imageLinks,
                                uploadState.value.email,
                                uploadState.value.price.toDouble(),
                                uploadState.value.startDate,
                                uploadState.value.endDate,
                                uploadState.value.address,
                                uploadState.value.propertyType,
                                uploadState.value.genderRestriction,
                                uploadState.value.numOfGuests.toInt(),
                                uploadState.value.numOfBedrooms.toInt(),
                                uploadState.value.numOfBathrooms.toInt()
                            )

                            invRep.createHousing(housingTemp)
                            uploadProgress.value = false
                            Navigator.navigate(Screen.HomeProfile)
                        }
                    }
                } else {
                    val exception = task.exception ?: Exception("Unknown error")
                    Log.e("UploadImages", "Error uploading image: $exception")
                }
            }
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

        val startDateResult = Validator.validateName(
            name = uploadState.value.startDate
        )

        val endDateResult = Validator.validateName(
            name = uploadState.value.endDate
        )

        val addressResult = Validator.validateName(
            name = uploadState.value.address
        )

        val propertyTypeResult = Validator.validateName(
            name = uploadState.value.propertyType
        )

        val genderRestrictionResult = Validator.validateName(
            name = uploadState.value.genderRestriction
        )

        val numOfGuestsResult = Validator.validateName(
            name = uploadState.value.numOfGuests
        )

        val numOfBedroomsResult = Validator.validateName(
            name = uploadState.value.numOfBedrooms
        )

        val numOfBathroomsResult = Validator.validateName(
            name = uploadState.value.numOfBathrooms
        )



        uploadState.value = uploadState.value.copy(
            nameError = nameResult.status,
            descriptionError = descriptionResult.status,
            priceError = priceResult.status,
            startDateError = startDateResult.status,
            endDateError = endDateResult.status,
            addressError = addressResult.status,
            propertyTypeError = propertyTypeResult.status,
            genderRestrictionError = genderRestrictionResult.status,
            numOfGuestsError = numOfGuestsResult.status,
            numOfBedroomsError = numOfBedroomsResult.status,
            numOfBathroomsError = numOfBathroomsResult.status
        )
        validationPassed.value = nameResult.status && descriptionResult.status && priceResult.status && startDateResult.status && endDateResult.status && addressResult.status && propertyTypeResult.status && genderRestrictionResult.status && numOfGuestsResult.status && numOfBedroomsResult.status && numOfBathroomsResult.status
    }
}