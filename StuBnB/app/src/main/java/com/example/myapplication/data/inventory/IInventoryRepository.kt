package com.example.myapplication.data.inventory

import com.example.myapplication.models.Inventory

interface IInventoryRepository {
    fun getInventory(callback: InventoryCallback)
    fun getInventoryOfUser(callback: InventoryCallback, userID : String)
    fun createInventory(newInventoryItem : Inventory)
}