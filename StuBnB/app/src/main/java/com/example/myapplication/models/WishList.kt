package com.example.myapplication.models

object WishList {
    val inventories: MutableList<Inventory> = mutableListOf()
    val housings: MutableList<Housing> = mutableListOf()

    fun clearAll(){
        inventories.clear()
        housings.clear()
    }

    fun includeInventory(item: Inventory): Boolean {
        return inventories.contains((item))
    }

    fun includeHousing(item: Housing): Boolean {
        return housings.contains((item))
    }

    fun addInventory(item: Inventory) { // this is a set
        if (!inventories.contains(item)) {
            inventories.add(item)
        }
    }

    fun addHousing(item: Housing) {
        if (!housings.contains(item)) { // this is a set
            housings.add(item)
        }
    }

    fun deleteInventory(item: Inventory) {
        inventories.remove(item)
    }

    fun deleteHousing(item: Housing) {
        housings.remove(item)
    }
}
