package com.example.myapplication.data

import com.example.myapplication.models.Inventory

interface IInventoryRepository {
    fun getInventory(): MutableList<Inventory>
    fun getInventoryOfUser(UserID : Integer): MutableList<Inventory>
}