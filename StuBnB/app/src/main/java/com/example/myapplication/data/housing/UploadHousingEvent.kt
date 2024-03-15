package com.example.myapplication.data.housing

import com.example.myapplication.models.Housing

sealed class UploadHousingEvent {
    data class NameChange(val name: String): UploadHousingEvent()
    data class DescriptionChange(val description: String): UploadHousingEvent()
    data class PriceChange(val price: String): UploadHousingEvent()
    data class StartDateChange(val startDate: String): UploadHousingEvent()
    data class EndDateChange(val endDate: String): UploadHousingEvent()
    data class AddressChange(val address: String): UploadHousingEvent()
    data class PropertyTypeChange(val propertyType: String): UploadHousingEvent()
    data class GenderRestrictionChange(val genderRestriction: String): UploadHousingEvent()
    data class NumOfGuestsChange(val numOfGuests: String): UploadHousingEvent()
    data class NumOfBedroomsChange(val numOfBedrooms: String): UploadHousingEvent()
    data class NumOfBathroomsChange(val numOfBathrooms: String): UploadHousingEvent()
    data class FavouriteChange(val house: Housing) : UploadHousingEvent()
    object ButtonClicked: UploadHousingEvent()
}