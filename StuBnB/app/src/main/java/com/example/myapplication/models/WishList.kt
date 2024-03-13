package com.example.myapplication.models

data class WishList(
    val inventories: MutableList<Inventory> = mutableListOf(),
    val housings: MutableList<Housing> = mutableListOf()
) {
    fun addInventory(item: Inventory) {
        inventories.add(item)
    }

    fun addHousing(item: Housing) {
        housings.add(item)
    }

    constructor(initialInventories: List<Inventory>, initialHousings: List<Housing>, dummy: Boolean) : this(
        inventories = mutableListOf<Inventory>().apply { addAll(initialInventories) },
        housings = mutableListOf<Housing>().apply { addAll(initialHousings) }
    )
}
