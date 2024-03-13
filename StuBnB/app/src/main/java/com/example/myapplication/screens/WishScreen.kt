package com.example.myapplication.screens

import androidx.compose.runtime.Composable
import com.example.myapplication.models.WishList

@Composable
fun WishList(wish: WishList) {
    HousingList(wish.housings)
    InventoryList(wish.inventories)
}
