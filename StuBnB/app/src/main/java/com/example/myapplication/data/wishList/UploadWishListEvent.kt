package com.example.myapplication.data.wishList

import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory

sealed class UploadWishListEvent {
    data class updateWishlistInventory(val inventory: Inventory): UploadWishListEvent()
    data class updateWishlistHousing(val house: Housing): UploadWishListEvent()
}
