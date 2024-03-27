package com.example.myapplication.data.wishList

import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory

data class UploadWishListState (
    var house: Housing = Housing(),
    var inventory: Inventory = Inventory()
)
