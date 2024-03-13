package com.example.myapplication.screens

import androidx.compose.runtime.Composable
import com.example.myapplication.models.WishList

@Composable
fun WishListScreen() {
    HousingList(WishList.housings)
    InventoryList(WishList.inventories)
}
