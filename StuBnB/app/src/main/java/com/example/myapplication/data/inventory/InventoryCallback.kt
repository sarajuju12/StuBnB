package com.example.myapplication.data.inventory

import com.example.myapplication.models.Inventory

interface InventoryCallback {
    fun onInventoryLoaded(inventoryList: MutableList<Inventory>)
}