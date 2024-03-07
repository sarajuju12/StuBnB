package com.example.myapplication.data.repositories

import com.example.myapplication.models.Inventory

interface InventoryCallback {
    fun onInventoryLoaded(inventoryList: MutableList<Inventory>)
}