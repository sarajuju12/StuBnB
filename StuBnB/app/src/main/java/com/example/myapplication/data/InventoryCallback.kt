package com.example.myapplication.data

import com.example.myapplication.models.Inventory

interface InventoryCallback {
    fun onInventoryLoaded(inventoryList: MutableList<Inventory>)
}