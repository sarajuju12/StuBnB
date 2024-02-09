package com.example.myapplication.data

import com.example.myapplication.models.Inventory

class InventoryRepository : IInventoryRepository {

    val temporaryTestingInventoryList: MutableList<Inventory> = mutableListOf()

    init {
        // Populating the temporaryTestingInventoryList
        temporaryTestingInventoryList.add(
            Inventory(
                name = "Textbook",
                description = "Description for Textbook",
                imageLinks = mutableListOf("link1a", "link1b")
            )
        )

        temporaryTestingInventoryList.add(
            Inventory(
                name = "Table",
                description = "Description for Table",
                imageLinks = mutableListOf("link2a", "link2b", "link2c")
            )
        )
    }

    override fun getInventory() : MutableList<Inventory>{
        return temporaryTestingInventoryList;
    }

    override fun getInventoryOfUser(UserID : Integer): MutableList<Inventory>{
        return temporaryTestingInventoryList;
    }

}